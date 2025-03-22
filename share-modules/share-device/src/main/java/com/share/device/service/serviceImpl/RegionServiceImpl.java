package com.share.device.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.Cabinet;
import com.share.device.domain.Region;
import com.share.device.mapper.CabinetMapper;
import com.share.device.mapper.RegionMapper;
import com.share.device.service.ICabinetService;
import com.share.device.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Override
    public List<Region> treeSelect(String code) {
        //1.根据当前地区的code=其他地区的parentCode查询出子地区
        LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Region::getParentCode, code);
        List<Region> regionList = baseMapper.selectList(queryWrapper);
        //2.添加判断，Region表中存在hasChildren字段，判断当前地区下是否存在子地区，如果存在，则hasChildren=true，否则为false
        if (!regionList.isEmpty()) {
            regionList.forEach(region -> {
                LambdaQueryWrapper<Region> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(Region::getParentCode, region.getCode());
                //查询子地区数量
                Long count = baseMapper.selectCount(queryWrapper1);
                if (count > 0) {
                    region.setHasChildren(true);
                } else {
                    region.setHasChildren(false);
                }
            });
        }
        return regionList;
    }

    @Override
    public String selectNameByCode(String code) {
        Region region = baseMapper.selectOne(new LambdaQueryWrapper<Region>().eq(Region::getCode, code));
        return region.getName();
    }
}