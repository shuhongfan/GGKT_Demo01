import request from '@/utils/request'

const API_NAME = '/admin/activity/couponInfo'

export default {

  getPageList(page, limit) {
    return request({
      url: `${API_NAME}/${page}/${limit}`,
      method: 'get'
    })
  },
  getById(id) {
    return request({
      url: `${API_NAME}/get/${id}`,
      method: 'get'
    })
  },

  save(role) {
    return request({
      url: `${API_NAME}/save`,
      method: 'post',
      data: role
    })
  },

  updateById(role) {
    return request({
      url: `${API_NAME}/update`,
      method: 'put',
      data: role
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

  getPageCouponUseList(page, limit, searchObj) {
    return request({
      url: `${API_NAME}/couponUse/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  }
}
