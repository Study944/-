package com.share.device.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.common.core.exception.ServiceException;
import com.share.common.security.utils.SecurityUtils;
import com.share.device.domain.Cabinet;
import com.share.device.domain.PowerBank;
import com.share.device.mapper.CabinetMapper;
import com.share.device.mapper.PowerBankMapper;
import com.share.device.service.ICabinetService;
import com.share.device.service.IPowerBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerBankServiceImpl extends ServiceImpl<PowerBankMapper, PowerBank> implements IPowerBankService {

    private final PowerBankMapper powerBankMapper;

    @Override
    public List<PowerBank> selectPowerBankList(PowerBank powerBank) {
        List<PowerBank>  list = powerBankMapper.selectPowerBankList(powerBank);
        return list;
    }

    @Override
    public int addPowerBank(PowerBank powerBank) {
        //1.判断是否有该编号的充电宝
        long count = count(new LambdaQueryWrapper<PowerBank>()
                .eq(PowerBank::getPowerBankNo, powerBank.getPowerBankNo()));
        if (count > 0){
            throw new ServiceException("该充电宝编号已存在");
        }
        //2.新增充电宝
        //2.1设置创建人和创建时间
        powerBank.setCreateBy(SecurityUtils.getUsername());
        powerBank.setCreateTime(new Date());
        int row = powerBankMapper.insert(powerBank);
        return row;
    }

}