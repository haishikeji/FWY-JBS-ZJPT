
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
				<template slot-scope="{type,size,row}" slot="menu">
					<el-button :type="type" icon="el-icon-user" @click="showUsers(row)">组局信息</el-button>
				</template>
            </avue-crud>
			<el-dialog title="组局成员" width="80%" :visible.sync="show">
				<avue-crud ref="record1"
						   :data="tableData1"
						   :table-loading="tableLoading1"
						   :option="tableOption1">
				</avue-crud>
			</el-dialog>
        </basic-container>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/corecar'
    import {getUserList} from '@/api/corecaruser'
    import {tableOption,tableOption1} from '@/const/crud/corecar'
    import {mapGetters} from 'vuex'

    export default {
        name: 'corecar',
        data() {
            return {
                searchForm: {},
                tableData: [],
				tableData1: [],
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableLoading: false,
                tableLoading1: false,
                tableOption: tableOption,
                tableOption1: tableOption1,
				show: false
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.shop_corecar_add, false),
                    delBtn: this.vaildData(this.permissions.shop_corecar_del, false),
                    editBtn: this.vaildData(this.permissions.shop_corecar_edit, false)
                };
            }
        },
        methods: {
            getList(page, params) {
                this.tableLoading = true
                fetchList(Object.assign({
                    current: page.currentPage,
                    size: page.pageSize
                }, params, this.searchForm )).then(response => {
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
                putObj(row).then(data => {
                    this.$message.success('修改成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            handleSave: function (row, done,loading) {
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
			showUsers(row){
				getUserList({cid:row.id}).then(res=>{
					this.tableData1=res.data.data;
					this.show=true;
				});
			}
        }
    }
</script>
