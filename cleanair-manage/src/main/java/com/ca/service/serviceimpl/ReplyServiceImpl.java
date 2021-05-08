package com.ca.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ca.mapper.AdminMapper;
import com.ca.mapper.LeaveApplyMapper;
import com.ca.pojo.LeaveApply;
import com.ca.service.ReplyService;
import com.ca.vo.Reply;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    //这里暂时写死 这个key代表的是请假流程的PROCESS_DEFINE_KEY
    private static final String PROCESS_DEFINE_KEY = "vacationProcess";


    @RequiresPermissions("qk:activiti:view")
    @Override
    public List<Reply> getAllReply(int page, int limit) {
        //获取流程引擎现在正在运行的流程
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> processInstanceList = processInstanceQuery.processDefinitionKey(PROCESS_DEFINE_KEY).listPage(page, limit);
        //创建一个存储信息list
        List<Reply> replyList = new ArrayList<>();
        for (ProcessInstance p : processInstanceList) {
            String processInstanceid = p.getProcessInstanceId();
            QueryWrapper<LeaveApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Process_instance_id", processInstanceid);
            LeaveApply leaveApply = leaveApplyMapper.selectOne(queryWrapper);

            //这里存入返回前端的数据
            Reply reply = new Reply();
            reply.setId(leaveApply.getId());
            reply.setProcessInstanceId(processInstanceid);
            reply.setUsername(adminMapper.selectById(leaveApply.getUserId()).getName());
            reply.setLeaveType(leaveApply.getLeaveType());
            //计算请假多长时间
            Double day = Math.floor((leaveApply.getEnd().getTime() - leaveApply.getStart().getTime()) / 86400000);
            String Duration = day.toString();
            System.out.println(Duration);
            reply.setDuration(Duration);

            replyList.add(reply);

        }


        return replyList;
    }


    @Override
    public int count() {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        int count = (int) processInstanceQuery.count();
        return count;
    }
}
