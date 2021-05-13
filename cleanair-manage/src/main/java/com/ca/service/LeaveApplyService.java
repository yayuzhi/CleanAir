package com.ca.service;

import com.ca.pojo.LeaveApply;
import com.ca.vo.RunApplyTable;

import java.util.List;
import java.util.Map;

public interface LeaveApplyService {

    //分页查询我正在请假的信息
    List<LeaveApply> findAllPage(int page, int limit, String username);
    int count(String username);

    //开始请假流程
    void startLeaveApply(LeaveApply leaveApply, String username, Map<String,Object> variables);



    LeaveApply findApplyById(int id);


    void updateLeaveApply(LeaveApply leaveApply);

    void cancelApply(String processInstanceId,int id);


    Map findhApplyById(int page,int limit,String username);
}
