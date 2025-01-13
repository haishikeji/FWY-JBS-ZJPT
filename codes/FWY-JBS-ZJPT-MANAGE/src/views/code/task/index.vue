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
				@row-del="rowDel">
				<template slot-scope="{row,size}" slot="search">
					<el-col :span="4">
						<avue-select
							v-model="product"
							:size="size"
							placeholder="请选择所属的产品"
							type="tree"
							label="所属产品"
							:dic="products">
						</avue-select>
					</el-col>
				</template>
				<template slot-scope="scope" slot="menu">
					<el-button size="small" @click.native="showRecord(scope.row, scope.index)">执行记录</el-button>
					<el-button v-if="scope.row.status===0" size="small" @click.native="startTask(scope.row, scope.index)">启动</el-button>
				</template>
			</avue-crud>
		</basic-container>
	</div>
</template>

<script>
import {fetchList, getObj, addObj, putObj, delObj, startTask} from "@/api/codetask";
import {fetchList as productList} from "@/api/productinfo";
import {tableOption} from "@/const/crud/codetask";
import {mapGetters} from "vuex";

export default {
	name: "codetask",
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
			products: [],
			product: null
		};
	},
	computed: {
		...mapGetters(["permissions"]),
		permissionList() {
			return {
				addBtn: this.vaildData(this.permissions.codetask_add, false),
				delBtn: this.vaildData(this.permissions.codetask_del, false),
				editBtn: this.vaildData(this.permissions.codetask_edit, false),
			};
		},
	},
	created: function () {
		var vm = this;
		productList(
			Object.assign(
				{
					current: 1,
					size: 9999,
				},
				{},
				{}
			)
		).then((res) => {
			console.log(res);
			var products = [{label: '全部', value: null}];
			console.log(products);
			vm.products = [];
			for (var i = 0; i < res.data.data.records.length; i++) {
				products.push({
					label: res.data.data.records[i].name,
					value: res.data.data.records[i].id,
				});
			}
			vm.products = products;
		});
	},
	methods: {
		getList(page, params) {
			this.tableLoading = true;
			fetchList(
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
			this.searchForm.productId = this.product;
			this.page.currentPage = 1;
			this.getList(this.page, form);
			done();
		},
		refreshChange() {
			this.getList(this.page);
		},
		showRecord(row, index) {
			// console.log(row)
			// console.log(index)
			this.$router.push('/code/task/example?id=' + row.id);
			//TODO 弹窗显示执行记录信息
		},
		startTask(row, index) {
			var vm = this;
			this.$confirm("是否启动任务？", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(function () {
					startTask({id: row.id}).then(res => {
						// if(res.data.data.data){
						vm.$message.success('启动成功');
						vm.refreshChange();
						// }
					});
				})

		}
	},
};
</script>
