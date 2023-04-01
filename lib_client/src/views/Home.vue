<template>
    <div>
        <el-container>
            <el-header height="120px" style="border-radius: 25px; margin-bottom: 5px;">
                <div class="title">
                    图书馆信息管理系统
                </div>
                <div>
                    <div class="uname">欢迎您，{{ $store.state.user.uname }}</div>
                    <el-link class="exit" @click="exit"><i class="el-icon-switch-button"></i>
                        &nbsp;退出登录</el-link></div>
            </el-header>
            <el-container style="height: 100%;">
                <el-aside width="240px" style=" margin-left: 20px; margin-right:15px;">
                    <el-menu default-active="/home/book-manage" class="el-menu-vertical-demo" 
                    @open="handleOpen" @close="handleClose" style="border-radius: 25px;"
                        background-color="#545c64" text-color="#fff"
                        active-text-color="#ffd04b">
                        <el-menu-item index="/home/book-manage" @click="$router.push('/home/book-manage')">
                            <template slot="title"><i class="el-icon-reading"></i>
                                图书管理</template>
                        </el-menu-item>
                        <el-menu-item index="/home/user-manage" @click="$router.push('/home/user-manage')"
                        v-if="isAdmin()">
                            <template slot="title">
                                <i class="el-icon-user"></i> 用户管理
                            </template>
                        </el-menu-item>
                        <el-menu-item index="/home/borrow-manage" @click="$router.push('/home/borrow-manage')"
                        v-if="isAdmin()">
                            <template slot="title"><i class="el-icon-coin"></i>
                                借阅记录</template>
                        </el-menu-item>
                        <el-menu-item index="/home/my-borrow" @click="$router.push('/home/my-borrow')">
                            <template slot="title"><i class="el-icon-coin"></i>
                                我的借阅</template>
                        </el-menu-item>
                        <el-menu-item index="/home/my-information" @click="$router.push('/home/my-information')">
                            <template slot="title"><i class="el-icon-setting"></i>
                                个人信息</template>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <el-container class="rightView">
                    <router-view class="right"></router-view>
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>
<script>
const options = {
    data() {
        return {
        };
    },
    mounted() {
        this.$nextTick(() => {
            document.title = '图书馆信息管理系统';
        });
    },
    methods: {
        // 判断是否管理员
        isAdmin(){
            // console.log(this.$store.state.user.uidentity);
            return this.$store.state.user.uidentity == 'admin';
        },
        // 退出登录
        exit() {
            this.$confirm('退出后需要重新登录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                this.$message({
                    type: 'success',
                    message: '退出成功!'
                });
                    this.$store.commit('setUser', {});
                    this.$router.push('/');
                    localStorage.clear();
                }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });          
            });
        }
    },
}
export default options;
</script>
<style>
.right {
    width: 100%;
    background-color: #cdd4db;
    border-radius: 25px;
    margin-right: 20px;
    /* margin-left: 20px; */
}
.rightView {
    width: 100%;
    height: 100%;
    background-color: #cdd4db;
    border-radius: 25px;
    margin-right: 10px;
}
.el-header {
    background-color: #cdd4db;
    /* background-image: linear-gradient(to right, #fbc2eb, #a6c1ee); */
    color: #333;
}
.el-menu-item {
    border-radius: 25px;
    text-align: center;
    margin-top: 20px;
    margin-bottom: 20px;
}
.uname {
    float: left;
    margin-left: 10px;
    font-size: 16px;
}
.exit {
    float: right;
    margin-right: 10px;
}
.title {
    font-size: 32px;
    text-align: center;
    font-weight: bold;
    font-family: '宋体';
    margin-top: 30px;
    margin-bottom: 20px;
}
</style>