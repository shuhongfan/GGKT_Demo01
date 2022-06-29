package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.vod.mapper.ChapterMapper;
import com.atguigu.ggkt.vod.service.ChapterService;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-22
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    //1 大纲列表（章节和小节列表）
    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        //定义最终数据list集合
        List<ChapterVo> finalChapterList = new ArrayList<>();

        //根据courseId获取课程里面所有章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        //根据courseId获取课程里面所有小节
        LambdaQueryWrapper<Video> wrapperVideo = new LambdaQueryWrapper<>();
        wrapperVideo.eq(Video::getCourseId,courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

        //封装章节
        //遍历所有章节
        for (int i = 0; i < chapterList.size(); i++) {
            //得到课程每个章节
            Chapter chapter = chapterList.get(i);
            // chapter -- ChapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            //得到每个章节对象放到finalChapterList集合
            finalChapterList.add(chapterVo);

            //封装章节里面小节
            //创建list集合用户封装章节所有小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //遍历小节list
            for (Video video:videoList) {
                //判断小节是哪个章节下面
                //章节id  和 小节chapter_id
                if(chapter.getId().equals(video.getChapterId())) {
                    // video  -- VideoVo
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    //放到videoVoList
                    videoVoList.add(videoVo);
                }
            }
            //把章节里面所有小节集合放到每个章节里面
            chapterVo.setChildren(videoVoList);
        }
        return finalChapterList;
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
