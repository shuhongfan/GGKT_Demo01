<template>
    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg" />
        <div >
          <van-form style="margin: 10px 10px;">
              <div style="text-align:center"><h2>绑定手机号</h2></div>
              <van-row>
                  <van-col span="24">
                      <van-field
                              v-model="phone"
                              center
                              name="phone"
                              label="手机号"
                              placeholder="手机号"
                              maxlength="11"
                      />
                  </van-col>
              </van-row>
              <van-row>
                  <van-col span="16">
                      <van-field
                              v-model="code"
                              center
                              name="code"
                              label="验证码"
                              placeholder="验证码"
                              maxlength="6"
                      />
                  </van-col>
                  <van-col span="8">
                      <van-button @click="gainCode" type="primary" size="small">{{ time }}</van-button>
                  </van-col>
              </van-row>
              <div style="margin: 16px;">
                  <van-button round block type="info" @click="onSubmit">提交</van-button>
              </div>
          </van-form>
        </div>
        <div style="padding: 20px 25px;line-height:30px">
            <p>
                注意：<br/>
                手机号码是会员的重要凭证，只要正确绑定手机号码才能观看视频直播与点播。
            </p>
        </div>
  </div>
</template>

<script>
import smsApi from '@/api/sms'
import userInfoApi from '@/api/userInfo'
export default {
    data() {
        return {
            show: true,

            phone: '',
            code: '',
            time: '获取验证码',
            isClick: true
        };
    },

    created() {

    },

    methods: {
        onSubmit() {
            if (!/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(this.phone)) {
                alert('请输入正确的手机号码');
                return;
            }
            if(this.code == '') {
                alert('手机验证码必须输入不正确');
                return;
            }

            userInfoApi.bindPhone({
                'phone': this.phone,
                'code': this.code
            }).then(response => {
                console.log(response.message)
                //用户信息设置手机号码
                let userInfoString = window.localStorage.getItem('userInfo') || '';
                if(userInfoString != '') {
                    let userInfo = JSON.parse(userInfoString)
                    userInfo.phone = this.phone;
                    window.localStorage.setItem('userInfo', JSON.stringify(userInfo));
                }
                this.$router.push({ path: '/bindSecond' })
            })
        },

        gainCode() {
            if (this.isClick) {
                if (/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(this.phone)) {
                    smsApi.send(this.phone).then(response => {
                        console.log(response.data);
                        this.code = response.data

                        this.isClick = false
                        let s = 60
                        this.time = s + 's'
                        let interval = setInterval(() => {
                            s--
                            this.time = s + 's'
                            if (s < 0) {
                                this.time = '重新获取'
                                this.isClick = true
                                clearInterval(interval)
                            }
                        }, 1000)
                    });
                } else {
                    alert('请输入正确的手机号码')
                }
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
        }
    }
};
</script>
<style lang="scss">

</style>
