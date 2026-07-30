package com.ruoyi.lite.core.module.monitor.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.lite.core.module.monitor.domain.SysLoginLog;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author fooyao
 */
public interface ISysLoginLogService {
    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
     void insertLoginLog(SysLoginLog loginLog);

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
     List<SysLoginLog> selectLoginLogList(SysLoginLog loginLog);

    /**
     * 批量删除系统登录日志
     *
     * @param logIds 需要删除的登录日志ID
     * @return 结果
     */
     int deleteLoginLogByIds(Long[] logIds);

    /**
     * 清空系统登录日志
     */
     void cleanLoginLog();

    Page<SysLoginLog> selectLoginLogPageList(SysLoginLog loginLog);
}
