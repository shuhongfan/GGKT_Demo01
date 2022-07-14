import request from '@/utils/request'

const api_name = '/api/user/userInfo'

export default {

  // 添加购物车
  bindPhone(bindPhoneVo) {
    return request({
      url: `${api_name}/bindPhone`,
      method: 'post',
      data: bindPhoneVo
    })
  },

  getCurrentUserInfo() {
    return request({
      url: `${api_name}/getCurrentUserInfo`,
      method: 'get'
    })
  }
}
