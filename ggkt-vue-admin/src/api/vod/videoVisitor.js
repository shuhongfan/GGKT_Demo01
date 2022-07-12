import request from '@/utils/request'

const API_NAME = '/admin/vod/videoVisitor'

export default {

  findCount(courseId, startDate, endDate) {
    return request({
      url: `${API_NAME}/findCount/${courseId}/${startDate}/${endDate}`,
      method: 'get'
    })
  }
}
