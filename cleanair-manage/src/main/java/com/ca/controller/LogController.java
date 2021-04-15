package com.ca.controller;

import com.ca.pojo.Item;
import com.ca.pojo.Log;
import com.ca.service.LogService;

import com.ca.vo.JsonResult;
import com.ca.vo.LayUITbale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yayuzhi
 */
@RequestMapping("/log-list")
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 回显所有的日志信息
     * @param username
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/findLogByPage")
    public String findLogByPage(String username,int page, int limit){
      if (username == null){
          int page1 = (page - 1) * limit;
          List<Log> logs = logService.findLogByPage(page1, limit);
          int count = logService.count();
          return new LayUITbale().LayUIResponseByLog(count, logs);
      }else {
          int page1 = (page - 1) * limit;
          List<Log> logs = logService.findLogByName(username,page1, limit);
          int count = logService.countbyname(username);
          return new LayUITbale().LayUIResponseByLog(count, logs);
      }
    }

    /**
     * 删除日志信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteLogById")
    public JsonResult deleteLogById(Integer id){
        logService.deleteLogById(id);
        return JsonResult.success("delete ok");
    }

    /**
     * 批量删除日志信息
     * @param ids
     * @return
     */
    @RequestMapping("/deleteLogsById")
    public JsonResult deleteLogsById(Integer... ids){
        for (Integer id:ids){
            logService.deleteLogById(id);
        }
        return JsonResult.success("delete ok");
    }
}
