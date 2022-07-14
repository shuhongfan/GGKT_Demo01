import request from '@/utils/request'

const api_name = '/api/vod/course'

export default {

  // 课程分页列表
  findPage(subjectParentId, pageNo, pageSize) {
    return request({
      url: `${api_name}/${subjectParentId}/${pageNo}/${pageSize}`,
      method: 'get'
    })
  },

  // 课程详情
  getInfo(courseId) {
    return request({
      url: `${api_name}/getInfo/${courseId}`,
      method: 'get'
    })
  }
}
