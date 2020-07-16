package com.shu.votetool.service.Impl;

import com.shu.votetool.dao.UserDOMapper;
import com.shu.votetool.dao.VoteSystemDOMapper;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import com.shu.votetool.model.entity.UserDOExample;
import com.shu.votetool.model.entity.VoteSystemDO;
import com.shu.votetool.model.request.NewVoteReq;
import com.shu.votetool.model.response.ErrorResult;
import com.shu.votetool.service.VoteService;
import com.shu.votetool.tool.TimeTool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    private UserDOMapper userDOMapper;

    @Override
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
            int id = voteSystemDOMapper.insertSelective(voteSystemDO);
            if(id > 0){
                return new ResponseEntity<Object>(id, HttpStatus.OK);
            }else{
                throw new AllException(EmAllException.DATABASE_ERROR);
            }
        }catch (AllException e) {
            log.error(e.getMessage());
            return new ResponseEntity<Object>(new ErrorResult(e.getErrCode(),
                    e.getHttpStatus(),
                    e.getMsg(),
                    "/voteSystem"
            ),
                    HttpStatus.OK);
        }
    }
}
