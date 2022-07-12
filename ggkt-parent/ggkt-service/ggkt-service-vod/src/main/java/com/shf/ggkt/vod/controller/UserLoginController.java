package com.shf.ggkt.vod.controller;


import com.shf.ggkt.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

//@CrossOrigin // 开启跨域
@RestController
@RequestMapping("/admin/vod/user")
public class UserLoginController {
    /**
     * 登录接口
     * @return
     */
    @PostMapping("login")
    public Result login() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }

    /**
     * info
     * @return
     */
    @GetMapping("info")
    public Result info(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("introduction", "I am a super administrator");
        map.put("name", "Super Admin");
        return Result.ok(map);
    }
}
