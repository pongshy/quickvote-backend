package com.shu.votetool.service.Impl;

import com.shu.votetool.dao.VoteSystemDOMapper;
import com.shu.votetool.dao.VoterDOMapper;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import com.shu.votetool.model.entity.VoteSystemDO;
import com.shu.votetool.model.response.ErrorResult;
import com.shu.votetool.service.MessageService;
import com.shu.votetool.tool.TimeTool;
import com.shu.votetool.tool.WXApiTool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: pongshy
 * @Date: 2020/7/20 10:53
 * @Description: 微信小程序提醒消息发送实现层
 **/
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Value("${wx.template_id}")
    private String template_id;

    @Value("${wx.page}")
    private String page;

    @Value("${wx.AppId}")
    private String AppId;

    @Value("${wx.secret}")
    private String secret;

    @Resource
    private VoteSystemDOMapper voteSystemDOMapper;

    @Resource
    private VoterDOMapper voterDOMapper;

    /*
     * @Description: 向未投票用户发送提醒消息
     * @Param: [String, String]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: pongshy
     * @createTime: 2020/7/20 12:41
     */
    @Override
    public ResponseEntity<Object> sendMsg(String openid, Integer id) {
        try {
            String access_token = WXApiTool.getAccessToken(AppId, secret);
            if (StringUtils.isEmpty(openid)) {
                throw new AllException(EmAllException.BAD_REQUEST, "openid为空");
            }
            if (id < 0){
                throw new AllException(EmAllException.BAD_REQUEST, "id错误");
            }
            VoteSystemDO voteSystemDO = voteSystemDOMapper.selectByPrimaryKey(id);
            if (null == voteSystemDO) {
                throw new AllException(EmAllException.BAD_REQUEST, "不存在该投票项目");
            }
            List<String> unVote = voterDOMapper.selectUnvoteBy(voteSystemDO.getId());
            if (!unVote.contains(openid)) {
                throw new AllException(EmAllException.BAD_REQUEST, "该用户不是该投票项目的未投用户");
            }
            //跳转页面
            String jumpPage = page + id;

            Map<String, Map> data = new HashMap<>();
            Map<String, String> temp = new HashMap<>();
            Map<String, String> temp1 = new HashMap<>();
            Map<String, String> temp2 = new HashMap<>();
            Date now = new Date(System.currentTimeMillis());

            temp.put("value", TimeTool.DateToString(now));
            temp1.put("value", "您还有未投票的项目呦~请点击转跳");
            temp2.put("value", "待投项目:" + voteSystemDO.getVoteName());

            data.put("time1", temp);
            data.put("thing2", temp1);
            data.put("thing3", temp2);

            String str = WXApiTool.sendMsgByApi(access_token, openid, template_id, jumpPage, data);
            log.info(str);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AllException ex) {
            log.info(ex.getMsg());

            return new ResponseEntity<>(new ErrorResult(ex, "/msg/send"), HttpStatus.OK);
        } catch (IOException e) {
            log.info(e.getMessage());

            return new ResponseEntity<>(
                    new ErrorResult(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "后端调用Api失败",
                            "/msg/send"
                    ),
                    HttpStatus.OK);
        }
    }
}
