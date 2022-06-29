import request from '@/utils/request'

const api_name = '/api/activity/couponInfo'

export default {

  findCouponInfo() {
    return request({
      url: `${api_name}/findCouponInfo`,
      method: 'get'
    })
  },

  findListPage(pageNo, pageSize) {
    return request({
      url: `${api_name}/${pageNo}/${pageSize}`,
      method: 'get'
    })
  }
}
