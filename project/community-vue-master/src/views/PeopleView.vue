<template>
  <div class="people">
    <div class="search">
      <el-form ref="form" :model="searchData" size="small" label-width="70px">
        <el-col :span="5">
          <el-form-item label="居民姓名">
            <el-input v-model="searchData.name" placeholder="请输入居民姓名" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="手机号">
            <el-input v-model="searchData.loginId" placeholder="请输入手机号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="居民类型">
            <el-select v-model="searchData.userType" placeholder="请选择居民类型" clearable @change="getPeopleList">
              <el-option v-for="item in userTypeList" :key="item.id" :label="item.text" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button @click="onReset">重置</el-button>
            <el-button type="success" @click="getPeopleList">查询</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <el-table v-loading="loading" :data="tableData" border style="width: 100%" :height="tableHeight">
      <el-table-column fixed type="index" label="序号" align="center" width="55"> </el-table-column>
      <el-table-column prop="name" label="居民姓名" align="center" width="160" sortable> </el-table-column>
      <el-table-column prop="loginId" label="手机号" align="center" width="200" sortable> </el-table-column>
      <el-table-column prop="userType" label="居民类型" align="center" width="160" sortable>
        <template slot-scope="scope">
          <el-tag :type="scope.row.userType === 0 ? 'warning' : 'primary'" disable-transitions>{{
            scope.row.userType === 0 ? '未认证用户' : '认证用户'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" width="180"> </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" align="center" width="180"> </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" min-width="120">
        <template slot-scope="scope">
          <el-button @click="showPeople(scope.row)" size="small">查看居民</el-button>
          <el-button type="primary" size="small" @click="updatePeople(scope.row)">{{scope.row.userType === 0 ? '认证用户' : '取消认证'}}</el-button>
          <el-button type="warning" size="small" @click="resetPassword(scope.row)">重置密码</el-button>
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
        <el-form-item label="居民姓名" label-width="80px" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="80px" prop="userId">
          <el-input v-model="form.userId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" label-width="80px" prop="gender">
            <el-select v-model="form.gender" placeholder="请选择">
                <el-option
                v-for="item in genderList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="小区" label-width="80px" prop="address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="楼号" label-width="80px" prop="build">
          <el-input v-model="form.build" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单元" label-width="80px" prop="major">
          <el-input v-model="form.major" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="门牌号" label-width="80px" prop="house">
          <el-input v-model="form.house" autocomplete="off"></el-input>
        </el-form-item>  
        <el-form-item label="说明" label-width="80px" prop="note">
          <el-input v-model="form.note" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="志愿时长" label-width="80px" prop="time">
          <el-input v-model="form.time" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建时间" label-width="80px" prop="createTime">
          <el-input v-model="form.createTime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="更新时间" label-width="80px" prop="updateTime">
          <el-input v-model="form.updateTime" autocomplete="off"></el-input>
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
import { getOnePeopleAPI, getPeopleListAPI, resetPasswordAPI, updatePeopleTypeAPI } from '@/api/peopleAPI';

export default {
  name: 'PeopleView',
  data() {
    return {
      searchData: {
        pagingRequest: {
          current: 1,
          size: 20,
        },
        userType: 0,
        name: '',
        loginId: '',
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
      genderList:[
        {
            value: 0,
            label:'女',
        },
        {
            value: 1,
            label:'男',
        }
      ],
      rules: {
        userName: [
          { required: true, message: '请输入居民名', trigger: 'blur' },
          { min: 3, max: 24, message: '居民名应为3-24位', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
        ],
        type: { required: true, message: '请输入手机号', trigger: 'blur' },
      },
      title: '',
      tableData: [],
      userTypeList: [
        { id: 0, text: '未认证用户', disabled: false },
        { id: 1, text: '认证用户', disabled: false },
      ],
    };
  },
  methods: {
    // 获取居民
    async getPeopleList() {
      this.loading = true;
      const { data: res } = await getPeopleListAPI(this.searchData);
      if(res.code === 200){
        this.tableData = res.data.records;
        this.total = res.data.total;
      } else {
        this.$message.error(res.msg)
      }
      this.loading = false;
    },
    // 重置密码
    resetPassword(row) {
      this.$confirm('此操作将重置该居民密码为初始密码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          const { data: res } = await resetPasswordAPI(row.loginId);
          if (res.code === 200) {
            this.$message.success('重置成功');
            this.getPeopleList();
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作',
          });
        });
    },
    // 查看居民
    async showPeople(row) {
      this.title = '查看居民';
      const {data:res} = await getOnePeopleAPI(row.loginId)
      if(res.code === 200){
        this.dialogVisible = true;
        this.disabled = true;
        this.form = res.data;
      } else {
        this.$message.error(res.msg)
      }
      
    },
    // 认证居民
    async updatePeople(row) {
      const data = {
        userId: row.loginId,
        typeId: row.userType === 0 ? 1 : 0
      }
      const {data:res} = await updatePeopleTypeAPI(data)
      if(res.code === 200){
        this.getPeopleList()
      }else {
        this.$message.error(res.msg)
      }
    },
    // 提交编辑居民
    onSubmit() {
      this.dialogVisible = false
    },
    // 重置查询
    onReset() {
      this.searchData = {
        pagingRequest: {
          current: 1,
          size: 20,
        },
        userType: 0,
        name: '',
        loginId: '',
      };
    },
    handleSizeChange(val) {
      this.searchData.pagingRequest.size = val;
      this.searchData.pagingRequest.current = 1;
      this.getPeopleList();
    },
    handleCurrentChange(val) {
      this.searchData.pagingRequest.current = val;
      this.getPeopleList();
    },
  },
  mounted() {
    this.getPeopleList();
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
