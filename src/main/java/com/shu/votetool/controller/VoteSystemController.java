package com.shu.votetool.controller;

import com.shu.votetool.model.request.NewVoteReq;
import com.shu.votetool.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * program: VoteSystemController
 * description: 投票控制层
 * create: 2020/7/16
 */
@RestController
@CrossOrigin
@RequestMapping("vote")
public class VoteSystemController {
    @Resource
    private VoteService voteService;

    @PostMapping("/voteSystem")
    public ResponseEntity<Object> newVoteSystem(@Validated @RequestBody NewVoteReq newVoteReq,
                    @RequestHeader("openid") String openid){
        return voteService.createVoteSystem(newVoteReq, openid);
    }
}
