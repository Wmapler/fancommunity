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
            <el-button type="success" @click="getActivityList">查询</el-button>
            <el-button type="primary" @click="addActivity">新增活动</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <el-table v-loading="loading" :data="tableData" border style="width: 100%" :height="tableHeight">
      <el-table-column fixed type="index" label="序号" align="center" width="55"> </el-table-column>
      <el-table-column prop="title" label="标题" align="center" width="240" sortable show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="note" label="内容" align="center" min-width="400" show-overflow-tooltip> </el-table-column>
      <el-table-column prop="loginId" label="发布人" align="center" width="140"> </el-table-column>
      <el-table-column prop="createTime" label="发布时间" align="center" width="160"> </el-table-column>
      <el-table-column prop="during" label="志愿时长" align="center" width="100"> </el-table-column>
      <el-table-column prop="needCount" label="所需人数" align="center" width="100"> </el-table-column>
      <el-table-column prop="applyCount" label="报名人数" align="center" width="100"> </el-table-column>
      <el-table-column prop="ended" label="是否结束" align="center" width="100">
        <template slot-scope="scope">
          {{ scope.row.ended === 0 ? '否' : '是' }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" min-width="320">
        <template slot-scope="scope">
          <el-button @click="showActivity(scope.row)" size="small">查看活动</el-button>
          <el-button @click="showActivityPeople(scope.row)" size="small">查看报名人</el-button>
          <el-button type="primary" size="small" @click="updateActivity(scope.row)">编辑活动</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="searchData.pagingRequest.current" :page-sizes="[20, 50, 100, 200]"
      :page-size="searchData.pagingRequest.size" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
    <el-dialog :title="title" :visible.sync="dialogVisible" center width="50%">
      <el-form ref="dialogForm" :model="form" :rules="rules" size="small" :disabled="disabled" class="my-dialog">
        <el-form-item label="标题" label-width="100px" prop="title">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" label-width="100px" prop="note">
          <el-input type="textarea" v-model="form.note" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="志愿时长" label-width="100px" prop="during">
          <el-input v-model="form.during" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所需人数" label-width="100px" prop="during">
          <el-input type="number" v-model="form.needCount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-show="title !== '新增活动'" label="发布人" label-width="100px" prop="loginId">
          <el-input v-model="form.loginId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-show="title !== '新增活动'" label="发布时间" label-width="100px" prop="createTime">
          <el-input v-model="form.createTime" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px" prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择日期时间"> </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="endTime">
          <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择日期时间"> </el-date-picker>
        </el-form-item>
        <el-form-item label="图片" label-width="100px" prop="endTime">
          <el-upload action="#" list-type="picture-card" :auto-upload="false" :http-request="uploadFile"
            :on-change="handleChange">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{ file }">
              <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
              <span class="el-upload-list__item-actions">
                <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
                  <i class="el-icon-download"></i>
                </span>
                <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmit" size="small">确 定</el-button>
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="dialogVisiblePeople" center width="50%">
      <el-table :data="dialogFormData" border style="width: 100%">
        <el-table-column fixed type="index" label="序号" align="center" width="55"> </el-table-column>
        <el-table-column prop="userId" label="手机号" align="center" min-width="240" sortable> </el-table-column>
        <el-table-column prop="name" label="姓名" align="center" min-width="240" sortable> </el-table-column>
      </el-table>
      <div slot="footer">
        <el-button size="small" @click="dialogVisiblePeople = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addActivityAPI, getActivityListAPI, updateActivityAPI } from '@/api/activityAPI.js';

export default {
  name: 'ActivityView',
  data() {
    return {
      searchData: {
        pagingRequest: {
          current: 1,
          size: 20,
        },
        note: '',
        title: '',
      },
      total: 0,
      tableHeight: 'calc(100vh - 110px)',
      loading: false,
      dialogVisible: false,
      dialogVisiblePeople: false,
      disabled: true,
      form: {
        id: '',
        title: '',
        note: '',
        during: '',
        userName: '',
        createTime: '',
        startTime: '',
        endTime: '',
        applyCount: '',
        needCount: '',
        ended: '',
      },
      dialogFormData: [],
      rules: {
        buildNum: [{ required: true, message: '请输入最大楼房数', trigger: 'blur' }],
        floorNum: [{ required: true, message: '请输入最大楼层数', trigger: 'blur' }],
        majorNum: { required: true, message: '请输入最大单元数', trigger: 'blur' },
        roomNum: { required: true, message: '请输入最大房间数', trigger: 'blur' },
        majorNum: { required: true, message: '请输入最大单元数', trigger: 'blur' },
      },
      title: '',
      tableData: [],
      fileList: [],
    };
  },
  methods: {
    // 获取活动
    async getActivityList() {
      this.loading = true;
      const { data: res } = await getActivityListAPI(this.searchData);
      if (res.code === 200) {
        this.tableData = res.data.records;
        this.total = res.data.total;
      } else {
        this.$message.error(res.msg);
      }
      this.loading = false;
    },
    // 查看活动
    showActivity(row) {
      this.title = '查看活动';
      this.dialogVisible = true;
      this.disabled = true;
      this.form = row;
    },
    // 查看报名人
    showActivityPeople(row) {
      this.title = '报名人信息';
      this.dialogVisiblePeople = true;
      this.dialogFormData = row.applyPersons;
    },
    // 编辑活动
    updateActivity(row) {
      this.title = '编辑活动';
      this.dialogVisible = true;
      this.disabled = false;
      this.form = Object.assign({}, row);
    },
    // 新增活动
    addActivity() {
      this.title = '新增活动';
      this.disabled = false;
      this.dialogVisible = true;
      this.form = {
        title: '',
        note: '',
      };
    },
    // 提交编辑活动
    onSubmit() {
      this.$refs.dialogForm.validate(async (valid) => {
        if (valid) {
          if (this.title === '新增活动') {
            const { data: res } = await addActivityAPI(this.form);
            if (res.code === 200) {
              this.$message.success('新增成功');
              this.getActivityList();
              this.dialogVisible = false;
            } else {
              this.$message.error(res.msg);
            }
          } else {
            const { data: res } = await updateActivityAPI(this.form);
            if (res.code === 200) {
              this.$message.success('修改成功');
              this.getActivityList();
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
    //自定义上传文件
    uploadFile(file) {
      this.form.append('file', file.file);
    },
    handleChange(file, fileList) {
      this.fileList = fileList;
      this.form.files = this.fileList;
      // console.log(this.fileList, "sb");
    },
    handleSizeChange(val) {
      this.searchData.pagingRequest.size = val;
      this.searchData.pagingRequest.current = 1;
      this.getActivityList();
    },
    handleCurrentChange(val) {
      this.searchData.pagingRequest.current = val;
      this.getActivityList();
    },
  },
  mounted() {
    this.getActivityList();
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
