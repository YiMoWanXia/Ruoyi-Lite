package com.ruoyi.lite.core.module.system.domain;

import com.ruoyi.lite.core.base.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 岗位表 sys_post
 *
 * @author fooyao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPost extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 岗位序号 */
    private Long postId;

    /** 岗位编码 */
    private String postCode;

    /** 岗位名称 */
    private String postName;

    /** 岗位排序 */
    private Integer postSort;

    /** 状态（0正常 1停用） */
    private String status;

    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;
}
