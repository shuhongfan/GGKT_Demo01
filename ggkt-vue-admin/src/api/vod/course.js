import request from '@/utils/request'

const API_NAME = '/admin/vod/course'

export default {
  // 课程列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `${API_NAME}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  // 添加课程基本信息
  saveCourseInfo(courseInfo) {
    return request({
      url: `${API_NAME}/save`,
      method: 'post',
      data: courseInfo
    })
  },
  // id获取课程信息
  getCourseInfoById(id) {
    return request({
      url: `${API_NAME}/get/${id}`,
      method: 'get'
    })
  },
  // 修改课程信息
  updateCourseInfoById(courseInfo) {
    return request({
      url: `${API_NAME}/update`,
      method: 'put',
      data: courseInfo
    })
  },
  // 获取发布课程信息
  getCoursePublishById(id) {
    return request({
      url: `${API_NAME}/getCoursePublishVo/${id}`,
      method: 'get'
    })
  },
// 发布课程
  publishCourseById(id) {
    return request({
      url: `${API_NAME}/publishCourseById/${id}`,
      method: 'put'
    })
  },
  removeById(id) {
    return request({
      url: `${API_NAME}/remove/${id}`,
      method: 'delete'
    })
  }
}
