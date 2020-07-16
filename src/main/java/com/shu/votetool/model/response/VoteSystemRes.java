package com.shu.votetool.model.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * program: VoteSystemRes
 * description: 投票信息返回
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class VoteSystemRes {
    private Integer id;

    private String voteName;

    private Integer singleVoteAgree;

    private String description;

    private String startTime;

    private String endTime;

    private String createTime;

    private Integer ispublic;

    private String openid;
}
