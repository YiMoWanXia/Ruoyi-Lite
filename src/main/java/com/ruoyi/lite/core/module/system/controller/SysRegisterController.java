package com.ruoyi.lite.core.module.system.controller;

import com.ruoyi.lite.core.base.common.utils.StringUtils;
import com.ruoyi.lite.core.base.framework.security.RegisterBody;
import com.ruoyi.lite.core.base.framework.security.service.SysRegisterService;
import com.ruoyi.lite.core.base.framework.web.controller.BaseController;
import com.ruoyi.lite.core.base.framework.web.domain.AjaxResult;
import com.ruoyi.lite.core.module.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author fooyao
 */
@RestController
@RequiredArgsConstructor
public class SysRegisterController extends BaseController {

    private final SysRegisterService registerService;

    private final ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
