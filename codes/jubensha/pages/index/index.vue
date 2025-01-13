<template>
	<view class="">
		
	<view :class="them?'light':''">
		<!-- <view class="address" @click="gohere" :style="{margin:margin}">
			<view class="left">
				<image src="../../static/img/weather.png" mode=""></image>
			</view>
			<view class="right">
				<view class="right-top">{{shopArr[0].name}}</view>
				<view class="right-bot">雷阵雨24°C</view>
			</view> 
		</view> -->
		<view class="banner" v-if="bannerview" :class="them?'light-banner':''">
			<swiper class="bannerswiper" autoplay="true">
				<swiper-item v-if="banner.length>0" v-for="item in banner">
					<image :src="item" mode="aspectFill" class="sliderimg"></image>
				</swiper-item>
				<swiper-item  v-if="banner.length==0">
          <!-- TODO 线上图片 -->
					<image src="http://res.jb.hnpinxun.cn/1000001348756919.png" mode="aspectFill" class="sliderimg"></image>
				</swiper-item>
			</swiper>
		</view>
		<div v-if="scroll" class="banner-top" :class="them?'light-banner-top':''" :style="{height:navheight}">
			<view class="" :style="{height:navpadding}">
				
				
			</view>
			<view class="" :style="{lineHeight:lineHeight}">
				{{shopArr[0].name||''}}
			</view>
		</div>
		<view class="banner-bot" :class="them?'light-banner-bot':''">
			
		<view class="index-top" :class="them?'light-index-top':''">
			<view class="index-left" :class="them?'light-index-l':''" @click="gohere">
				<view class="index-logo">
					<image :src="logoShow" mode="heightFix"  @error="errorFunction"></image>
					<!-- <image src="../../static/img/logo.png" mode="heightFix"></image> -->
				</view>
				<view class="index-title" :class="them?'light-index-title':''">
					{{shopArr[0].name||''}}
				</view>
				<view class="index-address">
					<image v-if="!them" class="index-icon" src="../../static/img/addr.png" mode="aspectFit"></image>
					<image v-if="them" class="index-icon" src="../../static/img/addr_l.png" mode="aspectFit"></image>
					<view class="address-name" :class="them?'light-index-title':''">
						{{shopArr[0].addr||''}}
					</view>
				</view>
			</view>
			<view class="index-right" :class="them?'light-index-l':''">
				<view class="index-day">
					{{month}}月{{day}}日
				</view>
				<image class="index-we" :src="src" mode="aspectFit"></image>
				<view class="index-w-name" :class="them?'light-index-title':''">{{type}}{{wendu}}°C</view>
				
				<!-- <view class="switchbg" @click="changetheme">切换主题</view> -->
			</view>
		</view>
		
		<view class="notice" @click="notice" :class="them?'light-index-notice':''">
			<view class="title">
				<view :class="them?'light-index-title':''">店铺通知:</view>
			</view>
			<swiper class="swiper" vertical='true' autoplay='true' interval='3000' circular='true'>
				<swiper-item v-for="item in noticeArr">
					<view class="con" :class="them?'light-index-con':''">
						{{item.content}}
					</view>
				</swiper-item>
			</swiper>
		</view>
		<view class="coupon" @click="coupon" :class="them?'light-index-coupon':''">
			<view class="coupon-left">
				<view class="left-title" :class="them?'light-index-title':''">优惠券中心</view>
				<view class="left-text" :class="them?'light-index-title':''">前往领券></view>
			</view>
			<view class="coupon-img"> 
				<image v-if="!them" src="../../static/img/coupon_index.png" mode=""></image>
				<image v-if="them" src="../../static/img/coupon_indexl.png" mode=""></image>
			</view>
		</view>
		<view class="vipview":class="them?'light-vipview':''" @click="user">
			<view class="vipitem">
				<view class="vipitem-img">
					<image v-if="!them" src="../../static/img/leval.png" mode=""></image>
					<image v-if="them" src="../../static/img/leval_l.png" mode=""></image>
				</view>
				<view :class="them?'light-index-user':''">普通会员</view>
			</view> 
			<view class="vipitem">
				<view class="vipitem-img">
					<image v-if="!them" src="../../static/img/wallet.png" mode=""></image>
					<image v-if="them" src="../../static/img/wallet_l1.png" mode=""></image>
				</view>
				<view :class="them?'light-index-yue':''">余额:{{dmUser.wallet?dmUser.wallet:'0'}}</view>
			</view>
		</view>
		<!-- <view class="zuju">
			<view class="zuju-top">
				<image src="../../static/img/detail_1.png" mode=""></image>
				<view class="top-c">
					<view class="top-peo">
						发起者
					</view>
					<view class="top-name">
						#剧本名称
					</view>
				</view>
				<view class="top-r">
					加入组局
				</view>
			</view>
			<view class="zuju-bot">
				<view class="bot-left">
					<image v-for="(item,i) in anthor" v-if="i<6" src="../../static/img/detail_1.png" mode=""></image>
				</view>
				<view class="bot-right">6 online</view>
			</view>
		</view> -->
		<view class="tuijian">
			<view class="tuijian-left" :class="them?'light-tuijian-left':''">
				<image v-if="!them" src="../../static/img/tuijian.png" mode="aspectFit"></image>
				<image v-if="them" src="../../static/img/tuijian_l.png" mode="aspectFit"></image>
				<text>剧本推荐</text>
			</view>
			<view class="tuijian-right" @click="moreDrama">MORE+</view>
		</view>
		<view class="recomm">
			<swiper class="recommswiper" @change='swiperChange'  previous-margin="40rpx" display-multiple-items="2.5">
				<swiper-item  v-for="(item,i) in recomment">
					<view class="recomm_item" @click="dramaDetail(item.id)">
						<view class="item_img">
							<image :src="item.image" mode="aspectFill"></image>
						</view>
						<view class="item_bot">
							<view class="bot_text">{{item.dramaName}}</view>
							<view>
								<u-rate size='26rpx' active-color="#FFF29A" inactive-color="#fff" :current="item.score" :disabled="true"></u-rate>
							</view>
						</view>
					</view>
				</swiper-item>
			</swiper>
		</view>
		<view class="tuijian">
			<view class="tuijian-left" :class="them?'light-tuijian-left':''">
				<image v-if="!them" src="../../static/img/zhuchiren.png" mode="aspectFit"></image>
				<image v-if="them" src="../../static/img/zhuchiren_l.png" mode="aspectFit"></image>
				<text>本店主持人</text>
			</view>
			<view class="tuijian-right" @click="moreDm">MORE+</view>
		</view>
		<view class="list">
			<swiper class="list_swiper" @change='swiperChange1' previous-margin="20rpx" display-multiple-items="1.2">
				<swiper-item  v-for="(item,i) in anthor">
					<view @click="dmDetail(item.id)" class="item" :class="{'active':item.sex==1}">
						<image :src="item.image" mode="aspectFill"></image>
						<view class="item_right">
							<view class="item_name">{{item.name}}</view>
							<view class="item_count">开本次数：{{item.invented}}次</view>
							<view><u-rate size='26rpx' active-color="#FFF29A" inactive-color="#fff" :current="item.score" :disabled="true"></u-rate></view>
						</view>
						<view class="item_hot" :class="{'active':item.sex==1}">
							{{item.hot}}
						</view>
					</view>
				</swiper-item>
				<swiper-item v-if="anthor.length==1">
					
				</swiper-item>
			</swiper>
		</view>
		</view>
		<canvas canvas-id="myCanvas" id="myCanvas" style="width: 750rpx;height: 600rpx;position: absolute;top: -1000rpx;"/>
	</view>
	
	</view>
</template>
<style scoped lang="scss" src="./index.scss"></style>
<script>
import{
	getrecomment,
	getdms,
	getuserInfo,
	getnotices,
	updateLoc
}from '../../static/js/api.js'

import qqmapsdk from '@/utils/qqmap-wx-jssdk1.2/qqmap-wx-jssdk.min.js';
const QQMapWX = new qqmapsdk({

  // TODO  腾讯地图key
	key: '************' //开发者密钥 腾讯地图key
});

let app = getApp()
export default {
	components: {
		// 注册
	},
	
	data() {
		return {
			notices:[1],
			noticeArr:[],
			recomment:[],
			anthor:[],
			swiperCurrent:1,
			swiperCurrent1:1,
			shopArr:[],
			system:{},
			margin:'',
			dmUser:{},
			banner:[],
			wendu:'',
			type:'',
			month:'',
			day:'',
			logoshare:'',
			menuButtonInfo:{},
			navheight:'',
			navpadding:'',
			lineHeight:'',
			scroll:false,
			bannerview:true,
			src:'',
			logo:'',
			logoShow:'',
			them:'',
		};
	},
	computed:{
		/* getWeather(){
			var type = this.type
			var src = '../../static/img/yin.png'
			if(type.indexOf('晴')>-1){
				src = '../../static/img/sunny.png'
				return src
			}
			if(type.indexOf('雨')>-1){
				src = '../../static/img/rain.png';
				return src
			}
			if(type.indexOf('阴')>-1){
				src = '../../static/img/yin.png'
				return src
			}
			if(type.indexOf('云')>-1){
				src = '../../static/img/cloud.png'
				return src
			}
			if(type.indexOf('雷阵雨')>-1){
				src = '../../static/img/lei.png'
				return src
			}
			if(type.indexOf('雪')>-1){
				src = '../../static/img/snow.png'
				return src
			}
			if(type.indexOf('阵雨')>-1){
				src = '../../static/img/zhengyu.png'
				return src
			}
			if(type.indexOf('未知')>-1){
				src = '../../static/img/yin.png'
				return src
			}
		} */
	},
	onPageScroll(e) { 
		// var system = uni.getSystemInfoSync()
		// let menuButtonInfo = uni.getMenuButtonBoundingClientRect()
		// var navheight = system.statusBarHeight*2 + menuButtonInfo.height*2 + menuButtonInfo.top
		
		if (e.scrollTop > 100) { 
			this.scroll = true
			this.bannerview = false
			app.setColor()
		} else { 
			this.scroll = false
			this.bannerview = true
			app.setNoNavColor()
		}
	},
	onReady() {
		app.setNoNavColor()
	},
	onLoad() {
		var system = uni.getSystemInfoSync()
		this.system = system
		let menuButtonInfo = uni.getMenuButtonBoundingClientRect()
		this.menuButtonInfo = menuButtonInfo
		this.navheight = system.statusBarHeight*2 + menuButtonInfo.height*2 + menuButtonInfo.top+'rpx'
		this.navpadding = system.statusBarHeight*2 + 'rpx'
		this.lineHeight = menuButtonInfo.height*2 + menuButtonInfo.top +'rpx'
		console.log(system,'11111')
		this.margin = system.statusBarHeight+'rpx'+' 0'+' 0'+' 0'
		this.item().then(res=>{
			console.log(5555555555555)
			this.shareImg()
		})
		var date = new Date();
		this.month = date.getMonth() + 1;
		this.day = date.getDate();
		
		// const ctx = uni.createCanvasContext('myCanvas')
		// ctx.drawImage('../../static/img/bg.png', 0, 0, 375, 300)
		// // ctx.draw()
		// ctx.drawImage('../../static/img/logo.png', 115, 90, 150, 150)
		// ctx.draw()
		
		// var that = this
		// setTimeout(function(){
		// 	uni.canvasToTempFilePath({
		// 	  x: 0,
		// 	  y: 0,
		// 	  width:375,
		// 	  height:300,
		// 	  canvasId: 'myCanvas',
		// 	  success: function(res) {
		// 	    console.log(res.tempFilePath,"11111")
		// 		that.logoshare = res.tempFilePath
		// 	  },
		// 	  complete:function(r){
		// 		  console.log(r,"22222")
		// 	  }
		// 	})
		// },500)
	},
	onPullDownRefresh(){
		var data = {
			page:1,
			limit:5
		}
		this.getLists(data)
		setTimeout(function () {
			uni.stopPullDownRefresh();
		}, 1000);
	},
	onShareAppMessage(){
		return {
		    title: this.shopArr[0].name,
		    path: '/pages/index/index',
			imageUrl:this.logoshare,
		}
	},
	onShareTimeline(){
		return {
		    title: this.shopArr[0].name,
		    path: '/pages/index/index',
			imageUrl:this.logoshare,
		}
	},
	// onTabItemTap(e){
	// 	// this.dmUser = uni.getStorageSync('dmUser')
	// 	this.item()
	// },
	onShow(){
		app.setColor()
		app.setNoNavColor()
		this.them = uni.getStorageSync('them')
		if(this.them){
			uni.setBackgroundColor({
				backgroundColor:'#ffffff',
				success(res){
					console.log(res)
				}
			})
		}
		
		var that = this
		if(!that.dmUser.name){
			uni.showLoading({
				title:'加载中'
			})
			app.getlogin().then(g=>{
				getuserInfo().then(r=>{
					if(r.code==0){
						console.log(r.data)
						that.dmUser = r.data
						uni.setStorageSync('dmUser',r.data)
						that.getLocationInfo()
					}
					uni.hideLoading()
				})
			})
		}else{
			that.getLocationInfo()
		}
	},
	methods: {
		changetheme()
		{
			
			var t = uni.getStorageSync('them')
			var tt = !t
			uni.setStorageSync('them',tt)
			this.them = tt
			app.setColor()
		},
		
		errorFunction(event){
			console.log(event.detail)
			this.logoShow = '../../static/img/logo.png'
		},
		
		shareImg(){
			uni.downloadFile({
			    url: this.logo, //仅为示例，并非真实的资源
			    success: (res) => {
			        if (res.statusCode === 200) {
			            console.log('下载成功');
						console.log(res.tempFilePath)
						const ctx = uni.createCanvasContext('myCanvas')
						ctx.drawImage('../../static/img/bg.png', 0, 0, 375, 300)
						ctx.drawImage(res.tempFilePath, 115, 40, 150, 150)
						ctx.draw()
			        }
			    }
			});
			var that = this
			setTimeout(function(){
				uni.canvasToTempFilePath({
				  x: 0,
				  y: 0,
				  width:375,
				  height:300,
				  canvasId: 'myCanvas',
				  success: function(res) {
				    console.log(res.tempFilePath,"11111")
					that.logoshare = res.tempFilePath
					
				  },
				  complete:function(r){
					  console.log(r,"22222")
				  }
				})
			},500)
		},
		insertStr(soure, start, newStr){
		  return soure.slice(0, start) + newStr + soure.slice(start);
		},
		item(){
			return new Promise((resolve, reject) => {
				var that = this
				const accountInfo = uni.getAccountInfoSync();
				let header = {
					'APPID':accountInfo.miniProgram.appId,
				}
				app.getList(header).then(res=>{
					var data = {
						page:1,
						limit:5
					}
					//后台返回天气温度
					console.log(JSON.parse(res.data.data[0].weather),'天气')
					var weather = JSON.parse(res.data.data[0].weather) 
					if(weather.status!=1000){
						that.type = '未知'
					}else{
						that.type = weather.data.forecast[0].type
						that.wendu = weather.data.wendu
					}
					if(weather){
						var type = that.type
						var src = that.src
						if(type.indexOf('晴')>-1){
							src = '../../static/img/sunny.png'
						}
						if(type.indexOf('雨')>-1){
							src = '../../static/img/rain.png';
						}
						if(type.indexOf('阴')>-1){
							src = '../../static/img/yin.png'
						}
						if(type.indexOf('云')>-1){
							src = '../../static/img/cloud.png'
						}
						if(type.indexOf('雷阵雨')>-1){
							src = '../../static/img/lei.png'
						}
						if(type.indexOf('雪')>-1){
							src = '../../static/img/snow.png'
						}
						if(type.indexOf('阵雨')>-1){
							src = '../../static/img/zhengyu.png'
						}
						if(type.indexOf('未知')>-1){
							src = '../../static/img/yin.png'
						}
						that.src = src
					}
					// var city = res.data.data[0].city
					that.shopArr = res.data.data
					that.banner = res.data.data[0].figureImage.split(",")
					var logo = this.insertStr(res.data.data[0].image,4,'s')
					that.logo = logo
					uni.setStorageSync("logo",logo)
					that.logoShow = res.data.data[0].image
					console.log(that.logo)
					that.getLists(data).then(r=>{
						// that.getWeather(city)
					})
					app.getlogin().then(g=>{
						getuserInfo().then(r=>{
							if(r.code==0){
								console.log(r.data)
								that.dmUser = r.data
								uni.setStorageSync('dmUser',r.data)
								resolve()
							}
						})
					})
				})
			})
		},
		notice(){
			uni.navigateTo({
				url:'./notice/index'
			})
		},
		user(){
			if(!this.dmUser.name){
				uni.showToast({
					title:'您还没有登录，请去个人档案登录',
					icon:'none'
				})
			}else{
				uni.navigateTo({
					url:'../user/wallet/index'
				})
			}
		},
		getUserProfile(){
			if(app.globalData.login){
				app.getUserInfo()
			}else{
				app.getlogin().then(res=>{
					app.getUserInfo()
				})
			}
		},
		dramaDetail(id){
			if(this.dmUser.name){
				uni.navigateTo({
					url:'../juList/detail/index?id=' + id
				})
			}else{
				// this.getUserProfile()
				uni.showToast({
					title:'您还没有登录，请去个人档案登录',
					icon:'none'
				})
			}
		},
		dmDetail(id){
			if(this.dmUser.name){
				uni.navigateTo({
					url:'./author/detail/index?id=' + id
				})
			}else{
				// this.getUserProfile()
				uni.showToast({
					title:'您还没有登录，请去个人档案登录',
					icon:'none'
				})
			}
			
		},
		moreDrama(){
			uni.switchTab({
				url:'../juList/index'
			})
		},
		moreDm(){
			uni.navigateTo({
				url:'./author/index'
			})
		},
		//前往领取优惠券
		coupon(){
			uni.navigateTo({
				url:'./couponList/index'
			})
		},
		getLists(data){
			var notice = {
				page:1,
				limit:5,
			}
			return new Promise((resolve, reject) => {
				getrecomment().then(res=>{
					this.recomment = res.data
				})
				getdms(data).then(res=>{
					this.anthor = res.data.records
				})
				getnotices(notice).then(res=>{
					this.noticeArr = res.data.records
					resolve()
				})
			})
		},
		getWeather(city){
			var that = this
			return new Promise((resolve, reject) => {
        // TODO 天气接口
				uni.request({
					url:'http://wthrcdn.etouch.cn/weather_mini?city='+ city,
					method:'GET',
					success:res=>{
						that.type = res.data.data.forecast[0].type
						that.wendu = res.data.data.wendu
					}
				})
				resolve()
			})
		},
		gohere(){
			var lat = Number(this.shopArr[0].lat)
			var lng = Number(this.shopArr[0].lng)
			uni.openLocation({
				latitude:lat,
				longitude:lng,
				 success: function () {
					console.log('success');
				},
				fail:function(res){
					console.log(res)
				}
			})
		},
		swiperChange(e)
		{
			console.log(e)
			let current = e.detail.current;
			this.swiperCurrent = current
		},
		swiperChange1(e)
		{
			console.log(e)
			let current = e.detail.current;
			this.swiperCurrent1 = current
		},
		
		getuserloc(param)
		{
			var that = this
			var dic = {}
			dic.province = param.province
			dic.lat = this.lat
			dic.lng = this.long
			dic.city = param.city
			dic.area = param.district
			dic.street = param.street
			updateLoc(dic).then(res=>{
				
			})
		},
		
		getLocationInfo()
		{
			var _this=this;
			uni.getLocation({
			    type: 'wgs84',
			    success: function (res) {
			        console.log('当前位置的经度2：' + res.longitude);
			        console.log('当前位置的纬度2：' + res.latitude);
					_this.long = res.longitude
					_this.lat = res.latitude
					_this.scopeLoc = true
					
					QQMapWX.reverseGeocoder({
					          location: {
					            latitude: res.latitude,
					            longitude: res.longitude,
					
					          },
					          success: function (res) {
								  _this.getuserloc(res.result.address_component)
					            console.log(res,'----------------')
			
					          },
					        });
							
							
			    },fail(res) {
			    	console.log(res)
			    }
			});
		},
		
	}
};
</script>
