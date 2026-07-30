package com.ruoyi.lite.core.base.framework.security.service;

import com.ruoyi.lite.core.base.common.constant.CacheConstants;
import com.ruoyi.lite.core.base.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.lite.core.base.common.exception.user.UserPasswordRetryLimitExceedException;
import com.ruoyi.lite.core.base.common.utils.SecurityUtils;
import com.ruoyi.lite.core.base.framework.redis.RedisCache;
import com.ruoyi.lite.core.base.framework.security.context.AuthenticationContextHolder;
import com.ruoyi.lite.core.module.system.domain.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 *
 * @author fooyao
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SysPasswordService {

    private final RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            retryCount = retryCount + 1;
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisCache.hasKey(getCacheKey(loginName))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
