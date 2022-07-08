<template>
  <div class="app-container">

    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" size="mini" @click="add">添 加</el-button>
    </el-card>

    <!-- banner列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;">

      <el-table-column
        label="序号"
        width="50"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="封面" width="200" align="center">
        <template slot-scope="scope">
          <img :src="scope.row.cover" width="100%">
        </template>
      </el-table-column>
      <el-table-column prop="courseName" label="直播名称" />
      <el-table-column prop="startTime" label="直播时间">
        <template slot-scope="scope">
          {{ scope.row.param.startTimeString }}至{{ scope.row.param.endTimeString }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="直播结束时间" />
      <el-table-column prop="param.teacherName" label="直播老师" />
      <el-table-column label="头衔" width="90">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.param.teacherLevel === 1" type="success" size="mini">高级讲师</el-tag>
          <el-tag v-if="scope.row.param.teacherLevel === 0" size="mini">首席讲师</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(scope.row.id)">修改</el-button>
          <el-button type="text" size="mini" @click="removeDataById(scope.row.id)">删除</el-button>
          <el-button type="text" size="mini" @click="showAccount(scope.row)">查看账号</el-button>
          <router-link :to="'/live/liveCourse/config/'+scope.row.id">
            <el-button type="text" size="mini">配置</el-button>
          </router-link>
          <router-link :to="'/live/liveVisitor/list/'+scope.row.id">
            <el-button type="text" size="mini">观看记录</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
    />

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="60%" >
      <el-form ref="flashPromotionForm" label-width="150px" size="small" style="padding-right: 40px;">
        <!-- 课程讲师 -->
        <el-form-item label="直播讲师">
          <el-select
            v-model="liveCourse.teacherId"
            placeholder="请选择">
            <el-option
              v-for="teacher in teacherList"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="直播讲师登录密码" v-if="liveCourse.id === ''">
          <el-input v-model="liveCourse.password"/>
        </el-form-item>
        <el-form-item label="直播名称">
          <el-input v-model="liveCourse.courseName"/>
        </el-form-item>
        <el-form-item label="直播开始时间">
          <el-date-picker
            v-model="liveCourse.startTime"
            type="datetime"
            placeholder="选择开始日期"
            value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
        <el-form-item label="直播结束时间">
          <el-date-picker
            v-model="liveCourse.endTime"
            type="datetime"
            placeholder="选择结束日期"
            value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
        <el-form-item label="直播封面">
          <el-upload
            :show-file-list="false"
            :on-success="handleCoverSuccess"
            :before-upload="beforeCoverUpload"
            :on-error="handleCoverError"
            :action="BASE_API+'/admin/vod/file/upload?module=cover'"
            class="cover-uploader">
            <img v-if="liveCourse.cover" :src="liveCourse.cover" width="60%">
            <i v-else class="el-icon-plus avatar-uploader-icon"/>
          </el-upload>
        </el-form-item>
        <el-form-item label="直播详情">
          <el-input v-model="liveCourse.description" type="textarea" rows="5"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="查看账号" :visible.sync="accountDialogVisible" width="60%" >
      <el-form ref="accountForm" label-width="150px" size="small" style="padding-right: 40px;">
        <div style="margin-left: 40px;">
          <h4>主播帮助信息</h4>
          <el-row style="height:35px;">
            <el-co >
              <span class="spd-info">主播登录链接：</span>
              <span class="spd-info">https://live.zhibodun.com/live/courseLogin.php?course_id={{ liveCourseAccount.courseId }}&role=admin</span>
            </el-co>
          </el-row>
          <el-row style="height:35px;">
            <el-col >
              <span class="spd-info">主播登录密码：{{ liveCourseAccount.zhuboKey }}</span>
            </el-col>
          </el-row>
        </div>
        <div style="margin-left: 40px;">
          <h4>主播客户端账号信息</h4>
          <el-row style="height:35px;">
            <el-col >
              <span class="spd-info">主播登录账户：{{ liveCourseAccount.zhuboAccount }}</span>
            </el-col>
          </el-row>
          <el-row style="height:35px;">
            <el-col >
              <span class="spd-info">主播登录密码：{{ liveCourseAccount.zhuboPassword }}</span>
            </el-col>
          </el-row>
        </div>

        <div style="margin-left: 40px;">
          <h4>助教信息</h4>
          <el-row style="height:35px;">
            <el-co >
              <span class="spd-info">助教登录连接：</span>
              <span class="spd-info">https://live.zhibodun.com/live/courseLogin.php?course_id={{ liveCourseAccount.courseId }}&role=admin</span>
            </el-co>
          </el-row>
          <el-row style="height:35px;">
            <el-col>
              <span class="spd-info">主播登录密码：{{ liveCourseAccount.adminKey }}</span>
            </el-col>
          </el-row>
        </div>
        <div style="margin-left: 40px;">
          <h4>学生观看信息</h4>
          <el-row style="height:35px;">
            <el-co >
              <span class="spd-info">观看连接：</span>
              <span class="spd-info">http://ggkt2.vipgz1.91tunnel.com/#/liveInfo/{{ liveCourseAccount.courseId }}</span>
            </el-co>
          </el-row>
          <el-row style="height:35px;">
            <el-col>
              <span class="spd-info">观看二维码：<img src="@/styles/qrcode.png" width="80px"/></span>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="accountDialogVisible = false" size="small">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/live/liveCourse'
import teacherApi from '@/api/vod/teacher'

const defaultForm = {
  id: '',
  courseName: '',
  startTime: '',
  endTime: '',
  teacherId: '',
  password: '',
  description: '',
  cover: 'https://cdn.uviewui.com/uview/swiper/1.jpg'
}
export default {
  data() {
    return {
      BASE_API: 'http://localhost:8333',
      listLoading: true, // 数据是否正在加载
      list: null, // banner列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询表单对象

      teacherList: [], // 讲师列表

       dialogVisible: false,
       liveCourse: defaultForm,
       saveBtnDisabled: false,

      accountDialogVisible: false,
      liveCourseAccount: {
        courseId: ''
      }
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    console.log('list created......')
    this.fetchData()

    // 获取讲师列表
    this.initTeacherList()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    console.log('list mounted......')
  },

  methods: {

    // 当页码发生改变的时候
    changeSize(size) {
      console.log(size)
      this.limit = size
      this.fetchData(1)
    },

    // 加载banner列表数据
    fetchData(page = 1) {
      console.log('翻页。。。' + page)
      // 异步获取远程数据（ajax）
      this.page = page

      api.getPageList(this.page, this.limit).then(
        response => {
          this.list = response.data.records
          this.total = response.data.total

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    // 获取讲师列表
    initTeacherList() {
      teacherApi.list().then(response => {
        this.teacherList = response.data
      })
    },

    // 重置查询表单
    resetData() {
      console.log('重置查询表单')
      this.searchObj = {}
      this.fetchData()
    },

    // 根据id删除数据
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return api.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.code) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    // -------------
    add(){
      this.dialogVisible = true
      this.liveCourse = Object.assign({}, defaultForm)
    },

    edit(id) {
      this.dialogVisible = true
      this.fetchDataById(id)
    },

    fetchDataById(id) {
      api.getById(id).then(response => {
        this.liveCourse = response.data
      })
    },

    saveOrUpdate() {
      this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.liveCourse.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      api.save(this.liveCourse).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.dialogVisible = false
          this.fetchData(this.page)
        }
      })
    },

    // 根据id更新记录
    updateData() {
      api.updateById(this.liveCourse).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.dialogVisible = false
          this.fetchData(this.page)
        }
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        this.liveCourse = response.data
      })
    },

    showAccount(row) {
      this.accountDialogVisible = true
      api.getLiveCourseAccount(row.id).then(response => {
        this.liveCourseAccount = response.data
        this.liveCourseAccount.courseId = row.courseId
      })
    },

    // ------------upload------------
    // 上传成功回调
    handleCoverSuccess(res, file) {
      this.liveCourse.cover = res.data
    },

    // 上传校验
    beforeCoverUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    // 错误处理
    handleCoverError() {
      console.log('error')
      this.$message.error('上传失败2')
    },
  }
}
</script>
<style scoped>
  .cover-uploader .avatar-uploader-icon {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;

    font-size: 28px;
    color: #8c939d;
    width: 450px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .cover-uploader .avatar-uploader-icon:hover {
    border-color: #409EFF;
  }
  .cover-uploader img {
    width: 450px;
    height: 200px;
    display: block;
  }
</style>