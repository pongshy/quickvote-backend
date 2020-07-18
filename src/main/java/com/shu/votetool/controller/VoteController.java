package com.shu.votetool.controller;

import com.shu.votetool.model.request.NewVoteReq;
import com.shu.votetool.model.request.UpdateVoteReq;
import com.shu.votetool.model.request.VoteReq;
import com.shu.votetool.model.request.VoteSystemListReq;
import com.shu.votetool.service.VoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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
public class VoteController {
    @Resource
    private VoteService voteService;

    /**
     * @Description: 创建投票项目
     * @Param: [newVoteReq, openid]
     * @return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: SoCMo
     * @Date: 2020/7/16
     */
    @ApiOperation(value = "创建投票项目", httpMethod = "POST")
    @PostMapping("/voteSystem")
    public ResponseEntity<Object> newVoteSystem(@Validated @RequestBody NewVoteReq newVoteReq,
                                                @RequestHeader("openid") String openid) {
        return voteService.createVoteSystem(newVoteReq, openid);
    }


    /**
     * @Description: 更新投票项目
     * @Param: [updateVoteReq, openid]
     * @return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: SoCMo
     * @Date: 2020/7/17
     */
    @ApiOperation(value = "更新投票项目", httpMethod = "PUT")
    @PutMapping("/voteSystem")
    public ResponseEntity<Object> updateVoteSystem(@Validated @RequestBody UpdateVoteReq updateVoteReq,
                                                   @RequestHeader("openid") String openid) {
        return voteService.updateVoteSystem(updateVoteReq, openid);
    }

    /**
     * @Description: 获取投票列表
     * @Param: [voteSystemListReq, openid]
     * @return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: SoCMo
     * @Date: 2020/7/16
     */
    @ApiOperation(value = "获取投票列表", httpMethod = "GET")
    @GetMapping("/voteSystemList")
    public ResponseEntity<Object> voteSystemList(@Validated VoteSystemListReq voteSystemListReq,
                                                 @RequestHeader("openid") String openid){
        return voteService.voteSystemList(voteSystemListReq, openid);
    }

    /**
    * @Description: 投票
    * @Param: [voteReq, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    @ApiOperation(value = "投票", httpMethod = "POST")
    @PostMapping("/voteRecord")
    public ResponseEntity<Object> vote(@Validated @RequestBody VoteReq voteReq,
                                                 @RequestHeader("openid") String openid){
        return voteService.vote(voteReq, openid);
    }

    /** 
    * @Description: 获取投票详情 
    * @Param: [id] 
    * @return: org.springframework.http.ResponseEntity<java.lang.Object> 
    * @Author: SoCMo
    * @Date: 2020/7/16 
    */
    @ApiOperation(value = "获取投票详情", httpMethod = "GET")
    @GetMapping("/voteDetail")
    public ResponseEntity<Object> voteDetail(@RequestParam Integer id){
        return voteService.voteSystemDetail(id);
    }
}
