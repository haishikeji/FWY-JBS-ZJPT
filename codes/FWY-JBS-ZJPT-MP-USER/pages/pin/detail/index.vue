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
		
		<u-popup v-model="popShow" mode="bottom" close-icon-color="#9f9f9f" :closeable="true" duration="700" border-radius="20" height="640rpx">
			<view class="userindex" :class="them?'light-bomview':''">
				<view class="index-bot">
					<view class="bot-left" :class="them?'light-bot-left':''">
						组局成员
					</view>
				</view>
				<view class="index-list">
					<scroll-view enable-flex="true" class="index-list" scroll-y="true" >
					<template v-for="(item,index) in dataArr.users">
						<view class="list-one">
							<view class="list-left">
								<view class="list-l-img">
									<image :src="item.avatar" mode=""></image>
								</view>
								<view class="list-l-text" :class="them?'light-333':''">
									{{item.name}}
								</view>
								<view class="list-l-imgs">
									<image v-if="item.sex==1&&!them" src="../../../static/img/icon/boy.png" mode=""></image>
									<image v-if="item.sex==2&&!them" src="../../../static/img/icon/g.png" mode=""></image>
									<image v-if="item.sex==1&&them" src="../../../static/img/icon/boy_l.png" mode=""></image>
									<image v-if="item.sex==2&&them" src="../../../static/img/icon/g_l.png" mode=""></image>
								</view>
							</view>
						</view>
					</template>
					</scroll-view>
				</view>
			</view>
		</u-popup>
		<view class="bomview" :class="them?'light-bomview':''">
			<view class="left" @click="popShow=true">
				<!-- <view class="btn">
					<image src="../../../static/img/icon/j_7.png" mode="aspectFit"></image>
					<text>想玩</text>
				</view>
				<view class="btn">
					<image src="../../../static/img/icon/j_8.png" mode=""></image>
					<text>玩过</text>
				</view> -->
				<view class="peo-left">
					<view class="time-img">
						<image v-if="!them" src="../../../static/img/icon/j_1.png" mode=""></image>
						<image v-if="them" src="../../../static/img/icon/j_1l.png" mode=""></image>
					</view>
					<view class="peo-text" :class="them?'light-peo-text':''" >
						已组人数：{{dataArr.curr}}/{{dataArr.num}}
					</view>
					<view class="peo-imgtop">
						<image v-if="!them" src="../../../static/img/more.png" mode=""></image>
						<image v-if="them" src="../../../static/img/more_l.png" mode=""></image>
					</view>
				</view>
				<view class="item-time">
					<view class="time-img">
						<image v-if="!them" src="../../../static/img/icon/j_3.png" mode=""></image>
						<image v-if="them" src="../../../static/img/icon/j_3l.png" mode=""></image>
					</view>
					<view class="time-text" :class="them?'light-peo-text':''">
						{{dataArr.playMou}}月{{dataArr.playDay}}日{{dataArr.type}}  {{dataArr.playHour}}
					</view>
				</view>
			</view>
			<view v-if="dataArr.state==1" class="right" :class="them?'light-right':''" @click="join">
				<text>加入组局</text>
			</view>
		</view>
		<view style="height: 120rpx;"></view>
		<u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
		
		<canvas canvas-id="myCanvas" id="myCanvas" style="width: 750rpx;height: 600rpx;position: absolute;top: -1000rpx;"/>
	</view>
</template>

<style scoped lang="scss" src="./index.scss"></style>

<script>
	import{
		getcarDetail,
		getjoinCars,
		getuserInfo
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				guige:[{'name':'盒装',id:1},{'name':'城限',id:2},{'name':'独家',id:3},{'name':'实景',id:4},{'name':'微剧本',id:5},{'name':'桌游',id:6}],
				ratearr:[1,1,1,1,1],
				showall:false,
				dataArr:{},
				id:'',
				dmUser:{},
				loading: true,
				popShow:false,
				shareImage:'',
				logoshare:'',
				downImgAll:'',//网络图片下载
				them:''
			};
		},
		onReady() {
			app.setColor()
		},
		onLoad(options) {
			var that = this
			that.getList(options).then(res=>{
				that.shareImg()
			})
			this.id = options.id
			this.them = uni.getStorageSync('them')
		},
		onShareAppMessage(){
			var name = uni.getStorageSync('dmUser').name||''
			var id = this.id
			var title = this.dataArr.dramaName
			var img = this.logoshare
			return {
			    title: name+ '邀你加入' +title,
			    path: '/pages/pin/detail/index?id=' + id,
				imageUrl:img
			}
		},
		onShareTimeline(){
			var name = uni.getStorageSync('dmUser').name||''
			var id = this.id
			var title = this.dataArr.dramaName
			var img = this.logoshare
			return {
			    title: name+ '邀你加入' +title,
			    path: '/pages/pin/detail/index?id=' + id,
				imageUrl:img
			}
		},
		methods: {
			insertStr(soure, start, newStr){
			  return soure.slice(0, start) + newStr + soure.slice(start);
			},
			download(url){
				// console.log(url,'地址')
				var that = this
				return new Promise((resolve, reject) => {
					uni.downloadFile({
						url:url,
						success:(res)=>{
							if(res.statusCode==200){
								console.log('下载成功');
								console.log(res.tempFilePath)
								resolve(res.tempFilePath)
							}
						},
						complete:(r)=>{
							// console.log(r,'原因')
						}
					})
				})
			},
			shareImg(){
				// return new Promise((resolve, reject) => {
					var that = this
					var userAvatar = that.dataArr.users
					const ctx = uni.createCanvasContext('myCanvas')
					// ctx.setFillStyle('#312c39')
					ctx.beginPath()
					ctx.rect(0, 0, 375, 300);
					ctx.setFillStyle('#312c39');
					ctx.setStrokeStyle('#312c39');
					ctx.fill();
					ctx.stroke();
					that.download(that.shareImage).then(res=>{
						console.log(res)
						ctx.drawImage(res, 15, 25, 150, 210)
						ctx.save();
						ctx.font = 'normal 19px'; // 字体
						ctx.setFontSize(19) //设置字体大小，默认10
						ctx.setFillStyle('#FFF29A') //文字颜色：默认黑色
						var num = that.dataArr.num-that.dataArr.curr
						var curr  =that.dataArr.curr
						let str = "《"+that.dataArr.dramaName+"》"+curr+"人等"+num+"人，快来加入组局吧～"
						that.drawText(ctx, str, 180, 50, 77, 90);
						that.forAvatar(ctx,userAvatar)//循环用户头像
						ctx.drawImage('../../../static/img/joinin.png', 15, 254, 345,45)
						setTimeout(function(){
							ctx.draw()
						},200)
					})//剧本图片
				// })
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
					  }
					})
				},1000)
			},
			forAvatar(ctx,data){
				var that = this
				var src = '../../../static/img/moreAvatar.png'
				var left = 180
				data.map((res,index)=>{
					if(index<4){
						left+=30
						that.drawAvatar(ctx,res,left)
					}else{
						ctx.drawImage(src,left,194,40,40)
					}
				})
			},
			drawAvatar(ctx,res,left){
				var that = this
				that.download(res.avatar).then(r=>{
					console.log(r,'临时路径')
					ctx.save()
					ctx.beginPath()
					ctx.arc(left, 194, 20, 0, 2 * Math.PI)
					ctx.setFillStyle('#ffffff')
					ctx.fill()
					ctx.clip()
					ctx.drawImage(r,left-20,174,40,40)
					ctx.restore()
					// ctx.drawImage(r,left,194,40,40)
				})
			},
			/**
			 * 绘制多行文本
			 * @param ctx canvas对象
			 * @param str 文本
			 * @param leftWidth 距离左侧的距离
			 * @param initHeight 距离顶部的距离
			 * @param titleHeight 文本的高度
			 * @param canvasWidth 文本的宽度
			 * @returns {*}
			 * */
			drawText(ctx, str, leftWidth, initHeight, titleHeight, canvasWidth) {
			    let lineWidth = 0;
			    let lastSubStrIndex = 0; //每次开始截取的字符串的索引
			    for (let i = 0; i < str.length; i++) {
			        lineWidth += ctx.measureText(str[i]).width;
			        if (lineWidth > canvasWidth) {
			            ctx.fillText(str.substring(lastSubStrIndex, i), leftWidth, initHeight); //绘制截取部分
			            initHeight += 29; //22为 文字大小20 + 2
			            lineWidth = 0;
			            lastSubStrIndex = i;
			            titleHeight += 21;
			        }
			        if (i == str.length - 1) { //绘制剩余部分
			            ctx.fillText(str.substring(lastSubStrIndex, i + 1), leftWidth, initHeight);
			        }
			    }
			    // 标题border-bottom 线距顶部距离
			    titleHeight = titleHeight + 10;
			    return titleHeight;
			},
			getList(options){
				return new Promise((resolve, reject) => {
					getcarDetail(options).then(res=>{
						console.log(res.data)
						var num = 0
						var a = [{score:5,count:0},{score:4,count:0},{score:3,count:0},{score:2,count:0},{score:1,count:0}]
						a.map(g=>{
							res.data.scores.map(r=>{
								if(r.score==g.score*2){
									g.count = r.count
								}
							})
							num = num + parseInt(g.count)
						})
						res.data.scores = a
						res.data.playMou = res.data.playTime.substring(5,7)
						res.data.playDay = res.data.playTime.substring(8,10)
						res.data.playHour = res.data.playTime.substring(11,16)
						res.data.count = num
						this.shareImage = this.insertStr(res.data.image,4,'s')
						this.dataArr = res.data
						var content = res.data.detail
						var reg = /style="[^=>]*"/g;
						console.log(content.replace(reg,''))
						this.dataArr.detail = content.replace(reg,'');
						this.loading = false;
						resolve()
					})
				})
			},
			showdesc()
			{
				this.showall = !this.showall
			},
			join(){				
				var that = this
				var dmUser = uni.getStorageSync('dmUser')||''
				
					if(dmUser.id){
							var id = this.id
							var data = {
								id:id
							}
						app.message().then(i=>{
						uni.showModal({
							title:'加入确认',
							content:'您加入剧本就不可再加入当前时间段的剧本，并且不可取消，是否确认',
							confirmText:'确认加入',
							success:function(res){
								if(res.confirm){
									getjoinCars(data).then(r=>{
										if(r.code==0){
											uni.showToast({
												title:'加入成功'
											})
											setTimeout(function(){
												uni.navigateTo({
													url:'../../user/dm/detail/index?id='+r.data
												})
											},400)
										}else{
											uni.showToast({
												title:r.msg,
												icon:'none'
											})
										}
									})
								}else{
									console.log('用户取消')
								}
							}
						})
						})
					}else{
						/* uni.showToast({
							title:'请授权登录',
							icon:'none'
						}) */
						app.getUserInfo().then(res=>{
							console.log('登录')
							getuserInfo().then(g=>{
								that.dmUser = g.data
								uni.setStorageSync('dmUser',g.data)
								uni.showToast({
									title:'登录成功,请点击加入'
								}) 
								
							})
						})
					}
			}
		},
	}
</script>

<style>
</style>
