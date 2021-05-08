package com.ca.service;

import com.ca.vo.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getAllReply(int page, int limit);
    int count();
}
