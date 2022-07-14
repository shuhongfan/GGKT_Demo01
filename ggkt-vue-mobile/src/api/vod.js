import request from '@/utils/request'

const api_name = '/api/vod'

export default {

  // 获取播放凭证
  getPlayAuth(courseId, videoId) {
    return request({
      url: `${api_name}/getPlayAuth/${courseId}/${videoId}`,
      method: 'get'
    })
  }
}
