<template>
  <div id="app">
    <div id="nav">
<!--      <router-link to="/">列表页</router-link> |-->
<!--      <router-link to="/info">详情页</router-link> |-->
<!--      <router-link to="/list">列表页</router-link> |-->
<!--      <router-link to="/order">下单页</router-link>-->
        <van-button round block type="info" @click="clearData">清空localStorage</van-button>
    </div>
    <router-view />
  </div>
</template>
<script>
import userInfoApi from '@/api/userInfo'
export default {
    data() {
        return {
            show: true
        };
    },

    created() {
        // 处理微信授权登录
       // this.wechatLogin();
    },

    methods: {
        wechatLogin() {
            // 处理微信授权登录
            let token = this.getQueryString('token') || '';
            if(token != '') {
                window.localStorage.setItem('token', token);
            }

            // 所有页面都必须登录，两次调整登录，这里与接口返回208状态
            token = window.localStorage.getItem('token') || '';
            if (token == '') {
                let url = window.location.href.replace('#', 'guiguketan')
                window.location = 'http://ggkt.vipgz1.91tunnel.com/api/user/wechat/authorize?returnUrl=' + url
            }
            console.log('token2：'+window.localStorage.getItem('token'));

            //绑定手机号处理
            // if(token != '') {
            //     this.bindPhone();
            // }
        },

        bindPhone() {
            let userInfoString = window.localStorage.getItem('userInfo') || '';
            alert('3:'+userInfoString);
            if(userInfoString!='') {
                alert('4:'+userInfoString);
                let userInfo = JSON.parse(userInfoString)
                let phone = userInfo.phone || '';
                if(phone == '') {
                    this.$router.push({ path: '/bindFirst' })
                }
            } else {
                alert('5:'+userInfoString);
                userInfoApi.getCurrentUserInfo().then(response => {
                    window.localStorage.setItem('userInfo', JSON.stringify(response.data));
                    alert('6:'+JSON.stringify(response.data));
                    let phone = response.data.phone || '';
                    console.log('phone:'+phone);
                    if(phone == '') {
                        this.$router.push({ path: '/bindFirst' })
                    }
                })
            }
        },

        getQueryString (paramName) {
            if(window.location.href.indexOf('?') == -1) return '';

            let searchString = window.location.href.split('?')[1];
            let i, val, params = searchString.split("&");

            for (i=0;i<params.length;i++) {
                val = params[i].split("=");
                if (val[0] == paramName) {
                    return val[1];
                }
            }
            return '';
        },

        clearData() {
            window.localStorage.setItem('token', '');
            window.localStorage.setItem('userInfo', '');
            let token = window.localStorage.getItem('token');
            alert('token:'+token);
        }
    }
};
</script>
<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
