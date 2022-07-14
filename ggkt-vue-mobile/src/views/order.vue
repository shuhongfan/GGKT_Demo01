<template>
    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg"/>
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <!-- offset：滚动条与底部距离小于 offset 时触发load事件 默认300，因此要改小，否则首次进入一直触发  -->
            <van-list v-model="loading" :finished="finished" finished-text="没有更多了" offset="10" @load="onLoad">
                <ul class="list">
                    <li v-for="(item,index) in list" :key="index">
                        <h1 class="van-ellipsis">{{ item.courseName }}</h1>
                        <div class="list-box">
                            <ul>
                                <li>订单号：{{ item.outTradeNo }}</li>
                                <li>订单金额：<span style="color:red;">￥{{ item.originAmount }} - ￥{{ item.couponReduce }} = ￥{{ item.finalAmount }}</span></li>
                                <li>下单日期：{{ item.createTime }}</li>
                                <li>支付状态：{{ item.orderStatus == 'UNPAID' ? '未支付' : '已支付' }}</li>
                                <li>支付日期：{{ item.payTime }}</li>
                            </ul>
                            <p>
                                <van-button @click="pay(item.id)" v-if="item.orderStatus == 'UNPAID'" type="info" size="mini">去支付</van-button>
                                <van-button @click="see(item.courseId)" v-else type="info" size="mini">去观看</van-button>
                            </p>
                        </div>
                    </li>
                </ul>
            </van-list>
        </van-pull-refresh>
    </div>

</template>

<script>
import orderApi from '@/api/order'

export default {
    name: "Order",

    data() {
        return {
            loading: false,
            finished: false,
            refreshing: false,

            pageNo: 1,
            pageSize: 5,
            pages: 1,
            list: []
        };
    },

    created() {
        // this.fetchData();
    },

    methods: {
        onLoad() {
            debugger
            if(!this.isLoading) {
                this.fetchData();
            }
        },

        onRefresh() {
            // 清空列表数据
            this.finished = false;

            this.pageNo = 1;
            this.fetchData();
        },

        fetchData() {
            orderApi.findListPage(this.pageNo, this.pageSize).then(response => {
                console.log(response.data);
                if (this.refreshing) {
                    this.list = [];
                    this.refreshing = false;
                }
                for (let i=0;i<response.data.records.length;i++) {
                    this.list.push(response.data.records[i]);
                }

                this.pages = response.data.pages;

                this.loading = false;
                if(this.pageNo >= this.pages) {
                    this.finished = true;
                }

                this.pageNo++;
            })
        },

        pay(orderId) {
            this.$router.push({ path: '/pay/'+orderId })
        },

        see(courseId) {
            this.$router.push({ path: '/play/'+courseId+'/0' })
        }
    }
}
</script>

<style lang="scss" scoped>
    .list {
        li {
            margin: 10px;
            padding-bottom: 5px;
            border-bottom: 1px solid #e5e5e5;
            h1 {
                font-size: 20px;
            }
            .list-box {
                display: flex;
                font-size: 14px;
                ul {
                    flex: 1;
                    margin: 0;
                    li {
                        margin: 0;
                        border-bottom: none;
                    }
                }
                p {
                    margin: 0;
                    width: 50px;
                    align-items: center;
                    align-self: flex-end;
                }
            }
        }
    }
</style>

