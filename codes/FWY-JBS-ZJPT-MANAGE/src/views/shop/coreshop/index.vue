
<template>
    <div class="execution">
        <basic-container>
            <avue-crud ref="crud"
					   v-model="form"
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
					   :upload-exceed="uploadExceed"
                       @row-del="rowDel">
				<template slot="addrs" slot-scope="scope">
					{{scope.row.addrs[2]}}
				</template>
				<template slot="dates" slot-scope="scope">
					{{scope.row.dates.join('~')}} {{scope.row.times.join('~')}}
				</template>
				<template slot-scope="{type,size,row}" slot="menu">
					<el-button :type="type" icon="el-icon-setting" @click="showPay(row)">支付信息</el-button>
				</template>
            </avue-crud>
			<el-drawer
				:visible.sync="drawer"
				:with-header="false">
				<div class="" style="padding:20px;">
					<avue-form ref="payForm" v-model="payForm" :option="tableOption1"></avue-form>
				</div>
			</el-drawer>
        </basic-container>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/coreshop'
    import {tableOption,tableOption1} from '@/const/crud/coreshop'
    import {mapGetters} from 'vuex'

    export default {
        name: 'coreshop',
        data() {
            return {
                searchForm: {},
				payForm: {},
                tableData: [],
				form: {},
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableLoading: false,
				drawer: false,
                tableOption: tableOption,
                tableOption1: tableOption1,
            }
        },
		watch: {
			'form.addrs'(v){
				this.form.lat=v[1]
				this.form.lng=v[0]
				this.form.addr=v[2]
			},
			'form.dates'(v){
				this.form.sdate=v[0]
				this.form.edate=v[1]
			},
			'form.times'(v){
				this.form.stime=v[0]
				this.form.etime=v[1]
			}
		},
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.shop_coreshop_add, false),
                    delBtn: this.vaildData(this.permissions.shop_coreshop_del, false),
                    editBtn: this.vaildData(this.permissions.shop_coreshop_edit, false)
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
					var records = response.data.data.records;
					records.map(r => {
						r.addrs=[r.lng,r.lat,r.addr];
						r.dates=[r.sdate,r.edate];
						r.times=[r.stime,r.etime];
						return r;
					})
					this.tableData = records
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
					if(data.data.code===0){
						this.$message.success('修改成功')
						done()
						this.getList(this.page)
					}else{
						this.$message.error(data.msg)
					}
                }).catch(() => {
                    loading();
                });
            },
            handleSave: function (row, done,loading) {
                addObj(row).then(data => {
                	if(data.data.code===0){
						this.$message.success('添加成功')
						done()
						this.getList(this.page)
					}else{
						this.$message.error(data.msg)
					}
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
			showPay(row){
            	this.payForm=Object.assign({}, row);
            	this.drawer=true;
			},
			uploadExceed(limit, files, fileList, column){
				fileList=[];
				this.$message.error('只能上传一个文件，请删除原文件')
			},
        }
    }
</script>
