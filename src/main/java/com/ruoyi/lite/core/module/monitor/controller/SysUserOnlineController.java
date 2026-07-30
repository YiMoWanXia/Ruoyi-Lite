package com.ruoyi.lite.core.module.monitor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.lite.core.base.common.constant.CacheConstants;
import com.ruoyi.lite.core.base.common.utils.StringUtils;
import com.ruoyi.lite.core.base.framework.aspectj.lang.annotation.Log;
import com.ruoyi.lite.core.base.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.lite.core.base.framework.redis.RedisCache;
import com.ruoyi.lite.core.base.framework.security.LoginUser;
import com.ruoyi.lite.core.base.framework.web.controller.BaseController;
import com.ruoyi.lite.core.base.framework.web.domain.AjaxResult;
import com.ruoyi.lite.core.base.framework.web.page.TableDataInfo;
import com.ruoyi.lite.core.module.monitor.domain.SysUserOnline;
import com.ruoyi.lite.core.module.system.service.ISysUserOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author fooyao
 */
@RestController
@RequestMapping("/monitor/online")
@RequiredArgsConstructor
public class SysUserOnlineController extends BaseController {

    private final ISysUserOnlineService userOnlineService;

    private final RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        Page<SysUserOnline> page = new Page<>();
        Page<SysUserOnline> sysUserOnlinePage = page.setRecords(userOnlineList);
        return getDataTable(sysUserOnlinePage);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return success();
    }
}
