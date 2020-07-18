package com.shu.votetool.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: pongshy
 * @Date: 2020/7/18 21:03
 * @Description: 未填写问卷的用户名
 **/
@Data
public class UnVotePersonsVO {
    private List<String> wxnameList;

    public UnVotePersonsVO() {
        this.wxnameList = new ArrayList<>();
    }
}
