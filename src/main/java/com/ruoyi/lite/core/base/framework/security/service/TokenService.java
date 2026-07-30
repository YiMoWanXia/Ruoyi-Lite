package com.ruoyi.lite.core.base.framework.security.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.lite.core.base.common.constant.CacheConstants;
import com.ruoyi.lite.core.base.common.utils.ServletUtils;
import com.ruoyi.lite.core.base.common.utils.ip.AddressUtils;
import com.ruoyi.lite.core.base.common.utils.ip.IpUtils;
import com.ruoyi.lite.core.base.framework.redis.RedisCache;
import com.ruoyi.lite.core.base.framework.security.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    // 令牌自定义标识
    @Value("${project.token.header}")
    private String header;

    // 令牌秘钥
    @Value("${project.token.secret}")
    private String secret;

    // 令牌有效期（默认1天）
    @Value("${project.token.expireTime}")
    private int expireTime;

    private final RedisCache redisCache;

    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    private static final long MILLIS_MINUTE_TEN = 10 * 60 * 1000L;

    public static final String LOGIN_USER_KEY = "tokenId";
    public static final String JWT_USERNAME = "name";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            try {
                JSONObject claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = claims.getStr(LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                return redisCache.getCacheObject(userKey);
            } catch (Exception e) {
                log.error("获取用户信息异常'{}'", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (loginUser != null && StrUtil.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StrUtil.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = UUID.fastUUID().toString();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, token);
        claims.put(JWT_USERNAME, loginUser.getUsername());
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 登录信息
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + (long) expireTime * 60 * 60 * 1000);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.HOURS);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        HttpServletRequest request = ServletUtils.getRequest();
        String userAgent = request.getHeader("User-Agent");
        String ip = IpUtils.getIpAddr(request);
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        try {
            UserAgent ua = UserAgentUtil.parse(userAgent);
            loginUser.setBrowser(ua.getBrowser().getName() + ua.getBrowser().getVersion(userAgent));
            loginUser.setOs(ua.getOs().getName() + ua.getOs().getVersion(userAgent));
        } catch (Exception e) {
            log.warn("获取用户ua异常，{}， user：{}", userAgent, JSON.toJSONString(loginUser));
        }
    }

    /**
     * 从数据声明生成令牌
     *
     * @param payloads 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> payloads) {
        JWTSigner signer = JWTSignerUtil.createSigner(HmacAlgorithm.HmacSHA512.getValue(), secret.getBytes(StandardCharsets.UTF_8));
        return JWTUtil.createToken(payloads, signer);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private JSONObject parseToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayloads();
    }

    /**
     * 获取请求token
     *
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StrUtil.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

}
