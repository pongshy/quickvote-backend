package com.shu.votetool.model.response;

import lombok.Data;

import java.util.List;

/**
 * program: VoteDetailRes
 * description: 获取投票项目详情
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class VoteDetailRes extends VoteSystemRes{
    private List<CandidateVO> candidateList;

    private List<VoteRecordVO> voteRecordVOList;

    private String wxName;
}
