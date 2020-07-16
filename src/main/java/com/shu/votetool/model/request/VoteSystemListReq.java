package com.shu.votetool.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * program: VoteSystemListReq
 * description: 获取投票列表请求体
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class VoteSystemListReq {
    @Range(min = 0, max = 1, message = "请求类型错误")
    private Integer type;

    @Range(min = 0, message = "页码错误")
    private int page;

    @Range(min = 0, message = "每页对象个数错误")
    private int perPageNum;
}
