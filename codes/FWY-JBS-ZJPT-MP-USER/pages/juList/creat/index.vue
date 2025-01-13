<template>
	<view :class="them?'light-index':''">
		<view class="topinfo">
			<image :src="dataArr.image" mode="aspectFill" class="img"></image>
			<view class="info">
				<view class="name">
					<view class="tip" :class="them?'light-tip':''">新</view>
					<view class="n_name" :class="them?'light-n_name':''">{{dataArr.dramaName}}</view>
				</view>
				<view class="mark">
					<view class="markname" :class="them?'light-markname':''" v-for="item in dataArr.typeNames">{{item}}</view>
				</view>
				<view class="faxing"></view>
				<!-- <view class="faxing">发行：在洛阳运营部</view> -->
				<view class="bom">
					<view class="price" :class="them?'light-price':''">
						<text :class="them?'light-price':''">￥</text>
						{{dataArr.price}}
						<text class="ren" :class="them?'light-price':''">/人</text>
					</view>
					<view class="menu">
						<view class="menuitem">
							<image v-if="!them" src="../../../static/img/icon/j_7.png" mode="aspectFit" class="menuimg"></image>
							<image v-if="them" src="../../../static/img/icon/j_7l.png" mode="aspectFit" class="menuimg"></image>
							<text :class="them?'light-price':''">{{dataArr.wants}}人想玩</text>
						</view>
						<view class="menuitem">
							<image v-if="!them" src="../../../static/img/icon/j_8.png" mode="aspectFit" class="menuimg"></image>
							<image v-if="them" src="../../../static/img/icon/j_8l.png" mode="aspectFit" class="menuimg"></image>
							<text :class="them?'light-played':''">{{dataArr.played}}人玩过</text>
						</view>
					</view>
				</view>
			</view>
			
		</view>
		
		<view class="markinfo">
			<view class="item">
				<image v-if="!them"  src="../../../static/img/d_1.png" mode="" class="bgimg"></image>
				<image v-if="them" src="../../../static/img/d_1l.png" mode="" class="bgimg"></image>
				<view class="iteminfo">
					<view class="top">
						<image v-if="!them" src="../../../static/img/icon/j_1.png" mode="aspectFit"></image>
						<image v-if="them" src="../../../static/img/icon/j_1l.png" mode="aspectFit"></image>
						<text :class="them?'light-333':''">组局人数</text>
					</view>
					<view class="bom" :class="them?'light-333':''">{{dataArr.male}}男{{dataArr.female}}女</view>
				</view>
			</view>
			<view class="item">
				<image v-if="!them"  src="../../../static/img/d_2.png" mode="" class="bgimg"></image>
				<image v-if="them" src="../../../static/img/d_2l.png" mode="" class="bgimg"></image>
				<view class="iteminfo">
					<view class="top">
						<image v-if="!them" src="../../../static/img/icon/j_3.png" mode="aspectFit"></image>
						<image v-if="them" src="../../../static/img/icon/j_3l.png" mode="aspectFit"></image>
						<text :class="them?'light-333':''">剧本时长</text>
					</view>
					<view class="bom" :class="them?'light-333':''">{{dataArr.duration}}小时</view>
				</view>
			</view>
			<view class="item">
				<image v-if="!them"  src="../../../static/img/d_3.png" mode="" class="bgimg"></image>
				<image v-if="them" src="../../../static/img/d_3l.png" mode="" class="bgimg"></image>
				<view class="iteminfo">
					<view class="top">
						<image v-if="!them" src="../../../static/img/icon/j_4.png" mode="aspectFit"></image>
						<image v-if="them" src="../../../static/img/icon/j_4l.png" mode="aspectFit"></image>
						<text :class="them?'light-333':''">剧本难度</text>
					</view>
					<view class="bom" :class="them?'light-333':''">{{dataArr.diffName}}</view>
				</view>
			</view>
		</view>
		<view class="index-bot">
			<view class="bot-one">
				<view class="index-title">
					<view class="index-line" :class="them?'light-index-line':''">
						
					</view>
					<view class="index-text" :class="them?'light-333':''">
						设置开局时间
					</view>
				</view>
				<view class="index-time">
					<view class="time-left" @click="show=true" :class="them?'light-left-input':''">
						<view class="left-input">
							<text :class="them?'light-333':''" v-if="timeObj.year">{{timeObj.year}}-{{timeObj.month}}-{{timeObj.day}} {{timeObj.hour}}:{{timeObj.minute}}</text>
							<text :class="them?'light-333':''" v-else>请选择开局时间</text>
							<u-picker mode="time" v-model="show" @confirm='confirm' :params="params"></u-picker>
						</view>
						<image v-if="!them" src="../../../static/img/creat_1.png" mode=""></image>
						<image v-if="them" src="../../../static/img/creat_1l.png" mode=""></image>
					</view>
					<view v-if="timeObj.hour>=5&&timeObj.hour<12" class="time-right" :class="them?'light-left-input':''">
						<image v-if="!them" src="../../../static/img/time_1a.png" mode=""></image>
						<image v-if="them" src="../../../static/img/time_1al.png" mode=""></image>
						<view class="right-text" :class="them?'light-right-text':''">
							上午场
						</view>
					</view>
					<view v-if="timeObj.hour>=12&&timeObj.hour<19" class="time-right" :class="them?'light-left-input':''">
						<image v-if="!them" src="../../../static/img/time_2a.png" mode=""></image>
						<image v-if="them" src="../../../static/img/time_2al.png" mode=""></image>
						<view class="right-text" :class="them?'light-right-text':''">
							下午场
						</view>
					</view>
					<view v-if="timeObj.hour>=19||timeObj.hour<5" class="time-right" :class="them?'light-left-input':''">
						<image v-if="!them" src="../../../static/img/time_2a.png" mode=""></image>
						<image v-if="them" src="../../../static/img/time_2al.png" mode=""></image>
						<view class="right-text" :class="them?'light-right-text':''">
							夜场
						</view>
					</view>
				</view>
			</view>
			<view class="bot-one">
				<!-- <view class="index-title money">
					<view class="index-money">
						<view class="index-line">
						
						</view>
						<view class="index-text">
							是否收取定金
						</view>
					</view>
					<view class="money-switch">
						<u-switch v-model="checked" size="40" active-color="#FFF29A" inactive-color="#45414C"></u-switch>
					</view>
				</view> -->
				<view v-if="checked" class="time-left">
					<view class="left-input">
						<text class="input-text" v-if="value">{{value}}</text>
						<u-input v-model="value" maxlength='9' placeholder="请输入金额(元)" />
					</view>
					<image src="../../../static/img/creat_2.png" mode=""></image>
				</view>
			</view>
			<view v-if="checked"  class="bot-one">
				<view class="index-title money">
					<view class="index-money">
						<view class="index-line">
						
						</view>
						<view class="index-text">
							是否没人收取定金
						</view>
					</view>
					<view class="money-switch">
						<u-switch v-model="checked1" size="40" active-color="#FFF29A" inactive-color="#45414C"></u-switch>
					</view>
				</view>
			</view>
		</view>
		<view class="index-btn" @click="creatBtn" :class="them?'light-index-btn':''">
			创建组局
		</view>
	</view>
</template>

<script>
	import{
		getdramaDetail,
		getcreate
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				guige:[{'name':'盒装',id:1},{'name':'城限',id:2},{'name':'独家',id:3},{'name':'实景',id:4},{'name':'微剧本',id:5},{'name':'桌游',id:6}],
				params: {
					year: true,
					month: true,
					day: true,
					hour: true,
					minute: true,
					second: false
				},
				show: false,
				timeObj:{},
				checked:false,
				checked1:false,
				value:'',//金额的值
				id:'',
				dataArr:{},
				them:''
			}
		},
		onReady() {
			app.setColor()
		},
		onLoad(options){
			this.getList(options)
			this.id = options.id
			this.them = uni.getStorageSync('them')
		},
		methods: {
			getList(options){
				getdramaDetail(options).then(res=>{
					this.dataArr = res.data
				})
			},
			confirm(event){
				console.log(event)
				this.timeObj = event
			},
			creatBtn(){
				var time = this.timeObj.year+'-'+this.timeObj.month+'-'+this.timeObj.day+' '+this.timeObj.hour+':'+this.timeObj.minute+':'+'00'
				var type
				var hour = this.timeObj.hour
				if(hour>=5&&hour<12){
					type='上午场'
				}
				if(hour>=12&&hour<19){
					type='下午场'
				}
				if(hour>=19||hour<5){
					type='夜场'
				}
				var data = {
					sdid:this.dataArr.id,
					price:this.dataArr.price,
					playTime:time,
					type:type,
					num:this.dataArr.male+this.dataArr.female
				}
				getcreate(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'创建成功!'
						})
						var id = res.data.id
						setTimeout(function(){
							uni.redirectTo({
								url:'../../pin/detail/index?id=' + id
							})
						},500)
					}else{
						uni.showToast({
							title:res.msg,
							icon:'none'
						})
					}
				})
			}
		}
	}
</script>

<style scoped lang="scss" src="./index.scss"></style>