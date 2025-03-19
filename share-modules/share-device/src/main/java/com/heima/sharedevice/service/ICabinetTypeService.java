package com.heima.sharedevice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.sharedevice.domain.CabinetType;

import java.util.List;

public interface ICabinetTypeService extends IService<CabinetType> {
    /**
     * 查询柜机类型列表
     *
     * @param cabinetType 柜机类型
     * @return 柜机类型集合
     */
    public List<CabinetType> selectCabinetTypeList(CabinetType cabinetType);

}
