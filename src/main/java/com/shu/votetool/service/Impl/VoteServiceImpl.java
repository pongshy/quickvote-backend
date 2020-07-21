package com.shu.votetool.service.Impl;

import com.shu.votetool.dao.*;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import com.shu.votetool.model.entity.*;
import com.shu.votetool.model.request.NewVoteReq;
import com.shu.votetool.model.request.UpdateVoteReq;
import com.shu.votetool.model.request.VoteReq;
import com.shu.votetool.model.request.VoteSystemListReq;
import com.shu.votetool.model.response.*;
import com.shu.votetool.service.VoteService;
import com.shu.votetool.tool.NumberTool;
import com.shu.votetool.tool.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
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
            if(!startTime.before(endTime) || !now.before(endTime)){
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
    * @Description: 修改投票项目
    * @Param: [newVoteReq, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/17
    */
    @Override
    @Transactional(rollbackFor = AllException.class)
    public ResponseEntity<Object> updateVoteSystem(UpdateVoteReq updateVoteReq, String openid) {
        try{
            VoteSystemDO voteSystemDO = voteSystemDOMapper.selectByPrimaryKey(updateVoteReq.getId());
            if(voteSystemDO == null){
                throw new AllException(EmAllException.BAD_REQUEST, "投票项目不存在");
            }
            if(!voteSystemDO.getOpenid().equals(openid)){
                throw new AllException(EmAllException.IDENTITY_ERROR, "投票项目只能由创建者修改！");
            }

            Date now = new Date();
            if(now.after(voteSystemDO.getStartTime())){
                throw new AllException(EmAllException.BAD_REQUEST, "投票已开始或已结束");
            }

            //需要更新的对象体
            VoteSystemDO newVoteSystemDO = new VoteSystemDO();
            BeanUtils.copyProperties(updateVoteReq, newVoteSystemDO);


            //如果需要对开始时间进行修改
            if(updateVoteReq.getStartTime() != null && !updateVoteReq.getStartTime().isEmpty()){
                Date startTime = TimeTool.stringToDate(updateVoteReq.getStartTime());
                if(startTime == null  || startTime.after(voteSystemDO.getEndTime())){
                    throw new AllException(EmAllException.BAD_REQUEST, "开始时间设置错误");
                }
                newVoteSystemDO.setStartTime(startTime);
            }

            //如果需要对结束时间进行修改
            if(updateVoteReq.getEndTime() != null && !updateVoteReq.getEndTime().isEmpty()){
                Date endTime = TimeTool.stringToDate(updateVoteReq.getEndTime());
                if(endTime == null  || voteSystemDO.getStartTime().after(endTime)){
                    throw new AllException(EmAllException.BAD_REQUEST, "结束时间设置错误");
                }
                newVoteSystemDO.setEndTime(endTime);
            }

            if(newVoteSystemDO.getStartTime() != null && newVoteSystemDO.getEndTime() != null){
                if(newVoteSystemDO.getStartTime().after(newVoteSystemDO.getEndTime())){
                    throw new AllException(EmAllException.BAD_REQUEST, "时间设置错误");
                }
            }


            if(voteSystemDOMapper.updateByPrimaryKeySelective(newVoteSystemDO) > 0){
                if(updateVoteReq.getCandidate() != null && !updateVoteReq.getCandidate().isEmpty()){
                    CandidateDOExample candidateDOExample = new CandidateDOExample();
                    candidateDOExample.createCriteria().andVoteIdEqualTo(updateVoteReq.getId());
                    candidateDOMapper.deleteByExample(candidateDOExample);

                    for(String candidate: updateVoteReq.getCandidate()){
                        CandidateDO candidateDO = new CandidateDO();
                        candidateDO.setCandidateName(candidate);
                        candidateDO.setVoteId(updateVoteReq.getId());
                        if(candidateDOMapper.insertSelective(candidateDO) <= 0){
                            throw new AllException(EmAllException.DATABASE_ERROR);
                        }
                    }
                }

                return new ResponseEntity<Object>(HttpStatus.OK);
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
            VoteSystemList voteSystemList = new VoteSystemList();
            List<VoteSystemDO> voteSystemDOList = null;
            int fromIndex = voteSystemListReq.getPerPageNum() * voteSystemListReq.getPage();
            int endIndex = fromIndex + voteSystemListReq.getPerPageNum();

            if(voteSystemListReq.getType() == 0){
                VoterDOExample voterDOExample = new VoterDOExample();
                voterDOExample.createCriteria().andOpenidEqualTo(openid);
                voterDOExample.setOrderByClause("id DESC");
                List<VoterDO> voterDOList = voterDOMapper.selectByExample(voterDOExample);
                if(voterDOList == null){
                    throw new AllException(EmAllException.DATABASE_ERROR);
                }
                if(voterDOList.isEmpty()){
                    throw new AllException(EmAllException.DATABASE_ERROR, "您还未参加过投票！");
                }

                //去重
                List<VoterDO> filterVoterList = new ArrayList<>();
                for(VoterDO voterDO: voterDOList){
                    if(filterVoterList.isEmpty() || !filterVoterList.get(filterVoterList.size() - 1).getVoteId().equals(voterDO.getVoteId())){
                        filterVoterList.add(voterDO);
                    }
                }

                //所需id列表
                List<Integer> needVoteIdList = filterVoterList.stream().map(VoterDO::getVoteId).collect(Collectors.toList());

                //若不需要搜索字符串，加快搜索速度
                if(voteSystemListReq.getStrRequired().isEmpty()){
                    voteSystemList.setNum(needVoteIdList.size());
                    needVoteIdList = needVoteIdList.subList(fromIndex, Math.min(endIndex, needVoteIdList.size()));
                }

                //准备查询
                VoteSystemDOExample voteSystemDOExample = new VoteSystemDOExample();
                VoteSystemDOExample.Criteria criteria = voteSystemDOExample.createCriteria().andIdIn(needVoteIdList);
                //若有需查询的字符串
                if(!voteSystemListReq.getStrRequired().isEmpty()){
                    criteria.andVoteNameLike("%" + voteSystemListReq.getStrRequired() + "%");
                }

                //查询
                voteSystemDOList = voteSystemDOMapper.selectByExample(voteSystemDOExample);
                if(voteSystemDOList == null || voterDOList.isEmpty()){
                    throw new AllException(EmAllException.BAD_REQUEST, "当前无数据");
                }

                //若有需查询的字符串，进行分页
                if(!voteSystemListReq.getStrRequired().isEmpty()){
                    voteSystemList.setNum(voteSystemDOList.size());
                    voteSystemDOList = voteSystemDOList.subList(fromIndex, Math.min(endIndex, voteSystemDOList.size()));
                }

                //按照needVoteIdList的顺序给结果排序
                Map<Integer, VoteSystemDO> voteSystemListMap = voteSystemDOList.stream().collect(Collectors.toMap(VoteSystemDO::getId, voteSystemDO -> voteSystemDO));
                voteSystemDOList.clear();
                for(Integer id: needVoteIdList){
                    VoteSystemDO voteSystemDO = voteSystemListMap.get(id);
                    if(voteSystemDO != null){
                        voteSystemDOList.add(voteSystemDO);
                    }
                }

            }else if(voteSystemListReq.getType() == 1){
                VoteSystemDOExample voteSystemDOExample = new VoteSystemDOExample();
                VoteSystemDOExample.Criteria criteria = voteSystemDOExample.createCriteria()
                        .andOpenidEqualTo(openid);
                voteSystemDOExample.setOrderByClause("id DESC");
                if(!voteSystemListReq.getStrRequired().isEmpty()){
                    criteria.andVoteNameLike("%" + voteSystemListReq.getStrRequired() + "%");
                }
                voteSystemDOList = voteSystemDOMapper.selectByExample(voteSystemDOExample);
                if(voteSystemDOList == null){
                    throw new AllException(EmAllException.DATABASE_ERROR, "您未参与过已知的投票项目!");
                }

                voteSystemList.setNum(voteSystemDOList.size());

                voteSystemDOList = voteSystemDOList.subList(fromIndex, Math.min(endIndex, voteSystemDOList.size()));
            }else {
                throw new AllException(EmAllException.DATABASE_ERROR);
            }

            List<String> openIdList = voteSystemDOList.stream().map(VoteSystemDO::getOpenid).collect(Collectors.toList());
            UserDOExample userDOExample = new UserDOExample();
            userDOExample.createCriteria().andOpenidIn(openIdList);
            List<UserDO> userDOList = userDOMapper.selectByExample(userDOExample);
            if(userDOList == null || userDOList.isEmpty()){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }
            Map<String, UserDO> userDOMap = userDOList.stream().collect(Collectors.toMap(UserDO::getOpenid, userDO -> userDO, (k1, k2) -> k1));

            CandidateDOExample candidateDOExample = new CandidateDOExample();
            candidateDOExample.createCriteria().andVoteIdIn(voteSystemDOList.stream().map(VoteSystemDO::getId).collect(Collectors.toList()));
            List<CandidateDO> candidateDOList = candidateDOMapper.selectByExample(candidateDOExample);
            if(candidateDOList == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }

            List<Integer> voteIdList = voteSystemDOList.stream().map(VoteSystemDO::getId).collect(Collectors.toList());
            VoterDOExample voterDOExample = new VoterDOExample();
            voterDOExample.createCriteria().andVoteIdIn(voteIdList);
            List<VoterDO> voterDOList = voterDOMapper.selectByExample(voterDOExample);
            if(voterDOList == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }


            //获取所有投票的投票对象列表
            voteSystemList.setVoteSystemResList(voteSystemDOList.stream().map(voteSystemDO -> {
                VoteSystemRes voteSystemRes = new VoteSystemRes();
                BeanUtils.copyProperties(voteSystemDO, voteSystemRes);
                voteSystemRes.setStartTime(TimeTool.timeToSecond(voteSystemDO.getStartTime()));
                voteSystemRes.setEndTime(TimeTool.timeToSecond(voteSystemDO.getEndTime()));
                voteSystemRes.setCreateTime(TimeTool.timeToSecond(voteSystemDO.getCreateTime()));
                voteSystemRes.setHeadImg(userDOMap.get(voteSystemDO.getOpenid()).getWimage());
                List<CandidateDO> CandidateListNeed = candidateDOList.stream().filter(candidateDO -> candidateDO.getVoteId().equals(voteSystemDO.getId())).collect(Collectors.toList());

                voteSystemRes.setVoterNum((int) voterDOList.stream().filter(voterDO -> voterDO.getVoteId().equals(voteSystemDO.getId())).count());
                voteSystemRes.setCandidateNum(CandidateListNeed.size());
                voteSystemRes.setReceivedVote(CandidateListNeed.stream().mapToInt(CandidateDO::getAgree).sum());
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
    public ResponseEntity<Object> voteSystemDetail(int id, String openid) {
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
            voteDetailRes.setEndTimeFormat(TimeTool.timeToSecondWX(voteSystemDO.getEndTime()));
            voteDetailRes.setCreateTime(TimeTool.timeToSecond(voteSystemDO.getCreateTime()));


            CandidateDOExample candidateDOExample = new CandidateDOExample();
            candidateDOExample.createCriteria().andVoteIdEqualTo(id);
            List<CandidateDO> candidateDOList = candidateDOMapper.selectByExample(candidateDOExample);
            if(candidateDOList == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }

            voteDetailRes.setReceivedVote(candidateDOList.stream().mapToInt(CandidateDO::getAgree).sum());
            voteDetailRes.setCandidateList(candidateDOList.stream().map(candidateDO -> {
                CandidateVO candidateVO = new CandidateVO();
                BeanUtils.copyProperties(candidateDO, candidateVO);
                candidateVO.setPercentage(NumberTool.doubleToStringWithH(NumberTool.intDivision(candidateDO.getAgree(), voteDetailRes.getReceivedVote())));
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
            voteDetailRes.setMyVoteRecordVoList(voteDetailRes.getVoteRecordVOList().stream().filter(voteRecordVO -> voteRecordVO.getOpenid().equals(openid)).collect(Collectors.toList()));

            UserDO userDO = userDOMapper.selectByPrimaryKey(voteDetailRes.getOpenid());
            if(userDO == null){
                throw new AllException(EmAllException.DATABASE_ERROR);
            }

            voteDetailRes.setCandidateNum(voteDetailRes.getCandidateList().size());
            voteDetailRes.setHeadImg(userDO.getWimage());
            voteDetailRes.setWxName(userDO.getWname());
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
            List<CandidateDO> candidateDOList = candidateDOMapper.selectByExample(candidateDOExample);
            if(candidateDOList == null || candidateDOList.size() != voteReq.getCandidateIdList().size()){
                throw new AllException(EmAllException.BAD_REQUEST, "您选择的选项ID不存在！");
            }
            Map<Integer, CandidateDO> candidateDOMap = candidateDOList.stream().collect(Collectors.toMap(CandidateDO::getId, candidateDO -> candidateDO, (k1, k2) -> k1));

            for(Integer candidateId: voteReq.getCandidateIdList()){
                VoteRecordDO voteRecordDO = new VoteRecordDO();
                voteRecordDO.setCandidateId(candidateId);
                voteRecordDO.setOpenid(openid);
                voteRecordDO.setVoteId(voteReq.getVoteSystemId());
                voteRecordDO.setVoteTime(new Date());

                if(voteRecordDOMapper.insertSelective(voteRecordDO) < 0){
                    throw new AllException(EmAllException.DATABASE_ERROR);
                }else {
                    CandidateDO candidateDO = new CandidateDO();
                    candidateDO.setAgree(candidateDOMap.get(candidateId).getAgree() + 1);
                    candidateDO.setId(candidateId);
                    candidateDOMapper.updateByPrimaryKeySelective(candidateDO);
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

    /*
     * @Description: 获取用户参与的投票项目数与发起的投票项目数
     * @Param: [openid]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: pongshy
     * @Date: 2020/7/18
     **/
    @Override
    public ResponseEntity<Object> getUserVoteNum(String openid) {
        try {
            if (StringUtils.isEmpty(openid)) {
                throw new AllException(EmAllException.BAD_REQUEST, "openid为空");
            }
            UserDO userDO = userDOMapper.selectByPrimaryKey(openid);

            if (userDO != null) {
                VoteSystemNumVO response = new VoteSystemNumVO();

                List<Integer> idList = voteSystemDOMapper.selectVoteSystemIdByOpenid(openid);
                response.setVoteCreatedNum(idList.size());

                VoterDOExample voterDOExample = new VoterDOExample();
                voterDOExample.createCriteria()
                        .andOpenidEqualTo(openid);
                int count = voterDOMapper.countByExample(voterDOExample);
                response.setVotingNum(count);

                return new ResponseEntity<Object>(response, HttpStatus.OK);
            } else {
                throw new AllException(EmAllException.BAD_REQUEST, "openid不存在");
            }
        } catch (AllException ex) {
            log.info(ex.getMsg());

            return new ResponseEntity<>(new ErrorResult(ex, "/vote/voteSystemNum"), HttpStatus.OK);
        }
    }

    /**
    * @Description: 增加投票员
    * @Param: [id, openid]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: SoCMo
    * @Date: 2020/7/19
    */
    @Override
    public ResponseEntity<Object> addVoter(int voteId, String openid) {
        try {
            VoteSystemDO voteSystemDO = voteSystemDOMapper.selectByPrimaryKey(voteId);
            if(voteSystemDO == null){
                throw new AllException(EmAllException.BAD_REQUEST, "该投票不存在!");
            }

            VoterDOExample voterDOExample = new VoterDOExample();
            voterDOExample.createCriteria()
                    .andVoteIdEqualTo(voteId)
                    .andOpenidEqualTo(openid);
            if(voterDOMapper.countByExample(voterDOExample) == 0){
                VoterDO voterDO = new VoterDO();
                voterDO.setOpenid(openid);
                voterDO.setVoteId(voteId);
                voterDOMapper.insertSelective(voterDO);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AllException ex) {
            log.info(ex.getMsg());
            return new ResponseEntity<>(new ErrorResult(ex, "/vote/voteSystemNum"), HttpStatus.OK);
        }
    }

    /*
     * @Description: 获取未投人员名单
     * @Param: [Integer]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: pongshy
     * @Date: 2020/7/18
     **/
    @Override
    public ResponseEntity<Object> getUninvestedPersons(Integer id) {
        try {
            if (id < 0) {
                throw new AllException(EmAllException.BAD_REQUEST, "id为空");
            }
            List<String> openid_list = voterDOMapper.selectUnvoteBy(id);
            List<UnVotePersonsVO> voList = new ArrayList<>();

            if (openid_list == null || openid_list.isEmpty()) {
                return new ResponseEntity<>(voList, HttpStatus.OK);
            } else {
                UserDOExample userDOExample = new UserDOExample();

                userDOExample.createCriteria()
                        .andOpenidIn(openid_list);
                List<UserDO> userDOList = userDOMapper.selectByExample(userDOExample);

                for (UserDO userDO : userDOList) {
                    UnVotePersonsVO unVotePersonsVO = new UnVotePersonsVO();

                    unVotePersonsVO.setWimage(userDO.getWimage());
                    unVotePersonsVO.setWname(userDO.getWname());
                    unVotePersonsVO.setOpenid(userDO.getOpenid());

                    voList.add(unVotePersonsVO);
                }

                return new ResponseEntity<>(voList, HttpStatus.OK);
            }
        }
        catch (AllException ex) {
            log.info(ex.getMsg());
            return new ResponseEntity<>(new ErrorResult(ex, "/vote/uninvested"), HttpStatus.OK);
        }
    }
}
