<template>
  <div>
    <div style="display: flex;justify-content: center;margin-top: 8px">
      <el-input v-model="keyword" placeholder="通过用户名搜索用户..." prefix-icon="el-icon-search"
                style="width: 400px;margin-right: 8px"></el-input>

      <el-button type="primary" icon="el-icon-search" @click="doSearch">搜索</el-button>
    </div>

    <div class="admin-content">
      <el-card class="admin-card" v-for="(admin,index) in admins" :key="index">
        <div slot="header" class="clearfix">
          <span>{{ admin.name }}</span>
          <el-button style="float: right; padding: 3px 0;color: red" type="text" icon="el-icon-delete"
                     @click="deleteAdmin(admin)"></el-button>
        </div>

        <div>
          <div class="img-content">
            <img :src="admin.userFace" :title="admin.name" class="userface-img">
          </div>
        </div>

        <div class="userinfo-content">
          <div> 用户名：{{ admin.name }}</div>
          <div> 手机号码：{{ admin.phone }}</div>
          <div> 电话号码：{{ admin.telephone }}</div>
          <div> 地址：{{ admin.address }}</div>
          <div style="display: flex;font-size: 12px"> 用户状态：
            <el-switch
                style="display: block"
                v-model="admin.enabled"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="已启用"
                @change="enabledChange(admin)"
                inactive-text="未启用">
            </el-switch>
          </div>
          <div>
            用户角色：
            <el-tag style="margin-right: 4px" type="success" v-for="(role,indexj) in admin.roles" :key="indexj">
              {{ role.nameZh }}
            </el-tag>
            <el-popover
                placement="right"
                title="角色列表"
                width="200"
                @show="showPop(admin)"
                @hide="hidePop(admin)"
                trigger="click">

              <el-select v-model="selectRoles" multiple placeholder="请选择">
                <el-option
                    v-for="(r,index) in allRoles"
                    :key="index"
                    :label="r.nameZh"
                    :value="r.id">
                </el-option>
              </el-select>

              <el-button slot="reference" type="text" icon="el-icon-more"></el-button>
            </el-popover>

          </div>
          <div>
            备注：{{ admin.remark }}
          </div>
        </div>

      </el-card>


    </div>
  </div>
</template>

<script>
export default {
  name: "SysAdmin",
  data() {
    return {

      admins: [],
      keyword: '',
      allRoles: [],
      selectRoles: []
    };
  },
  mounted() {
    this.initAdmins();
  },

  methods: {

    hidePop(admin) {

      let adminroles = admin.roles;
      let flag = true;
      if (adminroles.length != this.selectRoles.length) {
        flag = false;
      } else {
        for (let i = 0; i < adminroles.length; i++) {
          if (adminroles[i].id != this.selectRoles[i]) {
            flag = false;
            break;
          }
        }
      }

      if (!flag) {
        let url = '/system/admin/roles/?adminId=' + admin.id;
        this.selectRoles.forEach(id => {
          url += '&rids=' + id;
        });
        this.putRequest(url).then(resp => {
          if (resp) {
            this.initAdmins();
          }
        });
      }

    },

    showPop(admin) {
      this.initAllRoles();
      let roles = admin.roles;
      this.selectRoles = [];

      // for (let i = 0; i < roles.length; i++) {
      //   this.selectRoles[i] = roles[i].id;
      // }

      roles.forEach(r => {
        this.selectRoles.push(r.id);
      });
    },

    initAllRoles() {
      this.getRequest('/system/admin/roles').then(resp => {
        if (resp) {
          this.allRoles = resp;
        }
      });
    },

    enabledChange(admin) {
      admin.authorities = null;
      this.putRequest('/system/admin/', admin).then(resp => {
        if (resp) {
          this.initAdmins();
        }
      });
    },

    deleteAdmin(admin) {
      this.$confirm('此操作将永久删除[' + admin.name + ']操作员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/admin/' + admin.id).then(resp => {
          if (resp) {
            this.initAdmins();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    doSearch() {
      this.initAdmins();
    },

    initAdmins() {
      this.getRequest('/system/admin/?keyword=' + this.keyword).then(resp => {
        if (resp) {
          this.admins = resp;
        }
      });
    }

  }
}
</script>

<style scoped>
.admin-card {
  width: 350px;
  margin-bottom: 20px;
}

.admin-content {
  display: flex;
  margin-top: 8px;
  justify-content: space-around;
  flex-wrap: wrap;
}

.userface-img {
  width: 72px;
  height: 72px;
  border-radius: 72px;

}

.img-content {
  width: 100%;
  display: flex;
  justify-content: center;
}

.userinfo-content {
  font-size: 12px;
  color: #409eff;

}
</style>