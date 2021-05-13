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
import java.util.Map;

@RestController
@RequestMapping("/reply-list")
public class ReplyController {


    @Autowired
    private ReplyService replyService;


    /**
     * 显示待审批的信息
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/showReply")
    public String showReplies(int page,int limit){
        //这里需要进行权限的认证 避免没有批复权限的 ？？？？直接给shiro判断 不使用taskservice了
        //但是不想让shiro进行验证就自己写一个验证
        int page1 = (page-1)*limit;
        String username = ShiroUtils.getAdminname();
        List<Reply> replyList = replyService.getAllReply(username,page1, limit);
        int count = replyService.count();
        return new LayUITbale().LayUIResponseByReply(count,replyList);
    }

    @RequestMapping("/show")
    public JsonResult showReply(int id){

        Reply reply = replyService.showReply(id);

        return JsonResult.success(reply);
    }


    @RequestMapping("/deptleaderapprove")
    public  JsonResult returnReply(String processInstanceId,boolean deptleaderapprove){
        String username = ShiroUtils.getAdminname();
        replyService.returnReply(processInstanceId,username,deptleaderapprove);
        return JsonResult.success("return ok");
    }

    @RequestMapping("/showHReply")
    public String showHReply(int page,int limit){
        int page1 = (page-1)*limit;
        String username = ShiroUtils.getAdminname();
        Map map = replyService.getAllHReply(username,page1, limit);
        int count= (int) map.get("count");
        List<Reply> replyList = (List<Reply>) map.get("replyList");
        return new LayUITbale().LayUIResponseByReply(count,replyList);
    }
}
