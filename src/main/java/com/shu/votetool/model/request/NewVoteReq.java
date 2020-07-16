package com.shu.votetool.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * program: NewVoteReq
 * description: 创建投票项目的请求体
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class NewVoteReq {
    @NotBlank(message = "投票名不能为空")
    private String voteName;

    @Min(value = 0, message = "最小投票人数为0")
    private Integer singleVoteAgree;

    private String description;

    @NotBlank(message = "开始时间不能为空")
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    @Range(min = 0, max = 1, message = "是否公开选择为0或1")
    private Integer ispublic;

    @Size(min = 1, message = "至少有一个投票对象")
    private List<String> candidate;
}
