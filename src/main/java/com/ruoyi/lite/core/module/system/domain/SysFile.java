package com.ruoyi.lite.core.module.system.domain;


import com.ruoyi.lite.core.base.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件信息表 sys_file
 * 
 * @author fooyao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFile extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long id;
    
    /** 父级ID */
    private Long parentId;
    
    /** 是否为文件夹 */
    private Integer isFolder;

    /** 文件名称 */
    private String fileName;
    
    /** 原始文件名 */
    private String originalName;

    /** 文件路径 */
    private String filePath;
    
    /** 文件URL */
    private String fileUrl;

    /** 文件大小 */
    private Long fileSize;

    /** 文件类型 */
    private String fileType;

    /** 文件扩展名 */
    private String fileExt;
    
    /** 内容哈希值 */
    private String contentHash;
    
    /** MIME类型 */
    private String mimeType;
    
    /** 业务类型 */
    private String businessType;
    
    /** 业务ID */
    private Long businessId;

    /** 文件状态 */
    private String status;
    
    /** 处理进度 */
    private Integer processProgress;
    
    /** 下载次数 */
    private Integer downloadCount;
    
    /** 错误信息 */
    private String errorMessage;

    private String metadata;

}
