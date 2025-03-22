package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.web.page.TableDataInfo;
import com.share.device.domain.Station;
import com.share.device.service.IStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(name = "站点接口管理")
@RestController
@RequestMapping("/station")
public class StationController extends BaseController {

    @Autowired
    private IStationService stationService;

    /**
     * 查询站点列表
     */
    @Operation(summary = "查询站点列表")
    @GetMapping("/list")
    public TableDataInfo list(Station station)
    {
        startPage();
        List<Station> list = stationService.selectStationList(station);
        return getDataTable(list);
    }
    /**
     * 新增站点
     */
    @Operation(summary = "新增站点")
    @PostMapping
    public AjaxResult add(@RequestBody Station station)
    {
        return toAjax(stationService.saveStation(station));
    }
    /**
     * 修改站点
     */
    @Operation(summary = "修改站点")
    @PutMapping
    public AjaxResult edit(@RequestBody Station station)
    {
        return toAjax(stationService.updateStation(station));
    }
    /**
     * 根据id获取站点详细信息
     */
    @Operation(summary = "获取站点详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stationService.getById(id));
    }
    /**
     * 根据ids删除站点
     */
    @Operation(summary = "删除站点")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        //删除站点时，需要删除mongodb中的数据
        return toAjax(stationService.removeByIds(Arrays.asList(ids)));
    }
}
