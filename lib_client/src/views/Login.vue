<template>
  <div class="container">
    <div class="login-wrapper">
      <div class="header">登录</div>
      <form class="form-wrapper" :model="user" action="">
        <input type="text" name="uid" placeholder="账号" class="input-item"
               v-model="user.username" v-on:blur="nameBlur">
        <input type="password" name="password" placeholder="密码" class="input-item"
               v-model="user.password" v-on:blur="pwdBlur" @keyup.enter="login">
        <button class="btn" @click="login" type="button">登录</button>
      </form>
      <div class="msg">
        还没有账号？
        <router-link :to="{ path: '/register' }" key="register">点击注册</router-link>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

const options = {
  data() {
    return {
      user: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    // 登录
    login() {
      if (this.nameBlur() === false || this.pwdBlur() === false) {
        return;
      }
      axios.post('/api/login', this.user,{headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }}).then(res => {
        // console.log(res.data);
        if (res.data.code === 20001) {
          this.$message({
            message: res.data.msg,
            type: 'success'
          });
          // 获取到登录响应后，将 token 存储到本地存储中
          localStorage.setItem('token', res.data.data.token)
          // this.$store.commit('setToken', res.data.data.token);
          this.$store.commit('setUser', res.data.data.user);
          this.$router.push('/home');
        } else {
          this.$message({
            message: res.data.msg,
            type: 'error'
          });
        }
      });
    },
    // 账号输入框失去焦点
    nameBlur() {
      if (this.user.username === '') {
        this.$message({
          message: '请输入账号',
          type: 'error'
        });
        return false;
      } else
        return true;
    },
    // 密码输入框失去焦点
    pwdBlur() {
      if (this.user.password === '') {
        this.$message({
          message: '请输入密码',
          type: 'error'
        });
        return false;
      } else
        return true;
    }
  },
  mounted() {
    //在vuex中设置一个flash变量，当flash为true时，刷新页面
    if (this.$store.state.flash === true) {
      this.$store.commit('setFlash', false);
      this.$router.go(0);
    }
    this.$nextTick(() => {
      document.title = '欢迎来到图书馆信息管理系统';
    });
  }
}
export default options;
</script>

<style>
* {
  margin: 0;
  padding: 0;
}

.container {
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
  position: absolute;
  width: 100%;
  height: 100%;
}

.login-wrapper {
  background-color: #fff;
  width: 380px;
  height: 550px;
  border-radius: 15px;
  padding: 0 50px;
  position: relative;
  left: 50%;
  top: 15%;
  transform: translate(-50%, 0%);

}

.header {
  font-size: 45px;
  font-weight: bold;
  text-align: center;
  line-height: 200px;
  font-family: '宋体',serif;
}

.input-item {
  display: block;
  width: 100%;
  margin-bottom: 20px;
  border: 0;
  padding: 10px;
  border-bottom: 1px solid rgb(128, 125, 125);
  font-size: 15px;
  outline: none;
}

.input-item::placeholder {
  text-transform: uppercase;
}

.btn {
  cursor: pointer;
  border: 0px;
  font-size: 20px;
  font-family: '宋体',serif;
  text-align: center;
  padding: 10px;
  width: 100%;
  margin-top: 40px;
  border-radius: 8px;
  background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
  color: #fff;
}

.msg {
  font-size: 15px;
  text-align: center;
  line-height: 88px;
}

a {
  text-decoration-line: none;
  color: #a6c1ee;
}
</style>

