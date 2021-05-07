package com.ca.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ca.mapper.AdminMapper;
import com.ca.mapper.LeaveApplyMapper;
import com.ca.pojo.LeaveApply;
import com.ca.service.LeaveApplyService;
import com.ca.util.ShiroUtils;
import com.ca.vo.RunApplyTable;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveApplyServiceImpl implements LeaveApplyService {

    @Autowired
    private LeaveApplyMapper leaveApplyMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    //这里暂时写死 这个key代表的是请假流程的PROCESS_DEFINE_KEY
    private static final String PROCESS_DEFINE_KEY = "vacationProcess";


    @Override
    public List<LeaveApply> findAllPage(int page, int limit, String username) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> processInstanceList = processInstanceQuery.processDefinitionKey(PROCESS_DEFINE_KEY).involvedUser(username).listPage(page, limit);
        List<LeaveApply> leaveApplyList = new ArrayList<>();
        for (ProcessInstance p : processInstanceList) {
            String processInstanceid = p.getProcessInstanceId();
            QueryWrapper<LeaveApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Process_instance_id", processInstanceid);
            LeaveApply leaveApply = leaveApplyMapper.selectOne(queryWrapper);
            if (adminMapper.selectById(leaveApply.getUserId()).getName().equals(username)) {
                leaveApplyList.add(leaveApply);
            } else {
                continue;
            }
        }
        return leaveApplyList;
    }

    @Override
    public int count() {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        int count = (int) processInstanceQuery.count();
        return count;
    }

    @Override
    public void startLeaveApply(LeaveApply leaveApply, String username, Map<String, Object> variables) {
        //给leaveApply赋值
        Integer userid = adminMapper.selectByName(username).getId();
        leaveApply.setUserId(userid);
        leaveApplyMapper.insert(leaveApply);
        String businesskey = String.valueOf(leaveApply.getId());
        identityService.setAuthenticatedUserId(username);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINE_KEY, businesskey, variables);
        String processInstanceid = processInstance.getId();
        leaveApply.setProcessInstanceId(processInstanceid);
        leaveApplyMapper.updateById(leaveApply);
    }

    @Override
    public LeaveApply findApplyById(int id) {


        return leaveApplyMapper.selectById(id);
    }
}
