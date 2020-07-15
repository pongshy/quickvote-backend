package com.shu.votetool.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/*
  * @Description: 用户信息——用户名、头像
  * @Author: pongshy
  * @Date: 2020/6/19
 **/
@Data
public class UserInfo {
    @NotBlank
    private String username;

    @NotBlank
    private String avatarUrl;
}
