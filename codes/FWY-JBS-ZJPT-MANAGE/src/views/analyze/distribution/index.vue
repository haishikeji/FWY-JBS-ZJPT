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
				<div ref="sourceChart" class="chart"></div>
			</el-row>
			<el-row>
				<el-col :span="12">
					<div class="chart" ref="timeChart"></div>
				</el-col>
				<el-col :span="12">
					<div class="chart" ref="deepChart"></div>
				</el-col>
			</el-row>
		</el-card>
	</div>
</template>

<script>
import {distribution, getShops} from '@/api/analyze'
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
			sourceChart:null,
			timeChart:null,
			deepChart:null,
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
			distribution({beginDate:beginDate,endDate:beginDate,appid:this.appid}).then(res=>{
				this[data]=res.data.data.list;
				this[data+'Charts']();
			})
		},
		initCharts(){
			this.sourceChart=echarts.init(this.$refs.sourceChart);
			this.timeChart=echarts.init(this.$refs.timeChart);
			this.deepChart=echarts.init(this.$refs.deepChart);
		},
		dayDataCharts(){
			this.sourceChart.setOption(this.getOption(this.dayData,'access_source_session_cnt'));
			this.timeChart.setOption(this.getOption(this.dayData,'access_staytime_info'));
			this.deepChart.setOption(this.getOption(this.dayData,'access_depth_info'));
		},
		getOption(data,type){
			var obj=data[type];
			if(type==='access_source_session_cnt'){
				const obj_key={1:'小程序历史列表',2:'搜索',3:'会话',4:'扫一扫二维码',5:'公众号主页',6:'聊天顶部',7:'系统桌面',8:'小程序主页',9:'附近的小程序',10:'其他',
					11:'模板消息',12:'客服消息',13:'公众号菜单',14:'APP分享',15:'支付完成页',16:'长按识别二维码',17:'相册选取二维码',18:'公众号文章',19:'钱包',20:'卡包',
					21:'小程序内卡券',22:'其他小程序',23:'其他小程序返回',24:'卡券适用门店列表',25:'搜索框快捷入口',26:'小程序客服消息',27:'公众号下发',28:'系统会话菜单',29:'任务栏-最近使用',30:'长按小程序菜单圆点',
					31:'连wifi成功页',32:'城市服务',33:'微信广告',34:'其他移动应用',35:'发现入口-我的小程序',36:'任务栏-我的小程序',37:'微信圈子',38:'手机充值',39:'H5',40:'插件',
					41:'大家在用',42:'发现页',43:'浮窗',44:'附近的人',45:'看一看',46:'朋友圈',47:'企业微信',48:'视频',49:'收藏',50:'微信红包',
					51:'微信游戏中心',52:'摇一摇',53:'公众号导购消息',54:'识物',55:'小程序订单',56:'小程序直播',57:'群工具',59:'群待办',60:'第三方APP',62:'视频号'
				};
				let l = [],d = [];
				for (const key in obj) {
					l.push(obj_key[key]);
					d.push(obj[key]);
				}
				return {
					title: {
						text: '访问来源分布'
					},
					tooltip: {
						trigger: 'axis',
						axisPointer: {            // 坐标轴指示器，坐标轴触发有效
							type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					xAxis: [
						{
							type: 'category',
							data: l,
							axisTick: {
								alignWithLabel: true
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					series: [
						{
							name: '访问来源分布',
							type: 'bar',
							barWidth: '60%',
							data: d
						}
					]
				}
			}else{
				let title = '访问深度的分布';
				let obj_key={1:'1 页',2:'2 页',3:'3 页',4:'4 页',5:'5 页',6:'6-10 页',7:'>10 页'};
				if(type==='access_staytime_info'){
					obj_key={1:'0-2s',2:'3-5s',3:'6-10s',4:'11-20s',5:'20-30s',6:'30-50s',7:'50-100s',8:'>100s'};
					title='访问时长分布';
				}
				let d = [];
				for (const key in obj) {
					d.push({value:obj[key],name:obj_key[key]});
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
