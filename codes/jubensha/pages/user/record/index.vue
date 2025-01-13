<template>
	<view style="padding: 60rpx;min-height: 100%;" :class="{'light-index':them}">
		<view class="index-top" :class="{'light-333':them}">
			查到您的充值记录共   <text :class="{'light-index-color':them}" style="color: #FFF29A;">{{total}}</text>条
		</view>
		<view class="nodata" v-if="numList.length==0">
			<image v-if="!them" src="../../../static/img/noinfo.png" mode=""></image> 
			<image v-if="them" src="../../../static/img/noinfo_l.png" mode=""></image> 
			<view class="" :class="{'light-333':them}">
				暂无充值记录
			</view>
		</view>
		<view class="timeZhou">
		  <view class="item" :class="{'light-item':them}" v-for="item in numList">
		   <view class="dain" :class="{'light-dian':them}"><view :class="{'light-dian2':them}" class="dian2"></view></view>
		   <view class="time" :class="{'light-333':them}">
			<!-- 2021-08-13 18:00 -->
			{{item.updateTime}}
		   </view>
		   <view class="timedec">
			   <view :class="{'light-333':them}" style="margin-right: 40rpx;">{{item.orderType==4?'微信充值':'储值卡充值'}}    <text :class="{'light-index-color':them}" style="color: #FFF29A;">¥{{item.realPay}}</text></view>
			   <view :class="{'light-333':them}">可用余额   <text :class="{'light-index-color':them}" style="color: #FFF29A;">¥{{item.availableBalance?item.availableBalance:'0'}}</text></view>
		   </view>
		  </view>
		 </view>
		
	</view>
</template>

<script>
	import{
		gettouprecord
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				numList: [],
				page:1,
				hsmore:false,
				total:'',
				them:''
			}
		},
		onReady() {
			app.setColor()
		},
		onLoad() {
			this.getList()
			this.them = uni.getStorageSync('them')
		},
		onReachBottom(){
			if(this.hsmore){
				this.page++
				this.getList()
			}else{
				
			}
		},
		methods: {
			getList(){
				uni.showLoading({
					title:'加载中'
				})
				var data = {
					pageNo:this.page,
					pageSize:10
				}
				gettouprecord(data).then(res=>{
					if(res.code==0){
						if(res.data.records.length==10){
							this.hsmore = true
						}else{
							this.hsmore = false
						}
						this.total = res.data.total
						this.numList = this.numList.concat(res.data.records)
						this.numList.map(r=>{
							r.updateTime = r.updateTime.substring(0,16)
						})
						uni.hideLoading({
							
						})
					}else{
						uni.showToast({
							title:res.msg,
							icon:'none'
						})
					}
				})
			},
		}
	}
</script>
<style scoped lang="scss" src="./index.scss"></style>
