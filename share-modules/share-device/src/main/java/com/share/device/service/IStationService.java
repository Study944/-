package com.share.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.device.domain.Region;
import com.share.device.domain.Station;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface IStationService extends IService<Station>
{
    // 查询站点列表
    List<Station> selectStationList(Station station);
    // 新增站点
    int saveStation(Station station);
    // 修改站点
    int updateStation(Station station);
    // 删除站点
    boolean removeByIds(Collection<?> list);
}