import request from '@/utils/request'

const API_NAME = '/admin/live/liveCourse'

export default {

  getPageList(page, limit) {
    return request({
      url: `${API_NAME}/${page}/${limit}`,
      method: 'get'
    })
  },

  findLatelyList() {
    return request({
      url: `${API_NAME}/findLatelyList`,
      method: 'get'
    })
  },

  getById(id) {
    return request({
      url: `${API_NAME}/getInfo/${id}`,
      method: 'get'
    })
  },

  getLiveCourseAccount(id) {
    return request({
      url: `${API_NAME}/getLiveCourseAccount/${id}`,
      method: 'get'
    })
  },

  save(liveCourse) {
    return request({
      url: `${API_NAME}/save`,
      method: 'post',
      data: liveCourse
    })
  },

  updateById(liveCourse) {
    return request({
      url: `${API_NAME}/update`,
      method: 'put',
      data: liveCourse
    })
  },
  removeById(id) {
    return request({
      url: `${API_NAME}/remove/${id}`,
      method: 'delete'
    })
  },
  removeRows(idList) {
    return request({
      url: `${API_NAME}/batchRemove`,
      method: 'delete',
      data: idList
    })
  },

  getCourseConfig(id) {
    return request({
      url: `${API_NAME}/getCourseConfig/${id}`,
      method: 'get'
    })
  },

  updateConfig(liveCourseConfigVo) {
    return request({
      url: `${API_NAME}/updateConfig`,
      method: 'put',
      data: liveCourseConfigVo
    })
  }
}
