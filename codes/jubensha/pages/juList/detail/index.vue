<template>
	<view class="u-skeleton" :class="them?'light-index':''">
		<view class="topinfo">
			<image :src="dataArr.image" mode="aspectFill" class="img"></image>
			<view class="info">
				<view class="info-top">
					<view class="name">
						<view class="tip" :class="them?'light-tip':''">新</view>
						<view class="n_name" :class="them?'light-n_name':''">{{dataArr.dramaName}}</view>
					</view>
					<view class="mark">
						<view class="markname" :class="them?'light-markname':''" v-for="item in dataArr.typeNames">{{item}}</view>
					</view>
				</view>
				<view class="info-bot">
				
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
			
		</view>
		
		<view class="markinfo">
			<view class="item">
				<image v-if="!them" src="../../../static/img/d_1.png" mode="" class="bgimg"></image>
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
				<image v-if="!them" src="../../../static/img/d_2.png" mode="" class="bgimg"></image>
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
				<image v-if="!them" src="../../../static/img/d_3.png" mode="" class="bgimg"></image>
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
		
		
		<view class="rateview" :class="them?'light-rateview':''">
			<view class="left">
				<view class="name" :class="them?'light-name':''">剧本口碑</view>
				<view class="rate" :class="them?'light-rate':''">{{dataArr.score}}</view>
				<u-rate v-if="!them" :current="dataArr.score/2" active-color="#FFF29A" inactive-color="#908C96" inactive-icon="star-fill" gutter="5" :disabled="true" size='16'></u-rate>
				<u-rate v-if="them" :current="dataArr.score/2" active-color="#FF9D66" inactive-color="#DEDEDE" inactive-icon="star-fill" gutter="5" :disabled="true" size='16'></u-rate>
				<view class="num" :class="them?'light-num':''">{{dataArr.count}}人点评</view>
			</view>
			<view class="right">
				<view class="item" v-for="item in dataArr.scores">
					<view class="sname" :class="them?'light-num':''">{{item.score}}星</view>
					<view class="pro" v-if="!them"><u-line-progress active-color="#FFF29A" inactive-color="#4D4953" :percent="item.count/dataArr.count*100" height="12" :show-percent="false"></u-line-progress></view>
					<view class="pro" v-if="them"><u-line-progress active-color="#FF9D66" inactive-color="#DEDEDE" :percent="item.count/dataArr.count*100" height="12" :show-percent="false"></u-line-progress></view>
					<view class="sname" :class="them?'light-num':''">{{item.count}}人</view>
				</view>
			</view>
		</view>
		
		<view class="descview">
			<view class="top">
				<view :class="them?'light-index-line':''" class="line"></view>
				<text :class="them?'light-333':''">剧本简介</text>
			</view>
			<view class="desc" :class="{'all':showall==true,'light-desc':them==true}">
				<u-parse :html="dataArr.detail"></u-parse>
				<!-- <view class="">
					{{dataArr.detail}}
				</view> -->
				<!-- <mpHtml :content="dataArr.detail" /> -->
			</view>
			<view class="arrow" v-if="showall==false" @click="showdesc"><image src="../../../static/img/icon/down.png" mode="aspectFit"></image></view>
		</view>
		
		<view class="descview">
			<view class="top">
				<view :class="them?'light-index-line':''" class="line"></view>
				<text :class="them?'light-333':''">人物简介</text>
			</view>
			<view class="scroll">
				<scroll-view scroll-x="true" >
					<view class="item" :class="them?'light-item':''" v-for="item in dataArr.roles">
						<view class="itembody" :class="them?'light-itembody':''">
							<image :src="item.image" mode="aspectFill"></image>
							<view class="info">
								<view class="name" :class="them?'light-item-name':''">{{item.name}}</view>
								<view class="con over3"  :class="them?'light-item-666':''">{{item.detail?item.detail:''}}</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>
		
		<view class="recomview">
			<view class="top">
				<view class="left">
					<view :class="them?'light-index-line':''" class="line"></view>
					<text :class="them?'light-333':''">相关推荐</text>
				</view>
				<view class="right" @click="change">
					<image src="../../../static/img/icon/j_9.png" mode="aspectFit"></image>
					<text>换一批</text>
				</view>
			</view>
			<view class="list">
				<view class="item" @click="dramaDetail(item.id)" v-for="item in tuijianList">
					<image :src="item.image" mode="aspectFill"></image>
					<view class="name">{{item.name}}</view>
				</view>
			</view>
			
		</view>
		<view class="light-bomview" :class="them?'light-bomview':'dark-bomview'" v-if="them&&dmUser.dmid>0" @click="play">
			<image src="" mode=""></image>
			<view class="">
				预约玩本
			</view>
		</view>
		<view class="bomview" v-if="!them&&dmUser.dmid==0">
			<view class="left">
				<view class="btn" @click="wantPlay">
					<image v-if="dataArr.wants==0" src="../../../static/img/icon/j_7.png" mode="aspectFit"></image>
					<image v-if="dataArr.wants==1" src="../../../static/img/icon/j_7a.png" mode="aspectFit"></image>
					<text>想玩</text>
				</view>
				<view class="btn" @click="played">
					<image v-if="dataArr.played==0" src="../../../static/img/icon/j_8.png" mode=""></image>
					<image v-if="dataArr.played==1" src="../../../static/img/icon/j_8a.png" mode=""></image>
					<text>玩过</text>
				</view>
			</view>
			<view v-if="dmUser.dmid==0" class="right" @click="play">
				<image src="../../../static/img/icon/j_10.png" mode="aspectFit"></image>
				<text>创建组局</text>
			</view>
		</view>
		
		<view class="bomview" :class="them?'light-bom':''" v-if="them&&dmUser&&dmUser.dmid==0">
			<view class="left">
				<view class="btn" @click="wantPlay">
					<image v-if="dataArr.wants==0" src="../../../static/img/icon/j_7l.png" mode="aspectFit"></image>
					<image v-if="dataArr.wants==1" src="../../../static/img/icon/j_7al.png" mode="aspectFit"></image>
					<text>想玩</text>
				</view>
				<view class="btn" @click="played">
					<image v-if="dataArr.played==0" src="../../../static/img/icon/j_8l.png" mode=""></image>
					<image v-if="dataArr.played==1" src="../../../static/img/icon/j_8al.png" mode=""></image>
					<text>玩过</text>
				</view>
			</view>
			<view v-if="dmUser&&dmUser.dmid==0" class="right" :class="them?'light-right':''" @click="play">
				<image src="../../../static/img/icon/j_2w.png" mode="aspectFit"></image>
				<text>创建组局</text>
			</view>
		</view>
		
		
		<view style="height: 120rpx;"></view>
		<u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
	</view>
</template>

<style scoped lang="scss" src="./index.scss"></style>

<script>
	import{
		getdramaDetail,
		getrelated,
		getwants,
		getplayed
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				// guige:[{'name':'盒装',id:1},{'name':'城限',id:2},{'name':'独家',id:3},{'name':'实景',id:4},{'name':'微剧本',id:5},{'name':'桌游',id:6}],
				tuijianList:[],
				ratearr:[1,1,1,1,1],
				showall:false,
				dataArr:{},
				page:1,
				id:'',
				wantPlays:1,
				playeds:1,
				dmUser:'',
				loading: true,
				them:''
			};
		},
		onReady() {
			app.setColor()
		},
		onLoad(options) {
			console.log(options)
			this.getList(options)
			this.id = options.id
			this.dmUser = uni.getStorageSync('dmUser')
			this.them = uni.getStorageSync('them')
		},
		onShareAppMessage(){
			var id = this.id
			var name = uni.getStorageSync('dmUser').name||''
			var img = this.dataArr.image
			var title = this.dataArr.dramaName
			return {
			    title: name + '邀你查看剧本' + title,
			    path: '/pages/juList/detail/index?id=' + id,
				imageUrl:img,
			}
		},
		onShareTimeline(){
			var id = this.id
			var name = uni.getStorageSync('dmUser').name||''
			var img = this.dataArr.image
			var title = this.dataArr.dramaName
			return {
			    title: name + '邀你查看剧本' + title,
			    path: '/pages/juList/detail/index?id=' + id,
				imageUrl:img,
			}
		},
		methods: {
			wantPlay(){
				var id = this.id
				var that = this
				var data
				if(this.dataArr.wants==0){
					data = {
						did:id,
						cancel:0
					}
				}else{
					data = {
						did:id,
						cancel:1
					}
				}
				getwants(data).then(res=>{
					// console.log(res.data)
					if(res.code==0){
						var options = {
							id:id
						}
						that.getList(options)
					}
				})
			},
			played(){
				var data 
				var id = this.id
				var that = this
				if(this.dataArr.played==0){
					data = {
						did:id,
						cancel:0
					}
				}else{
					data = {
						did:id,
						cancel:1
					}
				}
				getplayed(data).then(res=>{
					// console.log(res.data)
					if(res.code==0){
						var options = {
							id:id
						}
						that.getList(options)
					}
				})
			},
			dramaDetail(id){
				/* uni.pageScrollTo({
					scrollTop: 0,
				})

				var data = {
					id:id
				}
				this.getList(data) */
				uni.redirectTo({
					url:'./index?id=' + id
				})
			},
			change(){
				uni.showLoading({
					title:'加载中'
				})
				var maxpage
				this.page++
				if(this.tuijianList.length<6){
					this.page = 1
				}
				if(this.page>maxpage){
					this.page = 1
				}
				var data = {
					id:this.id,
					page:this.page
				}
				getrelated(data).then(res=>{
					if(res.code==0){
						this.tuijianList = res.data.records	
						maxpage = res.data.pages
					}
					uni.hideLoading({
						
					})
				})
			},
			getList(options){
				uni.showLoading({
					title:'加载中'
				})
				getdramaDetail(options).then(res=>{
					console.log(res.data)
					var num = 0
					var a = [{score:5,count:0},{score:4,count:0},{score:3,count:0},{score:2,count:0},{score:1,count:0}]
					a.map(g=>{
						res.data.scores.map(r=>{
							if(r.score/2==g.score){
								g.count = r.count
							}
						})
						num = num + parseInt(g.count)
					})
					res.data.scores = a
					this.dataArr = res.data 
					var content = res.data.detail
					/* this.dataArr.detail = content.replace(/<p[^>]*>/gi,function(match,capture){
					  return match.replace(/color: \"(.*)\"/gi,'');
					}); */
					this.dataArr.count = num
					var reg = /style="[^=>]*"/g;
					console.log(content.replace(reg,''))
					this.dataArr.detail = content.replace(reg,'');
					this.loading = false;
				})
				var data = {
					id:options.id,
					page:this.page
				}
				getrelated(data).then(res=>{
					if(res.code==0){
						this.tuijianList = res.data.records
					}
				})
				uni.hideLoading({
					
				})
			},
			showdesc()
			{
				this.showall = !this.showall
			},
			play(){
				var id = this.id
				uni.navigateTo({
					url:"../creat/index?id=" + id
				})
			}
		},
	}
</script>

<style>
</style>
