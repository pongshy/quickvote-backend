package com.shu.votetool.model.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: pongshy
 * @Date: 2020/7/18 20:09
 * @Description: 用户参与投票项目数和创建投票项目数
 **/
@Data
public class VoteSystemNumVO {
    @NotNull
    private Integer voteCreatedNum;

    @NotNull
    private Integer votingNum;

    public VoteSystemNumVO() {
        this.voteCreatedNum = 0;
        this.votingNum = 0;
    }
}
