import request from '@/utils/request'

const API_NAME = '/admin/order/orderInfo'

export default {

  getPageList(page, limit, searchObj) {
    return request({
      url: `${API_NAME}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  }
}
