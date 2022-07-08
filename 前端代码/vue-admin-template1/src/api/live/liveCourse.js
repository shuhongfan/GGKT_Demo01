import request from '@/utils/request'

const api_name = '/admin/live/liveCourse'

export default {

  getPageList(page, limit) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get'
    })
  },

  findLatelyList() {
    return request({
      url: `${api_name}/findLatelyList`,
      method: 'get'
    })
  },

  getById(id) {
    return request({
      url: `${api_name}/getInfo/${id}`,
      method: 'get'
    })
  },

  getLiveCourseAccount(id) {
    return request({
      url: `${api_name}/getLiveCourseAccount/${id}`,
      method: 'get'
    })
  },

  save(liveCourse) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: liveCourse
    })
  },

  updateById(liveCourse) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: liveCourse
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  removeRows(idList) {
    return request({
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: idList
    })
  },

  getCourseConfig(id) {
    return request({
      url: `${api_name}/getCourseConfig/${id}`,
      method: 'get'
    })
  },

  updateConfig(liveCourseConfigVo) {
    return request({
      url: `${api_name}/updateConfig`,
      method: 'put',
      data: liveCourseConfigVo
    })
  },
}