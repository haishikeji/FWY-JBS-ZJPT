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
				:table-loading="tableLoading"
				:option="tableOption"
				@on-load="getList"
			>
				<template slot-scope="scope" slot="menu">
					<el-button
						size="small"
						@click.native="showSelectProduct(scope.row, scope.index)"
					>提交到代码库
					</el-button
					>
				</template>
			</avue-crud>
		</basic-container>
		<el-dialog
			:visible.sync="showProductSelectFlag"
			title="选择模板所属的产品"
			width="300px"
		>
			<avue-select
				v-model="product"
				width="100%"
				placeholder="请选择所属的产品"
				type="tree"
				:dic="products"
			></avue-select>
			<span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click.native="submitTempRes()"
		>确 定</el-button
		>
      </span>
		</el-dialog>
	</div>
</template>

<script>
import {draftList, addToTemp} from "@/api/code";
import {fetchList} from "@/api/productinfo";
import {tableOption} from "@/const/crud/codeDraft";
import {mapGetters} from "vuex";

export default {
	name: "drafts",
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
			showProductSelectFlag: false, //产品选择框
			products: [],
			product: {},
		};
	},
	// computed: {
	//   ...mapGetters(["permissions"]),
	//   permissionList() {
	//     return {
	//       addBtn: this.vaildData(this.permissions.info_applets_add, false),
	//       delBtn: this.vaildData(this.permissions.info_applets_del, false),
	//       editBtn: this.vaildData(this.permissions.info_applets_edit, false),
	//       configBtn: this.vaildData(this.permissions.info_applets_config, false),
	//     };
	//   },
	// },
	methods: {
		getList(page, params) {
			this.tableLoading = true;
			draftList()
				.then((response) => {
					this.tableData = response.data.data;
					// this.page.total = response.data.data.total;
					this.tableLoading = false;
				})
				.catch(() => {
					this.tableLoading = false;
				});
		},
		showSelectProduct(row, index) {
			var vm = this;
			vm.showProductSelectFlag = true;
			vm.nowRow = row;
			fetchList(
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
				var products = [];
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

		submitTempRes() {
			var vm = this;
			var row = vm.nowRow;
			console.log(vm.product)
			this.$confirm(
				"是否确认将代码提交到代码库?",
				"提示",
				{
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				}
			).then(function () {
				addToTemp({draftId: row.draftId, productId: vm.product}).then(
					(res) => {
						if (res.data.data) {
							vm.$message.success("提交成功");
							vm.showProductSelectFlag=false;
						} else {
							vm.$message.error("提交失败");
						}
					}
				);
			});
		},
	},
};
</script>
