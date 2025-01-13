<template>
	<view :class="{'light-index':them}">
		<image src="../../../../static/img/icon/close.png" mode="" @click="back" class="back" :style="{'top':menutop+'px'}"></image>
		<view class="banner">
			<swiper class="bannerswiper" @change='swiperChange'>
				<swiper-item v-for="item in list.image">
					<image :src="item" mode="aspectFill" class="sliderimg"></image>
				</swiper-item>
			</swiper>
			<view class="dots" v-if="list.image.length>1">
				<view class="dot" :class="{'active':currindex==i}" v-for="(item,i) in list.image"></view>
			</view>
		</view>
		<view class="name">
			<text :class="{'light-333':them}">{{list.name}}</text>
			<image v-if="list.sex==2&&!them" src="../../../../static/img/icon/g.png" mode="aspectFit"></image>
			<image v-if="list.sex==1&&!them" src="../../../../static/img/icon/boy.png" mode="aspectFit"></image>
			<image v-if="list.sex==2&&them" src="../../../../static/img/icon/g_l.png" mode="aspectFit"></image>
			<image v-if="list.sex==1&&them" src="../../../../static/img/icon/boy_l.png" mode="aspectFit"></image>
		</view>
		<view class="rateview" :class="{'light-rateview':them}">
			<view class="left">
				<image v-if="!them" src="../../../../static/img/icon/zan.png" mode="aspectFit"></image>
				<image v-if="them" src="../../../../static/img/icon/zan_l.png" mode="aspectFit"></image>
				<view class="score" :class="{'light-score':them}">{{list.score}}<text>分</text></view>
			</view>
			<view class="line"></view>
			<view class="star">
				<view class="tip" :class="{'light-333':them}">DM好评指数</view>
				<u-rate v-if="!them" :current="list.score/2" active-color="#FFF29A" inactive-color="#908C96" inactive-icon="star-fill" gutter="20" :disabled="true" size='34'></u-rate>
				<u-rate v-if="them" :current="list.score/2" active-color="#FF9D66" inactive-color="#EEEEEE" inactive-icon="star-fill" gutter="20" :disabled="true" size='34'></u-rate>
			</view>
			<!-- <view class="ping">评分</view> -->
		</view>
		<view class="descview">
			<view class="top">
				<view class="line" :class="{'light-line':them}"></view>
				<text :class="{'light-333':them}">DM简介</text>
			</view>
			<u-read-more show-height="260">
				<rich-text :nodes="list.detail"></rich-text>
			</u-read-more>
			<!-- <view class="desc" :class="{'all':showall==true}">{{list.detail}}</view>
			<view class="arrow" v-if="showall==false" @click="showdesc"><image src="../../../../static/img/icon/down.png" mode="aspectFit"></image></view> -->
		</view>
		<view class="list">
			<view class="top">
				<view class="line" :class="{'light-line':them}"></view>
				<text :class="{'light-333':them}">可开剧本</text>
			</view>
			<template v-for="item in list.dramas">
			<view class="item" @click="dramaDetail(item.id)" :class="{'light-item':them}">
				<view class="imgbox">
					<image :src="item.image" mode="aspectFill" class="img"></image>
				</view>
				<view class="info">
					<view class="name">
						<view class="name-left">
							<view class="tip" :class="{'light-tip':them}">新</view>
							<view class="n_name" :class="{'light-333':them}">{{item.dramaName}}</view>
						</view>
						<view class="name-right" :class="{'light-score':them}">
							{{item.score}}<text style="font-size: 20rpx;">分</text>
						</view>
					</view>
					<view class="mark">
						<view class="markname" :class="{'light-markname':them}" v-for="items in item.typeNames">{{items}}</view>
					</view>
					<view class="info-bot">
						<text class="bot-text" :class="{'light-bot-text':them}">剧本简介：</text><u-parse :html="item.detail"></u-parse>
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
		getdmDetail
	}from '../../../../static/js/api.js'
	let app = getApp()
	export default {
		components: {
			// 注册
		},
		
		data() {
			return {
				options:'',
				list:{},
				menutop:20,
				banners:[1,1,1],
				currindex:0,
				showall:false,
				guige:[{'name':'盒装',state:1,id:1},	{'name':'城限',state:1,id:2},{'name':'独家',state:1,id:3},{'name':'实景',state:1,id:4},{'name':'微剧本',state:1,id:5},{'name':'桌游',state:1,id:6}],
				people:[{'name':'≤4人',state:1,id:1},{'name':'5人',state:1,id:2},{'name':'6人',state:1,id:3},{'name':'7人',state:1,id:4},{'name':'8人',state:1,id:5},{'name':'9人',state:1,id:6},{'name':'≥10人',state:1,id:7}],
				them:''
			};
		},
		onReady() {
			app.setColor()
		},
		onLoad(options) {
			this.them = uni.getStorageSync('them')
			var rect = uni.getStorageSync('menubtn')
			this.menutop = rect.top+2||20
			console.log(rect)
			// var id = options.id
			this.getList(options)
			this.options = options
		},
		onShareAppMessage(){
			var id = this.options.id
			return {
			    title: this.list.name,
			    path: '/pages/index/author/index?id=' + id
			}
		},
		methods: {
			dramaDetail(id){
				uni.navigateTo({
					url:'../../../juList/detail/index?id=' + id
				})
			},
			getList(options){
				getdmDetail(options).then(res=>{
					console.log(res.data)
					res.data.image = res.data.image.split(',')
					this.list = res.data 
					this.list.typeNames = this.list.typeNames.split(',')
				})
			},
			swiperChange(e)
			{
				this.currindex = e.detail.current
			},
			back(){
				uni.navigateBack({
					delta:1
				})
			},
			showdesc()
			{
				this.showall = !this.showall
			},
		}
	};
</script>

<style>
</style>
