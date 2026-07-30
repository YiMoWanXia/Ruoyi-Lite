package com.ruoyi.lite.core.base.framework.security.service;

import com.ruoyi.lite.core.base.common.enums.UserStatus;
import com.ruoyi.lite.core.base.common.exception.ServiceException;
import com.ruoyi.lite.core.base.common.utils.MessageUtils;
import com.ruoyi.lite.core.base.common.utils.StringUtils;
import com.ruoyi.lite.core.base.framework.security.LoginUser;
import com.ruoyi.lite.core.module.system.domain.SysUser;
import com.ruoyi.lite.core.module.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户验证处理
 *
 * @author fooyao
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ISysUserService userService;

    private final SysPasswordService passwordService;

    private final SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        passwordService.validate(user);
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        Set<String> menuPermission = permissionService.getMenuPermission(user);
        return new LoginUser(user.getUserId(), user.getDeptId(), user, menuPermission);
    }
}
