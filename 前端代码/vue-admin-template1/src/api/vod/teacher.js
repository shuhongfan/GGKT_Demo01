import request from '@/utils/request'

const api_name = '/admin/vod/teacher'

export default {
    //讲师条件查询分页
    // current当前页  limit每页记录数 searchObj条件对象
    pageList(current,limit,searchObj) {
        return request({
            url: `${api_name}/findQueryPage/${current}/${limit}`,
            method: 'post',
            //使用json格式传递  写法 data:searchObj
            //使用普通格式传递  写法 params:searchObj
            data:searchObj
          })
    },
    //讲师删除
    removeTeacherId(id) {
        return request({
            url: `${api_name}/remove/${id}`,
            method: 'delete'
          })
    },
    //讲师添加
    saveTeacher(teacher) {
        return request({
            url: `${api_name}/saveTeacher`,
            method: 'post',
            data:teacher
          })
    },
    //根据id查询
    getTeacherById(id) {
        return request({
            url: `${api_name}/getTeacher/${id}`,
            method: 'get'
          })
    },
    //讲师修改
    updateTeacher(teacher) {
        return request({
            url: `${api_name}/updateTeacher`,
            method: 'post',
            data:teacher
          })
    },
    //批量删除
    batchRemove(idList) {
        return request({
          url: `${api_name}/removeBatch`,
          method: `delete`,
          data: idList
        })
    },
    //所有讲师
    list() {
      return request({
        url: `${api_name}/findAll`,
        method: `get`
      })
  }
}
