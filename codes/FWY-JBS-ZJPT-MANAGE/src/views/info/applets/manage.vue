<!--
  -    Copyright (c) 2018-2025, lengleng All rights reserved.
  -
  - Redistribution and use in source and binary forms, with or without
  - modification, are permitted provided that the following conditions are met:
  -
  - Redistributions of source code must retain the above copyright notice,
  - this list of conditions and the following disclaimer.
  - Redistributions in binary form must reproduce the above copyright
  - notice, this list of conditions and the following disclaimer in the
  - documentation and/or other materials provided with the distribution.
  - Neither the name of the pig4cloud.com developer nor the names of its
  - contributors may be used to endorse or promote products derived from
  - this software without specific prior written permission.
  - Author: lengleng (wangiegie@gmail.com)
  -->
<template>
  <div class="execution">
    <basic-container>
      <avue-crud
        ref="crud"
        :page="page"
        :data="tableData"
        :permission="permissionList"
        :table-loading="tableLoading"
        :option="tableOption"
        @on-load="getList"
        @search-change="searchChange"
        @refresh-change="refreshChange"
        @size-change="sizeChange"
        @current-change="currentChange"
        @row-update="handleUpdate"
        @row-save="handleSave"
        @row-del="rowDel"
      >
        <template slot="status" slot-scope="scope">
          {{ getStatus(scope.row) }}
        </template>
        <template slot-scope="scope" slot="menu">
          <el-button
            size="small"
            @click.native="version(scope.row, scope.index)"
            >版本</el-button
          >
          <el-button
            size="small"
            @click.native="showSetting(scope.row, scope.index)"
            >配置</el-button
          >
        </template>
      </avue-crud>
    </basic-container>
    <el-dialog
      :visible.sync="dialogVisible"
      append-to-body
      width="80%"
      top="20px"
    >
      <wxconfig :wxcomfigData="wxcomfigData" />

    </el-dialog>
  </div>
</template>

<script>
import {
  manageList,
  getObj,
  addObj,
  putObj,
  delObj,
  readAuthUrl,
  initSetting,
  createWxMp,
} from "@/api/applets";
import { tableOption } from "@/const/crud/appletsManage";
import { mapGetters } from "vuex";
import wxconfig from '../../../components/wxconfig/index'

export default {
  name: "applets",
  data() {
    return {
      searchForm: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,

      // 配置弹窗
      dialogVisible: false,
      wxcomfigData: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.info_applets_add, false),
        delBtn: this.vaildData(this.permissions.info_applets_del, false),
        editBtn: this.vaildData(this.permissions.info_applets_edit, false),
        configBtn: this.vaildData(this.permissions.info_applets_config, false),
      };
    },
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true;
      manageList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    rowDel: function (row, index) {
      this.$confirm("是否确认删除ID为" + row.id, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(row.id);
        })
        .then((data) => {
          this.$message.success("删除成功");
          this.getList(this.page);
        });
    },
    handleUpdate: function (row, index, done, loading) {
      putObj(row)
        .then((data) => {
          this.$message.success("修改成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    handleSave: function (row, done, loading) {
      addObj(row)
        .then((data) => {
          this.$message.success("添加成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.page.currentPage = 1;
      this.getList(this.page, form);
      done();
    },
    refreshChange() {
      this.getList(this.page);
    },
    showAuth() {
      readAuthUrl()
        .then((data) => {
          var authUrl = data.data.data;
          authUrl = encodeURIComponent(authUrl);
          // TODO  跳转页面
          var goUrl = "http://**********.cn/auth.html?url=" + authUrl;
          window.open(goUrl);
          done();
        })
        .catch(() => { });
    },
    version(row, done, loading){
      console.log(row);
      this.$router.push('/code/version?appid='+row.appid)
    },
    showSetting(row, done, loading) {
      console.log(row);
      this.dialogVisible = true;
      this.wxcomfigData = row
    },

    getStatus(row) {
      // console.log(row);
      switch (row.status) {
        case 0:
          return "未提交";
        case 1:
          return "认证中";
        case 2:
          return "审核中";
        case 3:
          return "已授权";
      }
    },
  },
  components: {
    wxconfig
  }
};
</script>
