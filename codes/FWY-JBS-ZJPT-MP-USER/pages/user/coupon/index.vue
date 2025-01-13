<template>
	<view class="coupon" :class="{'light-index':them}">
		<view class="coupon-tab" :class="{'light-coupon-tab':them}">
			<template v-for="(item,index) in list">
				<view class="tab-one" :class="{'active':item.states==2,'light-tab-one':them}" @click="tabsChange(index)">
					{{item.name}}
				</view>
			</template>
			<!-- <u-tabs-swiper ref="uTabs" :bold='false' :list="list" :current="current" @change="tabsChange" :is-scroll="false"
				 swiperWidth="750" active-color="#FFF29A" bg-color="#312C39" inactive-color="#9f9f9f"></u-tabs-swiper> -->
		</view>
		<view class="coupon-content">
			<template v-if="dataArr.length>0" v-for="(item, index) in dataArr">
				<view class="coupon-one" :class="{'light-coupon-one':them}">
					<view class="coupon-top-l" :class="'use'+item.useState">
						<view class="">
							<text style="font-size: 32rpx;">￥</text><text style="font-size: 78rpx;font-weight: bold;">
								{{item.num}}
							</text>
						</view>
						<view class="">
							满{{item.numLimit}}元可用
						</view>
					</view>
					<view class="coupon-top-r">
						<view class="top-r-top">
							{{item.title}}
						</view>
						<view class="top-r-c" v-if="item.validLimit==1">
							{{item.vtime}}
						</view>
						<view class="top-r-c" v-if="item.validLimit==1&&tem.useState==1">
							{{item.useTime}}已使用
						</view>
						<view class="top-r-c" v-if="item.validLimit==0">
							不限时间
						</view>
						<view class="top-r-b">
							<view class="red" v-if="item.useState==0">
								未使用
							</view>
							<view class="blue" v-if="item.useState==1">
								已使用
							</view>
							<view class="c999" v-if="item.useState==2">
								已过期
							</view>
							<view class="c999" v-if="item.useState==3">
								已失效
							</view>
						</view>
					</view>
					<view class="coupon-img">
						<image v-if="item.useState==2" src="../../../static/img/coupon_2a.png" mode=""></image>
						<image v-if="item.useState==1" src="../../../static/img/coupon_3a.png" mode=""></image>
					</view>
				</view>
			</template>
			<template v-if="dataArr.length==0">
				<view class="nodata">
					<image v-if="!them" src="../../../static/img/nofun.png" mode=""></image>
					<image v-if="them" src="../../../static/img/nofun_l.png" mode=""></image>
					<view class="" :class="{'light-333':them}">
						暂无相关优惠券～
					</view>
				</view>
			</template>
		</view>
		<!-- <swiper :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
				<swiper-item class="swiper-item" v-for="(item1, index1) in list" :key="index1">
					<scroll-view scroll-y style="width: 100%;" @scrolltolower="onreachBottom">
						<view class="coupon-one" v-if="index1==0" v-for="(item, index) in listArr">
							<view class="coupon-top-l use_1">
								<view class="">
									<text style="font-size: 32rpx;">￥</text><text style="font-size: 78rpx;font-weight: bold;">
										{{item.num}}
									</text>
								</view>
								<view class="">
									{{item.type}}
								</view>
							</view>
							<view class="coupon-top-r">
								<view class="top-r-top">
									{{item.name}}
								</view>
								<view class="top-r-c">
									{{item.time}}
								</view>
								<view class="top-r-b">
									<view class="red">
										立即使用
									</view>
									<view class="">
										<image src="../../../static/img/coupon_1a.png" mode=""></image>
									</view>
								</view>
							</view>
						</view>
						<view class="coupon-one" v-if="index1==2" v-for="(item, index) in listArr">
							<view class="coupon-top-l use_2">
								<view class="">
									<text style="font-size: 32rpx;">￥</text><text style="font-size: 78rpx;font-weight: bold;">
										{{item.num}}
									</text>
								</view>
								<view class="">
									{{item.type}}
								</view>
							</view>
							<view class="coupon-top-r">
								<view class="top-r-top">
									{{item.name}}
								</view>
								<view class="top-r-c">
									{{item.time}}
								</view>
								<view class="top-r-b">
									<view class="c999">
										已过期
									</view>
								</view>
							</view>
							<view class="coupon-img">
								<image src="../../../static/img/coupon_2a.png" mode=""></image>
							</view>
						</view>
						<view class="coupon-one" v-if="index1==1" v-for="(item, index) in listArr">
							<view class="coupon-top-l use_3">
								<view class="">
									<text style="font-size: 32rpx;">￥</text><text style="font-size: 78rpx;font-weight: bold;">
										{{item.num}}
									</text>
								</view>
								<view class="">
									{{item.type}}
								</view>
							</view>
							<view class="coupon-top-r">
								<view class="top-r-top">
									{{item.name}}
								</view>
								<view class="top-r-c">
									{{item.time}}
								</view>
								<view class="top-r-b">
									<view class="blue">
										已使用
									</view>
								</view>
							</view>
							<view class="coupon-img">
								<image src="../../../static/img/coupon_3a.png" mode=""></image>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
		</swiper> -->
	</view>
</template>
<style scoped lang="scss" src="./index.scss"></style>
<script>
import{
	getmylistcoupon
}from '../../../static/js/api.js'
let app = getApp()
export default {
	components: {
		// 注册
	},
	
	data() {
		return {
			list: [
				{
					name: '未使用',
					states:2,
					state:0
				}, {
					name: '已使用',
					states:1,
					state:1
				}, {
					name: '已过期',
					states:1,
					state:2
				}, {
					name:'已失效',
					states:1,
					state:3
				}],
			false:false,
			page:1,
			dataArr:[],
			hsmore:true,
			them:''
		};
	},
	onReady() {
		app.setColor()
	},
	onLoad() {
		this.getlist()
		this.them = uni.getStorageSync('them')
	},
	onReachBottom(){
		if(this.hsmore){
			this.page++
			this.getlist()
		}else{
		}
	},
	methods: {
		getlist(state){
			uni.showLoading({
				title:'加载中'
			})
			var data = {
				limit:10,
				page:this.page,
				state:state||0
			}
			getmylistcoupon(data).then(res=>{
				console.log(res.data)
				if(res.code==0){
					if(res.data.records.length==10){
						this.hsmore = true
					}else{
						this.hsmore = false
					}
					this.dataArr = this.dataArr.concat(res.data.records)
					uni.hideLoading({
						
					})
				}else{
					uni.showToast({
						icon:'none',
						title:res.msg
					})
					uni.hideLoading({
						
					})
				}
				
			})
		},
		tabsChange(index){
			this.page = 1
			this.list.map(res=>{
				res.states = 1
			})
			this.list[index].states = 2
			var state = this.list[index].state
			this.dataArr = []
			this.getlist(state)
		},
	}
};
</script>