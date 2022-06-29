<template>
  <div>
    <van-image width="100%" height="200" :src="courseVo.cover"/>
    <van-row>
      <van-col span="8">
        <div class="course_count">
          <h1>购买数</h1>
          <p>{{ courseVo.buyCount }}</p>
        </div>
      </van-col>
      <van-col span="8">
        <div class="course_count">
          <h1>课时数</h1>
          <p>{{ courseVo.lessonNum }}</p>
        </div>
      </van-col>
      <van-col span="8">
        <div class="course_count">
          <h1>浏览数</h1>
          <p>{{ courseVo.viewCount }}</p>
        </div>
      </van-col>
    </van-row>

    <h1 class="van-ellipsis course_title">{{ courseVo.title }}</h1>

    <div class="course_teacher_price_box">
      <div class="course_teacher_price">
        <div class="course_price">价格：</div>
        <div class="course_price_number">￥{{ courseVo.price }}</div>
      </div>
      <div>
        <van-button @click="see()" v-if="isBuy || courseVo.price == '0.00'" plain type="warning" size="mini">立即观看</van-button>
        <van-button @click="buy" v-else plain type="warning" size="mini">立即购买</van-button>
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
      <div class="course_content" v-html="description">
      </div>

      <div class="course_title_font">课程大纲</div>
      <div class="gap"></div>
      <van-collapse v-model="activeNames">
        <van-collapse-item :title="item.title" :name="item.id" v-for="item in chapterVoList" :key="item.id">
          <ul class="course_chapter_list" v-for="child in item.children" :key="child.id">
            <h2>{{child.title}}</h2>
            <p v-if="child.isFree == 1">
              <van-button @click="play(child)" type="warning" size="mini" plain>免费观看</van-button>
            </p>
            <p v-else>
              <van-button @click="play(child)" type="warning" size="mini" plain>观看</van-button>
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
import shareApi from '@/api/share'
import wxShare from '@/utils/wxShare'
export default {
  data() {
    return {
      loading: false,

      courseId: null,
      courseVo: {},
      description: '',
      teacher: {},
      chapterVoList: [],
      isBuy: false,

      activeNames: ["1"]
    };
  },

  created() {
    this.courseId = this.$route.params.courseId;
    this.fetchData();
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
        this.teacher = response.data.teacher;

        this.loading = false;

        //分享注册
        this.wxRegister();
      });
    },

    buy() {
      this.$router.push({ path: '/trade/'+this.courseId })
    },

    play(video) {
      let videoId = video.id;
      let isFree = video.isFree;
     if(isFree === 1 || this.isBuy || this.courseVo.price == '0.00') {
        this.$router.push({ path: '/play/'+this.courseId+'/'+videoId })
      } else {
         this.$router.push({ path: '/play/'+this.courseId+'/'+videoId })
        // if (window.confirm("购买了才可以观看, 是否继续？")) {
        //   this.buy()
        // }
      }
    },

    see() {
      this.$router.push({ path: '/play/'+this.courseId+'/0' })
    },

    wxRegister() {
      //说明：后台加密url必须与当前页面url一致
      let url = window.location.href.replace('#', 'guiguketan')
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
          'title': this.courseVo.title,
          'desc': this.description,
          'link': link,
          'imgUrl': this.courseVo.cover
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
