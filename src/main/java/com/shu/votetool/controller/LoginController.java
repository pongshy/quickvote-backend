package com.shu.votetool.controller;

import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import com.shu.votetool.model.Result;
import com.shu.votetool.model.request.UserInfo;
import com.shu.votetool.model.response.ErrorResult;
import com.shu.votetool.service.LoginService;
import com.shu.votetool.tool.ResultTool;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "测试接口", httpMethod = "GET")
    @GetMapping("/test")
    public ResponseEntity<String> testApi(@RequestParam("value") String value) {
        if (StringUtils.isEmpty(value)) {
            return ResponseEntity.ok("value为空");
        }
        return ResponseEntity.ok(value);
    }

    @ApiOperation(value = "登录接口", httpMethod = "GET")
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam(value = "code", defaultValue = "1") String code) throws Exception {
        if (code.equals("1")) {
            return new ResponseEntity<>(
                    new ErrorResult(
                           new AllException(EmAllException.BAD_REQUEST, "code为空"), "login"
                    ),
                    HttpStatus.OK
            );
        }

        return loginService.loginWX(code);
    }

    @ApiOperation(value = "用户信息更新接口", httpMethod = "POST")
    @PostMapping("/userInfo")
    public ResponseEntity<Object> updateUserInfo(HttpServletRequest request, @RequestBody @Validated UserInfo userInfo) throws Exception {
        String openid = request.getHeader("openid");

        if (StringUtils.isEmpty(openid)) {
            return new ResponseEntity<>(
                    new ErrorResult(
                            new AllException(EmAllException.BAD_REQUEST, "openid为空或无openid"), "/userInfo"
                    ),
                    HttpStatus.OK);
        }
        return loginService.updateUserInfo(openid, userInfo);
    }

}
