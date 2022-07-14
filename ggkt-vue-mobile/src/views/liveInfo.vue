<template>
  <div>
    <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg" />

    <h1 class="van-ellipsis course_title">{{ liveCourse.courseName }}</h1>

    <div class="course_teacher_price_box">
      <div class="course_teacher_price">
        <div class="course_price">直播时间：</div>
        <div class="course_price_number">{{ liveCourse.param.startTimeString }}至{{ liveCourse.param.endTimeString }}</div>

      </div>
    </div>
    <div class="course_teacher_price_box">
      <div class="course_teacher_box">
        <div class="course_teacher">主讲： {{ teacher.name }}</div>
        <van-image :src="teacher.avatar" round width="50px" height="50px" />
      </div>
    </div>

    <div class="course_contents">
      <div class="course_title_font">课程详情</div>
      <van-divider :style="{ margin: '5px 0 ' }" />
      <div class="course_content" v-html="description"></div>
    </div>

    <van-goods-action>
      <van-goods-action-button type="danger" text="直播中" @click="play"/>
      </van-goods-action>

    <van-loading vertical="true" v-show="loading">加载中...</van-loading>
  </div>
</template>


<script>
import api from '@/api/live'
import shareApi from '@/api/share'
import wxShare from '@/utils/wxShare'
export default {
  data() {
    return {
      loading: false,

      liveCourseId: null,
      liveCourse: {param:{}},
      description: '',
      teacher: {},
      liveStatus: 0,
      activeNames: ["1"]
    };
  },

  created() {
    this.liveCourseId = this.$route.params.liveCourseId;
    this.fetchData();
  },

  methods: {
    fetchData() {
      this.loading = true;
      api.getInfo(this.liveCourseId).then(response => {
        console.log(response.data);

        this.liveCourse = response.data.liveCourse;
        this.description = response.data.description;
        this.teacher = response.data.teacher;
        this.liveStatus = response.data.liveStatus;

        this.loading = false;

        //分享注册
        //this.wxRegister();
      });
    },

    play() {
      api.getPlayAuth(this.liveCourseId).then(response => {
        console.log(response.data);
        
        //this.$router.push({ path: '/liveOnline?token='+response.data.access_token })
        window.location = './live.html?token='+response.data.access_token;
        this.finished = true;
      });
    },

    wxRegister() {
      //说明：后台加密url必须与当前页面url一致
      let url = window.location.href.replace('#', 'guiguketan');
      shareApi.getSignature(url).then(response => {
        console.log(response.data);

        //记录分享用户
        let link = '';
        if(window.location.href.indexOf('?') != -1) {
          link = window.location.href + '&recommend=' + response.data.userEedId;
        } else {
          link = window.location.href + '?recommend=' + response.data.userEedId;
        }

        let option = {
          'title': this.liveCourse.courseName,
          'desc': this.description,
          'link': link,
          'imgUrl': this.liveCourse.cover
        }
        wxShare.wxRegister(response.data, option);
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
