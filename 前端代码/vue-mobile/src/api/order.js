import request from '@/utils/request'

const api_name = '/api/order'

export default {

    // 订单支付
    createJsapi(orderNo) {
        return request({
            url: `${api_name}/wxPay/createJsapi/${orderNo}`,
            method: 'get'
        })
    },

    // 订单信息
    getInfo(orderId) {
        return request({
            url: `${api_name}/orderInfo/getInfo/${orderId}`,
            method: 'get'
        })
    },

    submitOrder(orderFormVo) {
        return request({
            url: `${api_name}/orderInfo/submitOrder`,
            method: 'post',
            data: orderFormVo
        })
    },

    queryPayStatus(orderNo) {
        return request({
            url: `${api_name}/wxPay/queryPayStatus/${orderNo}`,
            method: 'get'
        })
    },

    findListPage(pageNo, pageSize) {
        return request({
            url: `${api_name}/orderInfo/${pageNo}/${pageSize}`,
            method: 'get'
        })
    },

    findCourseListPage(pageNo, pageSize) {
        return request({
            url: `${api_name}/orderInfo/course/${pageNo}/${pageSize}`,
            method: 'get'
        })
    }
}
