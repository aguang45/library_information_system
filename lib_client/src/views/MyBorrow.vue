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
                    <el-input placeholder="请输入书籍编号" v-model="selbid" clearable
                    style="width: 200px;margin-left: 20px;" @input="refreshTable"
                    >
                    </el-input>
                </span>
            </div>
            <el-table :data="tableData" style="width: 100%" 
            @selection-change="handleSelectionChange" :row-class-name="tableRowClassName"
            :header-cell-style="{background:'#f4f4f4',color:'#80878f'}">
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
                        <el-button type="success" size="small"
                         @click="returnBook(scope.row)" :disabled="!isFree(scope.row)">
                         归还</el-button>
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
    </div>
</template>
<script>
import axios from 'axios';
const options = {
    data() {
        return {
            bookDate:[], //书籍数据
            name: 'MyBorrow', // 组件名称
            currentPage: 1, // 当前页
            sizePage: 10, // 每页显示条数
            totalPage: 0, // 总条数
            selid: '', // 查询记录编号
            selbid: '', // 查询书籍编号
            tableData: [], // 表格数据
        }
    },
    mounted() {
        this.seluid=this.$store.state.user.uid;
        axios.get('/api/books').then(res => {
            // console.log(res.data.data);
            this.bookDate = res.data.data;
        // console.log(this.bookDate);
        });
        this.refreshTable();
    },
    methods: {
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
        //表格颜色
        tableRowClassName({row}) {
            // console.log(row);
            if (row.rdate == null) {
                return 'warning-row';
            } else  {
                return 'success-row';
            }
        },
        //按钮是否可用
        isFree(row) {
            return row.rdate == null;
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
        //表格内左侧多选框勾选情况
        handleSelectionChange(val) {
            this.multipleSelection = val;
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