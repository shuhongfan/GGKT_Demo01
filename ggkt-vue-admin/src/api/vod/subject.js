import request from '@/utils/request'

const API_NAME = '/admin/vod/subject'

export default {
  /**
   * 课程分类列表
   * @param id
   * @returns {*}
   */
  getChildList(id) {
    return request({
      url: `${API_NAME}/getChildrenSubject/${id}`,
      method: 'get'
    })
  }
}
