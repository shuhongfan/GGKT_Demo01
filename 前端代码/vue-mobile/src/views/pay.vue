<template>

    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg"/>

        <h1 class="van-ellipsis course_title">课程名称: {{ orderInfo.courseName }}</h1>

        <div class="course_teacher_price_box">
            <div class="course_price">订单号：{{ orderInfo.outTradeNo }}</div>
        </div>
        <div class="course_teacher_price_box">
            <div class="course_price">下单时间：{{ orderInfo.createTime }}</div>
        </div>
        <div class="course_teacher_price_box">
            <div class="course_price">支付状态：{{ orderInfo.orderStatus == '0' ? '未支付' : '已支付' }}</div>
        </div>
        <div class="course_teacher_price_box" v-if="orderInfo.orderStatus == '1'">
            <div class="course_price">支付时间：{{ orderInfo.payTime }}</div>
        </div>
        <van-divider />
        <div class="course_teacher_price_box">
            <div class="course_price">订单金额：<span style="color: red">￥{{ orderInfo.originAmount }}</span></div>
        </div>
        <div class="course_teacher_price_box">
            <div class="course_price">优惠券金额：<span style="color: red">￥{{ orderInfo.couponReduce }}</span></div>
        </div>
        <div class="course_teacher_price_box">
            <div class="course_price">支付金额：<span style="color: red">￥{{ orderInfo.finalAmount }}</span></div>
        </div>

        <van-goods-action>
            <van-goods-action-button type="danger" text="支付" @click="pay" v-if="orderInfo.orderStatus == '0'"/>
            <van-goods-action-button type="warning" text="去观看" @click="see" v-else/>
        </van-goods-action>

        <van-loading vertical="true" v-show="loading">加载中...</van-loading>
    </div>
</template>

<script>
import orderApi from '@/api/order'
export default {
    data() {
        return {
            loading: false,
            orderId: null,
            orderInfo: {},
            showList:false,
            chosenCoupon: -1,
            coupons: [],
            disabledCoupons: [],
            couponReduce: 0,
            finalAmount: 0
        };
    },
    created() {
        this.orderId = this.$route.params.orderId;
        this.fetchData();
    },
    methods: {
        fetchData() {
            this.loading = true;
            orderApi.getInfo(this.orderId).then(response => {
                this.orderInfo = response.data;
                alert(this.orderInfo.orderStatus)
                this.finalAmount = parseFloat(this.orderInfo.finalAmount) * 100;

                this.loading = false;
            });
        },
        pay() {
            this.loading = true;
            orderApi.createJsapi(this.orderInfo.outTradeNo).then(response => {
                console.log(response.data)
                this.loading = false;
                this.onBridgeReady(response.data)
            })
        },
        onBridgeReady(data) {
            let that = this;
            console.log(data)
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                   'appId': data.appId,     //公众号ID，由商户传入
                    'timeStamp': data.timeStamp,         //时间戳，自1970年以来的秒数
                    'nonceStr': data.nonceStr, //随机串
                    'package': data.package,
                    'signType': data.signType,         //微信签名方式：
                    'paySign': data.paySign //微信签名
                },
                function (res) {
                    if (res.err_msg == 'get_brand_wcpay_request:ok') {
                        // 使用以上方式判断前端返回,微信团队郑重提示：
                        //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                        console.log('支付成功')                      
                        that.queryPayStatus();
                    }
                });
        },
        queryPayStatus() {
            // 回调查询
            orderApi.queryPayStatus(this.orderInfo.outTradeNo).then(response => {
                console.log(response.data)
                this.fetchData()
            })
        },
        see() {
            this.$router.push({path: '/courseInfo/' + this.orderInfo.courseId})
        }
    }
};
</script>
<style lang="scss" scoped>
    .gap {
        height: 10px;
    }

    ::v-deep.van-image {
        display: block;
    }

    .course_count {
        background-color: #82848a;
        color: white;
        padding: 5px;
        text-align: center;
        border-right: 1px solid #939393;

        h1 {
            font-size: 14px;
            margin: 0;
        }

        p {
            margin: 0;
            font-size: 16px;
        }
    }

    .course_title {
        font-size: 20px;
        margin: 10px;
    }

    .course_teacher_price_box {
        margin: 10px;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .course_teacher_price {
            display: flex;
            font-size: 14px;
            align-items: center;

            .course_price_number {
                color: red;
                font-size: 18px;
                font-weight: bold;
            }
        }

        .course_teacher_box {
            display: flex;
            justify-content: center;
            align-items: center;

            .course_teacher {
                margin-right: 20px;
            }
        }
    }

    .course_contents {
        margin: 10px;

        .course_title_font {
            color: #68cb9b;
            font-weight: bold;
        }

        .course_content {
            margin-bottom: 20px;
        }
    }

    .course_chapter_list {
        display: flex;
        justify-content: space-between;
        align-items: center;

        h2 {
            font-size: 14px;
        }

        p {
            margin: 0;
        }
    }
</style>
