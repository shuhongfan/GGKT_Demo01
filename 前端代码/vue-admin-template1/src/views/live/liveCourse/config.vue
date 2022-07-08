<template>
  <div class="app-container">
    <el-form label-width="120px" size="small">

      <div style="background-color:#E0E0E0;width: 100%;padding: 1px 10px;margin: 10px 0;"><h3>
        功能设置&nbsp;&nbsp;&nbsp;
      </h3></div>
      <el-form-item label="界面模式">
        <el-radio-group v-model="liveCourseConfigVo.pageViewMode">
          <el-radio :label="1">全屏模式</el-radio>
          <el-radio :label="0">二分屏</el-radio>
          <el-radio :label="2">课件模式</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="观看人数开关">
        <el-radio-group v-model="liveCourseConfigVo.numberEnable">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="商城开关:">
        <el-radio-group v-model="liveCourseConfigVo.storeEnable">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <div style="background-color:#E0E0E0;width: 100%;padding: 1px 10px;margin: 10px 0;"><h3>
        商品列表&nbsp;&nbsp;&nbsp;
        <el-button type="" size="mini" @click="addCourse()">添加</el-button>
      </h3></div>
      <el-table
        v-loading="listLoading"
        :data="liveCourseConfigVo.liveCourseGoodsList"
        stripe
        border
        style="width: 100%;margin-top: 10px;">
        <el-table-column
          label="序号"
          width="70"
          align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="商品图片" width="120" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.img" width="80px">
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" width="100"/>
        <el-table-column prop="price" label="价格" width="100"/>
        <el-table-column prop="originalPrice" label="原价"/>
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="removeCourseById(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="添加课程" :visible.sync="dialogVisible" width="50%">
        <el-form :inline="true" label-width="150px" size="small" style="padding-right: 40px;">
          <el-table
            v-loading="listLoading"
            :data="courseList"
            stripe
            border
            style="width: 100%;margin-top: 10px;"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="55" />
            <el-table-column
              label="序号"
              width="70"
              align="center">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="分类">
              <template slot-scope="scope">
                {{ scope.row.param.subjectParentTitle }} > {{ scope.row.param.subjectTitle }}
              </template>
            </el-table-column>
            <el-table-column prop="title" label="课程名称" width="150"/>
            <el-table-column prop="lessonNum" label="课时" width="100"/>
            <el-table-column prop="param.teacherName" label="讲师"/>
          </el-table>
          <el-form-item style="margin-top: 10px;">
            <el-button type="" @click="dialogVisible = false">取消</el-button>
            <el-button type="" @click="selectCourse()">保存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <br/><br/>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import api from '@/api/live/liveCourse'
import courseApi from '@/api/vod/course'

const defaultForm = {
  id: '',
  liveCourseId: '',
  pageViewMode: 1,
  numberEnable: 1,
  storeEnable: 1,
  storeType: 1,
  liveCourseGoodsList: []
}

export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载

      liveCourseConfigVo: defaultForm,
      saveBtnDisabled: false,

      dialogVisible: false,
      courseList: [],
      multipleSelection: [] // 批量选择中选择的记录列表
    }
  },

  // 监听器
  watch: {
    $route(to, from) {
      console.log('路由变化......')
      console.log(to)
      console.log(from)
      this.init()
    }
  },

  // 生命周期方法（在路由切换，组件不变的情况下不会被调用）
  created() {
    console.log('form created ......')
    this.init()
  },

  methods: {

    // 表单初始化
    init() {
      this.liveCourseConfigVo.liveCourseId = this.$route.params.id
      this.fetchDataById(this.liveCourseConfigVo.liveCourseId)

      this.fetchCourseList()
    },

    back() {
      this.$router.push({ path: '/live/liveCourse/list' })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getCourseConfig(id).then(response => {
        if(null !== response.data.id) {
          this.liveCourseConfigVo = response.data
        }
        this.listLoading = false
      })
    },

    fetchCourseList() {
      courseApi.findAll().then(response => {
        //debugger
        this.courseList = response.data
      })
    },

    handleSelectionChange(selection) {
      console.log(selection)
      this.multipleSelection = selection
    },

    addCourse() {
      this.dialogVisible = true
    },

    selectCourse() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择对应课程!'
        })
        return
      }
      var list = []
      this.multipleSelection.forEach(item => {
        var obj = {
          liveCourseId: this.liveCourseConfigVo.liveCourseId,
          goodsId: item.id,
          name: item.title,
          img: item.cover,
          price: item.price,
          originalPrice: item.price,
          tab: '1',
          url: 'http://ggkt2.vipgz1.91tunnel.com/#/courseInfo/'+item.id,
          putaway: '1',
          pay: '1',
          qrcode: ''
        }
        list.push(obj)
      })
      this.liveCourseConfigVo.liveCourseGoodsList = list
      this.dialogVisible = false
    },

    removeCourseById(index) {
      this.liveCourseConfigVo.liveCourseGoodsList.splice(index, 1)
    },

    saveOrUpdate() {
      api.updateConfig(this.liveCourseConfigVo).then(response => {
        this.$message({
          type: 'success',
          message: response.message
        })
        this.$router.push({ path: '/live/liveCourse/list' })
      })
    }
  }
}
</script>
<style scoped>

  .littleMarginTop {
    margin-top: 10px;
  }

  .paramInput {
    width: 250px;
  }

  .paramInputLabel {
    display: inline-block;
    width: 100px;
    text-align: right;
    padding-right: 10px
  }

  .cardBg {
    background: #F8F9FC;
  }
</style>