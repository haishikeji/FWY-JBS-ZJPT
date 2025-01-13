<template>
	<div class="wxconfig">
		<basic-container>
			<el-tabs v-model="activeName">
				<el-tab-pane label="基本信息" name="baseInfoTab">
					<div class="">
						<table cellspacing="0" class="" width="100%">
							<tbody class="tbody">
							<tr>
								<td class="table_cell" style="width: 15%">
									<div class="">AppID</div>
								</td>
								<td class="table_cell">
									<div>{{ wxcomfigData.appid }}</div>
								</td>
							</tr>
							<tr>
								<td class="table_cell">
									<div class="">小程序名称</div>
								</td>
								<td class="table_cell">
									<div id="" class="">{{ wxcomfigData.appName }}</div>
								</td>
								<td class="table_cell">
									<p class="desc">
                      					<span v-if="wxcomfigData.status != 4">发布前可以申请修改2次，已经修改了<span>1</span>次</span>
										<span v-if="wxcomfigData.status == 4">小程序发布后，非个人类帐号可通过认证方式改名。</span>
									</p>
								</td>
								<td class="table_cell">
									<p class="sx" @click="showChangeName = true">修改</p>
								</td>
							</tr>

							<!-- <tr>
							<td class="table_cell">
							  <div class="">小程序简称</div>
							</td>
							<td class="table_cell">
							  <div class="">
								<span class="">祥云商铺</span>
							  </div>
							</td>
							<td class="table_cell">
							  <div class="">
								<p class="desc">一年内可申请修改2次</p>
								<p class="desc">今年还可修改2次</p>
							  </div>
							</td>
							<td class="table_cell">
							  <p class="sx" @click="changeShortName">修改</p>
							</td>
						  </tr> -->
							<tr>
								<td class="table_cell">
									<div class="">小程序头像</div>
								</td>
								<td class="table_cell">
									<div class="pic">
										<img :src="wxcomfigData.appHeadImg" class="meta_pic"/>
									</div>
								</td>
								<td class="table_cell">
									<p class="desc">一年内可申请修改5次</p>
									<p class="desc">今年还可修改4次</p>
								</td>
								<td class="table_cell">
									<p class="sx" @click="headPicflag = true">修改</p>
								</td>
							</tr>
							<tr>
								<td class="table_cell">
									<div class="">小程序码</div>
								</td>
								<td class="table_cell">
									<div class="pic">
										<img :src="wxcomfigData.appQrcodeUrl" class="meta_pic"/>
									</div>
								</td>
								<td class="table_cell">
									<p class="desc">小程序码</p>
								</td>
								<td class="table_cell">
									<a :href="wxcomfigData.appQrcodeUrl" target="_blank"
									>查看</a
									>
								</td>
							</tr>
							<tr>
								<td class="table_cell">
									<div class="">介绍</div>
								</td>
								<td class="table_cell">
									<div class="">{{ wxcomfigData.appSignature }}</div>
								</td>
								<td class="table_cell">
									<p class="desc">一个月内可申请5次修改</p>
									<p class="desc">本月还可修改5次</p>
								</td>
								<td class="table_cell">
									<p class="sx" @click="changeSignature">修改</p>
								</td>
							</tr>
							<tr>
								<td class="table_cell">
									<div class="">服务类目</div>
								</td>
								<td class="table_cell">
                    				<span class="" v-if="wxcomfigData.categories.first">{{ wxcomfigData.categories.first }}&gt;</span>
									<span class="">{{ wxcomfigData.categories.second }}</span>
								</td>
								<td class="table_cell">
									<div class="">
                      					<span>
											<p class="desc">一个月内可申请修改3次</p>
											<p class="desc">本月还可修改3次</p>
										</span>
									</div>
								</td>
								<td class="table_cell">
									<p class="sx" @click="getAllServices">修改</p>
								</td>
							</tr>
							<!-- <tr>
							  <td class="table_cell">
								<p>服务标签</p>
							  </td>
							  <td class="table_cell">
								<span class="">生活</span> &gt;
								<span class="">超市</span>
							  </td>
							  <td class="table_cell">
								<p class="desc">根据服务类目生成，向C端用户展示</p>
							  </td>
							  <td class="table_cell">
								<p class="sx">修改</p>
							  </td>
							</tr> -->
							</tbody>
						</table>
					</div>
				</el-tab-pane>
				<el-tab-pane label="配置管理" name="second">
					<div class="card">
						<h2>开发设置</h2>
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
<!--							<span slot="footer" class="dialog-footer">
								<el-button @click="dialogVisible = false">取 消</el-button>
								<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
							</span>-->
						</avue-crud>
					</div>
				</el-tab-pane>
				<el-tab-pane label="搜索管理" name="third">搜索管理</el-tab-pane>
				<el-tab-pane label="其他" name="fourth">
					<el-button size="small" @click.native="cancelAuth(scope.row, scope.index)">取消授权</el-button>
				</el-tab-pane>
			</el-tabs>
		</basic-container>

		<el-dialog
			:visible.sync="headPicflag"
			title="修改小程序头像"
			append-to-body
			top="40px"
		>
			<avue-form ref="form" :option="option" v-model="form">
				<template slot="menuForm">
					<el-button @click="headPicflag = false">取 消</el-button>
					<el-button type="primary" @click="handleSubmit">提 交</el-button>
				</template>
			</avue-form>
		</el-dialog>
		<!-- 修改名称的Dialog-->
		<el-dialog
			:visible.sync="showChangeName"
			title="修改小程序名称"
			:destroy-on-close="true"
			append-to-body
			top="40px"
		>
			<div class="tishi">
				<span> * </span>
				你申请的名称可能涉及特定主体姓名或名称，请提供可确认主体材料与名称对应的材料，或提供《商标注册证》等其他材料，以证明你有权合理且善意使用该名称，否则可能审核不通过!
			</div>
			<avue-form
				ref="nameform"
				:option="nameOption"
				v-model="nameform"
				:upload-preview="uploadPreview"
				:upload-error="uploadError"
				:upload-exceed="uploadExceed"
				:upload-delete="uploadDelete"
				:upload-before="uploadBefore"
				:upload-after="uploadAfter"
			>
			</avue-form>
			<!-- <el-row :span="24">
			  <el-col :span="6">
				<avue-input
				  v-model="appName"
				  placeholder="请输入小程序名称"
				></avue-input>
			  </el-col>
			  <el-col :span="6">
				<el-button type="success" icon="el-icon-search" circle></el-button>
				<span></span>
			  </el-col>
			</el-row> -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="showChangeName = false">取 消</el-button>
				<el-button type="primary" @click.native="innerVisible">提交</el-button>
			</div>
		</el-dialog>

		<!-- 修改服务类目弹窗 -->
		<el-dialog
			:visible.sync="servicesflag"
			title="服务类目"
			:destroy-on-close="true"
			append-to-body
			top="40px"
		>
			<avue-form
				ref="servicesform"
				:option="servicesOption"
				v-model="servicesform"
			>
				<template slot="cascader3Type" slot-scope="{ node, data }">
					<span>{{ (data || {}).label }}</span>
					<span v-if="!node.isLeaf">
            ({{ ((data || {}).children || []).length }})
          </span>
				</template>
			</avue-form>
			<div v-if="servicesChildflag">
				<avue-form
					ref="servicesChildform"
					:option="servicesChildOption"
					v-model="servicesChildform"
				>
				</avue-form>
			</div>
			<span slot="footer" class="dialog-footer">
        <el-button @click="servicesflag = false">取 消</el-button>
        <el-button type="primary" @click="servicesSubmit">提 交</el-button>
      </span>
		</el-dialog>
	</div>
</template>

<style scoped lang="scss" src="./scss/index.scss"></style>
<script>
import {allCategories, domains, headImg, modifyDomains, name, signature, getServices, setServices} from '@/api/wxconfig'
import {fetchList, getObj, addObj, putObj, delObj} from '@/api/basedomains'
import {tableOption, nameOption, servicesOption} from '@/const/crud/wxconfig'
import {mapGetters} from 'vuex'

export default {
	name: 'wxConfig',
	props: ['wxcomfigData'],
	data() {
		return {
			// wxcomfigData: this.wxcomfigData
			activeName: 'baseInfoTab',
			searchForm: {},
			tableData: [],
			page: {
				total: 0, // 总页数
				currentPage: 1, // 当前页数
				pageSize: 20 // 每页显示多少条
			},
			tableLoading: false,
			tableOption: tableOption,
			appNameDialog: false,
			//上传头像组件
			headPicflag: false,
			form: {},
			option: {
				labelWidth: 120,
				emptyBtn: false,
				submitBtn: false,
				column: [
					{
						label: '上传头像',
						// drag: true,
						prop: "headImgUrl",
						type: 'upload',
						fileSize: 3000,
						listType: 'picture-img',
						accept: "image/png,image/jpeg",
						action: '/fastfile/upload/px-jbs',
						multiple: false,
						showColumn: false,
						dataType: "string",
						propsHttp: {
							res: "data",
							url: "kpath"
						},
						canvasOption: {},
						showFileList: true,
						loadText: '附件上传中，请稍等',
						span: 24,
						tip: '3MB以下文件，支持png、jpg格式',
					}
				]
			},
			appName: null, //小程序名称
			showChangeName: false,
			nameform: {
				// img: []
			},
			nameOption: nameOption,
			files: [], //证明文件材料集合

			// 服务类目
			servicesflag: false,
			servicesform: {},
			servicesOption: {
				labelWidth: 150,
				emptyBtn: false,
				submitBtn: false,
				column: [
					{
						label: '服务类目',
						prop: 'cascader',
						type: "cascader",
						span: 24,
						rules: [{
							required: true,
							message: "请选择服务类目",
							trigger: "blur"
						}],
						change: ({value, column}) => {
							// console.log(value, column)
							// console.log(this.getCascaderObj(value, column.dicData))
							let arr = this.getCascaderObj(value, column.dicData);
							let d = arr[arr.length - 1].remark;
							d = JSON.parse(d).exter_list;
							this.servicesChildflag = false;
							this.servicesChildform = {};
							this.servicesChildOption.column = [];
							let list = [];
							if (d.length) {
								d.forEach(i => {
									list = list.concat(i.inner_list)
								})
								let column = [];
								this.servicesChildList = list;
								list.forEach((i, index) => {
									column.push({
										tip: "3MB以下文件，支持png、jpg格式",
										prop: "img" + index,
										span: 24,
										type: "upload",
										label: i.name,
										listType: 'picture-card',
										rules: [{message: "附件必须上传", required: true}],
										accept: "image/png,image/jpeg",
										action: '/fastfile/upload/px-jbs',
										dataType: "array",
										fileSize: 3000,
										limit: 2,
										required: true,
										propsHttp: {
											res: "data",
											url: "kpath"
										},
									})
								})
								this.servicesChildflag = true;
								this.servicesChildOption.column = column;
							}
							console.log(list)

						},
					}

				]
			},

			servicesChildflag: false,
			servicesChildform: {},
			servicesChildOption: {
				labelWidth: 250,
				emptyBtn: false,
				submitBtn: false,
				column: [
					// {
					//   tip: "3MB以下文件，支持png、jpg格式",
					//   prop: "img",
					//   span: 24,
					//   type: "upload",
					//   label: "上传附件",
					//   listType: 'picture-card',
					//   rules: [{ message: "附件必须上传", required: true }],
					//   accept: "image/png,image/jpeg",
					//   action: '/fastfile/file/upload/image/wxinfo',
					//   dataType: "array",
					//   fileSize: 3000,
					//   limit: 2,
					//   // multiple: false,
					//   required: true,
					//   propsHttp: {
					// 		res: "data",
					// 		url: "kpath"
					// },
					// }

				]
			}

		}
	},
	mounted() {
		// console.log('============mounted=================')
		// console.log(this.wxcomfigData)
		// this.getData();
		// this.getAllServices();
	},
	computed: {
		...mapGetters(['permissions']),
		permissionList() {
			return {
				addBtn: this.vaildData(this.permissions.info_basedomains_add, false),
				delBtn: this.vaildData(this.permissions.info_basedomains_del, false),
				editBtn: this.vaildData(this.permissions.info_basedomains_edit, false)
			};
		}
	},

	methods: {
		getData() {
			if (this.wxcomfigData.id) {
				var id = this.wxcomfigData.id;
				allCategories({
					id: id
				}).then(r => {
					console.loh(r)

				})
			}

		},
		getAllServices() {
			let vm = this;
			const loading = vm.$loading({
				lock: true,
				text: 'Loading',
				spinner: 'el-icon-loading',
				background: 'rgba(0, 0, 0, 0.7)'
			});
			// 获取服务类目
			if (this.wxcomfigData.id) {
				var id = this.wxcomfigData.id;
				getServices({
					id: id
				}).then(r => {
					console.log(r)
					if (r.data.data.length) {
						let arr = r.data.data;
						vm.servicesOption.column[0].dicData = arr;
					}
					vm.servicesflag = true;
					loading.close();

				}).catch(res => {
					loading.close();
				})
			}
		},


		// 上传图片
		uploadDelete(column, file) {
			console.log(column, file)
			return this.$confirm(`是否确定移除该选项？`);
		},
		uploadBefore(file, done, loading, column) {
			console.log(file, column)
			done()
			// this.$message.success('开始上传')
		},
		uploadError(error, column) {
			this.$message.success('上传失败')
			console.log(error, column)
		},
		uploadAfter(res, done, loading, column) {
			console.log(res, column)
			done()
			// this.nameform.img.push(res);
			// this.$message.success('上传成功');
		},
		uploadPreview(file, column, done) {
			console.log(file, column)
			done()//默认执行打开方法
			this.$message.success('查看控制台')
		},
		uploadExceed(limit, files, fileList, column) {
			console.log(limit, files, fileList, column)
			this.$message.success('查看控制台')
		},

		// 修改小程序名称
		innerVisible() {
			var vm = this;
			this.$refs.nameform.validate((vaild, done) => {
				if (vaild) {
					let value = vm.nameform;
					// debugger
					// let img = value.img.filter(function (s) {
					//   return s && s.trim();
					// })
					// img = img.toString();
					// name({ id: vm.wxcomfigData.id, name: value.name, otherStuffs: img, license: value.license }).then(res => {
					name({id: vm.wxcomfigData.id, name: value.name, license: value.license}).then(res => {
						console.log(res);
						done();
					})
				}
			})

		},


		// 修改小程序简介
		changeSignature() {
			let vm = this;
			this.$prompt('请输入小程序简介', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				inputValue: vm.wxcomfigData.appSignature
			}).then(({value}) => {
				const loading = vm.$loading({
					lock: true,
					text: 'Loading',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				signature({
					id: vm.wxcomfigData.id,
					signature: value
				}).then(r => {
					vm.getData();
					vm.$message.success("提交成功,请等待审核");
					loading.close();
				}).catch(r => {
					loading.close();
				})
			});
		},

		// 修改头像
		handleSubmit() {
			var vm = this;
			this.$refs.form.validate((vaild, done) => {
				if (vaild) {
					// this.$message.success(JSON.stringify(this.form));
					headImg({id: vm.wxcomfigData.id, headImgUrl: vm.form.headImgUrl}).then(res => {
						console.log(res);
					})
					setTimeout(() => {
						done()
					}, 2000)
				}
			})
		},

		getList(page, params) {
			this.tableLoading = true
			fetchList(Object.assign({
				current: page.currentPage,
				size: page.pageSize
			}, params, this.searchForm)).then(response => {
				if (response.data.data.records.length == 0) {
					this.tableOption.addBtn = true;
				}
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
			const temp = {};
			for (const key in row) {
				temp[key]=row[key].join(',');
			}
			addObj(temp).then(data => {
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

		// 匹配服务类目
		getCascaderObj(val, opt) {
			return val.map(function (value, index, array) {
				for (var itm of opt) {
					if (itm.value == value) {
						opt = itm.children;
						return itm;
					}
				}
				return null;
			});
		},

		// 修改服务类目提交
		servicesSubmit() {
			let vm = this;
			this.$refs.servicesform.validate((vaild, done) => {
				if (vaild) {
					// debugger
					if (vm.servicesChildflag) {
						this.$refs.servicesChildform.validate((vaild, done) => {
							if (vaild) {
								console.log(vm.servicesform)
								console.log(vm.servicesChildform)
								// setTimeout(() => {
								//   done()
								// }, 2000)
								let data = {
									id: this.wxcomfigData.id,
									first: vm.servicesform.cascader[0],
									second: vm.servicesform.cascader[1],
								}

								// let arr = [];
								// vm.servicesChildList.forEach((i, index) => {
								//   arr.push({
								//     key: i.name,
								//     value: vm.servicesChildform['img' + index].toString()
								//   })
								// })
								let arr = {};
								vm.servicesChildList.forEach((i, index) => {
									arr[i.name] = vm.servicesChildform['img' + index].toString();
								})
								data.certicates = arr;
								vm.setServices(data).then(() => {
									done()
								});
							}
						})
					} else {
						console.log(vm.servicesform)
						let data = {
							id: this.wxcomfigData.id,
							first: vm.servicesform.cascader[0],
							second: vm.servicesform.cascader[1]
						}
						vm.setServices(data).then(() => {
							done()
						});
					}
				}
			})
		},
		setServices(data) {
			// debugger
			let vm = this;
			return new Promise((resolve, reject) => {
				setServices(data).then(r => {
					resolve();
				}).catch(error => {
					reject(error)
				})
			})
		},


		/**
		 * 取消授权
		 */
		cancelAuth() {
			var row = this.wxcomfigData;
			this.$confirm(
				"是否确认取消" + row.name + "的名为：" + row.appName + "的小程序授权？",
				"提示",
				{
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				}
			).then(function () {
				//TODO 调用取消接口
				//TODO 关闭界面
			});
		}
	}
}
</script>

<style lang="scss">
</style>

