<template>

    <div>
        <van-image width="100%" height="200" :src="courseVo.cover"/>

        <h1 class="van-ellipsis course_title">{{ courseVo.title }}</h1>

        <div class="course_teacher_price_box">
            <div class="course_teacher_price">
                <div class="course_price">价格：</div>
                <div class="course_price_number">￥{{ courseVo.price }}</div>
            </div>
        </div>
        <div class="course_teacher_price_box">
            <div class="course_teacher_box">
                <div class="course_teacher">主讲： {{ teacher.name }}</div>
                <van-image :src="teacher.avatar" round width="50px" height="50px" />
            </div>
        </div>

        <van-loading vertical="true" v-show="loading">加载中...</van-loading>

        <div style="position:fixed;left:0px;bottom:50px;width:100%;height:50px;z-index:999;">
            <!-- 优惠券单元格 -->
            <van-coupon-cell
                    :coupons="coupons"
                    :chosen-coupon="chosenCoupon"
                    @click="showList = true"
            />
            <!-- 优惠券列表 -->
            <van-popup
                    v-model="showList"
                    round
                    position="bottom"
                    style="height: 90%; padding-top: 4px;"
            >
                <van-coupon-list
                        :coupons="coupons"
                        :chosen-coupon="chosenCoupon"
                        :disabled-coupons="disabledCoupons"
                        @change="onChange"
                />
            </van-popup>
        </div>

        <van-goods-action>
            <van-submit-bar :price="finalAmount" button-text="确认下单" @submit="sureOrder"/>
        </van-goods-action>
    </div>
</template>

<script>
import courseApi from '@/api/course'
import orderApi from '@/api/order'
import couponApi from '@/api/coupon'
export default {
    data() {
        return {
            loading: false,

            courseId: null,
            courseVo: {},
            teacher: {},

            orderId: null,

            showList:false,
            chosenCoupon: -1,
            coupons: [],
            disabledCoupons: [],
            couponId: null,
            couponUseId: null,

            couponReduce: 0,
            finalAmount: 0
        };
    },

    created() {
        this.courseId = this.$route.params.courseId;
        this.fetchData()
        this.getCouponInfo();
    },

    methods: {
        onChange(index) {
            debugger
            this.showList = false;
            this.chosenCoupon = index;

            this.couponId = this.coupons[index].id;
            this.couponUseId = this.coupons[index].couponUseId;
            this.couponReduce = this.coupons[index].value;
            this.finalAmount = parseFloat(this.finalAmount) - parseFloat(this.couponReduce)
        },

        fetchData() {
            //debugger
            this.loading = true;
            courseApi.getInfo(this.courseId).then(response => {
                // console.log(response.data);
                this.courseVo = response.data.courseVo;
                this.teacher = response.data.teacher;
                //转换为分
                this.finalAmount = parseFloat(this.courseVo.price)*100;

                this.loading = false;
            });
        },


        getCouponInfo() {
            //debugger
            couponApi.findCouponInfo().then(response => {
                // console.log(response.data);
                this.coupons = response.data.abledCouponsList;
                this.disabledCoupons = response.data.disabledCouponsList;
            });
        },

        sureOrder() {
            //debugger
            this.loading = true;
            let orderFormVo = {
                'courseId': this.courseId,
                'couponId': this.couponId,
                'couponUseId': this.couponUseId
            }
            orderApi.submitOrder(orderFormVo).then(response => {
                console.log(response.data)
                this.$router.push({ path: '/pay/'+response.data })
            })
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
