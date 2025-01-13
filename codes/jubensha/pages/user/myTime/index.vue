<template>
	<view class="index" :class="{'light-index':them}">
		<view class="index-top">
			<view class="top-left" :class="{'light-top-left':them}">
				是否启用空闲时间推送
			</view>
			<view @click="change" class="">
				<u-switch v-if="!them" v-model="checked" :loading="loading" size="40" active-color="#FFF29A"></u-switch>
				<u-switch v-if="them" v-model="checked" :loading="loading" size="40" active-color="#FF9D66"></u-switch>
			</view>
		</view>
		<view class="index-cen">
			*空闲时间设置说明：当您设置空闲时间将会给您推送您感兴趣的相关组局信息，若您关闭此选项将不会给您推送相关信息。
		</view>
		<view v-if="checked" class="index-bot">
			<template v-for="(item,index) in list">
				<view class="bot-one">
					<view class="one-week" :class="{'light-one-week':them}">
						{{item.name}}
					</view>
					<view class="one-morning" :class="{'active':item.mor==2,'light-999':them}" @click="morClick(index)">
						<view class="morning-img">
							<image v-if="item.mor==1&&!them" src="../../../static/img/time_1.png" mode=""></image>
							<image v-if="item.mor==2&&!them" src="../../../static/img/time_1a.png" mode=""></image>
							<image v-if="item.mor==1&&them" src="../../../static/img/time_1l.png" mode=""></image>
							<image v-if="item.mor==2&&them" src="../../../static/img/time_1al.png" mode=""></image>
						</view>
						<view class="morning-text">上午</view>
					</view>
					<view class="one-morning" :class="{'active':item.aft==2,'light-999':them}" @click="aftClick(index)">
						<view class="morning-img">
							<image v-if="item.aft==1&&!them" src="../../../static/img/time_2.png" mode=""></image>
							<image v-if="item.aft==2&&!them" src="../../../static/img/time_2a.png" mode=""></image>
							<image v-if="item.aft==1&&them" src="../../../static/img/time_2l.png" mode=""></image>
							<image v-if="item.aft==2&&them" src="../../../static/img/time_2al.png" mode=""></image>
						</view>
						<view class="morning-text">下午</view>
					</view>
					<view class="one-morning"  :class="{'active':item.nig==2,'light-999':them}" @click="nigClick(index)">
						<view class="morning-img">
							<image v-if="item.nig==1&&!them" src="../../../static/img/time_3.png" mode=""></image>
							<image v-if="item.nig==2&&!them" src="../../../static/img/time_3a.png" mode=""></image>
							<image v-if="item.nig==1&&them" src="../../../static/img/time_3l.png" mode=""></image>
							<image v-if="item.nig==2&&them" src="../../../static/img/time_3al.png" mode=""></image>
						</view>
						<view class="morning-text">晚间</view>
					</view>
				</view>
			</template>
		</view>
	</view>
</template>

<script>
	import{
		getpush,
		getpushDay,
		getuserInfo
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				checked:'',
				loading:false,
				list:[
					{
						name:'周一：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
					{
						name:'周二：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
					{
						name:'周三：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
					{
						name:'周四：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
					{
						name:'周五：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
					{
						name:'周六：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
					{
						name:'周日：',
						mor:'1',
						aft:'1',
						nig:'1',
						state:'1'
					},
				],
				them:''
			}
		},
		onReady() {
			app.setColor()
		},
		onLoad(){
			this.them = uni.getStorageSync('them')
			getuserInfo().then(res=>{
				var dmUser = res.data
				var list = this.list
				if(dmUser.push==1){
					this.checked = true
				}else{
					this.checked = false
				}
				dmUser.times.map(res=>{
					var index = parseInt(res.day)-1
					res.types.map(r=>{
						if(r=='上午'){
							list[index].mor = 2
						}else if(r=='下午'){
							list[index].aft = 2
						}else if(r=='晚间'){
							list[index].nig = 2
						}
					})
				})
			})
		},
		methods: {
			change(status) {
				var that = this
				that.loading = true
				app.message().then(i=>{
					var push
					if(that.checked){
						push = 1
					}else{
						push = 0
					}
					var data = {
						push:push
					}
					getpush(data).then(res=>{
						if(res.code==0){
							uni.showToast({
								title:'操作成功'
							})
							that.loading = false
						}else{
							uni.showToast({
								title:res.msg,
								icon:'none'
							})
						}
					})
				})
			},
			morClick(index){
				uni.showLoading({
					title:'设置中'
				})
				if(this.list[index].mor == 2){
					this.list[index].mor = 1
				}else if(this.list[index].mor == 1){
					this.list[index].mor = 2
				}
				var day = index + 1
				var type = '上午'
				var data = {
					type:type,
					day:day
				}
				getpushDay(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'设置成功'
						})
						setTimeout(function(){
							uni.hideLoading({})
						},200)
					}
				})
			},
			aftClick(index){
				uni.showLoading({
					title:'设置中'
				})
				if(this.list[index].aft == 2){
					this.list[index].aft = 1
				}else if(this.list[index].aft == 1){
					this.list[index].aft = 2
				}
				var day = index + 1
				var type = '下午'
				var data = {
					type:type,
					day:day
				}
				getpushDay(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'设置成功'
						})
						setTimeout(function(){
							uni.hideLoading({})
						},200)
					}
				})
			},
			nigClick(index){
				uni.showLoading({
					title:'设置中'
				})
				if(this.list[index].nig == 2){
					this.list[index].nig = 1
				}else if(this.list[index].nig == 1){
					this.list[index].nig = 2
				}
				var day = index + 1
				var type = '晚间'
				var data = {
					type:type,
					day:day
				}
				getpushDay(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'设置成功'
						})
						setTimeout(function(){
							uni.hideLoading({})
						},200)
					}
				})
			}
		},
	}
</script>

<style scoped lang="scss" src="./index.scss"></style>