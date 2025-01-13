<template>
	<view class="coupon" :class="{'light-index':them}">
		<view class="coupon-tab" :class="{'light-coupon-tab':them}">
			<template v-for="(item,index) in list">
				<view class="tab-one" :class="{'active':item.type==type,'light-tab-one':them}" @click="tabsChange(item.type)">
					{{item.name}}({{item.num}})
				</view>
			</template>
		</view>
		<view class="coupon-content">
			<template v-if="dataArr.length>0" v-for="(item, index) in dataArr">
				<view class="coupon-one" :class="{'light-coupon-one':them}">
					<view class="coupon-top-l" :class="'use'+item.type">
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
						<!-- <view class="top-r-c" v-if="item.validLimit==1&&tem.useState==1">
							{{item.useTime}}已使用
						</view> -->
						<view class="top-r-c" v-if="item.validLimit==0">
							不限时间
						</view>
						<view class="top-r-b">
							<view class="c999">
								<text v-if="item.receive>0">限领{{item.receive}}次</text>
								<text v-if="item.receive==0">可领多次</text>
							</view>
							<view class="lingbtn" v-if="item.receive==0 && (item.total>item.users || item.total==0)" @click="takecoupon(item.id)">立即领取</view>
							<view class="lingbtn" v-if="item.receive>0 && item.take<item.receive && (item.total>item.users || item.total==0)" @click="takecoupon(item.id)">立即领取</view>
						</view>
					</view>
					<view class="coupon-img">
						<image v-if="item.take>=item.receive && item.receive>0" src="../../../static/img/coupon_4a.png" mode=""></image>
					</view>
					<view class="coupon-out" v-if="item.total==item.users && item.total>0">已领完</view>
				</view>
			</template>
			<template v-if="dataArr.length==0">
				<view class="nodata">
					<image v-if="!them" src="../../../static/img/nofun.png" mode=""></image>
					<image v-if="them" src="../../../static/img/nofun_l.png" mode=""></image>
					<view class="" :class="{'light-tab-one':them}">
						暂无相关优惠券～
					</view>
				</view>
			</template>
		</view>
		
	</view>
</template>
<style scoped lang="scss" src="./index.scss"></style>
<script>
import{
	getlistcoupon,
	getreceivecoupon,
	getcouponnum
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
					name: '免单券',
					type:1,
					num:0
				}, {
					name: '折扣券',
					type:2,
					num:0
				}, {
					name: '代金券',
					type:3,
					num:0
				}],
			false:false,
			page:1,
			dataArr:[],
			hsmore:true,
			type:1,
			loadend:true,
			them:''
			
		};
	},
	onReady() {
		app.setColor()
	},
	onLoad() {
		this.getlist()
		this.getnum()
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
		getlist(){
			if(this.loadend==false)
			{
				return
			}
			this.loadend = false
			uni.showLoading({
				title:'加载中'
			})
			var data = {
				limit:10,
				page:this.page,
				type:this.type
			}
			getlistcoupon(data).then(res=>{
				console.log(res.data)
				this.loadend = true
				if(res.code==0){
					if(res.data.records.length<10){
						this.hsmore = false
					}
					this.dataArr = this.dataArr.concat(res.data.records)
					this.list[this.type-1].num = res.data.total
					
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
		
		getnum(){

			getcouponnum().then(res=>{
				console.log(res.data)
				if(res.code==0){
					this.list[0].num = res.data['1']
					this.list[1].num = res.data['2']
					this.list[2].num = res.data['3']
				}else{
					
				}
				
			})
		},
		
		
		tabsChange(index){
			this.page = 1
			this.hsmore = true
			this.dataArr = []
			this.type = index
			this.getlist()
		},
		
		takecoupon(e){
			uni.showLoading({
				title:'加载中'
			})
			var that = this
			getreceivecoupon({id:e}).then(res=>{
				console.log(res.data)
				uni.hideLoading({
					
				})
				if(res.code==0){
					uni.showToast({
						icon:'none',
						title:'领取成功'
					})
					setTimeout(function() {
						that.hsmore =  true
						that.dataArr = []
						that.page=1
						that.getlist()
					}, 2000);
				}else{
					uni.showToast({
						icon:'none',
						title:res.msg
					})
				}
				
			})
		},
		
	}
};
</script>