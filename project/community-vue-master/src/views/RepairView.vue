<template>
  <div class="people">
    <div class="search">
      <el-form ref="form" :model="searchData" size="small" label-width="70px">
        <el-col :span="5">
          <el-form-item label="内容">
            <el-input v-model="searchData.note" placeholder="请输入报修姓名" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="发布人">
            <el-input v-model="searchData.loginId" placeholder="请输入手机号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="状态">
            <el-select v-model="searchData.state" placeholder="请选择状态" clearable @change="getFeedbackList">
              <el-option v-for="item in stateList" :key="item.id" :label="item.text" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button @click="onReset">重置</el-button>
            <el-button type="success" @click="getFeedbackList">查询</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <el-table v-loading="loading" :data="tableData" border style="width: 100%" :height="tableHeight">
      <el-table-column fixed type="index" label="序号" align="center" width="55"> </el-table-column>
      <el-table-column prop="title" label="标题" align="center" width="140" sortable show-overflow-tooltip> </el-table-column>
      <el-table-column prop="content" label="内容" align="center" width="320" show-overflow-tooltip> </el-table-column>
      <el-table-column prop="loginId" label="发布人手机号" align="center" width="120"> </el-table-column>
      <el-table-column prop="state" label="状态" align="center" width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.state === 0 ? 'danger' : scope.row.state === 1 ? 'success' : 'primary'" disable-transitions>{{
            scope.row.state === 0 ? '未解决' : scope.row.state === 1 ? '已解决' : '解决中'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" width="160"> </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" align="center" width="160"> </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" min-width="120">
        <template slot-scope="scope">
          <el-button @click="showFeedback(scope.row)" size="small">查看报修</el-button>
          <el-button type="primary" size="small" @click="updateFeedback(scope.row)">修改状态</el-button>
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
        <el-form-item label="编号" label-width="100px" prop="multiplyId">
          <el-input v-model="form.multiplyId" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="标题" label-width="100px" prop="title">
          <el-input v-model="form.title" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="内容" label-width="100px" prop="content">
          <el-input v-model="form.content" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="发布人手机号" label-width="100px" prop="loginId">
          <el-input v-model="form.loginId" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="状态" label-width="100px" prop="state">
          <el-select v-model="form.state" placeholder="请选择状态" :disabled="show === 1" clearable>
            <el-option v-for="item in stateList" :key="item.id" :label="item.text" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" label-width="100px" prop="createTime">
          <el-input v-model="form.createTime" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="更新时间" label-width="100px" prop="updateTime">
          <el-input v-model="form.updateTime" autocomplete="off" disabled></el-input>
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
import { getMultipleAPI, updateMultipleAPI } from '@/api/multipleAPI';

export default {
  name: 'FeedbackView',
  data() {
    return {
      searchData: {
        pagingRequest: {
          current: 1,
          size: 20,
        },
        loginId: '',
        note: '',
        state: null,
        typeId: 3,
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
      stateList: [
        {
          id: 0,
          text: '未解决',
        },
        {
          id: 1,
          text: '已解决',
        },
        {
          id: 2,
          text: '解决中',
        },
      ],
    };
  },
  methods: {
    // 获取报修
    async getFeedbackList() {
      this.loading = true;
      const { data: res } = await getMultipleAPI(this.searchData);
      if (res.code === 200) {
        this.tableData = res.data.records;
        this.total = res.data.total;
      } else {
        this.$message.error(res.msg);
      }
      this.loading = false;
    },
    // 查看报修
    showFeedback(row) {
      this.title = '查看报修';
      this.dialogVisible = true;
      this.disabled = true;
      this.form = row;
    },
    // 编辑报修
    updateFeedback(row) {
      this.title = '编辑报修';
      this.dialogVisible = true;
      this.disabled = false;
      this.form = Object.assign({}, row);
    },
    // 提交编辑报修
    onSubmit() {
      this.$refs.dialogForm.validate(async (valid) => {
        if (valid) {
          const { data: res } = await updateMultipleAPI(this.form);
          if (res.code === 200) {
            this.$message.success('修改成功');
            this.getFeedbackList();
            this.dialogVisible = false;
          } else {
            this.$message.error(res.msg);
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
      this.getFeedbackList();
    },
    handleCurrentChange(val) {
      this.searchData.pagingRequest.current = val;
      this.getFeedbackList();
    },
  },
  mounted() {
    this.getFeedbackList();
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
