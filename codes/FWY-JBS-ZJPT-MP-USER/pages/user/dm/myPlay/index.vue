<template>
	<view class="index" :class="{'light-index':them}">
		<view class="index-top" :class="{'light-index-top':them}">
			<view v-for="(item,index1) in list" :class="{'active':index==index1,'light-top-one':them}" class="top-one" @click="tabClick(index1)">
				{{item}}
			</view>
		</view>
		<view class="index-content">
			<template v-for="(item,index) in dataArr">
				<view class="content-one" :class="{'light-content-one':them}" @click="myDetail(item.id)">
					<view class="one-left">
						<image :src="item.image" mode=""></image>
					</view>
					<view class="one-right">
						<view class="rght-title" :class="{'light-rght-title':them}">
							{{item.dramaName}}
						</view>
						<view class="right-team">
							<view class="team-img">
								<image v-if="!them" src="../../../../static/img/icon/j_1.png" mode=""></image>
								<image v-esle src="../../../../static/img/icon/j_1l.png" mode=""></image>
							</view>
							<view class="team-text" :class="{'light-team-text':them}">
								已组人数
							</view>
							<view class="team-num" :class="{'light-team-text':them}">
								{{item.curr}}/{{item.num}}
							</view>
						</view>
						<view class="right-time">
							<view class="time-img">
								<image v-if="!them" src="../../../../static/img/icon/j_3.png" mode=""></image>
								<image v-esle src="../../../../static/img/icon/j_3l.png" mode=""></image>
							</view>
							<view class="time-text" :class="{'light-team-text':them}">
								{{item.playMou}}月{{item.playDay}}日{{item.type}}  {{item.playHour}}
							</view>
						</view>
						<view class="right-money">
							<view class="money-num" :class="{'light-team-text':them}">
								<text style="font-size: 20rpx;">￥</text>{{item.price}}<text style="font-size: 20rpx;">/人</text>
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
				<image v-if="!them" src="../../../../static/img/nozuju.png" mode=""></image>
				<image v-if="them" src="../../../../static/img/nozuju_l.png" mode=""></image>
				<view class="" :class="{'light-rght-title':them}">
					暂无组局信息
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import{
		getdmCars
	}from '../../../../static/js/api.js'
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
				them:'',
			}
		},
		onLoad(){
			this.getList()
			this.them = uni.getStorageSync('them')
		},
		onReady() {
			app.setColor()
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
		methods: {
			myDetail(id){
				uni.navigateTo({
					url:'../detail/index?id=' + id
				})
			},
			tabClick(index){
				this.dataArr = []
				this.index = index
				this.getList()
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
				getdmCars(data).then(res=>{
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
			}
		}
	}
</script>
<style scoped lang="scss" src="./index.scss"></style>
