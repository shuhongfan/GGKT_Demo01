package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/vod/user")
//@CrossOrigin //跨域
public class UserLoginController {

    //login
    @PostMapping("login")
    public Result login() {
        //{"code":20000,"data":{"token":"admin-token"}}
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    //info
    @GetMapping("info")
    public Result info() {
        //{"code":20000,"data":
        // {"roles":["admin"],
        // "introduction":"I am a super administrator",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        // "name":"Super Admin"}}
        Map<String,Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return Result.ok(map);
    }
}
