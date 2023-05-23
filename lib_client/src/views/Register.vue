<template>
  <div class="rg_bg">
    <div class="rg_layout">
      <div class="rg_left">
        <p>新用户注册</p>
        <p>USER REGISTER</p>
      </div>
      <div class="rg_center">
        <div class="rg_form">
          <form action="#" method="post">
            <table class="rg_table">
              <tr>
                <td class="td_left"><label for="username">用户名</label></td>
                <td class="td_right">
                  <input type="text" name="username" id="username"
                         placeholder="请输入用户名" v-model="user.uid" v-on:blur="idBlur">
                </td>
              </tr>
              <tr>
                <td class="td_left"><label for="name">姓名</label></td>
                <td class="td_right">
                  <input type="text" name="name" id="name"
                         placeholder="请输入姓名" v-model="user.uname" v-on:blur="nameBlur"></td>
              </tr>
              <tr>
                <td class="td_left"><label for="tel">手机号</label></td>
                <td class="td_right">
                  <input type="text" name="tel" id="tel"
                         placeholder="请输入手机号" v-model="user.uphone" v-on:blur="phoneBlur"></td>
              </tr>
              <tr>
                <td class="td_left"><label for="password">密码</label></td>
                <td class="td_right">
                  <input type="password" name="password" id="password"
                         placeholder="请输入密码" v-model="user.password" v-on:blur="pwdBlur">
                </td>
              </tr>
              <tr>
                <td class="td_left"><label for="password">确认密码</label></td>
                <td class="td_right">
                  <input type="password" name="_password" id="_password"
                         placeholder="请再次输入密码" v-model="_password" v-on:blur="_pwdBlur">
                </td>
              </tr>
              <tr>
                <td class="td_left"><label for="checkcode">验证码</label></td>
                <td class="td_right"><input type="text" name="checkcode"
                                            id="checkcode" v-model="checkCode" v-on:blur="checkcodeBlur"
                                            placeholder="请输入验证码">
                  <img id="img_check" src="/api/checkCode" title="看不清?点击换一张" alt="加载失败"
                       @click="freshCheckCode">
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center"><input type="button"
                                                      id="btn_sub" value="注册" @click="register"></td>
              </tr>
            </table>
          </form>
        </div>
      </div>
      <div class="rg_right">
        <p>已有账号?
          <a @click="goLogin" href="/#/">立即登录</a>
          <!-- <router-link :to="{ path: './login' }">立即登录</router-link> -->
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

const options = {
  data() {
    return {
      _password: '',//确认密码
      checkCode: '', //验证码
      isExist: false, //账号是否存在
      user: {
        uid: '',
        uname: '',
        uphone: '',
        uidentity: '',
        password: ''
      }
    };
  },
  methods: {
    //跳转到登录页面
    goLogin() {
      this.$store.commit('setFlash', true);
      this.$router.push({path: '/login'});
    },
    //刷新验证码
    freshCheckCode() {
      document.getElementById('img_check').src = '/api/checkCode?id=' + new Date().getTime();
    },
    //验证账号是否存在
    idBlur() {
      if (this.user.uid === '') {
        this.$message({
          message: '请输入账号',
          type: 'error'
        });
        return false;
      } else {
        axios.get('/api/users/id/' + this.user.uid).then((res) => {
          if (res.data.code === 20041) {
            this.isExist = true;  //账号存在
            this.$message({
              message: '此账号已存在，请换一个',
              type: 'error'
            });
            return false;
          } else
            return true;
        }).catch((err) => {
          console.log(err);
        });
      }
    },
    //验证姓名
    nameBlur() {
      if (this.user.uname === '') {
        this.$message({
          message: '请输入姓名',
          type: 'error'
        });
        return false;
      } else
        return true;
    },
    //验证手机号
    phoneBlur() {
      if (this.user.uphone === '') {
        this.$message({
          message: '请输入手机号',
          type: 'error'
        });
        return false;
      } else
        return true;
    },
    //验证密码
    pwdBlur() {
      if (this.user.password === '') {
        this.$message({
          message: '请输入密码',
          type: 'error'
        });
        return false;
      } else
        return true;
    },
    //验证确认密码
    _pwdBlur() {
      // console.log(this._password);
      if (this._password === '' || this._password == null) {
        this.$message({
          message: '请再次输入密码',
          type: 'error'
        });
        return false;
      } else if (this._password !== this.user.password) {
        this.$message({
          message: '两次密码不一致',
          type: 'error'
        });
        return false;
      } else
        return true;
    },
    //验证验证码
    checkcodeBlur() {
      if (this.checkcode === '') {
        this.$message({
          message: '请输入验证码',
          type: 'error'
        });
        return false;
      } else
        return true;
    },
    //注册
    register() {
      if (this.user.uid === '' || this.user.uname === '' || this.user.uphone === '' ||
          this.user.password === '' || this._password === '' || this.checkCode === '') {
        this.$message({
          message: '请填写完整信息',
          type: 'error'
        });
        return false;
      }
      if (this.isExist === true) {
        this.$message({
          message: '此账号已存在，请换一个',
          type: 'error'
        });
        return false;
      } else {
        axios.post('/api/users/register', {
          user: this.user,
          checkCode: this.checkCode
        }).then((res) => {
          if (res.data.code === 30000) {
            this.$message({
              message: '注册成功',
              type: 'success'
            });
            this.freshCheckCode();
            this.$router.push({path: '/'});
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            });
            this.freshCheckCode();
          }
        })
      }
    }
  },
  mounted() {
    this.freshCheckCode();
    this.$nextTick(() => {
      document.title = '注册';
    });
  }
}
export default options;
</script>
<style>
.rg_table {
  margin-top: 60px;
  border-spacing: 10px;
}

.rg_bg {
  width: 100%;
  margin: auto;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
  position: absolute;
  height: 100%;
}

.rg_layout {
  width: 900px;
  height: 500px;
  border: 5px solid #EEEEEE;
  border-radius: 15px;
  background-color: white;
  /*让div水平居中*/
  margin: auto;
  margin-top: 11%;
}

.rg_left {
  float: left;
  margin: 15px;
  width: 20%;
}

.rg_left > p:first-child {
  color: #FFD026;
  font-size: 20px;
}

.rg_left > p:last-child {
  color: #A6A6A6;
}

.rg_center {
  float: left;
  width: 450px;
  margin: 15px;
}

.rg_right {
  float: right;
  margin: 15px;
}

.rg_right > p:first-child {
  font-size: 15px;
}

.rg_right p a {
  color: #a6c1ee;
}

.td_left {
  width: 100px;
  text-align: right;
  height: 45px;
}

.td_right {
  padding-left: 50px;
}

#username,
#password,
#_password,
#name,
#tel,
#checkcode {
  width: 251px;
  height: 32px;
  border: 1px solid #A6A6A6;
  /*设置边框圆角*/
  border-radius: 5px;
  padding-left: 10px;
}

#checkcode {
  width: 110px;
}

#img_check {
  margin-left: 15px;
  height: 40px;
  /*设置垂直居中*/
  vertical-align: middle;
  border-radius: 5px;
}

#btn_sub {
  cursor: pointer;
  border-radius: 8px;
  width: 150px;
  height: 40px;
  background-color: #FFD026;
  border: 1px solid #FFD026;
  margin-top: 20px;
}
</style>
