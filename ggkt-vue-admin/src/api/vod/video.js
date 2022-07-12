import request from '@/utils/request'

const API_NAME = '/admin/vod/video'

export default {

  save(video) {
    return request({
      url: `${API_NAME}/save`,
      method: 'post',
      data: video
    })
  },

  getById(id) {
    return request({
      url: `${API_NAME}/get/${id}`,
      method: 'get'
    })
  },

  updateById(video) {
    return request({
      url: `${API_NAME}/update`,
      method: 'put',
      data: video
    })
  },

  removeById(id) {
    return request({
      url: `${API_NAME}/remove/${id}`,
      method: 'delete'
    })
  }
}
