package com.ruoyi.lite.core.module.system.domain;

import com.ruoyi.lite.core.base.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


/**
 * 通知公告表 sys_notice
 *
 * @author fooyao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNotice extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long noticeId;

    /** 公告标题 */
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    private String noticeType;

    /** 公告内容 */
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    private String status;
}
