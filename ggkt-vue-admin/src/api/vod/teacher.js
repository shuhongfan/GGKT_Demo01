import request from '@/utils/request'

const API_NAME = '/admin/vod/teacher'

export default {
  /**
   * 讲师条件查询分页
   * @param current 当前页
   * @param limit 每页记录数
   * @param searchObj 条件对象
   * @returns {*}
   */
  pageList(current, limit, searchObj) {
    return request({
      url: `${API_NAME}/findQueryPage/${current}/${limit}`,
      method: 'post',
      // 使用json格式传递，写法 data：searchObj
      // 使用普通格式传递，写法 params：searchObj
      data: searchObj
    })
  },

  /**
   * 通过讲师id删除讲师
   * @param id
   * @returns {*}
   */
  removeTeacherById(id) {
    return request({
      url: `${API_NAME}/remove/${id}`,
      method: 'delete'
    })
  },

  /**
   * 讲师添加
   * @param teacher
   * @returns {*}
   */
  saveTeacher(teacher) {
    return request({
      url: `${API_NAME}/saveTeacher`,
      method: 'post',
      data: teacher
    })
  },

  /**
   * 根据id查询讲师
   * @param id
   * @returns {*}
   */
  getTeacherById(id) {
    return request({
      url: `${API_NAME}/getTeacher/${id}`,
      method: 'get'
    })
  },

  /**
   * 讲师修改
   * @param teacher
   * @returns {*}
   */
  updateTeacherById(teacher) {
    return request({
      url: `${API_NAME}/updateTeacher`,
      method: 'put',
      data: teacher
    })
  },

  /**
   * 批量删除
   * @param idList
   */
  batchRemove(idList) {
    return request({
      url: `${API_NAME}/removeBatch`,
      method: 'delete',
      data: idList
    })
  },

  /**
   * 查询所有讲师
   * @returns {*}
   */
  list() {
    return request({
      url: `${API_NAME}/findAll`,
      method: 'get'
    })
  }
}
