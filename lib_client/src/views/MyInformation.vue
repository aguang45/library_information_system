<template>
    <div style="width: 30%;margin: 0 auto;margin-top: 15%;">
        <el-form ref="user" :rules="rules" style="text-align: center;"
                :model="user" label-width="150px">

            <el-form-item label="用户名" prop="uid">
                <el-input v-model="user.uid" style="width: 218px" disabled="true"
                ></el-input>
            </el-form-item>

            <el-form-item label="姓名" prop="uname">
                <el-input v-model="user.uname" style="width: 218px" :disabled="edit">
                </el-input>
            </el-form-item>

            <el-form-item label="手机号" prop="uphone">
                <el-input v-model="user.uphone" style="width: 218px" :disabled="edit">
                </el-input>
            </el-form-item> 

            <el-form-item label="新密码" prop="password" v-if="!edit">
                <el-input show-password v-model="user.password" style="width: 218px"
                          :disabled="edit" v-on:blur="pwdCheck">
                </el-input>
            </el-form-item>

            <el-form-item label="确认新密码" prop="_password" v-if="!edit">
                <el-input show-password v-model="user._password" style="width: 218px"
                          :disabled="edit"
                v-on:blur="_pwdCheck">
                </el-input>
            </el-form-item>

            <el-form-item v-if="edit">
                <el-button type="primary" style="margin-right: 55px;" @click="setEdit">编辑
                </el-button>
            </el-form-item>

            <el-form-item v-if="!edit">
                <el-button type="primary" style="margin-right: 55px;" @click="submitForm">提交
                </el-button>
                <el-button type="primary" style="margin-right: 55px;" @click="noEdit">取消
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import axios from 'axios';
const options = {
    data() {
        return {
            edit: true,
            user:{
                uid:'',
                uname:'',
                uphone:'',
                _password:'',//确认密码
                password:''
            },
            rules: {
                uname: [
                    {required: true, message: '请输入姓名', trigger: 'blur'}
                ],
                uphone: [
                    {required: true, message: '请输入手机号', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: ' ', trigger: 'blur'}
                ],
                _password: [
                    {required: true, message: ' ', trigger: 'blur'}
                ]
            }
        };
    },
    created(){
        this.user.uid = this.$store.state.user.uid;
        this.refresh();
    },
    methods:{
        //获取用户信息
        refresh(){
            axios.get('/api/users/'+this.user.uid).then(res => {
                // console.log(res);
                if (res.data.code === 20041) {
                    this.user = res.data.data;
                    this.user.password = null;
                } else {
                    this.$message({
                        message: '获取用户信息失败',
                        type: 'error'
                    });
                    //跳转到主页面
                    this.$router.push({path: '/home/book-manage'});
                }
            })
        },
        //检查密码是否为空
        pwdCheck(){
            if(this.user.password == '' || this.user.password == null){
                this.$message({
                    message: '请输入密码',
                    type: 'error'
                });
                return false;
            }
            return true;
        },
        //检查两次密码是否一致
        _pwdCheck(){
            if(!this.pwdCheck()){
                return;
            }
            if(this.user._password == '' || this.user._password == null){
                this.$message({
                    message: '请再次输入密码',
                    type: 'error'
                });
                return false;
            }else if(this.user.password !== this.user._password){
                this.$message({
                    message: '两次输入的密码不一致',
                    type: 'error'
                });
                return false;
            }
            return true;
        },
        //取消编辑
        noEdit(){
            this.edit = true;
        },
        //编辑
        setEdit(){
            this.edit = false;
        },
        //提交
        submitForm(){
            if(!this._pwdCheck()){
                return;
            }
            if(this.user === null || this.user.uid === ''
                || this.user.uname === '' || this.user.uphone === ''){
                this.$message({
                    message: '请填写完整信息',
                    type: 'error'
                });
            }else{
                axios.put('/api/users', this.user).then(res => {
                    // console.log(res);
                    if (res.data.code === 20031) {
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    } else {
                        this.$message({
                            message: '修改失败',
                            type: 'error'
                        });
                    }
                    this.refresh();
                    this.edit = true;
                })
            }
        }
    }
    
}
export default options;
</script>
<style>
.rg_table {
    margin-top: 60px;
    border-spacing: 10px;
}

.rg_bg{
    width: 100%;
    height: 100vh;
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

.rg_left>p:first-child {
    color: #FFD026;
    font-size: 20px;
}

.rg_left>p:last-child {
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

.rg_right>p:first-child {
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
#email,
#name,
#tel,
#checkcode,
#birthday {
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
}

#btn_sub {
    border-radius: 8px;
    width: 150px;
    height: 40px;
    background-color: #FFD026;
    border: 1px solid #FFD026;
    margin-top: 20px;
}
</style>
