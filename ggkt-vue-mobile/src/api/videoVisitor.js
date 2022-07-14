import request from '@/utils/request'

const api_name = '/api/vod/videoVisitor'

export default {

  save(videoVisitor) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: videoVisitor
    })
  }
}
