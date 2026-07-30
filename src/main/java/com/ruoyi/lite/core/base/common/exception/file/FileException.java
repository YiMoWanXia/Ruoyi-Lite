package com.ruoyi.lite.core.base.common.exception.file;


import com.ruoyi.lite.core.base.common.exception.base.BaseException;

import java.io.Serial;

/**
 * 文件信息异常类
 *
 * @author fooyao
 */
public class FileException extends BaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
