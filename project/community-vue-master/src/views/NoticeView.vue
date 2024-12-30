<template>
  <div class="people">
    <div class="search">
      <el-form ref="form" :model="searchData" size="small" label-width="70px">
        <el-col :span="5">
          <el-form-item label="标题">
            <el-input v-model="searchData.title" placeholder="请输入标题" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="内容">
            <el-input v-model="searchData.note" placeholder="请输入内容" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item>
            <el-button @click="onReset">重置</el-button>
            <el-button type="success" @click="getNoticeList">查询</el-button>
            <el-button type="primary" @click="addNotice">新增公告</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <el-table v-loading="loading" :data="tableData" border style="width: 100%" :height="tableHeight">
      <el-table-column fixed type="index" label="序号" align="center" width="55"> </el-table-column>
      <el-table-column prop="title" label="标题" align="center" width="240" sortable show-overflow-tooltip> </el-table-column>
      <el-table-column prop="note" label="内容" align="center" min-width="400" show-overflow-tooltip> </el-table-column>
      <el-table-column prop="userName" label="发布人" align="center" width="140"> </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" width="160"> </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" min-width="260">
        <template slot-scope="scope">
          <el-button @click="showNotice(scope.row)" size="small">查看公告</el-button>
          <el-button type="primary" size="small" @click="updateNotice(scope.row)">编辑公告</el-button>
          <el-button type="danger" size="small" @click="deleteNotice(scope.row)">删除公告</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchData.pagingRequest.current"
      :page-sizes="[20, 50, 100, 200]"
      :page-size="searchData.pagingRequest.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
    <el-dialog :title="title" :visible.sync="dialogVisible" center width="50%">
      <el-form ref="dialogForm" :model="form" size="small" :disabled="disabled" class="my-dialog">
        <el-form-item label="标题" label-width="100px" prop="title">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" label-width="100px" prop="note">
          <el-input type="textarea" v-model="form.note" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-show="title !== '新增公告'" label="发布人" label-width="100px" prop="userName">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-show="title !== '新增公告'" label="创建时间" label-width="100px" prop="createTime">
          <el-input v-model="form.createTime" autocomplete="off" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmit" size="small">确 定</el-button>
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addNoticeAPI, deleteNoticeAPI, getNoticeAPI, updateNoticeAPI } from '@/api/noticeAPI';

export default {
  name: 'NoticeView',
  data() {
    return {
      searchData: {
        pagingRequest: {
          current: 1,
          size: 20,
        },
        note:'',
        title:'',
      },
      total: 0,
      tableHeight: 'calc(100vh - 110px)',
      loading: false,
      dialogVisible: false,
      disabled: true,
      form: {
        id: '',
        phone: '',
        userName: '',
        password: '',
        type: '',
      },
      title: '',
      tableData: [],
    };
  },
  methods: {
    // 获取公告
    async getNoticeList() {
      this.loading = true;
      const { data: res } = await getNoticeAPI(this.searchData);
      if (res.code === 200) {
        this.tableData = res.data.records;
        this.total = res.data.total;
      } else {
        this.$message.error(res.msg);
      }
      this.loading = false;
    },
    // 查看公告
    showNotice(row) {
      this.title = '查看公告';
      this.dialogVisible = true;
      this.disabled = true;
      this.form = row;
    },
    // 编辑公告
    updateNotice(row) {
      this.title = '编辑公告';
      this.dialogVisible = true;
      this.disabled = false;
      this.form = Object.assign({}, row);
    },
    // 新增公告
    addNotice() {
      this.title = "新增公告";
      this.disabled = false;
      this.dialogVisible = true;
      this.form = {
        title: "",
        note: "",
      };
    },
    // 删除公告
    async deleteNotice(row){
        const {data:res} = await deleteNoticeAPI(row)
        if(res.code === 200){
            this.$message.success('删除成功')
            this.getNoticeList()
        }else {
            this.$message.error(res.msg)
        }
    },
    // 提交编辑公告
    onSubmit() {
      this.$refs.dialogForm.validate(async (valid) => {
        if (valid) {
          if(this.title === "新增公告"){
            const { data: res } = await addNoticeAPI(this.form);
            if (res.code === 200) {
                this.$message.success('新增成功');
                this.getNoticeList();
                this.dialogVisible = false;
            } else {
                this.$message.error(res.msg);
            }
          } else {
            const { data: res } = await updateNoticeAPI(this.form);
            if (res.code === 200) {
                this.$message.success('修改成功');
                this.getNoticeList();
                this.dialogVisible = false;
            } else {
                this.$message.error(res.msg);
            }
          }
        } else {
          this.$message.error('请检查必填项');
          return false;
        }
      });
    },
    // 重置查询
    onReset() {
      this.searchData = {
        pageSize: 20,
        pageIndex: 1,
        type: '',
        userName: '',
        phone: '',
      };
    },
    handleSizeChange(val) {
      this.searchData.pagingRequest.size = val;
      this.searchData.pagingRequest.current = 1;
      this.getNoticeList();
    },
    handleCurrentChange(val) {
      this.searchData.pagingRequest.current = val;
      this.getNoticeList();
    },
  },
  mounted() {
    this.getNoticeList();
  },
};
</script>

<style lang="less" scoped>
.people {
  background: #f7f7f7;
  width: 100%;
  height: 100vh;
  padding: 12px;
  box-sizing: border-box;
  .search {
    .el-input {
      width: 200px !important;
    }
  }
  .pagination {
    position: absolute;
    bottom: 12px;
    right: 12px;
  }
  .my-dialog {
    width: 440px;
    margin-left: 50%;
    transform: translateX(-50%);
    /deep/ .el-form-item {
      width: 440px;
    }
    /deep/ .el-form-item__content {
      width: 360px !important;
    }
    .el-input,
    .el-select {
      width: 360px !important;
    }
  }
}
</style>
