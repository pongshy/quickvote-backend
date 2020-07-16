package com.shu.votetool.model.response;

import lombok.Data;

import java.util.List;

/**
 * program: VoteSystemList
 * description: 投票信息返回列表
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class VoteSystemList {
    private List<VoteSystemRes> voteSystemResList;

    private int num;
}
