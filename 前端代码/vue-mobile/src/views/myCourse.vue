<template>
    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg"/>
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <van-list v-model="loading" :finished="finished" finished-text="没有更多了" offset="10" @load="onLoad">
                <van-card
                        v-for="(item,index) in list" :key="index"
                        :price="item.finalAmount"
                        :title="item.courseName"
                        :desc="'总时长：'+item.durationSum+'分钟'"
                        :thumb="item.cover"
                >
                    <template #tags>
                        <br/>
                        <van-progress :percentage='item.progress' />
                    </template>
                    <template #footer>
                        <van-button size="mini" @click="see(item.courseId)">去观看</van-button>
                    </template>
                </van-card>
            </van-list>
            <!-- offset：滚动条与底部距离小于 offset 时触发load事件 默认300，因此要改小，否则首次进入一直触发  -->
<!--            <van-list v-model="loading" :finished="finished" finished-text="没有更多了" offset="10" @load="onLoad">-->
<!--                <ul class="list">-->
<!--                    <li v-for="(item,index) in list" :key="index">-->
<!--                        <van-image :src="item.cover" round width="100%" height="50px" />-->
<!--                        <h1 class="van-ellipsis">{{ item.courseName }}</h1>-->
<!--                        <div class="list-box">-->
<!--                            <ul>-->
<!--                                <li>观看进度：</li>-->
<!--                                <li><van-progress :percentage='item.progress' /></li>-->
<!--                                <li><span style="color:red;">￥{{ item.finalAmount }}</span></li>-->
<!--                            </ul>-->
<!--                            <br/>-->
<!--                            <p>-->
<!--                                <van-button @click="see(item.courseId)" type="info" size="mini">去观看</van-button>-->
<!--                            </p>-->
<!--                        </div>-->
<!--                    </li>-->
<!--                </ul>-->
<!--            </van-list>-->
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
            orderApi.findCourseListPage(this.pageNo, this.pageSize).then(response => {
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
                        margin: 5px 0;
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

