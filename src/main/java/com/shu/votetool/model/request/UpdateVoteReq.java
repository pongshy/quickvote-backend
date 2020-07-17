package com.shu.votetool.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * program: UpdateVoteReq
 * description: 更新投票项目
 * author: SoCMo
 * create: 2020/7/17
 */
@Data
public class UpdateVoteReq {
    @NotNull(message = "所需更新的投票项目ID不能为空")
    private Integer id;

    private String voteName;

    private Integer singleVoteAgree;

    private String description;

    private String startTime;

    private String endTime;

    private Integer ispublic;

    private List<String> candidate;
}
