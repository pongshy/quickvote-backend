package com.shu.votetool.service;

import com.shu.votetool.model.request.NewVoteReq;
import org.springframework.http.ResponseEntity;

/**
 * program: VoteService
 * description: 投票service层
 * create: 2020/7/16
 */
public interface VoteService {

    /**
    * @Description: 创建投票
    * @Param: [newVoteReq]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    public ResponseEntity<Object> createVoteSystem(NewVoteReq newVoteReq, String openid);
}
