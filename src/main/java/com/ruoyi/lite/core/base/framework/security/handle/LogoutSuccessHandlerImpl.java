package com.ruoyi.lite.core.base.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.lite.core.base.common.constant.Constants;
import com.ruoyi.lite.core.base.common.utils.ServletUtils;
import com.ruoyi.lite.core.base.common.utils.StringUtils;
import com.ruoyi.lite.core.base.framework.manager.AsyncManager;
import com.ruoyi.lite.core.base.framework.manager.factory.AsyncFactory;
import com.ruoyi.lite.core.base.framework.security.LoginUser;
import com.ruoyi.lite.core.base.framework.security.service.TokenService;
import com.ruoyi.lite.core.base.framework.web.domain.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 自定义退出处理类 返回成功
 *
 * @author fooyao
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private TokenService tokenService;

    /**
     * 退出处理
     *
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLoginLog(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
