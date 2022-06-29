<template>
    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg"/>
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
                <ul class="list" @click="popoverClick">
                    <li v-for="(item,index) in list" :key="index">
                        <h1 class="van-ellipsis">{{ item.couponName }}</h1>
                        <div class="list-box">
                            <ul>
                                <li>优惠券类型：{{ item.couponType == 1 ? '注册卷' : '推荐赠送卷' }}</li>
                                <li>订单金额：<span style="color:red;">￥{{ item.amount }}</span></li>
                                <li>领取日期：{{ item.getTime }}</li>
                                <li>失效日期：{{ item.expireTime }}</li>
                                <li>只用状态：{{ item.couponStatus == 0 ? '未使用' : '已使用' }}</li>
                                <li>使用规则：{{ item.ruleDesc }}</li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </van-list>
        </van-pull-refresh>
    </div>

</template>

<script>
import couponApi from '@/api/coupon'

export default {
    name: "Order",

    data() {
        return {
            loading: false,
            finished: false,
            refreshing: false,

            pageNo: 1,
            pageSize: 4,
            pages: 1,
            list: [],
            popoverIndex: -1
        };
    },

    created() {
        // this.fetchData();
    },

    methods: {
        onLoad() {
            this.fetchData();
        },

        onRefresh() {
            // 清空列表数据
            this.finished = false;

            this.pageNo = 1;
            this.fetchData();
        },

        fetchData() {
            this.loading = true;

            couponApi.findListPage(this.pageNo, this.pageSize).then(response => {
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

        popoverClick(e) {
            this.popoverIndex = e.target.dataset.index;
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

