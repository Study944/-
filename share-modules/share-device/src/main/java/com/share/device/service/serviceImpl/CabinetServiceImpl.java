package com.share.device.service.serviceImpl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetSlot;
import com.share.device.domain.CabinetType;
import com.share.device.domain.PowerBank;
import com.share.device.mapper.CabinetMapper;
import com.share.device.mapper.CabinetSlotMapper;
import com.share.device.mapper.CabinetTypeMapper;
import com.share.device.service.ICabinetService;
import com.share.device.service.ICabinetTypeService;
import com.share.device.service.IPowerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CabinetServiceImpl extends ServiceImpl<CabinetMapper, Cabinet> implements ICabinetService {

    @Autowired
    private CabinetMapper cabinetMapper;

    @Autowired
    private IPowerBankService powerBankService;

    @Autowired
    private CabinetSlotMapper cabinetSlotMapper;

    /**
     * 分页查询
     */
    @Override
    public List<Cabinet> selectCabinetList(Cabinet cabinet) {
        List<Cabinet> list = cabinetMapper.selectCabinetList(cabinet);
        return list;
    }

    @Override
    public List<Cabinet> searchNoUseList(String keyword) {
        LambdaQueryWrapper<Cabinet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Cabinet::getCabinetNo,keyword);
        queryWrapper.eq(Cabinet::getStatus,"0");
        List<Cabinet> cabinetList = cabinetMapper.selectList(queryWrapper);
        return cabinetList;
    }

    /**
     * 根据柜机 ID 查询柜机信息。
     * 查询与该柜机相关的插槽信息。
     * 获取插槽中可用的充电宝 ID 列表，并查询对应的充电宝信息。
     * 将充电宝信息填充到插槽对象中。
     * 最终返回柜机信息和包含充电宝信息的插槽列表。
     */
    @Override
    public Map<String, Object> getAllInfo(Long id) {
        // 查询柜机信息
        Cabinet cabinet = this.getById(id);

        // 查询插槽信息
        List<CabinetSlot> cabinetSlotList = cabinetSlotMapper.selectList(new LambdaQueryWrapper<CabinetSlot>().eq(CabinetSlot::getCabinetId, cabinet.getId()));
        // 获取可用充电宝id列表
        List<Long> powerBankIdList = cabinetSlotList.stream().filter(item -> null != item.getPowerBankId())
                .map(CabinetSlot::getPowerBankId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(powerBankIdList)) {
            List<PowerBank> powerBankList = powerBankService.listByIds(powerBankIdList);
            Map<Long,PowerBank> powerBankIdToPowerBankMap = powerBankList.stream().collect(Collectors.toMap(PowerBank::getId, PowerBank -> PowerBank));
            cabinetSlotList.forEach(item -> item.setPowerBank(powerBankIdToPowerBankMap.get(item.getPowerBankId())));
        }

        Map<String, Object> result = Map.of("cabinet", cabinet, "cabinetSlotList", cabinetSlotList);
        return result;
    }
}