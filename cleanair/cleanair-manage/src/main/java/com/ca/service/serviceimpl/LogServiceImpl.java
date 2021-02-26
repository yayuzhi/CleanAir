package com.ca.service.serviceimpl;

import com.ca.annotation.RequiredLog;
import com.ca.mapper.LogMapper;
import com.ca.pojo.Log;
import com.ca.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public List<Log> findLogByPage(int page, int limit) {
        List<Log> logs = logMapper.findAllPage(page, limit);
        return logs;
    }

    @Override
    public Integer count() {
        return logMapper.count();
    }

    @Override
    public Integer countbyname(String username) {
        return logMapper.countbyname(username);
    }

    @Override
    public void savelog(Log log) {
        logMapper.insert(log);
    }

    //    @RequiredLog("日志删除")
    @RequiresPermissions("qk:log:delete")
    @Override
    public void deleteLogById(Integer id) {
        logMapper.deleteById(id);
    }

    @Override
    public List<Log> findLogByName(String username, int page, int limit) {
        List<Log> logs = logMapper.findLogByName(username,page,limit);
        return logs;
    }
}
