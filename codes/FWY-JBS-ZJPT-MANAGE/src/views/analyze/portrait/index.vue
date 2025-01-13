<template>
	<div class="" style="padding: 0 10px;">
		<el-card>
			<el-row>
				<el-select v-if="role===1" v-model="appid">
					<el-option v-for="item in shops" :key="item.id" :value="item.appid" :label="item.name"></el-option>
				</el-select>
				<el-select v-model="value" placeholder="请选择日期">
					<el-option
						v-for="item in options"
						:key="item.value"
						:label="item.label"
						:value="item.value">
					</el-option>
				</el-select>
			</el-row>
			<el-row :gutter="20">
				<el-col :span="8">
					<div ref="newProvince" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="newCity" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="newGenders" class="chart"></div>
				</el-col>
			</el-row>
			<el-row :gutter="20">
				<el-col :span="8">
					<div ref="newPlatforms" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="newDevices" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="newAges" class="chart"></div>
				</el-col>
			</el-row>
			<el-row :gutter="20">
				<el-col :span="8">
					<div ref="province" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="city" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="genders" class="chart"></div>
				</el-col>
			</el-row>
			<el-row :gutter="20">
				<el-col :span="8">
					<div ref="platforms" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="devices" class="chart"></div>
				</el-col>
				<el-col :span="8">
					<div ref="ages" class="chart"></div>
				</el-col>
			</el-row>
		</el-card>
	</div>
</template>

<script>
import {getShops, portrait} from '@/api/analyze'
import * as moment from 'moment'
import * as echarts from 'echarts'
export default {
	name: "index",
	data(){
		return {
			options: [{
				value: 1,
				label: '昨天'
			}, {
				value: 7,
				label: '近七天'
			}, {
				value: 30,
				label: '近三十天'
			}],
			sday:'',
			eday:'',
			value: 1,
			dayData:{},
			newProvince:null,
			newCity:null,
			newGenders:null,
			newPlatforms:null,
			newDevices:null,
			newAges:null,
			province:null,
			city:null,
			genders:null,
			platforms:null,
			devices:null,
			ages:null,
			shops:[],
			role:0,
			appid:'',
		}
	},
	watch:{
		value(v,ov){
			if(ov!==v){
				if(v===1){
					this.initDay();
				}else if(v===7){
					this.initWeek();
				}else if(v===30){
					this.initMonth();
				}
			}
		},
		appid(v1){
			if(v1){
				if(this.value===1){
					this.initDay();
				}else if(this.value===7){
					this.initWeek();
				}else if(this.value===30){
					this.initMonth();
				}
			}
		}
	},
	methods:{
		initDay(){
			this.sday=moment().subtract(1, 'days').format('YYYYMMDD');
			this.eday=this.sday;
			this.getDayData();
		},
		initWeek(){
			this.eday=moment().subtract(1, 'days').format('YYYYMMDD');
			this.sday=moment().subtract(7, 'days').format('YYYYMMDD');
			this.getDayData();
		},
		initMonth(){
			this.eday=moment().subtract(1, 'days').format('YYYYMMDD');
			this.sday=moment().subtract(30, 'days').format('YYYYMMDD');
			this.getDayData();
		},
		dayCg(){
			this.getDayData();
		},
		getDayData(){
			this.getData(this.sday,this.eday,'dayData');
		},
		getData(beginDate,endDate,data){
			portrait({beginDate:beginDate,endDate:endDate,appid:this.appid}).then(res=>{
				this[data]=res.data.data;
				this[data+'Charts']();
			})
		},
		initCharts(){
			this.newProvince=echarts.init(this.$refs.newProvince);
			this.newCity=echarts.init(this.$refs.newCity);
			this.newGenders=echarts.init(this.$refs.newGenders);
			this.newPlatforms=echarts.init(this.$refs.newPlatforms);
			this.newDevices=echarts.init(this.$refs.newDevices);
			this.newAges=echarts.init(this.$refs.newAges);
			this.province=echarts.init(this.$refs.province);
			this.city=echarts.init(this.$refs.city);
			this.genders=echarts.init(this.$refs.genders);
			this.platforms=echarts.init(this.$refs.platforms);
			this.devices=echarts.init(this.$refs.devices);
			this.ages=echarts.init(this.$refs.ages);
		},
		dayDataCharts(){
			this.newProvince.setOption(this.getOption(this.dayData,'newProvince','省份'));
			this.newCity.setOption(this.getOption(this.dayData,'newCity','城市'));
			this.newGenders.setOption(this.getOption(this.dayData,'newGenders','性别'));
			this.newPlatforms.setOption(this.getOption(this.dayData,'newPlatforms','终端类型'));
			this.newDevices.setOption(this.getOption(this.dayData,'newDevices','机型'));
			this.newAges.setOption(this.getOption(this.dayData,'newAges','年龄'));
			this.province.setOption(this.getOption(this.dayData,'province','省份'));
			this.city.setOption(this.getOption(this.dayData,'city','城市'));
			this.genders.setOption(this.getOption(this.dayData,'genders','性别'));
			this.platforms.setOption(this.getOption(this.dayData,'platforms','终端类型'));
			this.devices.setOption(this.getOption(this.dayData,'devices','机型'));
			this.ages.setOption(this.getOption(this.dayData,'ages','年龄'));
		},
		getOption(data,type,t){
			var obj=[],d=[],title='';
			if(type.startsWith('new')){
				var str=type.replace('new','').toLowerCase();
				obj=data.visitUvNew[str];
				title='新用户-'+t;
			}else{
				obj=data.visitUv[type];
				title='活跃用户-'+t;
			}
			for (const k in obj) {
				d.push({value:obj[k],name:k});
			}
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
					type: 'scroll',
				},
				series: [
					{
						name: title,
						type: 'pie',
						radius: '50%',
						data: d,
						left: 100,
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
