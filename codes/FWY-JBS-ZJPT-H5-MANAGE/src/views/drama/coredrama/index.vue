<template>
    <div class="execution">
        <basic-container>
            <avue-crud ref="crud"
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
				<template slot="detail" slot-scope="{type,size,row}">
					<el-button type="text" @click="preview(row)">查看</el-button>
				</template>
				<template slot="image" slot-scope="{type,size,row}">
					<el-image
						style="height: 50px;width: 50px;"
						:src="row.image"
						:z-index="3000"
						:preview-src-list="[row.image]">
					</el-image>
				</template>
				<template slot-scope="{type,size,row}" slot="menu">
					<el-button v-if="row.roles.length>0" :type="type" icon="el-icon-user" @click="showRoles(row)">查看角色</el-button>
				</template>
            </avue-crud>
			<el-drawer
				:visible.sync="drawer"
				:with-header="false">
				<div style="padding:20px;">
					<div v-if="content" v-html="content"></div>
					<el-row v-if="roles.length>0" v-for="item in roles">
						<el-col :span="8">
							<el-image
								style="height: 200px;width: 90%;"
								:src="item.image"
								:z-index="3000"
								:preview-src-list="[item.image]">
							</el-image>
						</el-col>
						<el-col :span="16">
							<el-row>
								<h3>{{item.name}}</h3>
							</el-row>
							<el-row>
								<p>{{item.detail}}</p>
							</el-row>
						</el-col>
					</el-row>
				</div>
			</el-drawer>
        </basic-container>
    </div>
</template>
<style>
.el-scrollbar{
	height: 100%!important;
}
.content{
	padding:0 20px;
	height:100%;
	overflow:auto;
}
.content img{
	width:100%;
}
</style>
<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/coredrama'
    import {getRoles} from '@/api/coredramarole'
    import {tableOption} from '@/const/crud/coredrama'
    import {mapGetters} from 'vuex'
	import AvueUeditor from 'avue-plugin-ueditor'

    export default {
        name: 'coredrama',
        data() {
            return {
                searchForm: {},
                tableData: [],
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableLoading: false,
                tableOption: tableOption,
				content: '',
				drawer: false,
				loading: null,
				roles:[],
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.drama_coredrama_add, false),
                    delBtn: this.vaildData(this.permissions.drama_coredrama_del, false),
                    editBtn: this.vaildData(this.permissions.drama_coredrama_edit, false)
                };
            }
        },
        methods: {
            getList(page, params) {
                this.tableLoading = true
				var obj=Object.assign({
					current: page.currentPage,
					size: page.pageSize
				}, params, this.searchForm );
                if(obj.typeIds&&obj.typeIds.length>0){
					obj.typeIds = obj.typeIds.join(',');
				}
                fetchList(obj).then(response => {
                    this.tableData = response.data.data.records
                    this.page.total = response.data.data.total
                    this.tableLoading = false
                }).catch(() => {
                    this.tableLoading=false
                })
            },
            rowDel: function (row, index) {
                this.$confirm('是否确认删除', '提示', {
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
            handleUpdate: function (row, index, done,loading) {
				// const obj={title:row.title,image:row.image,state:row.state};
				// obj.value=this.getValue(row);
                putObj(row).then(data => {
                    this.$message.success('修改成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            handleSave: function (row, done,loading) {
				// const obj={title:row.title,image:row.image,state:row.state};
				// obj.value=this.getValue(row);
                addObj(row).then(data => {
                    this.$message.success('添加成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            sizeChange(pageSize){
                this.page.pageSize = pageSize
            },
            currentChange(current){
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
			preview(row){
				this.content=row.detail;
				this.roles=[];
				this.drawer=true;
			},
			showRoles(row){
				this.roles=row.roles;
				if(this.roles.length>0){
					this.drawer=true;
					this.content='';
				}else{
					this.$message.error('该剧本暂无角色');
				}
			},
			openFullScreen() {
				this.loading = this.$loading({
					lock: true,
					text: '加载中。。。',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
			},
        }
    }
</script>
