package com.shf.ggkt.vod.controller;


import com.shf.ggkt.model.vod.Chapter;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.vod.ChapterVo;
import com.shf.ggkt.vod.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
@RestController
//@CrossOrigin
@RequestMapping("/admin/vod/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

//    1.大纲列表（章节和小节列表）
    @ApiOperation("大纲列表")
    @GetMapping("getNestedTreeList/{courseId}")
    public Result getTreeList(@PathVariable Long courseId) {
        List<ChapterVo> list = chapterService.getNestedTreeList(courseId);
        return Result.ok(list);
    }

//    2. 添加章节
    @ApiOperation("添加章节")
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return Result.ok(null);
    }

    //    3. 修改-根据id查询
    @ApiOperation("修改-根据id查询")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    //    4. 修改-最终实现
    @ApiOperation("修改-最终实现")
    @PutMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    //    5. 删除章节
    @ApiOperation("删除章节")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok();
    }

}

