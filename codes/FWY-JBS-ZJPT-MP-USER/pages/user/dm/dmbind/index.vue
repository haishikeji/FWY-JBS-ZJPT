<template>
	<view>
		<view v-if="autophones" class="index">
			<view class="index-image">
				<image src="../../../../static/img/dmbind.png" mode=""></image>
			</view>
			<view class="index-bot">
				<view class="bot-wx">
					<button type="default" open-type="getPhoneNumber" @getphonenumber="decryptPhoneNumber">微信授权一键绑定</button>
				</view>
				<view @click="otherphone" class="bot-phone">
					使用其他手机号登录
				</view>	
			</view>
		</view>
		<view v-if="otherphones" class="phone">
			<view class="phone-image">
				<image src="../../../../static/img/dmbind.png" mode=""></image>
			</view>
			<view class="phone-title">
				<view class="title-image">
					<image src="../../../../static/img/ph.png" mode=""></image>
				</view>
				<view class="title-input">
					<u-input v-model="phone" :clearable='false' :custom-style="inputstyle"  placeholder="请输入手机号" />
				</view>
			</view>
			<view class="phone-title">
				<view class="title-image">
					<image src="../../../../static/img/phcode.png" mode=""></image>
				</view>
				<view class="title-input">
					<u-input v-model="phonecode" :custom-style="inputstyle" :clearable='false' placeholder="请输入验证码" />
				</view>
				<!-- <view class="text-btn"> -->
					<u-toast ref="uToast"></u-toast>
					<u-verification-code start-text="获取" change-text="X秒后获取" end-text="获取" @end="end" @start="start" ref="uCode" 
					@change="codeChange"></u-verification-code>
					<view  class="text-btn" @click="getCodes">{{tips}}</view>
				<!-- </view> -->
			</view>
				
			<view class="index-bot">
				<view class="bot-wx" @click="login">
					<button type="default">立即登录</button>
				</view>
				<view @click="autophone" class="bot-phone">
					返回自动登录
				</view>	
			</view>
		</view>
	</view>
</template>

<script>
	import{
		getphone,
		getbind,
		getsendSms,
		getcheckSms
	}from '../../../../static/js/api.js'
	export default {
		data() {
			return {
				autophones:true,
				otherphones:false,
				inputstyle:{
					color:'#fff'
				},
				phone:'',
				
				tips: '',
				// refCode: null,
				seconds: 10,
			}
		},
		onReady() {
					// 注意这里不能将一个组件赋值给data的一个变量，否则在微信小程序会
					// 造成循环引用而报错，如果你想这样做，请在非data中定义refCode变量
					this.refCode = this.$refs.uCode;
				},
		methods: {
			
			codeChange(text) {
				this.tips = text;
			},
			getCodes() {
				if(this.$refs.uCode.canGetCode) {
					// 模拟向后端请求验证码
					uni.showLoading({
						title: '正在获取验证码'
					})
					setTimeout(() => {
						uni.hideLoading();
						this.getcode()
						// 这里此提示会被this.start()方法中的提示覆盖
						this.$u.toast('验证码已发送');
						// 通知验证码组件内部开始倒计时
						this.$refs.uCode.start();
					}, 1000);
				} else {
					this.$u.toast('倒计时结束后再发送');
				}
			},
			end() {
				this.$u.toast('倒计时结束');
			},
			start() {
				this.$u.toast('倒计时开始');
			},
			
			
			otherphone(){
				this.autophones = false
				this.otherphones = true
			},
			autophone(){
				this.autophones = true
				this.otherphones = false
			},
			login(){
				var data = {
					code:this.phonecode,
					phone:this.phone
				}
				getcheckSms(data).then(res=>{
					if(res.code==0){
						var query = {
							phone:this.phone
						}
						getbind(query).then(r=>{
							if(r.code==0){
								uni.showToast({
									title:'绑定成功'
								})
								setTimeout(function(){
									uni.navigateTo({
										url:'../index'
									})
								},200)
							}else{
								uni.showToast({
									title:r.msg,
									icon:'none'
								})
							}
						})
					}
				})
			},
			getcode(){
				var data = {
					phone:this.phone
				}
				var reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
				if(!reg.test(data.phone)){
					uni.showToast({
						title:'请填写正确的手机号',
						icon:'none'
					})
				}else{
					getsendSms(data).then(res=>{
						if(res.code==0){
							uni.showToast({
								title:'验证码发送成功'
							})
						}
					})
				}
				
			},
			decryptPhoneNumber(e){
				var iv = e.detail.iv
				var encryptedData = e.detail.encryptedData
				uni.login({
					success:function(g){
						console.log(g.code,'code')
						var data = {
							iv :iv,
							encryptedData :encryptedData,
							code :g.code
						}
						getphone(data).then(res=>{
							console.log(res.data)
							var query = {
								phone:res.data
							}
							getbind(query).then(r=>{
								if(r.code==0){
									uni.showToast({
										title:'绑定成功'
									})
									setTimeout(function(){
										uni.navigateTo({
											url:'../index'
										})
									},200)
								}else{
									uni.showToast({
										title:r.msg,
										icon:'none'
									})
								}
							})
						})
					}
				})
			},
		}
	}
</script>
<style scoped lang="scss" src="./index.scss"></style>
