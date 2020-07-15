package com.shu.votetool.controller;

import com.shu.votetool.model.Result;
import com.shu.votetool.model.response.ErrorResult;
import com.shu.votetool.service.LoginService;
import com.shu.votetool.tool.ResultTool;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "测试接口", httpMethod = "GET")
    @GetMapping("/test")
    public ResponseEntity<String> testApi(@RequestParam("value") String value) {

//        return new ResponseEntity<Object>(new ErrorResult(404,
//                HttpStatus.BAD_REQUEST,
//                "测试接口",
//                "/login/test"),
//                HttpStatus.OK);
        return ResponseEntity.ok(value);
    }

    @ApiOperation(value = "登录接口", httpMethod = "GET")
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam(value = "code", defaultValue = "1") String code) throws Exception {
        if (code.equals("1")) {
            return new ResponseEntity<Object>(
                    new ErrorResult(
                            404,
                            HttpStatus.BAD_REQUEST,
                            "未接收到code",
                            "/login"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        return loginService.loginWX(code);
    }

//    @ApiOperation(value = "用户信息更新接口", httpMethod = "POST")
//    @PostMapping("/userInfo")
//    public ResponseEntity<Object> userInfo(@RequestBody )

}
