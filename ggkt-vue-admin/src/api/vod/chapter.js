import request from '@/utils/request'

const API_NAME = '/admin/vod/chapter'

export default {

  getNestedTreeList(courseId) {
    return request({
      url: `${API_NAME}/getNestedTreeList/${courseId}`,
      method: 'get'
    })
  },

  removeById(id) {
    return request({
      url: `${API_NAME}/remove/${id}`,
      method: 'delete'
    })
  },

  save(chapter) {
    return request({
      url: `${API_NAME}/save`,
      method: 'post',
      data: chapter
    })
  },

  getById(id) {
    return request({
      url: `${API_NAME}/get/${id}`,
      method: 'get'
    })
  },

  updateById(chapter) {
    return request({
      url: `${API_NAME}/update`,
      method: 'put',
      data: chapter
    })
  }
}
