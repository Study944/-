package com.share.device.controller;

import com.share.common.core.web.domain.AjaxResult;
import com.share.device.domain.CabinetType;
import com.share.device.service.ICabinetTypeService;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.page.TableDataInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag(name = "柜机类型接口管理")
@RestController
@RequestMapping("/cabinetType")
public class CabinetTypeController extends BaseController {
    @Autowired
    private ICabinetTypeService  cabinetTypeService;

    /**
     * 增加柜机类型
     */
    @Operation(summary = "增加柜机类型")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody CabinetType cabinetType){
        boolean isSuccess = cabinetTypeService.save(cabinetType);
        //使用toAjax方法，boolean-->AjaxResult
        AjaxResult ajaxResult = toAjax(isSuccess);
        return ajaxResult;
    }

    /**
     * 删除柜机类型
     */
    @Operation(summary = "删除柜机类型")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Long id){
        boolean isSuccess = cabinetTypeService.removeById(id);
        return toAjax(isSuccess);
    }
    /**
     * 修改柜机类型
     */
    @Operation(summary = "修改柜机类型")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody CabinetType cabinetType){
        boolean isSuccess = cabinetTypeService.updateById(cabinetType);
        return toAjax(isSuccess);
    }

    /**
     * 查询柜机类型列表
     */
    @Operation(summary = "查询柜机类型列表")
    @GetMapping("/list")
    public TableDataInfo list(CabinetType cabinetType)
    {
        startPage();
        List<CabinetType> list = cabinetTypeService.selectCabinetTypeList(cabinetType);
        return getDataTable(list);
    }
    /**
     * 根据ids查询柜机类型
     */
    @Operation(summary = "根据ids查询柜机类型")
    @GetMapping("/ids")
    public AjaxResult selectByIds(@RequestParam("ids") Long[] ids){
        List<CabinetType> list = cabinetTypeService.listByIds(Arrays.asList(ids));
        return AjaxResult.success(list);
    }

}
