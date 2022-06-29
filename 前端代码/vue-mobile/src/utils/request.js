import axios from 'axios'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://ggkt.vipgz1.91tunnel.com', // api 的 base_url
    //baseURL: 'http://glkt-api.atguigu.cn',
    timeout: 30000 // 请求超时时间

})

// http request 拦截器
service.interceptors.request.use(config => {
        //获取localStorage里面的token值
        let token = window.localStorage.getItem('token') || '';
        if (token != '') {
            //把token值放到header里面
            config.headers['token'] = token; // 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSCjAK0A0Ndg1S0lFKrShQsjI0MzY3MTQxMTbWUSotTi3yTAGKQZh-ibmpQB1KtQARkjypPAAAAA.B6dziXWxcc2mIYYaDQnXB1t0IHwQK-GwWNFsAQ0Z7CbCBVb11uoNjojWYotC8YEdlVW9Ahtq99LWtz1_Wbhhlw';//cookie.get('guli_token');
        }
        return config
    },
    err => {
        return Promise.reject(err);
    })
// http response 拦截器
service.interceptors.response.use(response => {
        if (response.data.code == 208) {
            //debugger
            // 替换# 后台获取不到#后面的参数
            let url = window.location.href.replace('#', 'guiguketan')
            window.location = 'http://ggkt.vipgz1.91tunnel.com/api/user/wechat/authorize?returnUrl=' + url
        } else {
            if (response.data.code == 20000) {
                return response.data
            } else {
                console.log("response.data:" + JSON.stringify(response.data))
                alert(response.data.message || 'error')
                return Promise.reject(response)
            }
        }
    },
    error => {
        return Promise.reject(error.response)   // 返回接口返回的错误信息
    });

export default service
