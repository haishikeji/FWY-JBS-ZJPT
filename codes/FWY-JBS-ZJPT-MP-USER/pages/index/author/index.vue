<template>
	<view :class="{'light-index':them}">
		<view class="filter">
			<view class="item small" :class="{'active':fiindex==1,'light-item':them}" @click="filterclick(1)">默认</view>
			<view class="item small" :class="{'active':fiindex==2||fiindex==3,'light-item1':them}"  @click="filterclick(2)">
				<view class="name"><!-- {{fiindex==2?'男':(fiindex==3?'女':'全部')}} -->{{fiindex==3?'女':'男'}}</view>
				<image src="../../../static/img/icon/f_2.png" mode="" v-if="fiindex==2"></image>
				<image src="../../../static/img/icon/f_3.png" mode="" v-if="fiindex==3"></image>
				<image src="../../../static/img/icon/f_1.png" mode="" v-else></image>
			</view>
			<view class="item" :class="{'active':fiindex==4||fiindex==5,'light-item':them}"  @click="filterclick(3)">
				<view class="name">主持场次</view>
				<image src="../../../static/img/icon/f_2.png" mode="" v-if="fiindex==4"></image>
				<image src="../../../static/img/icon/f_3.png" mode="" v-if="fiindex==5"></image>
				<image src="../../../static/img/icon/f_1.png" mode="" v-else></image>
			</view>
			<view class="item" :class="{'active':fiindex==6||fiindex==7,'light-item':them}"  @click="filterclick(4)">
				<view class="name">DM好评率</view>
				<image src="../../../static/img/icon/f_2.png" mode="" v-if="fiindex==6"></image>
				<image src="../../../static/img/icon/f_3.png" mode="" v-if="fiindex==7"></image>
				<image src="../../../static/img/icon/f_1.png" mode="" v-else></image>
			</view>
		</view>
	
		<view class="list">
			<template v-for="item in dataArr">
				<view class="item" @click="authorDetail(item.id)">
				<image src="../../../static/img/bg1.png" v-if="item.sex==2" mode="" class="bgimg"></image>
				<image src="../../../static/img/bg2.png" v-if="item.sex==1" mode="" class="bgimg"></image>
				<view class="hot">
					<view class="hotbody">
						<image src="../../../static/img/d_4.png" v-if="item.sex==1" mode=""></image>
						<image src="../../../static/img/d_5.png" v-if="item.sex==2" mode=""></image>
						<view class="hotcon">热度：{{item.hot}}</view>
					</view>
	
				</view>
				<view class="bg">
					<image :src="item.image[0]" mode="aspectFill" class="img"></image>
					<view class="info">
						<view class="top">
							<view class="name">{{item.name}}</view>
							<!-- <view class="hot">
								<image src="" mode=""></image>
								<view>9527</view>
							</view> -->
						</view>
						<view class="count">开本场次：{{item.invented}}</view>
						<view class="rate">
							好评率：
							<u-rate :current="item.score" active-color="#FFF29A" inactive-color="#ffffff" inactive-icon="star-fill" gutter="10" :disabled="true" size='32'></u-rate>
						</view>
					</view>
				</view>
			</view>
			</template>
		</view>
	</view>
</template>

<style scoped lang="scss" src="./index.scss"></style>
<script>
	import{
		getdms
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		components: {
			// 注册
		},
		
		data() {
			return {
				array:[1,1,1],
				fiindex:1,
				pageNo:1,
				hsmore:false,
				dataArr:[],
				them:''
			};
		},
		onReady() {
			app.setColor()
		},
		onLoad() {
			this.getList()
			this.them = uni.getStorageSync('them')
		},
		onPullDownRefresh(){
			this.dataArr = []
			this.getList()
			setTimeout(function () {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onReachBottom(){
			if(this.hsmore){
				this.pageNo++
				this.getList()
			}else{
				uni.showToast({
					title:"没有更多了",
					icon:'none'
				})
			}
		},
		onShareAppMessage(){
			return {
			    title: '主持人列表',
			    path: '/pages/index/author/index'
			}
		},
		methods: {
			authorDetail(id){
				uni.navigateTo({
					url:'./detail/index?id=' + id
				})
			},
			getList(){
				var fiindex = this.fiindex
				uni.showLoading({
					title:'加载中'
				})
				var number
				var rate
				var sex
				if(fiindex==2){
					sex = 1
				}
				if(fiindex==3){
					sex = 2
				}
				if(fiindex==4){
					number = 1
				}
				if(fiindex==5){
					number = -1
				}
				if(fiindex==6){
					rate = 1
				}
				if(fiindex==7){
					rate = -1
				}
				var data = {}
				if(this.hsmore){
					data.page = this.pageNo
				}else{
					data.page = 1
					this.pageNo = 1
				}
				data.limit = 10,
				data.number = number||''
				data.rate = rate||''
				data.sex = sex||''
				getdms(data).then(res=>{
					if(res.code==0){
						if(res.data.records.length==10){
							this.hsmore = true
						}else{
							this.hsmore = false
						}
						res.data.records.map(r=>{
							r.image = r.image.split(',')
						})
						this.dataArr = this.dataArr.concat(res.data.records)
						
					}else{
						uni.showToast({
							icon:'none',
							title:res.msg
						})
					}
					uni.hideLoading({
						
					})
				})
			},
			filterclick(e)
			{
				if(e==1)
				{
					this.fiindex = 1
				}else if(e==2)
				{
					if(this.fiindex==2)
					{
						this.fiindex = 3
					}else if(this.fiindex == 3)
					{
						this.fiindex = 2
					}else
					{
						this.fiindex = 2
					}
					
				}else if(e==3)
				{
					if(this.fiindex==4)
					{
						this.fiindex = 5
					}else if(this.fiindex == 5)
					{
						this.fiindex = 4
					}else
					{
						this.fiindex = 4
					}
				}else if(e==4)
				{
					if(this.fiindex==6)
					{
						this.fiindex = 7
					}else if(this.fiindex == 7)
					{
						this.fiindex = 6
					}else
					{
						this.fiindex = 6
					}
				}
				this.dataArr = []
				this.getList()
			}
		}
	};
	
</script>

<style>
</style>
