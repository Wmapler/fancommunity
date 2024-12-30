<template>
  <div class="people">
    <el-table v-loading="loading" :data="tableData" border style="width: 100%" :height="tableHeight">
      <el-table-column fixed type="index" label="序号" align="center" width="55"> </el-table-column>
      <el-table-column prop="vilId" label="小区编号" align="center" width="140" sortable show-overflow-tooltip> </el-table-column>
      <el-table-column prop="buildNum" label="最大楼房数" align="center" width="120" sortable> </el-table-column>
      <el-table-column prop="floorNum" label="最大楼层数" align="center" width="120" sortable> </el-table-column>
      <el-table-column prop="majorNum" label="最大单元数" align="center" width="120" sortable> </el-table-column>
      <el-table-column prop="roomNum" label="最大房间数" align="center" width="120" sortable> </el-table-column>
      <el-table-column prop="note" label="说明" align="center" width="240" show-overflow-tooltip> </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" width="160"> </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" min-width="120">
        <template slot-scope="scope">
          <el-button @click="showVillage(scope.row)" size="small">查看小区</el-button>
          <el-button type="primary" size="small" @click="updateVillage(scope.row)">编辑小区</el-button>
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
      <el-form ref="dialogForm" :model="form" :rules="rules" size="small" :disabled="disabled" class="my-dialog">
        <el-form-item label="小区编号" label-width="100px" prop="vilId">
          <el-input v-model="form.vilId" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="最大楼房数" label-width="100px" prop="buildNum">
          <el-input v-model="form.buildNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最大楼层数" label-width="100px" prop="floorNum">
          <el-input v-model="form.floorNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最大单元数" label-width="100px" prop="majorNum">
          <el-input v-model="form.majorNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最大房间数" label-width="100px" prop="roomNum">
          <el-input v-model="form.roomNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" label-width="100px" prop="note">
          <el-input type="textarea" v-model="form.note" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建时间" label-width="100px" prop="createTime">
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
import { getVillageAPI, updateVillageAPI } from '@/api/villageAPI';

export default {
  name: 'VillageView',
  data() {
    return {
      searchData: {
        pagingRequest: {
          current: 1,
          size: 20,
        },
      },
      total: 0,
      tableHeight: 'calc(100vh - 60px)',
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
      rules: {
        buildNum: [{ required: true, message: '请输入最大楼房数', trigger: 'blur' }],
        floorNum: [{ required: true, message: '请输入最大楼层数', trigger: 'blur' }],
        majorNum: { required: true, message: '请输入最大单元数', trigger: 'blur' },
        roomNum: { required: true, message: '请输入最大房间数', trigger: 'blur' },
        majorNum: { required: true, message: '请输入最大单元数', trigger: 'blur' },
      },
      title: '',
      tableData: [],
    };
  },
  methods: {
    // 获取居民
    async getVillageList() {
      this.loading = true;
      const { data: res } = await getVillageAPI(this.searchData);
      if (res.code === 200) {
        this.tableData = res.data.records;
        this.total = res.data.total;
      } else {
        this.$message.error(res.msg);
      }
      this.loading = false;
    },
    // 查看居民
    showVillage(row) {
      this.title = '查看居民';
      this.dialogVisible = true;
      this.disabled = true;
      this.form = row;
    },
    // 编辑居民
    updateVillage(row) {
      this.title = '编辑居民';
      this.dialogVisible = true;
      this.disabled = false;
      this.form = Object.assign({}, row);
    },
    // 提交编辑居民
    onSubmit() {
      this.$refs.dialogForm.validate(async (valid) => {
        if (valid) {
          const { data: res } = await updateVillageAPI(this.form);
          if (res.code === 200) {
            this.$message.success('修改成功');
            this.getVillageList();
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
    handleSizeChange(val) {
      this.searchData.pagingRequest.size = val;
      this.searchData.pagingRequest.current = 1;
      this.getVillageList();
    },
    handleCurrentChange(val) {
      this.searchData.pagingRequest.current = val;
      this.getVillageList();
    },
  },
  mounted() {
    this.getVillageList();
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
