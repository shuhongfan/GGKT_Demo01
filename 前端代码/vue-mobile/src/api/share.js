import request from '@/utils/request'

const api_name = '/api/wechat/share'

export default {

  getSignature(url) {
    return request({
      url: `${api_name}/getSignature?url=${url}`,
      method: 'get'
    })
  }
}
