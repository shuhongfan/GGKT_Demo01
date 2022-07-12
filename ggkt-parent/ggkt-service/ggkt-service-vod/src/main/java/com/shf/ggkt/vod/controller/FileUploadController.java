package com.shf.ggkt.vod.controller;

import com.shf.ggkt.result.Result;
import com.shf.ggkt.vod.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
//@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param multipartFile
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("upload")
    public Result uploadFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile multipartFile){
        String url = fileService.upload(multipartFile);
        return Result.ok(url).message("上传文件成功");
    }

}
