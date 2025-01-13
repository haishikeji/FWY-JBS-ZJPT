<template>
  <el-menu
    :default-active="$route.path"
    class="el-menu-vertical-demo"
    @select="handleSelect">
    <!-- <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-s-data"></i>
        <span>统计</span>
      </template>
      <el-menu-item index="/shuseranalysis/index">用户分析</el-menu-item>
    </el-submenu> -->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-s-tools"></i>
        <span>管理</span>
      </template>
      <el-menu-item index="/admin/user/index">用户管理</el-menu-item>
      <el-menu-item index="/admin/role/index">角色管理</el-menu-item>
      <el-menu-item index="/admin/menu/index">菜单管理</el-menu-item>
    </el-submenu>
    <!-- <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-s-platform"></i>
        <span>开发</span>
      </template>
      <el-menu-item index="/shsetting/index">基本配置</el-menu-item>
      <el-menu-item index="/">接入权限</el-menu-item>
    </el-submenu> -->
  </el-menu>
</template>

<script>
// import { mapGetters } from "vuex";
// import logo from "../logo";
// import sidebarItem from "./sidebarItem";

export default {
  name: "shSidebar",
  // components: { sidebarItem, logo },
  data() {
    return {

    };
  },
  props: {

  },
  watch: {
  },
  created() {
    
  },
  // computed: {
  //   ...mapGetters([
  //     "website",
  //     "menu",
  //     "tag",
  //     "keyCollapse",
  //     "screen",
  //     "userInfo",
  //     'leftFlag'
  //   ]),
  //   nowTagValue: function () {
  //     return this.$router.$avueRouter.getValue(this.$route);
  //   }
  // },
  mounted() {
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      this.$router.push({ path: key });
    },
  }
};
</script>
<style lang="scss" scoped>

</style>

