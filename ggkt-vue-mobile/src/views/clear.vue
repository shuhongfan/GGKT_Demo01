<template>
  <div>
    清空token
    <van-button round block type="info" @click="clearData">提交</van-button>
  </div>
</template>

<script>
export default {
  name: "Clear",

  data() {
    return {

    };
  },

  created(){
  },

  methods: {
    clearData() {
      window.localStorage.setItem('token', '');
      window.localStorage.setItem('userInfo', '');
      let token = window.localStorage.getItem('token');
      alert('token:'+token);
    }
  }
}
</script>
