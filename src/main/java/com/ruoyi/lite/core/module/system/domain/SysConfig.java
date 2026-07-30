package com.ruoyi.lite.core.module.system.domain;

import com.ruoyi.lite.core.base.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


/**
 * 参数配置表 sys_config
 *
 * @author fooyao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    private Long configId;

    /** 参数名称 */
    private String configName;

    /** 参数键名 */
    private String configKey;

    /** 参数键值 */
    private String configValue;

    /** 系统内置（Y是 N否） */
    private String configType;

}
