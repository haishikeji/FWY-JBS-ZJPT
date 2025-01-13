<template>
	<view class="index" :class="them?'light-index':''">
		<view class="index-top" :class="them?'light-index-top':''">
			<view v-if="!them" v-for="(item,index1) in list" :class="index==index1?'active':''" class="top-one" @click="tabClick(index1)">
				{{item}}
			</view>
			<view v-if="them" v-for="(item,index1) in list" :class="index==index1?'active':''" class="light-top-one" @click="tabClick(index1)">
				{{item}}
			</view>
		</view>
		<view class="index-content">
			<template v-for="(item,index) in dataArr">
				<view @click="teamDetail(item.id)" class="content-one" :class="them?'light-content-one':''">
					<view class="one-left">
						<image :src="item.image" mode=""></image>
					</view>
					<view class="one-right">
						<view class="rght-title" :class="them?'light-333':''">
							{{item.dramaName}}
						</view>
						<view class="right-team">
							<view class="team-img">
								<image v-if="!them" src="../../../static/img/icon/j_1.png" mode=""></image>
								<image v-if="them" src="../../../static/img/icon/j_1l.png" mode=""></image>
							</view>
							<view class="team-text" :class="them?'light-team-text':''">
								已组人数
							</view>
							<view class="team-num" :class="them?'light-team-text':''">
								{{item.curr}}/{{item.num}}
							</view>
						</view>
						<view class="right-time">
							<view class="time-img">
								<image v-if="!them" src="../../../static/img/icon/j_3.png" mode=""></image>
								<image v-if="them" src="../../../static/img/icon/j_3l.png" mode=""></image>
							</view>
							<view class="time-text" :class="them?'light-team-text':''">
								{{item.playMou}}月{{item.playDay}}日{{item.type}}  {{item.playHour}}
							</view>
						</view>
						<view class="right-money">
							<view class="money-num" :class="them?'light-team-text':''">
								<text style="font-size: 20rpx;" :class="them?'light-team-text':''">￥</text>{{item.price}}<text :class="them?'light-team-text':''" style="font-size: 20rpx;">/人</text>
							</view>
							<view @click.stop="paypop(item.id)" v-if="item.state==2" class="money-pay" :class="{'active':item.payState==2,'light-money-pay':them}">
								{{item.payState==1?'已支付':'去支付'}}
							</view>
						</view>
					</view>
					<view class="one-state">
						<view v-if="item.state==1" :class="{'light-one-state':them}" style="color: #3D3943;background: url(https://pic.hlt.hnpinxun.cn/jubensha/team_1.png);">
							组局中
						</view>
						<view v-if="item.state==2" style="background: url(https://pic.hlt.hnpinxun.cn/jubensha/team_2.png);">
							已组成
						</view>
						<view v-if="item.state==3" style="background: url(https://pic.hlt.hnpinxun.cn/jubensha/team_3.png);">
							已结束
						</view>
						<view v-if="item.state==4" style="background: url(https://pic.hlt.hnpinxun.cn/jubensha/team_4.png);">
							已解散
						</view>
					</view>
				</view>
			</template>
			<view class="nodata" v-if="dataArr.length==0">
				<image v-if="!them" src="../../../static/img/nozuju.png" mode=""></image>
				<image v-if="them" src="../../../static/img/nozuju_l.png" mode=""></image>
				<view class="" :class="them?'light-333':''">
					暂无组局信息
				</view>
			</view>
		</view>
		<u-popup v-model="autoPop" mode="bottom" closeable="true" height="400rpx">
			<view class="auto">
				<view class="auto-title">
					请选择付款方式
				</view>
				<view class="yue" @click="pay">
					<image src="../../../static/img/yue.png" mode=""></image>
					<view class="">
						账户余额 (剩余：￥{{wallet}})
					</view>
				</view>
				<view class="yue" @click="weixinpay">
					<image src="../../../static/img/weixin.png" mode=""></image>
					<view class="">
						微信支付
					</view>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import{
		getmycars,
		getuserpay,
		getuserInfo,
		getcarye,
		getpayuser
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				list:[
					'全部',
					'组局中',
					'已组成',
					'已结束',
					'已解散'
				],
				index:'',
				dataArr:[],
				pageNo:1,
				hsmore:false,
				autoPop:false,
				id:'',
				wallet:'',
				them:''
			}
		},
		onReady() {
			app.setColor()
		},
		onLoad(){
			this.dataArr = []
			this.getList()
			this.them = uni.getStorageSync('them')
		},
		onPullDownRefresh(){
			this.dataArr = []
			this.getList()
			setTimeout(function () {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onReachBottom(){
			if(this.hsmore){
				this.pageNo++
				this.getList()
			}else{
				
			}
		},
		onShareAppMessage(){
			return {
			    title: '我的组局',
			    path: '/pages/user/myTeam/index'
			}
		},
		methods: {
			paypop(id){
				this.id = id
				this.autoPop = true
			},
			weixinpay(){
				uni.showLoading({
					title:'加载中'
				})
				var that = this
				var data = {
					id:this.id,
					type:2,
					ids:''
				}
				getpayuser(data).then(res=>{
					console.log(res.data,'pay')
					if(res.code==0){
						uni.requestPayment({
							provider: 'wxpay',
							timeStamp: res.data.timeStamp,
							nonceStr: res.data.nonceStr,
							package: res.data.packageValue,
							signType: res.data.signType,
							paySign: res.data.paySign,
							success: function (res) {
								console.log('success:' + JSON.stringify(res));
								that.dataArr = []
								that.getList()
								that.autoPop = false
								uni.hideLoading({})
							},
							fail: function (err) {
								console.log('fail:' + JSON.stringify(err));
							}
						});
					}
				})
				
			},
			pay(){
				var that = this
				var data = {
					id:this.id,
					type:1,
					ids:''
				}
				if(this.wallet==0){
					uni.showToast({
						title:'余额不足',
						icon:'none'
					})
				}else{
					uni.showModal({
						content:'确认使用余额支付',
						success(res) {
							if(res.confirm){
								getpayuser(data).then(res=>{
									console.log(res.data,'pay')
									if(res.code==0){
										uni.showToast({
											title:'支付成功'
										})
										that.dataArr = []
										that.getList()
										that.autoPop = false
									}else{
										uni.showToast({
											title:res.msg,
											icon:'none'
										})
									}
								})
							}
						}
					})
				}
			},
			tabClick(index){
				this.dataArr = []
				this.index = index
				this.getList()
			},
			teamDetail(id){
				uni.navigateTo({
					url:'../dm/detail/index?id=' + id
				})
			},
			getList(){
				uni.showLoading({
					title:'加载中'
				})
				var data = {}
				if(this.hsmore){
					data.page = this.pageNo
				}else{
					data.page = 1
					this.pageNo = 1
				}
				data.limit = 10
				data.state = this.index==0?'':this.index
				getmycars(data).then(res=>{
					if(res.code==0){
						if(res.data.records.length==10){
							this.hsmore = true
						}else{
							this.hsmore = false
						}
						res.data.records.map(r=>{
							r.playMou = r.playTime.substring(5,7)
							r.playDay = r.playTime.substring(8,10)
							r.playHour = r.playTime.substring(11,16)
						})
						this.dataArr = this.dataArr.concat(res.data.records)
						
					}else{
						uni.showToast({
							icon:'none',
							title:res.msg
						})
					}
					uni.hideLoading({
						
					})
				})
				getuserInfo().then(res=>{
					this.wallet = res.data.wallet
				})
			}
		}
	}
</script>
<style scoped lang="scss" src="./index.scss"></style>
