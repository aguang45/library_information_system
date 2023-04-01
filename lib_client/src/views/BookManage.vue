<template>
    <div>
        <template>
            <div style="margin-top: 20px;margin-left: 40px;margin-bottom: 20px;">
                <label style="font-family: '宋体';font-weight: bolder;">快速查询：</label>
                <span>
                    <el-input placeholder="请输入书本编号" v-model="selbid" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    >
                    </el-input>
                    <el-input placeholder="请输入书名" v-model="selbname" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    ></el-input>
                    <el-input placeholder="请输入作者" v-model="selbauthor" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    ></el-input>
                    <el-select v-model="selbstatus" style="width: 200px;margin-left: 20px;"
                    placeholder="请选择状态" clearable @change="refreshTable">
                        <el-option value="已借出" label="已借出"></el-option>
                        <el-option value="未借出" label="未借出"></el-option>
                    </el-select>
                </span>
                <div style="float: right;">
                    <el-button type="primary" style="margin-right: 10px;" @click="addBook"
                    v-if="isAdmin()">新增</el-button>
                    <el-button type="danger" style="margin-right: 60px;" @click="deleteByIds"
                    v-if="isAdmin()">批量删除</el-button>
                </div>
            </div>
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange"
            :header-cell-style="{background:'#f4f4f4',color:'#80878f'}" :row-class-name="tableRowClassName">
                <el-table-column type="selection" align="center" width="50" v-if="isAdmin()">
                </el-table-column>
                <el-table-column type="index" label="序号" width="100" align="center">
                </el-table-column>
                <el-table-column prop="bid" label="编号" align="center" sortable>
                </el-table-column>
                <el-table-column prop="bname" label="书名" align="center" sortable>
                </el-table-column>
                <el-table-column prop="bauthor" label="作者" align="center" sortable>
                </el-table-column>
                <el-table-column prop="bstatus" label="状态" align="center" sortable>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="warning" size="small" plain style="margin-right: 10px;" v-if="isAdmin()"
                        @click="editBook(scope.row)">修改</el-button>
                        <el-popconfirm confirm-button-text='确定' cancel-button-text='不用了' 
                        icon="el-icon-info" icon-color="red" title="确定要删除吗？"
                        @confirm="deleteOne(scope.row)">
                            <el-button slot="reference" type="danger" size="small" plain v-if="isAdmin()"
                            > 删除</el-button>
                        </el-popconfirm>
                        <el-button type="primary" size="small" plain style="margin-left: 10px;"
                        :disabled="isFree(scope.row)" @click="borrowBook(scope.row)">借阅</el-button>
                        <el-button type="success" size="small" plain style="margin-left: 10px;" v-if="isAdmin()"
                        :disabled="!isFree(scope.row)" @click="returnBook(scope.row)">归还</el-button>
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
            <el-form ref="book" :rules="rules" style="text-align: center;"
                     :model="book" label-width="80px">

                <el-form-item label="编号" prop="bid">
                    <el-input v-model="book.bid" style="width: 218px" placeholder="请输入书籍编号"
                              :disabled="flag" v-on:blur="checkBid"
                    ></el-input>
                </el-form-item>

                <el-form-item label="书名" prop="bname">
                    <el-input v-model="book.bname" style="width: 218px" placeholder="请输入书籍名称"></el-input>
                </el-form-item>

                <el-form-item label="作者" prop="bauthor">
                    <el-input v-model="book.bauthor" style="width: 218px" placeholder="请输入书籍作者"></el-input>
                </el-form-item> 

                <el-form-item label="状态" prop="bstatus" >
                    <el-select v-model="book.bstatus" style="width: 218px" clearable :disabled="!flag">
                        <el-option value="未借出" label="未借出"></el-option>
                        <el-option value="已借出" label="已借出"></el-option>
                    </el-select>
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
            name: 'BookManage',
            currentPage: 1, // 当前页
            sizePage: 10, // 每页显示条数
            totalPage: 0, // 总条数
            selbid: '', // 查询编号
            selbname: '', // 查询书名
            selbauthor: '', // 查询作者
            selbstatus: '', // 查询状态
            multipleSelection: [], // 表格内左侧多选框勾选情况
            tableData: [], // 表格数据
            dialogVisible: false, // 添加与修改弹窗
            title: '', // 弹窗标题
            book: {
                bid: '',
                bname: '',
                bauthor: '',
                bstatus: '未借出'
            }, // 书籍信息
            borrow: {
            }, // 借阅信息
            flag: false, // 是否禁用弹窗输入框
            //添加数据表单内容检查
            rules: {
                bid: [
                {required: true, message: '请输入书籍编号', trigger: 'blur'}
                    ],
                bname: [
                    {required: true, message: '请输入书名', trigger: 'blur'}
                ],
                bauthor: [
                    {required: true, message: '请输入作者', trigger: 'blur'}
                ],
                bstatus: [
                    {required: true, message: '请选择状态', trigger: 'change'}
                ]
            }
        }
    },
    mounted() {
        this.refreshTable();
    },
    methods: {
        //身份判断
        isAdmin(){
            // console.log(this.$store.state.user.uidentity);
            return this.$store.state.user.uidentity == 'admin';
        },
        //借阅按钮
        borrowBook(row) {
            // console.log(row);
            this.borrow={};
            this.borrow.uid=this.$store.state.user.uid;
            this.borrow.bid=row.bid;
            this.borrow.bdate=this.getTime();
            // console.log(this.borrow);
            axios.post('/api/borrows', this.borrow)
                .then(response => {
                    // console.log(response);
                    if (response.data.code === 20011) {
                        this.$message({
                            message: '借阅成功',
                            type: 'success'
                        });
                        this.refreshTable();
                    } else {
                        this.$message({
                            message: '借阅失败',
                            type: 'error'
                        });
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        //归还按钮
        returnBook(row) {
            // console.log(row);
            this.borrow={};
            this.borrow.bid=row.bid;
            this.borrow.rdate=this.getTime();
            // console.log(this.borrow);
            axios.put('/api/borrows/return', this.borrow)
                .then(response => {
                    // console.log(response);
                    if (response.data.code === 20031) {
                        this.$message({
                            message: '归还成功',
                            type: 'success'
                        });
                        this.refreshTable();
                    } else {
                        this.$message({
                            message: '归还失败',
                            type: 'error'
                        });
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        //获取当前时间
        getTime() {
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours();
            var minute = date.getMinutes();
            var second = date.getSeconds();
            return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        },
        //借阅按钮是否可用
        isFree(row) {
            return row.bstatus == '已借出';
        },
        //表格颜色
        tableRowClassName({row}) {
            // console.log(row);
            if (row.bstatus == '已借出') {
                return 'warning-row';
            } else if (row.bstatus == '未借出') {
                return 'success-row';
            }
            return '';
        },
        //提交表单
        submitForm() {
            if(this.book === null || this.book.bid === '' || this.book.bname === '' 
            || this.book.bauthor === '' || this.book.bstatus === ''){
                this.$message({
                    message: '请填写完整信息',
                    type: 'error'
                });
            }else{
                if (this.flag) {
                    //修改书籍信息
                    axios.put('/api/books', this.book).then(res => {
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
                    //添加书籍信息
                    const checkUidPromise = this.checkBid();
                    checkUidPromise.then((result) => {
                        if (result) {
                            axios.post('/api/books', this.book).then(res => {
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
        //添加书籍
        addBook() {
            this.cleanForm();
            this.title = '添加书籍';
            this.dialogVisible = true;
            this.flag = false;
            this.book = {
                bid: '',
                bname: '',
                bauthor: '',
                bstatus: '未借出'
            };
        },
        //修改书籍
        editBook(row) {
            this.cleanForm();
            this.title = '修改书籍';
            this.dialogVisible = true;
            this.flag = true;
            this.book = row;
            // console.log(row);
        },
        //验证书籍编号是否存在
        async checkBid(){
            if (this.book.bid === '') {
                return false;
            }
                try {
                    const res = await axios.get('/api/books/' + this.book.bid);
                    if (res.data.code == 20041) {
                        this.$message({
                            message: '此书籍编号已存在，请换一个',
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
                this.$refs['book'].clearValidate();
            })
            this.book = {
                bid: '',
                bname: '',
                bauthor: '',
                bstatus: '未借出'
            }
        },
        //刷新表格
        refreshTable() {
            axios.get('/api/books/page',{
                params: {
                    page: this.currentPage,
                    size: this.sizePage,
                    bid: this.selbid,
                    bname: this.selbname,
                    bauthor: this.selbauthor,
                    bstatus: this.selbstatus
                }
            }).then(res => {
                // console.log(res);
                this.tableData = res.data.records;
                this.totalPage = res.data.total;
            })
        },
        //删除单个书籍
        deleteOne(row) {
            // console.log('确定删除');
            // console.log(row);
            axios.delete('/api/books/' + row.bid).then(res => {
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
                // console.log(res);
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
                        ids.push(item.bid);
                    })
                    // console.log(ids);
                    axios.delete('/api/books/ids', {
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
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>