package com.share.device.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.Cabinet;
import com.share.device.domain.Station;
import com.share.device.domain.StationLocation;
import com.share.device.mapper.CabinetMapper;
import com.share.device.mapper.StationMapper;
import com.share.device.repository.StationLocationRepository;
import com.share.device.service.ICabinetService;
import com.share.device.service.IRegionService;
import com.share.device.service.IStationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements IStationService {

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private ICabinetService cabinetService;

    @Autowired
    private IRegionService regionService;

    @Autowired
    private StationLocationRepository stationLocationRepository;

    @Override
    public List<Station> selectStationList(Station station) {
        List<Station> stationList = stationMapper.selectStationList(station);
        //根据柜机id查询柜机编号
        stationList.forEach(station1 -> {
            if (station1.getCabinetId() != null){
                //查询柜机编号
                Cabinet cabinet = cabinetService.getById(station1.getCabinetId());
                //写入
                station1.setCabinetNo(cabinet.getCabinetNo());
            }
        });
        return stationList;
    }
    //新增站点
    @Override
    public int saveStation(Station station) {
        station.setFullAddress(getNameByCode(station));
        int row = baseMapper.insert(station);
        //对新增站点的具体经纬度信息添加到mongodb中，方便查询
        StationLocation stationLocation = new StationLocation();
        stationLocation.setId(ObjectId.get().toString());
        stationLocation.setStationId(station.getId());
        stationLocation.setLocation(new GeoJsonPoint(station.getLongitude().doubleValue(),
                station.getLatitude().doubleValue()));
        stationLocation.setCreateTime(new Date());
        stationLocationRepository.save(stationLocation);

        return row;
    }
    //修改站点
    @Override
    public int updateStation(Station station) {
        station.setFullAddress(getNameByCode(station));
        int row = baseMapper.updateById(station);
        //根据站点id查询对应的mongodb中的经纬度信息用于修改
        StationLocation stationLocation = stationLocationRepository.getByStationId(station.getId());
        stationLocation.setLocation(new GeoJsonPoint(station.getLongitude().doubleValue(),
                station.getLatitude().doubleValue()));
        stationLocationRepository.save(stationLocation);
        return row;
    }
    //删除站点，需要删除mysql中的数据和mongodb中的数据，所以需要使用事务控制
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(Collection<?> list) {
        //删除mongodb中的数据
        for (Object id : list) {
            stationLocationRepository.deleteByStationId(Long.parseLong(id.toString()));
        }
        //删除mysql中的数据
        return super.removeByIds(list);
    }

    //根据Station的Code查询站点具体地址
    private String getNameByCode(Station station){
        String province = regionService.selectNameByCode(station.getProvinceCode());
        String city = regionService.selectNameByCode(station.getCityCode());
        String district = regionService.selectNameByCode(station.getDistrictCode());
        return province+city+district+station.getAddress();
    }
}