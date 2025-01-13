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
				<div ref="dayDataNewChart" class="chart"></div>
			</el-row>
		</el-card>
	</div>
</template>

<script>
import {getShops, summary} from '@/api/analyze'
import * as moment from 'moment'
import * as echarts from 'echarts'
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
			dayDataNewChart:null,
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
			this.getData(this.day,'dayData');
		},
		getData(beginDate,data){
			summary({beginDate:beginDate,endDate:beginDate,appid:this.appid}).then(res=>{
				this[data]=res.data.data;
				this[data+'Charts']();
			})
		},
		initCharts(){
			this.dayDataNewChart=echarts.init(this.$refs.dayDataNewChart);
		},
		dayDataCharts(){
			this.dayDataNewChart.setOption(this.getOption(this.dayData));
		},
		getOption(data){
			var l=['累计用户数','转发次数','转发人数'];
			var d=[],lj=[],zc=[],zr=[];
			data.map(v=>{
				d.push(v.refDate);
				lj.push(v.visitTotal);
				zc.push(v.sharePv);
				zr.push(v.shareUv);
			});
			return {
				title: {
					text: '数据概况'
				},
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
				},
				legend: {
					data: l
				},
				xAxis: [
					{
						type: 'category',
						axisTick: {show: false},
						data: d
					}
				],
				yAxis: [
					{
						type: 'value'
					}
				],
				series: [
					{
						name: '累计用户数',
						type: 'bar',
						emphasis: {
							focus: 'series'
						},
						data: lj
					},{
						name: '转发次数',
						type: 'bar',
						emphasis: {
							focus: 'series'
						},
						data: zc
					},{
						name: '转发人数',
						type: 'bar',
						emphasis: {
							focus: 'series'
						},
						data: zr
					},
				]
			}
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
		this.initCharts();
		this.initDay();
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
