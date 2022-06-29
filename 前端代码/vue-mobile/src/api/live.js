import request from '@/utils/request'

const api_name = '/api/live/liveCourse'

export default {

  list() {
    return request({
      url: `${api_name}/list`,
      method: 'get'
    })
  },

  // 直播详情
  getInfo(liveCourseId) {
    return request({
      url: `${api_name}/getInfo/${liveCourseId}`,
      method: 'get'
    })
  },

  getPlayAuth(liveCourseId) {
    return request({
      url: `${api_name}/getPlayAuth/${liveCourseId}`,
      method: 'get'
    })
  }
}
