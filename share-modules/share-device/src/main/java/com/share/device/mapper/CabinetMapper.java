package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetType;

import java.util.List;

public interface CabinetMapper extends BaseMapper<Cabinet> {

    List<Cabinet> selectCabinetList(Cabinet cabinet);
}
