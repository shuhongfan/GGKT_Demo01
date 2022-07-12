package com.shf.ggkt.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.vod.Chapter;
import com.shf.ggkt.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getNestedTreeList(Long courseId);

    void removeChapterByCourseId(Long id);
}
