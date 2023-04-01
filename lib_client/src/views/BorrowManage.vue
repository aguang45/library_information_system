<template>
    <div>
        <template>
            <div style="margin-top: 20px;margin-left: 40px;margin-bottom: 20px;">
                <label style="font-family: '宋体';font-weight: bolder;">快速查询：</label>
                <span>
                    <el-input placeholder="请输入借阅编号" v-model="selid" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    >
                    </el-input>
                    <el-input placeholder="请输入用户名" v-model="seluid" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    >
                    </el-input>
                    <el-input placeholder="请输入书籍编号" v-model="selbid" clearable
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
            <el-table :data="tableData" style="width: 100%" 
            @selection-change="handleSelectionChange" :row-class-name="tableRowClassName"
            :header-cell-style="{background:'#f4f4f4',color:'#80878f'}">
                <el-table-column type="selection" align="center" width="50">
                </el-table-column>
                <el-table-column type="index" label="序号" width="100" align="center">
                </el-table-column>
                <el-table-column prop="id" label="记录编号" align="center" sortable>
                </el-table-column>
                <el-table-column prop="uid" label="用户名" align="center" sortable>
                </el-table-column>
                <el-table-column prop="bid" label="书籍编号" align="center" sortable>
                </el-table-column>
                <el-table-column prop="bdate" label="借阅时间" align="center" sortable>
                </el-table-column>
                <el-table-column prop="rdate" label="归还时间" align="center" sortable>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="warning" size="small" plain style="margin-right: 10px;" 
                        @click="editUser(scope.row)">修改</el-button>
                        <el-popconfirm confirm-button-text='确定' cancel-button-text='不用了' 
                        icon="el-icon-info" icon-color="red" title="确定要删除吗？"
                        @confirm="deleteOne(scope.row)">
                            <el-button slot="reference" type="danger" size="small" plain
                            > 删除</el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination style="text-align: center;margin-top: 8px;"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5,10,15,20]"
                :page-size="sizePage"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalPage">
                </el-pagination>
        </template>
        <!--    添加与修改-->
        <el-dialog :title="title" :visible.sync="dialogVisible"
                width="25%" center>
            <el-form ref="borrow" :rules="rules" style="text-align: center;"
                     :model="borrow" label-width="80px">

                <el-form-item label="用户名" prop="uid">
                    <el-select v-model="borrow.uid" filterable clearable
                    placeholder="选择用户名">
                        <el-option
                                v-for="i in userDate"
                                :key="i.uid"
                                :label="i.uid"
                                :value="i.uid">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="书籍编号" prop="bid">
                    <el-select v-model="borrow.bid" filterable clearable
                    placeholder="选择书籍编号">
                        <el-option
                                v-for="i in bookDate"
                                :key="i.bid"
                                :label="i.bid"
                                :value="i.bid">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="借阅时间" prop="bdate">
                    <div class="block">
                        <el-date-picker
                            v-model="borrow.bdate"
                            type="datetime"
                            placeholder="选择借阅日期时间"
                            default-time="12:00:00"
                            value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </div>
                </el-form-item> 

                <el-form-item label="归还时间" prop="rdate">
                    <div class="block">
                        <el-date-picker
                            v-model="borrow.rdate"
                            type="datetime"
                            placeholder="选择归还日期时间"
                            default-time="12:00:00"
                            value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </div>
                </el-form-item> 

                <el-form-item>
                    <el-button type="primary" style="margin-right: 55px;" 
                    @click="submitForm">提交</el-button>
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
            borrow: {
                bid: '',
                uid: '',
                bdate: '',
                rdate: ''
            },
            userDate:[], //用户数据
            bookDate:[], //书籍数据
            name: 'UserManage', // 组件名称
            currentPage: 1, // 当前页
            sizePage: 10, // 每页显示条数
            totalPage: 0, // 总条数
            selid: '', // 查询记录编号
            seluid: '', // 查询用户名
            // seluname: '', // 查询姓名
            selbid: '', // 查询书籍编号
            // selbname: '', // 查询书籍名称
            multipleSelection: [], // 表格内左侧多选框勾选情况
            tableData: [], // 表格数据

            dialogVisible: false, // 添加与修改弹窗
            title: '', // 弹窗标题
            flag: false, // 是否禁用用户名输入框
            //添加数据表单内容检查
            rules: {
                uid: [
                    {required: true, message: '请选择用户姓名', trigger: 'change'}
                ],
                bid: [
                    {required: true, message: '请选择书名', trigger: 'change'}
                ],
                bdate: [
                    {required: true, message: '请选择借阅时间', trigger: 'change'}
                ],
                rdate: [
                    {required: false, message: '请选择归还时间', trigger: 'change'}
                ]
            }
        }
    },
    mounted() {
        this.refreshTable();
        axios.get('/api/users').then(res => {
            // console.log(res.data);
            this.userDate = res.data.data;
        // console.log(this.userDate);
        });
        axios.get('/api/books').then(res => {
            // console.log(res.data.data);
            this.bookDate = res.data.data;
        // console.log(this.bookDate);
        });
    },
    methods: {
        //表格颜色
        tableRowClassName({row}) {
            // console.log(row);
            if (row.rdate == null) {
                return 'warning-row';
            } else  {
                return 'success-row';
            }
        },
        //提交表单
        submitForm() {
            // console.log(this.borrow);
            if(this.borrow.bdate == '' ||this.borrow.bdate == null 
            || this.borrow.uid == '' || this.borrow.uid == null
            || this.borrow.bid == '' || this.borrow.bid == null){
                this.$message({
                    message: '请填写完整信息',
                    type: 'error'
                });
            }else{
                if (this.flag) {
                    //修改用户
                    axios.put('/api/borrows', this.borrow).then(res => {
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
                    axios.post('/api/borrows', this.borrow).then(res => {
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
            }
        },
        //添加记录
        addUser() {
            this.cleanForm();
            this.title = '添加记录';
            this.dialogVisible = true;
            this.flag = false;
            this.borrow = {};
        },
        //修改记录
        editUser(row) {
            this.cleanForm();
            this.title = '修改记录';
            this.dialogVisible = true;
            this.flag = true;
            this.borrow = row;
            // console.log(row);
        },
        //清空dialog中表单中各控件的值
        cleanForm() {
            this.$nextTick(()=>{
                this.$refs['borrow'].clearValidate();
            })
            this.borrow = {};
        },
        //刷新表格
        refreshTable() {
            // console.log('refresh');
            axios.get('/api/borrows/page',{
                params: {
                    page: this.currentPage,
                    size: this.sizePage,
                    id: this.selid,
                    uid: this.seluid,
                    bid: this.selbid
                }
            }).then(res => {
                // console.log(res);
                this.tableData = res.data.records;
                this.totalPage = res.data.total;
            })
        },
        //删除单个记录
        deleteOne(row) {
            // console.log('确定删除');
            // console.log(row);
            axios.delete('/api/borrows/' + row.id).then(res => {
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
            if (this.multipleSelection.length == 0)
                this.$message({
                    message: '请至少选择一项',
                    type: 'warning'
                });
            else {
                //弹出提示框
                this.$confirm('此操作将永久删除这些数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // console.log(this.multipleSelection);
                    let ids = [];
                    this.multipleSelection.forEach(item => {
                        ids.push(item.id);
                    })
                    // console.log(ids);
                    axios.delete('/api/borrows/ids', {
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
<style>
 .el-table .warning-row {
    background: rgb(236, 188, 188);
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>