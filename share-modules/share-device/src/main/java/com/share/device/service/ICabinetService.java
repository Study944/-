package com.share.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetType;

import java.util.List;
import java.util.Map;

public interface ICabinetService extends IService<Cabinet>
{
    //分页查询
    List<Cabinet> selectCabinetList(Cabinet cabinet);
    //搜索未使用柜机
    List<Cabinet> searchNoUseList(String keyword);
    //获取全部详细信息
    Map<String, Object> getAllInfo(Long id);
}