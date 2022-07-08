<template>
  <div class="app-container">
    讲师列表
    <!--查询表单-->
  <el-card class="operate-container" shadow="never">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="名称">
        <el-input v-model="searchObj.name" placeholder="讲师名" />
      </el-form-item>

      <el-form-item label="头衔">
        <el-select v-model="searchObj.level" clearable placeholder="头衔">
          <el-option value="1" label="高级讲师"/>
          <el-option value="0" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="入驻时间">
        <el-date-picker
          v-model="searchObj.joinDateBegin"
          placeholder="开始时间"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item label="-">
        <el-date-picker
          v-model="searchObj.joinDateEnd"
          placeholder="结束时间"
          value-format="yyyy-MM-dd" />
      </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
  </el-card>

<!-- 工具按钮 -->
<el-card class="operate-container" shadow="never">
  <i class="el-icon-tickets" style="margin-top: 5px"></i>
  <span style="margin-top: 5px">数据列表</span>
  <el-button class="btn-add" @click="add()" style="margin-left: 10px;">添加</el-button>
  <el-button class="btn-add" @click="batchRemove()" >批量删除</el-button>
</el-card>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      stripe
      @selection-change="handleSelectionChange">
      <el-table-column type="selection"/>
      <el-table-column
        label="#"
        width="50">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="80" />
      <el-table-column label="头衔" width="90">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.level === 1" type="success" size="mini">高级讲师</el-tag>
          <el-tag v-if="scope.row.level === 0" size="mini">首席讲师</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="简介" />
      <el-table-column prop="sort" label="排序" width="60" />
      <el-table-column prop="joinDate" label="入驻时间" width="160" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="removeById(scope.row.id)">删除</el-button>
          <router-link :to="'/vod/teacher/edit/'+scope.row.id">
            <el-button type="text" size="mini">修改</el-button>
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
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />

  </div>
</template>

<script>
//引入定义接口js文件
import teacherApi from '@/api/vod/teacher'
export default {
  data() {  //初始值
    return {
      list: [], // 讲师列表
      total: 0, // 总记录数
      page: 1, // 当前页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询条件
      multipleSelection: []// 批量删除选中的记录列表
    }
  },
  created() { //页面渲染之前
    this.fetchData()
  },
  methods:{ //具体方法
    //批量删除
    batchRemove() {
      //判断非空
      if(this.multipleSelection.length===0) {
        this.$message.warning('请选择要删除的记录！')
        return
      }
      this.$confirm('此操作将删除该讲师信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var idList = []
          // [1,2,3]
          //遍历数组
          for(var i=0;i<this.multipleSelection.length;i++) {
            var obj = this.multipleSelection[i]
            var id = obj.id
            //放到数组
            idList.push(id)
          }
          //调用接口删除
          teacherApi.batchRemove(idList)
            .then(response => {
                //提示
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                //刷新
                this.fetchData()
            })
        })
    },
    //复选框发生变化，调用方法，选中复选框行内容传递
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    //跳转到添加表单页面
    add() {
      this.$router.push({path:'/vod/teacher/create'})
    },
    fetchData() {
      //ajax
      teacherApi.pageList(this.page,this.limit,this.searchObj)
        .then(response => {
          this.list = response.data.records
          this.total = response.data.total
        })
    },
    //改变每页显示记录数
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    //改变页码数
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },
    //清空
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    //讲师删除
    removeById(id) {
      this.$confirm('此操作将删除该讲师信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //调用接口删除
          teacherApi.removeTeacherId(id)
            .then(response => {
                //提示
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                //刷新
                this.fetchData()
            })
        })
    }
  }
}
</script>