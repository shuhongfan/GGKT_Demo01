<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="入驻时间">
        <el-date-picker v-model="teacher.joinDate" value-format="yyyy-MM-dd"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
            -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="0" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro"/>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacher from '@/api/vod/teacher'

export default {
  name: 'Form',
  data() {
    return {
      BASE_API: 'http://localhost:8301',
      teacher: { // 初始化讲师默认数据
        sort: 0,
        level: 1
      },
      saveBtnDisabled: false // 保存按钮是否禁用，防止表单重复提交
    }
  },
  created() {
    // 获取路径id值，根据id查询得到数据
    const id = this.$route.params.id
    if (id) {
      this.fetchDataById(id)
    }
  },
  methods: {
    /**
     * 添加教师
     */
    save: function() {
      // 添加
      teacher.saveTeacher(this.teacher)
        .then(response => {
          this.$message({ // 弹出提示信息
            type: 'success',
            message: '添加成功！'
          })
          // 跳转到列表页面
          this.$router.push({ path: '/vod/teacher/list' })
        })
    },
    /**
     * 修改教师
     */
    update: function() {
      // 添加
      teacher.updateTeacherById(this.teacher)
        .then(response => {
          this.$message({ // 弹出提示信息
            type: 'success',
            message: '修改成功！'
          })
          // 跳转到列表页面
          this.$router.push({ path: '/vod/teacher/list' })
        })
    },

    /**
     * 添加或修改讲师
     */
    saveOrUpdate() {
      // 禁用保存按钮
      this.saveBtnDisabled = true
      if (!this.teacher.id) { // 没有id，添加
        this.save()
      } else { // 有id，修改讲师
        this.update()
      }
    },

    /**
     * 根据id查询记录
     * @param id
     */
    fetchDataById(id) {
      teacher.getTeacherById(id).then(response => {
        this.teacher = response.data
      })
    }
  }
}
</script>

<style scoped>

</style>
