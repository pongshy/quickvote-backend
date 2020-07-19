package com.shu.votetool.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * program: NewVoterReq
 * description: 增加投票员
 * author: SoCMo
 * create: 2020/7/19
 */
@Data
public class NewVoterReq {
    @NotNull(message = "voteId不能为空")
    private Integer voteId;
}
