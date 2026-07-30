package com.ruoyi.lite.core.module.monitor.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.lite.core.base.common.utils.sql.PageHelper;
import com.ruoyi.lite.core.module.monitor.domain.SysLoginLog;
import com.ruoyi.lite.core.module.monitor.mapper.SysLoginLogMapper;
import com.ruoyi.lite.core.module.monitor.service.ISysLoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author fooyao
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysLoginLogServiceImpl implements ISysLoginLogService {

    private final SysLoginLogMapper loginLogMapper;

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginLog() {
        loginLogMapper.cleanLoginLog();
    }


    /**
     * 批量删除系统登录日志
     *
     * @param logIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLoginLogByIds(Long[] logIds) {
        return loginLogMapper.deleteLoginLogByIds(logIds);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginLog> selectLoginLogList(SysLoginLog loginLog) {
        return loginLogMapper.selectLoginLogList(loginLog);
    }

    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    @Override
    public void insertLoginLog(SysLoginLog loginLog) {
        String sub = loginLog.getMsg().substring(0, 250);
        loginLog.setMsg(sub);
        loginLogMapper.insertLoginLog(loginLog);
    }

    @Override
    public Page<SysLoginLog> selectLoginLogPageList(SysLoginLog loginLog) {
        return loginLogMapper.selectLoginLogPageList(loginLog, PageHelper.getPage(loginLog));
    }
}
