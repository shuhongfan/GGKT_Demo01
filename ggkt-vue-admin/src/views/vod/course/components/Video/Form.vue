<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog :visible="dialogVisible" title="添加课时" @close="close()">
    <el-form :model="video" label-width="120px">
      <el-form-item label="课时标题">
        <el-input v-model="video.title"/>
      </el-form-item>
      <el-form-item label="课时排序">
        <el-input-number v-model="video.sort" :min="0"/>
      </el-form-item>
      <el-form-item label="是否免费">
        <el-radio-group v-model="video.isFree">
          <el-radio :label="0">免费</el-radio>
          <el-radio :label="1">默认</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 上传视频 -->
      <el-form-item label="上传视频">
        <div class="upload_video" id="upload_video">
          <el-upload
            class="upload-demo"
            ref="upload"
            action="#"
            :http-request="uploadVideo"
            :limit="1"
            :on-remove="handleRemove"
            :on-change="handleChange"
            :auto-upload="false"
          >
            <el-button slot="trigger" size="small" type="primary">选取视频</el-button>
            <el-button style="margin-left: 40px;" size="small" type="success" @click="submitUpload">点击上传</el-button>
            <el-progress class="progress" :text-inside="true" :stroke-width="18" :percentage="progress"
                         status="exception"
            ></el-progress>
            <div slot="tip" class="el-upload__tip">只能上传mp4文件，且不超过500M</div>
          </el-upload>
          <video :src="videoURL" id="video" autoplay></video>
          <img id="video_img" style="width:90px;height:160px;display:none">
        </div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import videoApi from '@/api/vod/video'
import vodApi from '@/api/vod/vod'
import TcVod from 'vod-js-sdk-v6'
import request from '@/utils/request'
import { MessageBox } from 'element-ui'

export default {
  data() {
    return {
      BASE_API: 'http://localhost:8301',
      dialogVisible: false,
      video: {
        sort: 0,
        free: false
      },
      fileList: [], // 上传文件列表
      uploadBtnDisabled: false,
      // 上传成功后的地址
      videoURL: '',
      // 进度条百分比
      progress: 0,
      // base64图片地址  注：这个是项目需要设置一个默认的视频封面，不需要的忽略就行
      imgBase: '',
      // 上传视频获取成功后拿到的fileID【备用】
      fileId: ''
    }
  },
  methods: {
    open(chapterId, videoId) {
      this.dialogVisible = true
      this.video.chapterId = chapterId
      if (videoId) {
        videoApi.getById(videoId).then(response => {
          this.video = response.data
          // 回显
          if (this.video.videoOriginalName) {
            this.fileList = [{ 'name': this.video.videoOriginalName }]
          }
        })
      }
    },
    close() {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },
    resetForm() {
      this.video = {
        sort: 0,
        free: false
      }
      this.fileList = [] // 重置视频上传列表
    },
    saveOrUpdate() {
      if (!this.video.id) {
        this.save()
      } else {
        this.update()
      }
    },
    save() {
      this.video.courseId = this.$parent.$parent.courseId
      videoApi.save(this.video).then(response => {
        this.$message.success(response.message)
        // 关闭组件
        this.close()
        // 刷新列表
        this.$parent.fetchNodeList()
      })
    },
    update() {
      videoApi.updateById(this.video).then(response => {
        this.$message.success(response.message)
        // 关闭组件
        this.close()
        // 刷新列表
        this.$parent.fetchNodeList()
      })
    },
    // 获取签名  这里的签名请求是由后端提供的，只需要拿到后端给的签名请求即可
    getVodSignature() {
      const url = '/admin/vod/sign'
      request({
        url: url,
        method: 'get'
      }).then(response => {
        return response.data
      })
    },
    // 文件列表改变时  将文件列表保存到本地
    handleChange(file, fileList) {
      this.fileList = fileList
    },
    // 点击上传时
    submitUpload() {
      if (this.fileList.length < 1) return MessageBox('请先选取视频，再进行上传', '提示')
      this.uploadVideo()
    },
    // 自定义上传
    uploadVideo(e) {
      // 当
      console.log(this.fileList[0].raw)
      if (this.fileList.length < 1) {
        window.alert('您还没有选取文件')
      } else {
        // 必须以函数的形式返回  sdk参数限制
        const getSignature = async() => {
          const data = await this.getVodSignature()
          return data
        }
        const tcVod = new TcVod({
          getSignature: getSignature // 获取上传签名的函数
        })
        // 获取通过elementui上传到本地的文件  因为参数类型必须为file 不能直接以对象的形式传输
        const mediaFile = this.fileList[0].raw
        const uploader = tcVod.upload({
          mediaFile: mediaFile
        })
        // 监听上传进度
        uploader.on('media_progress', info => {
          this.progress = parseInt(info.percent * 100)
        })
        // 上传结束时，将url存到本地
        uploader.done().then(doneResult => {
          // 保存地址
          // console.log(doneResult)
          // console.log(this.fileId)
          this.fileId = doneResult.fileId
          this.videoURL = doneResult.video.url
          // 将视频的第一帧保存为封面  不需要封面的可以直接忽略掉以下代码
          const canvas = document.createElement('canvas')
          const img = document.getElementById('video_img')
          const video = document.getElementById('video')
          video.setAttribute('crossOrigin', 'anonymous')
          canvas.width = video.clientWidth
          canvas.height = video.clientHeight
          video.onloadeddata = (res) => {
            canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height)
            const dataURL = canvas.toDataURL('image/png')
            img.setAttribute('src', dataURL)
            // 拿到base64的字符串，并保存到本地
            this.imgBase = dataURL.split(',')[1]
          }
        })
      }
    },
    // 点击删除时
    handleRemove(file, fileList) {
      console.log(file, fileList.length)
    }
  }
}
</script>
