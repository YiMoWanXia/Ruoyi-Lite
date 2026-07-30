package com.ruoyi.lite.core.module.common;

import com.ruoyi.lite.core.base.framework.redis.RedisCache;
import com.ruoyi.lite.core.base.framework.web.domain.AjaxResult;
import com.ruoyi.lite.core.module.system.service.ISysConfigService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码操作处理
 *
 * @author fooyao
 */
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final RedisCache redisCache;

    private final ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) {
        return AjaxResult.success();
    }

}
