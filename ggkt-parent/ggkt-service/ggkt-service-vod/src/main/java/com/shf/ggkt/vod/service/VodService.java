package com.shf.ggkt.vod.service;

import java.util.Map;

public interface VodService {
    String uploadVideo();

    void removeVideo(String fileId);

    Map<String, Object> getPlayAuth(Long courseId, Long videoId);
}
