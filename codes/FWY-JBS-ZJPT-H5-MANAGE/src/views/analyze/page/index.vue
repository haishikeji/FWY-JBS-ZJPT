<template>
	<div class="" style="padding: 0 10px;">
		<el-card>
			<el-row>
				<el-select v-if="role===1" v-model="appid">
					<el-option v-for="item in shops" :key="item.id" :value="item.appid" :label="item.name"></el-option>
				</el-select>
				<el-date-picker
					v-model="day"
					type="date"
					placeholder="选择日期"
					value-format="yyyyMMdd"
					@change="dayCg"
					:picker-options="pickerOptions">
				</el-date-picker>
			</el-row>
			<el-row>
				<avue-crud ref="crud"
						   :data="tableData"
						   :table-loading="tableLoading"
						   :option="tableOption"
						   @on-load="initDay">
				</avue-crud>
			</el-row>
		</el-card>
	</div>
</template>

<script>
import {getShops, page} from '@/api/analyze'
import * as moment from 'moment'
export default {
	name: "index",
	data(){
		return {
			dayData:{},
			pickerOptions: {
				disabledDate(time) {
					return time.getTime() > moment().subtract(1, 'days').format('x');
				},
			},
			day:'',
			tableLoading: false,
			tableOption: {
				align: "center",
				index: false,
				column: [
					{
						prop: "pagePath",
						label: "页面路径",
					},{
						prop: "pageVisitPv",
						label: "访问次数",
					},{
						prop: "pageVisitUv",
						label: "访问人数",
					},{
						prop: "pageStayTimePv",
						label: "次均停留时长",
					},{
						prop: "entryPagePv",
						label: "进入页次数",
					},{
						prop: "exitPagePv",
						label: "退出页次数",
					},{
						prop: "pageSharePv",
						label: "转发次数",
					},{
						prop: "pageShareUv",
						label: "转发人数",
					},
				],
				gutter: 0,
				stripe: true,
				menu: false,
				addBtn:false,
				editBtn:false,
				delBtn:false,
				emptyBtn: false,
			},
			shops:[],
			role:0,
			appid:'',
		}
	},
	methods:{
		initDay(){
			this.day=moment().subtract(1, 'days').format('YYYYMMDD');
			this.getDayData();
		},
		dayCg(){
			this.getDayData();
		},
		getDayData(){
			this.getData(this.day);
		},
		getData(beginDate){
			this.tableLoading = true
			page({beginDate:beginDate,endDate:beginDate,appid:this.appid}).then(res=>{
				this.tableData =res.data.data;
				this.tableLoading = false
			})
		},
		initShops(){
			getShops().then(res=>{
				this.shops=res.data.data.shops
				this.role=res.data.data.role;
			});
		}
	},
	watch:{
		appid(v1){
			if(v1){
				this.getDayData();
			}
		}
	},
	mounted() {
		moment.locale('zh-cn');
		this.initShops();
	}
}
</script>

<style scoped>
	.el-divider--vertical{
		height: 100% !important;
	}
	.chart{
		height: 300px;
	}
</style>
