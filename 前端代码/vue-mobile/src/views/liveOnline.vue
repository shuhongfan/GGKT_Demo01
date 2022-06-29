<template>
    <div>
        <van-image width="100%" height="200" src="https://cdn.uviewui.com/uview/swiper/1.jpg" />
         <!-- #toast -->
  <div id="toast"></div>
  <!-- #wrap -->
  <div class="wrapper">
    <!-- #画板播放器 -->
    <div id="pptPlayer">
      <p id="loaddoc">播放器 Loading...</p>
    </div>
    <!-- #摄像头模式 -->
    <div id="cameraPlayer">
      <p id="loadcam">摄像头 Loading...</p>
    </div>
    <!-- #桌面分享｜视频插播模式 -->
    <div id="videoPlayer">
      <p id="loadplayer">视频播放器 Loading...</p>
    </div>
  </div>
  <!-- #chat -->
  <div class="chat-wrap">
    <h4>聊天模块</h4>
    <ul id="chat-list" class="mod-chat-list"></ul>
    <label>
      <input id="chatVal" type="text" /><button id="chatSubmit">发送聊天</button>
    </label>
  </div>
  </div>
</template>

<script>
    // [第一步] 如何获取 access_token => http://open.talk-fun.com/docs/getstartV2/access_token.html
    // [第二步] 根据Api文档方法 监听 / 调用方法 JS Api => http://open.talk-fun.com/docs/js/sdk.js.getstart.html
    var url  = window.location.search
	var token = url.split("=")[1]
	alert(token)
	//var token = 'QjMwcDZzYmYxczNidDZ5UjZzcTY5ATMkJjM2IzY1MGM8xHf9JyMzETN1gjMfdDMxQzMyEjI6ISZtFmbyJCLwojIhJCLdtlOiIHd0FmIsYDO5gzM2YDN2EjOiUWbpR3ZlJnIsISM3gTN1ETOzUjI6ICZphnIsMzN4MDN6ICZpBnIsAjOiQWanJCLzMTM1UDOyojIkl2XlNnc192YiwiIiojIyFGdhZXYiwCM6IiclRmbldmIsYDO5ATNxcDN2EjOiUmcpBHelJCL3ATM0MjMxojIklWbv9mciwiIxMjM3UHX3ITO1UHXiojIl1WYut2Yp5mIsIiclNXdiojIlx2byJCLiQTNiojIklWdiwyM3gzM0ojIkl2XyVmb0JXYwJye';
    // 更多配置项 => https://open.talk-fun.com/docs/js/sdk.js.getstart.html?h=%E9%85%8D%E7%BD%AE%E9%A1%B9
    var HT = new MT.SDK.main(token, {
      config: {
        techOrder: 'FLV' // Safari 浏览器建议设置为 HLS
      }
    }, function (data) {
      console.warn('sdk加载完成', data)
    })
    // 连接状态
    HT.on('connect', function () {
      console.log('TalkFun通信 => 连接成功...')
    })
    // 课件播放器
    HT.whiteboardPlayer('pptPlayer', 'docPlayer', function (player) {
      console.log('课件播放器 => 初始化成功')
      document.querySelector('#loadplayer').innerHTML = '画板模块加载完成'
    })
    // 视频插播 | 桌面分享
    HT.videoPlayer('videoPlayer', 'modVideoplayer', function (player) {
      console.log('视频播放器 => 初始化成功')
      document.querySelector('#loadplayer').innerHTML = '视频插播加载完成'
    })
    // 摄像头播放器
    HT.camera('cameraPlayer', 'modCameraPlayer', function () {
      console.log('摄像头播放器 => 初始化成功')
      document.querySelector('#loadcam').innerHTML = '摄像头模块加载完成'
    })
    // 接收聊天
    var receivedChat = function (chat) {
      var tpl = chat.nickname + ': ' + chat.msg
      var chatItem = document.createElement('li')
      chatItem.innerHTML = tpl
      chatItem.className = 'chat-' + chat.xid
      document.querySelector('#chat-list').appendChild(chatItem)
    }
    // 接收聊天信息
    HT.on('chat:send', function (chat) {
      receivedChat(chat)
    })
    // 发送聊天信息
    document.querySelector('#chatSubmit').addEventListener('click', function () {
      var chatIpt = document.querySelector('#chatVal')
      var chatValue = chatIpt.value
      HT.emit('chat:send', { msg: chatValue }, function (res) {
        // 发送成功
        if (Number(res.code) === 0) {
          receivedChat(res.data)
          chatIpt.value = ''
        } 
        // 发送失败
        else {
          console.warn(res.msg)
        }
      })
    }, false)
    // Flash插件异常
    HT.on('flash:load:error', function (obj) {
      if (!obj.flash) {
        document.querySelector('#flashTip').style.display = 'block'
      }
    })
    // 课程错误信息
    HT.on('live:course:access:error', function (res) {
      console.error('错误信息 ==>', res)
    })
    // 课程错误信息
    HT.on('system:room:error', function (res) {
      var toast = document.querySelector('#toast')
      if (typeof res === 'string') {
        toast.innerHTML = res.msg
      }
      else if (res.msg) {
        toast.innerHTML = res.msg
      }
      toast.style.display = 'block'
      var _left = toast.clientWidth / 2
      toast.style.marginLeft = -_left + 'px'
    })
  </script>
<style lang="scss" scoped>
    * {
      margin: 0;
      padding: 0;
      list-style-type: none;
      font-family: "Microsoft YaHei", "STHeiti";
    }
    .flash-wran {
      display: none;
      position: absolute;
      top: 0;
      width: 100%;
      padding: 5px 0;
      text-align: center;
      background: #fff6a2;
      border: 1px solid #ffd913;
    }
    .flash-wran a {
      color: red;
    }
    .wrapper {
      /*display: flex;*/
      padding: 10px;
    }
    #cameraPlayer, 
    #pptPlayer {
      height: auto;
      flex: 1;
      text-align: center;
      font-size: 12px;
      overflow: hidden;
    }
    #pptPlayer {
      height: 300px;
      width: 100%;
    }
    #modPptPlayer,
    #modCameraPlayer {
      margin-top: 10px;
      border: 1px solid #c7c7c7;
    }
    .chat-wrap {
      padding: 5px;
      margin: 10px;
      border: 2px solid #cccccc;
      background: #f1f1f1
    }
    .mod-chat-list {
      margin: 20px 0;
      border: 1px solid #CCCCCC;
      min-height: 100px;
      font-size: 12px;
      background: #dedede;
      padding: 5px;
      max-height: 200px;
      overflow-y: scroll;
    }
    .mod-chat-list li {
      padding: 2px 0;
      margin-bottom: 5px;
      border-bottom: 1px dotted #CCCCCC;
    }
    input {
      display: inline-block;
      width: 200px;
      padding: 5px;
    }
    button {
      display: inline-block;
      padding: 5px;
      margin-left: 5px;
    }
    #toast {
      padding: 20px;
      position: fixed;
      z-index: 100;
      display: none;
      background: rgba(212, 28, 28, 0.8);
      left: 50%;
      top: 30%;
      border-radius: 50em;
      font-size: 14px;
      color: #FFFFFF;
      box-shadow: 0 0 6px 0px #bb2d2d;
    }
    #talkfun-video-wrap, #talkfun-camera-wrap {
      position: relative;
      background: #000;
    }
  </style>
