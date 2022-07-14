import request from '@/utils/request'

const API_NAME = '/admin/wechat/menu'

export default {

  findMenuInfo() {
    return request({
      url: `${API_NAME}/findMenuInfo`,
      method: `get`
    })
  },

  findOneMenuInfo() {
    return request({
      url: `${API_NAME}/findOneMenuInfo`,
      method: `get`
    })
  },

  save(menu) {
    return request({
      url: `${API_NAME}/save`,
      method: `post`,
      data: menu
    })
  },

  getById(id) {
    return request({
      url: `${API_NAME}/get/${id}`,
      method: `get`
    })
  },

  updateById(menu) {
    return request({
      url: `${API_NAME}/update`,
      method: `put`,
      data: menu
    })
  },

  syncMenu() {
    return request({
      url: `${API_NAME}/syncMenu`,
      method: `get`
    })
  },

  removeById(id) {
    return request({
      url: `${API_NAME}/remove/${id}`,
      method: 'delete'
    })
  },

  removeMenu() {
    return request({
      url: `${API_NAME}/removeMenu`,
      method: `delete`
    })
  }
}
