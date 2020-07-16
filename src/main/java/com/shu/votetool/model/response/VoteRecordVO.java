package com.shu.votetool.model.response;

import lombok.Data;

import java.util.Date;

/**
 * program: VoteRecordVO
 * description: 投票记录
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class VoteRecordVO {
    private String openid;

    private Integer voteId;

    private Integer candidateId;

    private String voteTime;
}
