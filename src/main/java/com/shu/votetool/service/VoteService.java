package com.shu.votetool.service;

import com.shu.votetool.exception.AllException;
import com.shu.votetool.model.request.NewVoteReq;
import com.shu.votetool.model.request.UpdateVoteReq;
import com.shu.votetool.model.request.VoteReq;
import com.shu.votetool.model.request.VoteSystemListReq;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    /**
    * @Description: 修改投票
    * @Param: [newVoteReq, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/17
    */
    public ResponseEntity<Object> updateVoteSystem(UpdateVoteReq updateVoteReq, String openid);

    /**
    * @Description: 获取投票列表，type[0:全部列表, 1:我的列表]
    * @Param: [type]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    public ResponseEntity<Object> voteSystemList(VoteSystemListReq voteSystemListReq, String openid);

    /**
    * @Description: 获取投票项目详情
    * @Param: [id, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    public ResponseEntity<Object> voteSystemDetail(int id);

    /** 
    * @Description: 投票 
    * @Param: [candidateIdList, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object> 
    * @Author: SoCMo
    * @Date: 2020/7/16 
    */ 
    public ResponseEntity<Object> vote(VoteReq voteReq, String openid);

    /*
     * @Description: 获取用户参与的投票项目数与发起的投票项目数
     * @Param: [openid]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: pongshy
     * @Date: 2020/7/18
     **/
    public ResponseEntity<Object> getUserVoteNum(String openid);
}
