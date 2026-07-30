package com.ruoyi.lite.core.module.monitor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.lite.core.base.framework.aspectj.lang.annotation.Log;
import com.ruoyi.lite.core.base.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.lite.core.base.framework.security.service.SysPasswordService;
import com.ruoyi.lite.core.base.framework.web.controller.BaseController;
import com.ruoyi.lite.core.base.framework.web.domain.AjaxResult;
import com.ruoyi.lite.core.base.framework.web.page.TableDataInfo;
import com.ruoyi.lite.core.module.monitor.domain.SysLoginLog;
import com.ruoyi.lite.core.module.monitor.service.ISysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统访问记录
 *
 * @author fooyao
 */
@RestController
@RequestMapping("/monitor/loginLog")
@RequiredArgsConstructor
public class SysLoginLogController extends BaseController {

    private final ISysLoginLogService sysLoginLogService;

    private final SysPasswordService passwordService;

    @PreAuthorize("@ss.hasPermi('monitor:loginLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLoginLog loginLog) {
        Page<SysLoginLog> list = sysLoginLogService.selectLoginLogPageList(loginLog);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('monitor:loginLog:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(sysLoginLogService.deleteLoginLogByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:loginLog:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        sysLoginLogService.cleanLoginLog();
        return success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:loginLog:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    public AjaxResult unlock(@PathVariable String userName) {
        passwordService.clearLoginRecordCache(userName);
        return success();
    }
}
