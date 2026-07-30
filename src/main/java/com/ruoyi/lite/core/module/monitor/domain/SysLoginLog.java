package com.ruoyi.lite.core.module.monitor.domain;

import com.ruoyi.lite.core.base.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统访问记录表 sys_loginLog
 *
 * @author fooyao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginLog extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long logId;

    /** 用户账号 */
    private String userName;

    /** 登录状态 0成功 1失败 */
    private String status;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 提示消息 */
    private String msg;
}