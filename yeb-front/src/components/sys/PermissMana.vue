<template>
  <div>
    <div class="pmsTool">
      <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input size="small" v-model="role.nameZh" placeholder="请输入角色中文名"/>
      <el-button size="small" type="primary" icon="el-icon-plus">添加角色</el-button>
    </div>

    <div class="pmsMain">
      <el-collapse accordion @change="change" v-model="activeName">
        <el-collapse-item v-for="(item,index) in roles" :title="item.nameZh" :name="item.id" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问资源</span>
              <el-button style="float: right; padding: 3px 0; color:#ff0000" type="text"
                         icon="el-icon-delete"></el-button>
            </div>
            <div>
              <el-tree :data="allMenus" :props="defaultProps" show-checkbox
                       ref="tree"
                       :default-checked-keys="selectMenus" node-key="id" >

              </el-tree>
              <div style="display: flex;justify-content: flex-end">
                <el-button size="mini" @click="cancelUpdate" >取消修改</el-button>
                <el-button size="mini" type="primary" @click="doUpdate(item.id,index)">确认修改</el-button>

              </div>
            </div>
          </el-card>
        </el-collapse-item>

      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermissMana",
  data() {
    return {
      activeName: '-1',
      selectMenus: [],
      role: {
        name: '',
        nameZh: ''
      },
      roles: [],
      allMenus: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  mounted() {
    this.initRoles();
  },
  methods: {

    cancelUpdate() {
      this.activeName = '-1';
    },

    doUpdate(rid, index) {
      let tree = this.$refs.tree[index];
      let selectedKeys = tree.getCheckedKeys(true);
      let url = '/system/basic/permiss/?rid=' + rid;
      selectedKeys.forEach(key => {
        url += '&mids=' + key;
      });
      this.putRequest(url).then(resp => {
        if (resp) {
          this.initRoles();
          this.activeName = '-1';
        }
      });
    },

    change(rid) {
      if (rid) {
        this.initAllMenus();
        this.initSelectMenus(rid);
      }
    },

    initSelectMenus(rid) {
      this.getRequest('/system/basic/permiss/mid/' + rid).then(resp => {
        if (resp) {
          this.selectMenus = resp;
        }
      });


    },

    initAllMenus() {
      this.getRequest('/system/basic/permiss/menus').then(resp => {
        if (resp) {
          this.allMenus = resp;
        }
      });
    },

    initRoles() {
      this.getRequest('/system/basic/permiss/').then(resp => {
        if (resp) {
          this.roles = resp;
        }
      });
    }
  }
}
</script>

<style scoped>
.pmsTool {
  display: flex;
  justify-content: flex-start;
}

.pmsTool .el-input {
  width: 300px;
  margin-right: 8px;
}

.pmsMain {
  margin-top: 8px;
  width: 700px;
}
</style>