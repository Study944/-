package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.security.utils.SecurityUtils;
import com.share.device.domain.PowerBank;
import com.share.device.service.IPowerBankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Tag(name = "充电宝接口管理")
@RestController
@RequestMapping("/powerBank")
public class PowerBankController extends BaseController {

    @Autowired
    private IPowerBankService powerBankService;

    /**
     * 分页查询
     */
    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public AjaxResult list(PowerBank powerBank) {
        startPage();
        List<PowerBank> list =powerBankService.selectPowerBankList(powerBank);
        return AjaxResult.success(list);
    }
    /**
     * 获取充电宝详细信息
     */
    @Operation(summary = "获取充电宝详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(powerBankService.getById(id));
    }
    /**
     * 增加充电宝
     */
    @Operation(summary = "增加充电宝")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PowerBank powerBank) {
        int row = powerBankService.addPowerBank(powerBank);
        return toAjax(row);
    }
    /**
     * 修改充电宝
     */
    @Operation(summary = "修改充电宝")
    @PutMapping
    public AjaxResult edit(@RequestBody PowerBank powerBank)
    {
        powerBank.setUpdateBy(SecurityUtils.getUsername());
        powerBank.setUpdateTime(new Date());
        return toAjax(powerBankService.updateById(powerBank));
    }
    /**
     * 删除充电宝
     */
    @Operation(summary = "删除充电宝")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(powerBankService.removeBatchByIds(Arrays.asList(ids)));
    }
}
