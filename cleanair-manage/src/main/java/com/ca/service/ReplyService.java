package com.ca.service;

import com.ca.vo.Reply;

import java.util.List;
import java.util.Map;

public interface ReplyService {
    List<Reply> getAllReply(String username,int page, int limit);

    int count();

    Reply showReply(int id);

    void returnReply(String processInstanceId,String username,boolean deptleaderapprove);

    Map getAllHReply(String username,int page,int limit);
}
