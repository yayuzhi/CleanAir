package com.ca.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ca.mapper.AdminMapper;
import com.ca.mapper.LeaveApplyMapper;
import com.ca.pojo.HisLeaveApply;
import com.ca.pojo.LeaveApply;
import com.ca.service.LeaveApplyService;
import com.ca.util.ShiroUtils;
import com.ca.vo.RunApplyTable;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
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

    @Autowired
    HistoryService historyService;

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
    public int count(String username) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> processInstanceList = processInstanceQuery.processDefinitionKey(PROCESS_DEFINE_KEY).involvedUser(username).list();
        return processInstanceList.size();
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

    @Override
    public void updateLeaveApply(LeaveApply leaveApply) {
        leaveApplyMapper.updateById(leaveApply);
    }

    @Override
    public void cancelApply(String processInstanceId, int id) {
        runtimeService.deleteProcessInstance(processInstanceId, "取消申请");
        leaveApplyMapper.deleteById(id);
    }

    @Override
    public Map findhApplyById(int page, int limit, String username) {
        Map map = new HashMap();
        //取到请假 本人 的 his list的  根据每一个的businessKey 找到leaveApply
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(PROCESS_DEFINE_KEY).startedBy(username).finished();
        //历史记录的总数
        int count = (int) historicProcessInstanceQuery.count();
        List<HisLeaveApply> hisLeaveApplyList = new ArrayList<>();
        List<LeaveApply> leaveApplyList = new ArrayList<>();
        List<HistoricProcessInstance> historicProcessInstanceList = historicProcessInstanceQuery.listPage(page, limit);
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstanceList) {
            //历史数据返回前端
            HisLeaveApply hisLeaveApply = new HisLeaveApply();

            //BusinessKey 在流程开始的时候就当作leaveApply的主键存储在数据库里面了
            String businessKey = historicProcessInstance.getBusinessKey();
            LeaveApply leaveApply = leaveApplyMapper.selectById(businessKey);
            //如何得到处理的结果
            hisLeaveApply.setLeaveApply(leaveApply);
            hisLeaveApplyList.add(hisLeaveApply);
            leaveApplyList.add(leaveApply);
            //这里写下申请的批复人信息
            List<HistoricActivityInstance> his = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(leaveApply.getProcessInstanceId()).orderByHistoricActivityInstanceStartTime().asc().list();
            leaveApply.setApplicant(his.get(1).getAssignee());
        }

        map.put("count", count);
        map.put("hisLeaveApplyList", hisLeaveApplyList);
        map.put("leaveApplyList", leaveApplyList);
        return map;
    }
}
