package com.ca.controller;


import com.ca.pojo.Item;
import com.ca.pojo.LeaveApply;
import com.ca.service.LeaveApplyService;
import com.ca.util.ShiroUtils;
import com.ca.vo.JsonResult;
import com.ca.vo.LayUITbale;
import com.ca.vo.RunApplyTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yayuzhi
 */
@Controller
@RequestMapping("/apply-list")
public class LeaveApplyController {

    @Autowired
    private LeaveApplyService leaveApplyService;

    /**
     * 分页查询 正在申请的请假流程
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/findApplyByPage")
    @ResponseBody
    public String findApplyByPage(int page, int limit) {
        //拿到查询开始的 数字
        int page1 = (page - 1) * limit;
        //拿到用户的名字
        String username = ShiroUtils.getAdminname();
        List<LeaveApply> leaveApplyList = leaveApplyService.findAllPage(page1, limit, username);
        int count = leaveApplyService.count();
        return new LayUITbale().LayUIResponseByLeaveApply(count, leaveApplyList);
    }

    /**
     * 添加请假 流程的添加
     *
     * @param leaveApply
     * @return
     */
    @RequestMapping("/addApply")
    @ResponseBody
    public JsonResult addApply(LeaveApply leaveApply) {
        String username = ShiroUtils.getAdminname();
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyusername", username);
        leaveApplyService.startLeaveApply(leaveApply, username, variables);
        return JsonResult.success("add ok");
    }


    @RequestMapping("/edit")
    @ResponseBody
    public JsonResult showApply(int id) {
        LeaveApply leaveApply = leaveApplyService.findApplyById(id);

        return JsonResult.success(leaveApply);
    }
}
