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
				<template slot-scope="scope" slot="searchMenu">
					<el-button size="small" @click.native="showAddExample()">添加任务</el-button>
				</template>

				<template slot-scope="scope" slot="menu">
					<el-button size="small" v-if="scope.row.status===1" @click.native="subAudit(scope.row, scope.index)">提交审核</el-button>
					<el-button size="small" v-if="scope.row.status===2" @click.native="showAuditStatus(scope.row, scope.index)">查看审核状态</el-button>
					<el-button size="small" v-if="scope.row.status===3&&scope.row.useStatus===1" @click.native="releaseApp(scope.row, scope.index)">发布</el-button>
				</template>
			</avue-crud>
			<el-dialog
				:visible.sync="addExampleDialog"
				append-to-body
				width="80%"
				top="20px"
			>
				<el-transfer v-model="upAppids" :data="tfApplets" :titles="['可选','已选']"></el-transfer>
				<div slot="footer" class="dialog-footer">
					<el-button @click="addExampleDialog = false">取 消</el-button>
					<el-button type="primary" @click="submitAddExample">确 定</el-button>
				</div>
			</el-dialog>
		</basic-container>
	</div>
</template>

<script>
import {
	fetchList,
	getObj,
	addObj,
	putObj,
	delObj,
	readAuditStatus,
	releaseApp,
	batchSave,
	subAudit
} from '@/api/codetaskexample'
import {renewableList} from '@/api/applets'
import {tableOption} from '@/const/crud/codetaskexample'
import {mapGetters} from 'vuex'

export default {
	name: 'codetaskexample',
	data() {
		return {
			searchForm: {},
			tableData: [],
			upAppids: [],
			tfApplets: [],
			page: {
				total: 0, // 总页数
				currentPage: 1, // 当前页数
				pageSize: 20 // 每页显示多少条
			},
			tableLoading: false,
			addExampleDialog: false,
			nowRow: null,
			tableOption: tableOption
		}
	},
	computed: {
		...mapGetters(['permissions']),
		permissionList() {
			return {
				addBtn: this.vaildData(this.permissions.mpopen_codetaskexample_add, false),
				delBtn: this.vaildData(this.permissions.mpopen_codetaskexample_del, false),
				editBtn: this.vaildData(this.permissions.mpopen_codetaskexample_edit, false)
			};
		}
	},
	methods: {
		getList(page, params) {
			this.tableLoading = true
			fetchList(Object.assign({
				current: page.currentPage,
				size: page.pageSize,
				taskId: this.$route.query.id
			}, params, this.searchForm)).then(response => {
				this.tableData = response.data.data.records
				this.page.total = response.data.data.total
				this.tableLoading = false
			}).catch(() => {
				this.tableLoading = false
			})
		},
		rowDel: function (row, index) {
			this.$confirm('是否确认删除ID为' + row.id, '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(function () {
				return delObj(row.id)
			}).then(data => {
				this.$message.success('删除成功')
				this.getList(this.page)
			})
		},
		handleUpdate: function (row, index, done, loading) {
			putObj(row).then(data => {
				this.$message.success('修改成功')
				done()
				this.getList(this.page)
			}).catch(() => {
				loading();
			});
		},
		handleSave: function (row, done, loading) {
			addObj(row).then(data => {
				this.$message.success('添加成功')
				done()
				this.getList(this.page)
			}).catch(() => {
				loading();
			});
		},
		sizeChange(pageSize) {
			this.page.pageSize = pageSize
		},
		currentChange(current) {
			this.page.currentPage = current
		},
		searchChange(form, done) {
			this.searchForm = form
			this.page.currentPage = 1
			this.getList(this.page, form)
			done()
		},
		refreshChange() {
			this.getList(this.page)
		},
		showAuditStatus(row, index) {
			console.log(row);
			var vm = this;
			readAuditStatus({id: row.id}).then(res => {
				console.log(res);
				var data = res.data.data;
				if (data.errcode == "0" && data.status == 0) {
					this.$message.success("审核通过，随时可以发布代码了");
					this.getList(this.page);
				} else {
					if (data.status === 2) {
						this.$message.success("审核中...请耐心等待！");
					} else {
						this.$message.error(data.errmsg);
					}
				}
			});
		},
		releaseApp(row, index) {
			console.log(row);
			releaseApp({id: row.id}).then(res => {
				if (res.data.data) {
					this.$message.success("发布成功");
					this.getList(this.page);
				} else {
					this.$message.error("发布失败，请联系管理员");
				}
			});
		},
		/**
		 * 添加任务
		 */
		showAddExample() {
			this.addExampleDialog = true;
			var vm = this;
			//如果已经有了就不再查询加载
			console.log(this.tfApplets == null || this.tfApplets.length == 0);
			if (this.tfApplets == null || this.tfApplets.length == 0) {
				//TODO 获取当前产品的ID
				renewableList({id: this.$route.query.id}).then(res => {
					var ds = res.data.data;
					console.log(res.data.data);
					vm.tfApplets = [];
					vm.upAppids = [];
					for (var i = 0; i < ds.length; i++) {
						var d = ds[i];
						var item = {
							key: d.appid,
							label: ((d.appName == null || d.appName == '') ? '未命名' : d.appName) + "【" + d.name + "】"
						};
						vm.tfApplets.push(item);
					}
				});
			}
		},
		/**
		 * 提交添加信息
		 */
		submitAddExample() {
			var vm = this;
			if (vm.upAppids.length == 0) {
				vm.$message.error('未选择要更新的小程序');
				return;
			}
			console.log(vm.upAppids);
			var appIds = '';
			for (var i = 0; i < vm.upAppids.length; i++) {
				appIds = appIds + ',' + vm.upAppids[i];
			}
			appIds = appIds.substring(1);
			batchSave({taskId: this.$route.query.id, appIds: appIds}).then(res => {
				vm.$message.success('新增成功');
				vm.addExampleDialog=false;
				vm.refreshChange();
			})
		},
		/**
		 * 提交审核
		 */
		subAudit(row) {
			var vm = this;
			subAudit({id: row.id}).then(res => {
				vm.$message.success('提交审核成功');
				vm.refreshChange();
			})
		}

	}
}
</script>
