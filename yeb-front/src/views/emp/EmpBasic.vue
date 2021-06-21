<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <div>
        <el-input size="mini" style="width: 300px;margin-right: 8px"
                  placeholder="请输入员工名进行搜索..."
                  prefix-icon="el-icon-search"
                  v-model="empName"
                  @keydown.enter.native="initEmps"
                  @clear="initEmps"
                  clearable></el-input>
        <el-button size="mini" type="primary" icon="el-icon-search" @click="initEmps">搜索</el-button>
        <el-button size="mini" type="primary">
          <i class="fa fa-angle-double-down" aria-hidden="true"></i>
          高级搜索
        </el-button>
      </div>

      <div>
        <!--        导入导出 搜索-->

        <el-upload
            style="display: inline-flex;margin-right: 8px"
            action="/employee/basic/import"
            :show-file-list="false"
            :headers="headers"
            :before-upload="beforeUpload"
            :disabled="importDataDisabled"
            :on-success="onSuccess"
            :on-error="onError"
            :file-list="fileList">
          <el-button size="mini" :icon="importDataBtnicon" type="success">
            {{importDataBtnText}}
          </el-button>
        </el-upload>


        <el-button @click="exportData" icon="el-icon-download" size="mini" type="success">
           导出数据
        </el-button>

        <el-button size="mini" type="primary" icon="el-icon-plus">添加员工</el-button>
      </div>

    </div>

    <div style="margin-top: 10px">
      <el-table

          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"



          size="mini"
          :data="emps"
          stripe
          border
          style="width: 100%">

        <el-table-column
            type="selection"
            width="55">
        </el-table-column>

        <el-table-column
            prop="name"
            label="姓名"
            fixed
            width="60">
        </el-table-column>
        <el-table-column
            prop="workID"
            label="工号"
            width="70">
        </el-table-column>
        <el-table-column
            prop="gender"
            width="50"
            label="性别">
        </el-table-column>
        <el-table-column
            prop="birthday"
            width="85"
            align="left"
            label="出生日期">
        </el-table-column>
        <el-table-column
            prop="idCard"
            width="130"
            align="left"
            label="身份证号">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            width="75"
            label="婚姻状况">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            width="50"
            align="left"
            label="民族">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            width="90"
            label="籍贯">
        </el-table-column>
        <el-table-column
            prop="politicsStatus.name"
            width="120"
            align="left"
            label="政治面貌">
        </el-table-column>
        <el-table-column
            prop="email"
            width="160"
            align="left"
            label="电子邮件">
        </el-table-column>
        <el-table-column
            prop="phone"
            width="90"
            align="left"
            label="电话号码">
        </el-table-column>
        <el-table-column
            prop="address"
            width="280"
            align="left"
            label="联系地址">
        </el-table-column>
        <el-table-column
            prop="department.name"
            width="100"
            align="left"
            label="所属部门">
        </el-table-column>
        <el-table-column
            prop="joblevel.name"
            width="100"
            align="left"
            label="职称">
        </el-table-column>
        <el-table-column
            prop="position.name"
            width="100"
            align="left"
            label="职位">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            width="100"
            align="left"
            label="聘用形式">
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            width="80"
            align="left"
            label="最高学历">
        </el-table-column>
        <el-table-column
            prop="school"
            width="140"
            align="left"
            label="毕业院校">
        </el-table-column>
        <el-table-column
            prop="specialty"
            width="140"
            align="left"
            label="专业">
        </el-table-column>
        <el-table-column
            prop="workState"
            width="80"
            align="left"
            label="在职状态">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            width="95"
            align="left"
            label="入职日期">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            width="95"
            align="left"
            label="转正日期">
        </el-table-column>
        <el-table-column
            prop="beginContract"
            width="100"
            align="left"
            label="合同起始日期">
        </el-table-column>
        <el-table-column
            prop="endContract"
            width="100"
            align="left"
            label="合同截至日期">
        </el-table-column>
        <el-table-column
            width="100"
            align="left"
            label="合同期限">
          <template slot-scope="scope">
            <el-tag >{{scope.row.contractTerm}}</el-tag>年
          </template>
        </el-table-column>
        <el-table-column
            width="200"
            fixed="right"
            label="操作">
          <template slot-scope="scope">
            <el-button style="padding:3px" size="mini" type="primary">编辑</el-button>
            <el-button style="padding:3px" size="mini" type="primary">查看高级资料</el-button>
            <el-button style="padding:3px" size="mini" type="danger">删除</el-button>

          </template>
        </el-table-column>


      </el-table>

      <div style="display: flex;justify-content: flex-end">
        <el-pagination
            background
            @current-change="currentChange"
            @size-change="sizeChange"
            :page-size="size"
            layout='sizes,prev, pager, next, jumper, ->, total'
            :total="total">
        </el-pagination>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data() {
    return {
      headers: {
        Authorization: window.sessionStorage.getItem("tokenStr")
      },
      importDataDisabled: false,
      importDataBtnText:'导入数据',
      importDataBtnicon:'el-icon-upload2',
      emps: [],
      loading:false,
      total: 0,
      currentPage:1,
      size:20,
      empName: ''
    };
  },
  mounted() {
    this.initEmps();
  },

  methods: {
    onSuccess() {
      this.importDataBtnicon = 'el-icon-upload2';
      this.importDataBtnText = '导入数据';
      this.importDataDisabled = false;
      this.initEmps();
    },
    onError() {
      this.importDataBtnicon = 'el-icon-upload2';
      this.importDataBtnText = '导入数据';
      this.importDataDisabled = false;
    },
    beforeUpload() {
      this.importDataBtnicon = 'el-icon-loading';
      this.importDataBtnText = '正在导入';
      this.importDataDisabled = true;

    },
    exportData() {
      this.downloadRequest('/employee/basic/export');
    },

    sizeChange(size) {
      this.size = size;
      this.initEmps();
    },

    currentChange(currentPage) {
      this.currentPage = currentPage;
      this.initEmps();
    },

    initEmps() {
      this.loading = true;
      this.getRequest('/employee/basic/?currentPage='+this.currentPage+'&size='+this.size+'&name='+this.empName).then(resp => {
        this.loading = false;
        if (resp) {
          this.emps = resp.data;
          this.total = resp.total;
        }
      });
    }


  }
}
</script>

<style scoped>

</style>