package com.shu.votetool.model.response;

import lombok.Data;

/**
 * program: CandidateVO
 * description: 选项返回体
 * author: SoCMo
 * create: 2020/7/16
 */
@Data
public class CandidateVO {
    private Integer id;

    private String candidateName;

    private Integer agree;

    private String percentage;
}
