package com.ca.controller;

import com.ca.pojo.LeaveApply;
import com.ca.service.LeaveApplyService;
import com.ca.service.ReplyService;
import com.ca.util.ShiroUtils;
import com.ca.vo.LayUITbale;
import com.ca.vo.Reply;
import com.ca.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply-list")
public class replyController {


    @Autowired
    private ReplyService replyService;


    /**
     * 显示待审批的信息
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/showReply")
    public String showReply(int page,int limit){
        //这里需要进行权限的认证 避免没有批复权限的 ？？？？直接给shiro判断 不使用taskservice了
        int page1 = (page-1)*limit;

        List<Reply> replyList = replyService.getAllReply(page1, limit);
        int count = replyService.count();
        return new LayUITbale().LayUIResponseByReply(count,replyList);
    }
}
