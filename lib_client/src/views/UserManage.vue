<template>
    <div>
        <template>
            <div style="margin-top: 20px;margin-left: 40px;margin-bottom: 20px;">
                <label style="font-family: '宋体';font-weight: bolder;">快速查询：</label>
                <span>
                    <el-input placeholder="请输入用户名" v-model="seluid" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    >
                    </el-input>
                    <el-input placeholder="请输入姓名" v-model="seluname" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    >
                    </el-input>
                </span>
                <div style="float: right;">
                    <el-button type="primary" style="margin-right: 10px;"
                               @click="addUser">新增</el-button>
                    <el-button type="danger" style="margin-right: 60px;"
                               @click="deleteByIds">批量删除</el-button>
                </div>
            </div>
            <el-table :data="tableData" stripe style="width: 100%"
                      @selection-change="handleSelectionChange"
            :header-cell-style="{background:'#f4f4f4',color:'#80878f'}">
                <el-table-column type="selection" align="center" width="50">
                </el-table-column>
                <el-table-column type="index" label="序号" width="100" align="center">
                </el-table-column>
                <el-table-column prop="uid" label="用户名" align="center" sortable>
                </el-table-column>
                <el-table-column prop="uname" label="姓名" align="center" sortable>
                </el-table-column>
                <el-table-column prop="uphone" label="电话" align="center" sortable>
                </el-table-column>
                <el-table-column prop="uidentity" label="身份" align="center" sortable>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="warning" size="small" plain style="margin-right: 10px;" 
                        @click="editUser(scope.row)">修改</el-button>
                        <!-- <el-button type="danger" size="small" plain>删除</el-button> -->
                        <el-popconfirm confirm-button-text='确定'
                                       cancel-button-text='不用了'
                        icon="el-icon-info" icon-color="red" title="确定要删除吗？"
                        @confirm="deleteOne(scope.row)">
                            <el-button slot="reference" type="danger"
                                       size="small" plain
                            > 删除</el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination style="text-align: center;margin-top: 8px;"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="sizePage"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalPage">
                </el-pagination>
        </template>
        <!--    添加与修改-->
        <el-dialog :title="title" :visible.sync="dialogVisible"
                width="25%" center>
            <el-form ref="user" :rules="rules" style="text-align: center;"
                     :model="user" label-width="80px">

                <el-form-item label="用户名" prop="uid">
                    <el-input v-model="user.uid" style="width: 218px"
                              :disabled="flag" v-on:blur="checkUid"
                              placeholder="请输入用户名"
                    ></el-input>
                </el-form-item>

                <el-form-item label="姓名" prop="uname">
                    <el-input v-model="user.uname" style="width: 218px"
                              placeholder="请输入用户姓名"
                    ></el-input>
                </el-form-item>

                <el-form-item label="手机号" prop="uphone">
                    <el-input v-model="user.uphone" style="width: 218px"
                              placeholder="请输入用户手机号"
                    ></el-input>
                </el-form-item> 

                <el-form-item label="身份" prop="uidentity">
                    <el-select v-model="user.uidentity" style="width: 218px" clearable>
                        <el-option value="user" label="普通用户"></el-option>
                        <el-option value="admin" label="管理员"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input show-password v-model="user.password" style="width: 218px"
                    placeholder="请输入用户登录密码"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" style="margin-right: 55px;" @click="submitForm">提交
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import axios from 'axios';
const options = {
    data() {
        return {
            name: 'UserManage',
            currentPage: 1, // 当前页
            sizePage: 10, // 每页显示条数
            totalPage: 0, // 总条数
            seluid: '', // 查询用户名
            seluname: '', // 查询姓名
            multipleSelection: [], // 表格内左侧多选框勾选情况
            tableData: [], // 表格数据
            dialogVisible: false, // 添加与修改弹窗
            title: '添加学生信息', // 弹窗标题
            user: {
                uid: '',
                uname: '',
                uphone: '',
                uidentity: '',
                password: ''
            }, // 用户信息
            flag: false, // 是否禁用用户名输入框
            //添加数据表单内容检查
            rules: {
                uid: [
                {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                uname: [
                    {required: true, message: '请输入姓名', trigger: 'blur'}
                ],
                uidentity: [
                    {required: true, message: '请选择用户身份', trigger: 'change'}
                ],
                uphone: [
                    {required: true, message: '请输入手机号', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入登录密码', trigger: 'blur'}
                ],
                }
        }
    },
    mounted() {
        this.refreshTable();
    },
    methods: {
        //提交表单
        submitForm() {
            if(this.user === null || this.user.uid === ''
                || this.user.uname === '' || this.user.uphone === ''
                || this.user.uidentity === '' || this.user.password === ''){
                this.$message({
                    message: '请填写完整信息',
                    type: 'error'
                });
            }else{
                if (this.flag) {
                    //修改用户
                    axios.put('/api/users', this.user).then(res => {
                        // console.log(res);
                        if (res.data.code === 20031) {
                            this.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                            this.dialogVisible = false;
                            this.refreshTable();
                        } else {
                            this.$message({
                                message: '修改失败',
                                type: 'error'
                            });
                        }
                    })
                } else {
                    //添加用户
                    const checkUidPromise = this.checkUid();
                    checkUidPromise.then((result) => {
                        if (result) {
                            axios.post('/api/users', this.user).then(res => {
                                // console.log(res);
                                if (res.data.code === 20011) {
                                    this.$message({
                                        message: '添加成功',
                                        type: 'success'
                                    });
                                    this.dialogVisible = false;
                                    this.refreshTable();
                                } else {
                                    this.$message({
                                        message: '添加失败',
                                        type: 'error'
                                    });
                                }
                            })
                        }
                    });
                }
            }
        },
        //添加用户
        addUser() {
            this.cleanForm();
            this.title = '添加用户';
            this.dialogVisible = true;
            this.flag = false;
            this.user = {};
        },
        //修改用户
        editUser(row) {
            this.cleanForm();
            this.title = '修改用户';
            this.dialogVisible = true;
            this.flag = true;
            this.user = { ...row };
            this.user.password='';
            // console.log(row);
        },
        //验证用户名是否存在
        async checkUid(){
                try {
                    const res = await axios.get('/api/users/id/' + this.user.uid);
                    if (res.data.code == 20041) {
                        this.$message({
                            message: '此账号已存在，请换一个',
                            type: 'error'
                        });
                        return false;
                    } else {
                        return true;
                    }
                } catch (err) {
                    console.log(err);
                    return false;
                }
        },
        //清空dialog中表单中各控件的值
        cleanForm() {
            this.$nextTick(()=>{
                this.$refs['user'].clearValidate();
            })
            this.user = {};
        },
        //刷新表格
        refreshTable() {
            // console.log('refresh');
            axios.get('/api/users/page',{
                params: {
                    page: this.currentPage,
                    size: this.sizePage,
                    uid: this.seluid,
                    uname: this.seluname
                }
            }).then(res => {
                // console.log(res);
                this.tableData = res.data.records;
                this.totalPage = res.data.total;
            })
        },
        //删除单个用户
        deleteOne(row) {
            // console.log('确定删除');
            // console.log(row);
            axios.delete('/api/users/' + row.uid).then(res => {
                // console.log(res);
                if (res.data.code === 20021) {
                    this.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                    this.refreshTable();
                } else {
                    this.$message({
                        message: '删除失败',
                        type: 'error'
                    });
                }
                this.refreshTable();
            })
        },            
        //表格内左侧多选框勾选情况
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        //批量删除
        deleteByIds() {
            if (this.multipleSelection.length === 0)
                this.$message({
                    message: '请至少选择一项',
                    type: 'warning'
                });
            else {
                //弹出提示框
                this.$confirm('此操作将永久删除这些数据, 是否继续?',
                    '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // console.log(this.multipleSelection);
                    let ids = [];
                    this.multipleSelection.forEach(item => {
                        ids.push(item.uid);
                    })
                    // console.log(ids);
                    axios.delete('/api/users/ids', {
                        data: ids
                    }).then(() => {
                        this.$message({
                        type: 'success',
                        message: '操作成功'
                    });
                        this.refreshTable();
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
        //分页
        handleSizeChange(val) {
            this.sizePage = val;
            this.refreshTable();
        },
        //分页
        handleCurrentChange(val) {
            this.currentPage = val;
            this.refreshTable();
        }
    }
}
export default options;
</script>