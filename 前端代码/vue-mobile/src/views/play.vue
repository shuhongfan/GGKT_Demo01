<template>
    <div>
        <video id="player-container-id" preload="auto" width="600" height="400" playsinline webkit-playsinline x5-playsinline></video>
        <h1 class="van-ellipsis course_title">{{ courseVo.title }}</h1>

        <div class="course_teacher_price_box">
            <div class="course_teacher_price">
                <div class="course_price">价格：</div>
                <div class="course_price_number">￥{{ courseVo.price }}</div>
                <div class="course_teacher">主讲： {{ courseVo.teacherName }}</div>
            </div>
            <div>
                <van-button @click="getPlayAuth('0')" v-if="isBuy || courseVo.price == '0.00'" plain type="warning" size="mini">立即观看</van-button>
                <van-button @click="buy" v-else plain type="warning" size="mini">立即购买</van-button>
            </div>
        </div>

        <div class="course_contents">
            <div class="course_title_font">课程大纲</div>
            <div class="gap"></div>
            <van-collapse v-model="activeNames">
                <van-collapse-item :title="item.title" :name="item.id" v-for="item in chapterVoList" :key="item.id">
                    <ul class="course_chapter_list" v-for="child in item.children" :key="child.id">
                        <h2 :style="activeVideoId == child.id ? 'color:blue' : ''">{{child.title}}</h2>
                        <p v-if="child.isFree == 1">
                            <van-button @click="see(child)" type="warning" size="mini" plain>免费观看</van-button>
                        </p>
                        <p v-else>
                            <van-button @click="see(child)" type="warning" size="mini" plain>观看</van-button>
                        </p>
                    </ul>
                </van-collapse-item>
            </van-collapse>
        </div>

        <van-loading vertical="true" v-show="loading">加载中...</van-loading>
    </div>
</template>

<script>
import courseApi from '@/api/course'
import vodApi from '@/api/vod'
// import videoVisitorApi from '@/api/videoVisitor'
export default {
    data() {
        return {
            loading: false,

            courseId: null,
            videoId: null,

            courseVo: {},
            description: '',
            chapterVoList: [],
            isBuy: false,
            // firstVideo: null,

            activeNames: ["1"],
            activeVideoId: 0, //记录当前正在播放的视频
            player: null
        };
    },

    created() {
        this.courseId = this.$route.params.courseId;
        this.videoId = this.$route.params.videoId || '0';

        this.fetchData();
        this.getPlayAuth(this.videoId);
    },

    methods: {
        fetchData() {
            this.loading = true;
            courseApi.getInfo(this.courseId).then(response => {
                console.log(response.data);

                this.courseVo = response.data.courseVo;
                this.description = response.data.description;
                this.isBuy = response.data.isBuy;
                this.chapterVoList = response.data.chapterVoList;

                this.loading = false;
            });
        },

        see(video) {
            let videoId = video.id;
            let isFree = video.isFree;
            //if(isFree === 1 || this.isBuy || this.courseVo.price == '0.00') {
                this.getPlayAuth(videoId);
            // } else {
            //     if (window.confirm("购买了才可以观看, 是否继续？")) {
            //         this.buy()
            //     }
            // }
        },

        buy() {
            this.$router.push({ path: '/trade/'+this.courseId })
        },

        getPlayAuth(videoId) {
            if (this.player != null) {
                // 是销毁之前的视频，不销毁的话，它会一直存在
                this.player.dispose();
            }

            vodApi.getPlayAuth(this.courseId, videoId).then(response => {
                console.log(response.data);
                this.play(response.data);

                //展开章节
                this.activeNames = [response.data.chapterId]
                //选中播放视频
                this.activeVideoId = response.data.videoId
            })
        },
		//视频播放
        play(data) {
            //window.location = './video.html?fileID='+data.videoSourceId+'&appID='+data.appId;
            var player = TCPlayer("player-container-id", { /**player-container-id 为播放器容器ID，必须与html中一致*/
                fileID: data.videoSourceId, /**请传入需要播放的视频fileID 必须 */
                appID: data.appId, /**请传入点播账号的子应用appID 必须 */
                psign: ""
                /**其他参数请在开发文档中查看 */
             });
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

        .course_teacher {
            margin-left: 20px;
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