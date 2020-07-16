package com.shu.votetool.service.Impl;

import com.shu.votetool.dao.*;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import com.shu.votetool.model.entity.*;
import com.shu.votetool.model.request.NewVoteReq;
import com.shu.votetool.model.request.VoteReq;
import com.shu.votetool.model.request.VoteSystemListReq;
import com.shu.votetool.model.response.*;
import com.shu.votetool.service.VoteService;
import com.shu.votetool.tool.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * program: VoteServiceImpl
 * description: 投票service层
 * create: 2020/7/16
 */
@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    @Resource
    private VoteSystemDOMapper voteSystemDOMapper;

    @Resource
    private VoterDOMapper voterDOMapper;

    @Resource
    private CandidateDOMapper candidateDOMapper;

    @Resource
    private VoteRecordDOMapper voteRecordDOMapper;

    @Resource
    private UserDOMapper userDOMapper;

    @Override
    @Transactional(rollbackFor = AllException.class)
    public ResponseEntity<Object> createVoteSystem(NewVoteReq newVoteReq, String openid) {
        try{
            UserDOExample userDOExample = new UserDOExample();
            userDOExample.createCriteria().andOpenidEqualTo(openid);
            if(userDOMapper.countByExample(userDOExample) <= 0){
                throw new AllException(EmAllException.BAD_REQUEST);
            }

            Date startTime = TimeTool.stringToDate(newVoteReq.getStartTime());
            Date endTime = TimeTool.stringToDate(newVoteReq.getEndTime());
            if(startTime == null || endTime == null){
                throw new AllException(EmAllException.BAD_REQUEST);
            }

            Date now = new Date();
            if(!now.before(startTime) || !startTime.before(endTime)){
                throw new AllException(EmAllException.BAD_REQUEST);
            }

            VoteSystemDO voteSystemDO = new VoteSystemDO();
            BeanUtils.copyProperties(newVoteReq, voteSystemDO);

            voteSystemDO.setStartTime(startTime);
            voteSystemDO.setEndTime(endTime);
            voteSystemDO.setCreateTime(now);
            voteSystemDO.setOpenid(openid);
            if(voteSystemDOMapper.insertSelective(voteSystemDO) > 0){
                int id = voteSystemDO.getId();
                for(String candidate: newVoteReq.getCandidate()){
                    CandidateDO candidateDO = new CandidateDO();
                    candidateDO.setCandidateName(candidate);
                    candidateDO.setVoteId(id);
                    if(candidateDOMapper.insertSelective(candidateDO) <= 0){
                        throw new AllException(EmAllException.DATABASE_ERROR);
                    }
                }

                return new ResponseEntity<Object>(id, HttpStatus.OK);
            }else{
                throw new AllException(EmAllException.DATABASE_ERROR);
            }
        }catch (AllException e) {
            if(e.getMsg().equals("数据库异常或数据有误")){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

            log.error(e.getMsg());
            return new ResponseEntity<Object>(new ErrorResult(e, "/voteSystem"), HttpStatus.OK);
        }
    }

    /**
    * @Description: 获取投票列表
    * @Param: [type]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    @Override
    public ResponseEntity<Object> voteSystemList(VoteSystemListReq voteSystemListReq, String openid) {
        try{
            VoteSystemDOExample voteSystemDOExample = new VoteSystemDOExample();
            voteSystemDOExample.setOrderByClause("id DESC limit " + voteSystemListReq.getPerPageNum() * voteSystemListReq.getPage()
                                        + ", " + voteSystemListReq.getPerPageNum());
            VoteSystemDOExample.Criteria criteria = voteSystemDOExample.createCriteria();
            if(voteSystemListReq.getType() == 0){
                criteria.andOpenidEqualTo(openid);
            }

            List<VoteSystemDO> voteSystemDOList = voteSystemDOMapper.selectByExample(voteSystemDOExample);
            if(voteSystemDOList == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }

            List<Integer> voteIdList = voteSystemDOList.stream().map(VoteSystemDO::getId).collect(Collectors.toList());

            //获取所有投票的投票对象列表
//            CandidateDOExample candidateDOExample = new CandidateDOExample();
//            candidateDOExample.createCriteria().andVoteIdIn(voteIdList);
//            List<CandidateDO> candidateDOList = candidateDOMapper.selectByExample(candidateDOExample);
            VoteSystemList voteSystemList = new VoteSystemList();
            voteSystemList.setNum(voteSystemDOList.size());
            voteSystemList.setVoteSystemResList(voteSystemDOList.stream().map(voteSystemDO -> {
                VoteSystemRes voteSystemRes = new VoteSystemRes();
                BeanUtils.copyProperties(voteSystemDO, voteSystemRes);
                voteSystemRes.setStartTime(TimeTool.timeToSecond(voteSystemDO.getStartTime()));
                voteSystemRes.setEndTime(TimeTool.timeToSecond(voteSystemDO.getEndTime()));
                voteSystemRes.setCreateTime(TimeTool.timeToSecond(voteSystemDO.getCreateTime()));
                return voteSystemRes;
            }).collect(Collectors.toList()));

            return new ResponseEntity<Object>(voteSystemList, HttpStatus.OK);
        }catch (AllException e) {
            log.error(e.getMsg());
            return new ResponseEntity<Object>(new ErrorResult(e, "/voteSystemList"), HttpStatus.OK);
        }
    }

    /**
    * @Description: 获取投票详情
    * @Param: [id, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    @Override
    public ResponseEntity<Object> voteSystemDetail(int id) {
        try{
            VoteSystemDOExample voteSystemDOExample = new VoteSystemDOExample();
            voteSystemDOExample.createCriteria().andIdEqualTo(id);
            List<VoteSystemDO> voteSystemList = null;
            if(id < 0 || (voteSystemList = voteSystemDOMapper.selectByExample(voteSystemDOExample)) == null || voteSystemList.isEmpty()){
                throw new AllException(EmAllException.BAD_REQUEST, "该投票不存在！");
            }

            VoteDetailRes voteDetailRes = new VoteDetailRes();
            VoteSystemDO voteSystemDO = voteSystemList.get(0);
            BeanUtils.copyProperties(voteSystemDO, voteDetailRes);
            voteDetailRes.setStartTime(TimeTool.timeToSecond(voteSystemDO.getStartTime()));
            voteDetailRes.setEndTime(TimeTool.timeToSecond(voteSystemDO.getEndTime()));
            voteDetailRes.setCreateTime(TimeTool.timeToSecond(voteSystemDO.getCreateTime()));

            CandidateDOExample candidateDOExample = new CandidateDOExample();
            candidateDOExample.createCriteria().andVoteIdEqualTo(id);
            List<CandidateDO> candidateDOList = candidateDOMapper.selectByExample(candidateDOExample);
            if(candidateDOList == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }
            voteDetailRes.setCandidateList(candidateDOList.stream().map(candidateDO -> {
                CandidateVO candidateVO = new CandidateVO();
                BeanUtils.copyProperties(candidateDO, candidateVO);
                return candidateVO;
            }).collect(Collectors.toList()));

            VoteRecordDOExample voteRecordDOExample = new VoteRecordDOExample();
            voteRecordDOExample.createCriteria().andVoteIdEqualTo(voteSystemDO.getId());
            voteRecordDOExample.setOrderByClause("id DESC");
            List<VoteRecordDO> voteRecordDOList = voteRecordDOMapper.selectByExample(voteRecordDOExample);
            if(voteRecordDOList == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }
            voteDetailRes.setVoteRecordVOList(voteRecordDOList.stream().map(voteRecordDO -> {
                VoteRecordVO voteRecordVO = new VoteRecordVO();
                BeanUtils.copyProperties(voteRecordDO, voteRecordVO);
                voteRecordVO.setVoteTime(TimeTool.timeToSecond(voteRecordDO.getVoteTime()));
                return voteRecordVO;
            }).collect(Collectors.toList()));

            return new ResponseEntity<Object>(voteDetailRes, HttpStatus.OK);
        }catch (AllException e) {
            log.error(e.getMsg());
            return new ResponseEntity<Object>(new ErrorResult(e, "/voteDetail"), HttpStatus.OK);
        }
    }

    /**
    * @Description: 投票
    * @Param: [candidateIdList, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/16
    */
    @Override
    @Transactional(rollbackFor = AllException.class)
    public ResponseEntity<Object> vote(VoteReq voteReq, String openid) {
        try{
            VoteSystemDOExample voteSystemDOExample = new VoteSystemDOExample();
            voteSystemDOExample.createCriteria().andIdEqualTo(voteReq.getVoteSystemId());
            List<VoteSystemDO> voteSystemDOList = voteSystemDOMapper.selectByExample(voteSystemDOExample);
            if(voteSystemDOList == null || voteSystemDOList.isEmpty()){
                throw new AllException(EmAllException.BAD_REQUEST, "该投票不存在！");
            }

            VoteSystemDO voteSystemDO = voteSystemDOList.get(0);
            if(voteReq.getCandidateIdList().size() > voteSystemDO.getSingleVoteAgree()){
                throw new AllException(EmAllException.BAD_REQUEST, "您选择的投票选项过多！");
            }

            Date now = new Date();
            if(now.before(voteSystemDO.getStartTime())){
                throw new AllException(EmAllException.BAD_REQUEST, "投票还未开始！");
            }
            if(now.after(voteSystemDO.getEndTime())){
                throw new AllException(EmAllException.BAD_REQUEST, "投票已结束！");
            }

            if(voteSystemDO.getIspublic() == 0){
                VoterDOExample voterDOExample = new VoterDOExample();
                voterDOExample.createCriteria()
                        .andVoteIdEqualTo(voteReq.getVoteSystemId());
                if(voterDOMapper.countByExample(voterDOExample) < 0){
                    throw new AllException(EmAllException.IDENTITY_ERROR, "您暂时无权投票！");
                }
            }

            CandidateDOExample candidateDOExample = new CandidateDOExample();
            candidateDOExample.createCriteria().andIdIn(voteReq.getCandidateIdList());
            if(candidateDOMapper.countByExample(candidateDOExample) != voteReq.getCandidateIdList().size()){
                throw new AllException(EmAllException.BAD_REQUEST, "您选择的选项ID不存在！");
            }

            for(Integer candidateId: voteReq.getCandidateIdList()){
                VoteRecordDO voteRecordDO = new VoteRecordDO();
                voteRecordDO.setCandidateId(candidateId);
                voteRecordDO.setOpenid(openid);
                voteRecordDO.setVoteId(voteReq.getVoteSystemId());
                voteRecordDO.setVoteTime(new Date());

                if(voteRecordDOMapper.insertSelective(voteRecordDO) < 0){
                    throw new AllException(EmAllException.DATABASE_ERROR);
                }
            }
            return new ResponseEntity<Object>(HttpStatus.OK);
        }catch (AllException e) {
            if(e.getMsg().equals("数据库异常或数据有误")){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

            log.error(e.getMsg());
            return new ResponseEntity<Object>(new ErrorResult(e, "/voteRecord"), HttpStatus.OK);
        }
    }
}
