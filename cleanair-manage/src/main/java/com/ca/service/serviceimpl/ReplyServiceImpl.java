package com.ca.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ca.mapper.AdminMapper;
import com.ca.mapper.LeaveApplyMapper;
import com.ca.pojo.LeaveApply;
import com.ca.service.ReplyService;
import com.ca.util.ShiroUtils;
import com.ca.vo.Reply;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplyServiceImpl implements ReplyService {
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

    @Autowired
    private HistoryService historyService;

    //这里暂时写死 这个key代表的是请假流程的PROCESS_DEFINE_KEY
    private static final String PROCESS_DEFINE_KEY = "vacationProcess";


    @RequiresPermissions("qk:activiti:view")
    @Override
    public List<Reply> getAllReply(String username, int page, int limit) {
        /**
         * 这是第一种方法 但是 这个是直接把所有在运行的请假流程全部取出来
         */
//        //获取流程引擎现在正在运行的流程
//        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
//        List<ProcessInstance> processInstanceList = processInstanceQuery.processDefinitionKey(PROCESS_DEFINE_KEY).listPage(page, limit);
//        //创建一个存储信息list
//        List<Reply> replyList = new ArrayList<>();
//        for (ProcessInstance p : processInstanceList) {
//            String processInstanceid = p.getProcessInstanceId();
//            QueryWrapper<LeaveApply> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("Process_instance_id", processInstanceid);
//            LeaveApply leaveApply = leaveApplyMapper.selectOne(queryWrapper);
//
//            //这里存入返回前端的数据
//            Reply reply = new Reply();
//            reply.setId(leaveApply.getId());
//            reply.setProcessInstanceId(processInstanceid);
//            reply.setUsername(adminMapper.selectById(leaveApply.getUserId()).getName());
//            reply.setLeaveType(leaveApply.getLeaveType());
//            //计算请假多长时间
//            Double day = Math.floor((leaveApply.getEnd().getTime() - leaveApply.getStart().getTime()) / 86400000);
//            String Duration = day.toString();
//
//            reply.setDuration(Duration);
//
//            replyList.add(reply);
//
//        }
//
//
//        return replyList;
        /**
         * 第二种方法
         */
        List<Reply> replyList = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("部门经理").listPage(page, limit);
        for (Task task : taskList) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            //这里的businesskey 就是leaveApply的主键id 在之前的启动流程的时候 是直接拿 leaveapply的主键当作businesskey启动的
            LeaveApply leaveApply = leaveApplyMapper.selectById(businessKey);

            //这里存入返回前端的数据
            Reply reply = new Reply();
            reply.setId(leaveApply.getId());
            reply.setProcessInstanceId(processInstanceId);
            reply.setUsername(adminMapper.selectById(leaveApply.getUserId()).getName());
            reply.setLeaveType(leaveApply.getLeaveType());
            //计算请假多长时间
            Double day = Math.floor((leaveApply.getEnd().getTime() - leaveApply.getStart().getTime()) / 86400000);
            String Duration = day.toString();
            reply.setDuration(Duration);
            replyList.add(reply);
        }
        return replyList;
    }


    @Override
    public int count() {
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("部门经理").list();
        return taskList.size();
    }

    @Override
    public Reply showReply(int id) {

        LeaveApply leaveApply = leaveApplyMapper.selectById(id);
        String username = adminMapper.selectById(leaveApply.getUserId()).getName();
        Reply reply = new Reply();
        reply.setLeaveType(leaveApply.getLeaveType());
        reply.setStart(leaveApply.getStart());
        reply.setEnd(leaveApply.getEnd());
        reply.setReason(leaveApply.getReason());
        reply.setUsername(username);
        return reply;
    }

    @Override
    public void returnReply(String processInstanceId, String username, boolean deptleaderapprove) {
        Map<String, Object> variables = new HashMap<>();
        //这个true 是同意
        variables.put("deptleaderapprove", deptleaderapprove);
        //给taskid 签名 设置assignee
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        //写下了处理人 和处理意见
        LeaveApply leaveApply = new LeaveApply();
        leaveApply.setResult(deptleaderapprove);

        QueryWrapper<LeaveApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_instance_id", processInstanceId);

        leaveApplyMapper.update(leaveApply, queryWrapper);

        taskService.claim(task.getId(), username);

        taskService.complete(task.getId(), variables);
    }

    @Override
    public Map getAllHReply(String username, int page, int limit) {
        Map map = new HashMap();
        List<Reply> replyList = new ArrayList<>();
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().taskAssignee(username).listPage(page, limit);
        int count = historicActivityInstanceList.size();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            String processInstanceId = historicActivityInstance.getProcessInstanceId();
            QueryWrapper<LeaveApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Process_instance_id", processInstanceId);
            LeaveApply leaveApply = leaveApplyMapper.selectOne(queryWrapper);

            //这里存入返回前端的数据
            Reply reply = new Reply();
            reply.setId(leaveApply.getId());
            reply.setProcessInstanceId(processInstanceId);
            reply.setUsername(adminMapper.selectById(leaveApply.getUserId()).getName());
            reply.setLeaveType(leaveApply.getLeaveType());
            reply.setResult(leaveApply.getResult());
            //计算请假多长时间
            Double day = Math.floor((leaveApply.getEnd().getTime() - leaveApply.getStart().getTime()) / 86400000);
            String Duration = day.toString();
            reply.setDuration(Duration);
            replyList.add(reply);
        }

        map.put("count", count);
        map.put("replyList", replyList);
        return map;
    }
}
