package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-22
 */
public interface ChapterService extends IService<Chapter> {

    //1 大纲列表（章节和小节列表）
    List<ChapterVo> getTreeList(Long courseId);

    //根据课程id删除章节
    void removeChapterByCourseId(Long id);
}
