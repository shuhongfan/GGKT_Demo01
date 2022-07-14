<template>
    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg"/>
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <!-- offset：滚动条与底部距离小于 offset 时触发load事件 默认300，因此要改小，否则首次进入一直触发  -->
            <van-list v-model="loading" :finished="finished" finished-text="没有更多了" offset="10" @load="onLoad">
                <van-card
                        v-for="(item,index) in list" :key="index"
                        :price="item.price"
                        :title="item.title"
                        :thumb="item.cover"
                >
                    <template #tags>
                        <van-tag round plain color="#ffe1e1" text-color="#ad0000">课时数：{{ item.lessonNum }}</van-tag>
                        <br/>
                        <van-tag round plain color="#ffe1e1" text-color="#ad0000">购买数：{{ item.buyCount }}</van-tag>
                        <br/>
                        <van-tag round plain color="#ffe1e1" text-color="#ad0000">访问量：{{ item.viewCount }}</van-tag>
                    </template>
                    <template #footer>
                        <van-button size="mini" @click="info(item.id)">去看看</van-button>
                    </template>
                </van-card>
            </van-list>
        </van-pull-refresh>
    </div>
</template>

<script>
import courseApi from '@/api/course'

export default {
    name: "Course",

    data() {
        return {
            subjectParentId: 1,

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
        this.subjectParentId = this.$route.params.subjectId;
    },

    methods: {
        onLoad() {
            //debugger
            this.fetchData();
        },

        onRefresh() {
            // 清空列表数据
            this.finished = false;

            this.pageNo = 1;
            // 重新加载数据
            // 将 loading 设置为 true，表示处于加载状态
            this.loading = true;
            this.fetchData();
        },

        fetchData() {
            courseApi.findPage(this.subjectParentId, this.pageNo, this.pageSize).then(response => {
                console.log(response.data);
                if (this.refreshing) {
                    this.list = [];
                    this.refreshing = false;
                }
                for (let i=0;i<response.data.records.length;i++) {
                    this.list.push(response.data.records[i]);
                }
                this.pages = response.data.totalPage;

                this.loading = false;
                if(this.pageNo >= this.pages) {
                    this.finished = true;
                }

                this.pageNo++;
            });
        },

        info(id) {
            this.$router.push({path: '/courseInfo/' + id})
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
