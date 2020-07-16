package com.shu.votetool.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * program: VoteReq
 * description: 投票请求体
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class VoteReq {
    @NotNull(message = "投票ID不能为空")
    private int voteSystemId;

    @Size(min = 0, message = "投票选项不能为空")
    private List<Integer> candidateIdList;
}
