import request from '@/utils/request'

const api_name = '/api/user/sms'

export default {

  // 添加购物车
  send(phone) {
    return request({
      url: `${api_name}/send/${phone}`,
      method: 'get'
    })
  }
}
