package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.web.page.TableDataInfo;
import com.share.device.domain.Cabinet;
import com.share.device.service.ICabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.share.common.core.utils.PageUtils.startPage;

@Tag(name = "充电宝柜机详细信息")
@RestController
@RequestMapping("/cabinetT")
public class CabinetController extends BaseController {

    @Autowired
    private ICabinetService cabinetService;

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public TableDataInfo list(Cabinet cabinet){
        startPage();
        List<Cabinet> list = cabinetService.selectCabinetList(cabinet);
        return getDataTable(list);
    }

    /**
     * 新增充电宝柜机
     */
    @Operation(summary = "新增充电宝柜机")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Cabinet cabinet){
        boolean isSuccess = cabinetService.save(cabinet);
        return toAjax(isSuccess);
    }
    /**
     * 修改充电宝柜机
     */
    @Operation(summary = "修改充电宝柜机")
    @PutMapping
    public AjaxResult edit(@RequestBody Cabinet cabinet)
    {
        return toAjax(cabinetService.updateById(cabinet));
    }

    /**
     * 删除充电宝柜机
     */
    @Operation(summary = "删除充电宝柜机")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cabinetService.removeBatchByIds(Arrays.asList(ids)));
    }
    /**
     * 获取充电宝
     */
    @Operation(summary = "获取充电宝柜机详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cabinetService.getById(id));
    }
    /**
     * 搜索未使用柜机
     */
    @Operation(summary = "搜索未使用柜机")
    @GetMapping(value = "/searchNoUseList/{keyword}")
    public AjaxResult searchNoUseList(@PathVariable String keyword)
    {
        return success(cabinetService.searchNoUseList(keyword));
    }
    /**
     * 获取充电宝
     */
    @Operation(summary = "获取充电宝柜机全部详细信息")
    @GetMapping(value = "/getAllInfo/{id}")
    public AjaxResult getAllInfo(@PathVariable("id") Long id)
    {
        return success(cabinetService.getAllInfo(id));
    }
}
