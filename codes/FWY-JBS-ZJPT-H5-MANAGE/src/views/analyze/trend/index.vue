<template>
	<div class="" style="padding: 0 10px;">
		<el-card>
			<el-row style="text-align: center;">
				<el-select v-if="role===1" v-model="appid">
					<el-option v-for="item in shops" :key="item.id" :value="item.appid" :label="item.name"></el-option>
				</el-select>
			</el-row>
			<el-row type="flex" justify="space-around" style="text-align: center;height: 820px;">
				<el-col :span="8">
					<div class="title">按天统计</div>
					<el-date-picker
						v-model="day"
						type="date"
						placeholder="选择日期"
						value-format="yyyyMMdd"
						@change="dayCg"
						:picker-options="pickerOptions">
					</el-date-picker>
					<div ref="dayDataNewChart" class="chart"></div>
					<div ref="dayDataChart" class="chart"></div>
				</el-col>
				<el-divider direction="vertical"></el-divider>
				<el-col :span="8">
					<div class="title">按周统计</div>
					<el-date-picker
						v-model="week"
						type="week"
						format="yyyy 第 WW 周"
						placeholder="选择周"
						value-format="yyyyMMdd"
						@change="weekCg"
						:picker-options="pickerOptions1">
					</el-date-picker>
					<div ref="weekDataNewChart" class="chart"></div>
					<div ref="weekDataChart" class="chart"></div>
				</el-col>
				<el-divider direction="vertical"></el-divider>
				<el-col :span="8">
					<div class="title">按月统计</div>
					<el-date-picker
						v-model="month"
						type="month"
						placeholder="选择月"
						value-format="yyyyMMdd"
						@change="monthCg"
						:picker-options="pickerOptions2">
					</el-date-picker>
					<div ref="monthDataNewChart" class="chart"></div>
					<div ref="monthDataChart" class="chart"></div>
				</el-col>
			</el-row>
		</el-card>
	</div>
</template>

<script>
import {getShops, trend} from '@/api/analyze'
import * as moment from 'moment'
import * as echarts from 'echarts'
export default {
	name: "index",
	data(){
		return {
			dayData:{},
			weekData:{},
			monthData:{},
			pickerOptions: {
				disabledDate(time) {
					return time.getTime() > moment().subtract(1, 'days').format('x');
				},
			},
			pickerOptions1: {
				disabledDate(time) {
					return time.getTime() > moment().subtract(1, 'weeks').format('x');
				},
			},
			pickerOptions2: {
				disabledDate(time) {
					return time.getTime() > moment().subtract(1, 'months').format('x');
				},
			},
			day:'',
			week:'',
			month:'',
			dayDataNewChart:null,
			dayDataChart:null,
			weekDataNewChart:null,
			weekDataChart:null,
			monthDataNewChart:null,
			monthDataChart:null,
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
		initWeek(){
			this.week=moment().subtract(1, 'weeks').startOf('week').format('YYYYMMDD');
			this.getWeekData();
		},
		initMonth(){
			this.month=moment().subtract(1, 'months').startOf('month').format('YYYYMMDD');
			this.getMonthData();
		},
		dayCg(){
			this.getDayData();
		},
		getDayData(){
			this.getData(1,this.day,this.day,'dayData');
		},
		weekCg(){
			this.getWeekData();
		},
		getWeekData(){
			this.getData(2,this.week,moment(this.week).endOf('week').format('YYYYMMDD'),'weekData');
		},
		monthCg(){
			this.getMonthData();
		},
		getMonthData(){
			this.getData(3,this.month,moment(this.month).endOf('month').format('YYYYMMDD'),'monthData');
		},
		getData(type,beginDate,endDate,data){
			console.log(type,beginDate,endDate);
			trend({type:type,beginDate:beginDate,endDate:endDate,appid:this.appid}).then(res=>{
				this[data]=res.data.data;
				this[data+'Charts']();
			})
		},
		initCharts(){
			this.dayDataNewChart=echarts.init(this.$refs.dayDataNewChart);
			this.dayDataChart=echarts.init(this.$refs.dayDataChart);
			this.weekDataNewChart=echarts.init(this.$refs.weekDataNewChart);
			this.weekDataChart=echarts.init(this.$refs.weekDataChart);
			this.monthDataNewChart=echarts.init(this.$refs.monthDataNewChart);
			this.monthDataChart=echarts.init(this.$refs.monthDataChart);
		},
		dayDataCharts(){
			this.dayDataNewChart.setOption(this.getOption(this.dayData,'count'));
			this.dayDataChart.setOption(this.getOption(this.dayData));
		},
		weekDataCharts(){
			this.weekDataNewChart.setOption(this.getOption(this.weekData,'count'));
			this.weekDataChart.setOption(this.getOption(this.weekData));
		},
		monthDataCharts(){
			this.monthDataNewChart.setOption(this.getOption(this.monthData,'count'));
			this.monthDataChart.setOption(this.getOption(this.monthData));
		},
		getOption(data,type){
			var title='时长统计';
			var d=[];
			if(type==='count'){
				title='数量统计';
			}
			data.map(v=>{
				if(type==='count'){
					d.push({value:v.sessionCnt,name:'打开次数'});
					d.push({value:v.visitPv,name:'访问次数'});
					d.push({value:v.visitUv,name:'访问人数'});
					d.push({value:v.visitUvNew,name:'新用户数'});
				}else{
					d.push({value:v.stayTimeUv,name:'人均停留时长'});
					d.push({value:v.stayTimeSession,name:'次均停留时长'});
					d.push({value:v.visitDepth,name:'平均访问深度'});
				}
			})
			return {
				title: {
					text: title,
					left: 'center'
				},
				tooltip: {
					trigger: 'item'
				},
				legend: {
					orient: 'vertical',
					left: 'left',
				},
				series: [
					{
						name: title,
						type: 'pie',
						radius: '50%',
						data: d,
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
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
				this.getWeekData();
				this.getMonthData();
			}
		}
	},
	mounted() {
		moment.locale('zh-cn');
		this.initCharts();
		this.initDay();
		this.initWeek();
		this.initMonth();
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
