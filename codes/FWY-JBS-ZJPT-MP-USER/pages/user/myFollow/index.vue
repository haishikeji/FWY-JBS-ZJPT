<template>
	<view class="pages" :class="{'light-index':them}">
		<!-- <view class="filterview">
			<view class="name" :class="{'active':item.state==2}" v-for="(item,i) in theme" @click="tagselect(i)">{{item.name}}</view>
		</view> -->
		<view class="filterview"  :style="themenum==5?'':them?'height:auto;background: rgba(249, 249,249, 0.85);box-shadow: 0px 4rpx 4rpx 0 rgba(0, 0, 0, 0.15);border-bottom-left-radius: 40rpx;border-bottom-right-radius: 40rpx;':'height:auto;background: rgba(51, 51, 51, 0.8);box-shadow: 0px 2rpx 6rpx rgba(32, 32, 32, 0.5);backdrop-filter: blur(8rpx);border-bottom-left-radius: 40rpx;border-bottom-right-radius: 40rpx;'">
			<view class="item">
				<view class="title" :class="{'active':ttindex==-1,'light-title':them}" @click="getAll3">全部题材</view>
				<view class="content maxHeight" :class="{'h60':themenum==5}">
					<view class="name theme" :class="{'active':item.state==2,'light-title':them}" v-for="(item,i) in theme"  @click="tagselect(i)">{{item.name}}</view>
					<!-- <view class="name theme nn" @click="showall">
						<view>{{themenum==5?'展开':'收起'}}</view>
						<image src="../../static/img/icon/j_5.png" mode="aspectFit" v-if="themenum==5"></image>
						<image src="../../static/img/icon/j_6.png" mode="aspectFit" v-else></image>
					</view> -->
				</view>
			</view>
			<view class="open">
				<view class="open-line">
					
				</view>
				<view class="open-more" :class="{'light-open-more':them}" @click="showall">
					{{themenum==5?'展开更多':'收起'}}
				</view>
				<view class="open-line">
					
				</view>
			</view>
		</view>
		
		<u-mask :show="show" z-index='98'></u-mask>
		<view class="juben">
			<view class="result" :class="{'light-open-more':them}"  v-if="dataArr.length>0">共检索到{{total?total:'0'}}个结果</view>
			<view class="nodata" v-if="dataArr.length==0">
				<image v-if="!them" src="../../../static/img/nozuju.png" mode=""></image>
				<image v-if="them" src="../../../static/img/nozuju_l.png" mode=""></image>
				<view class="" :class="{'light-open-more':them}">
					暂无关注的剧本，快去关注吧
				</view>
			</view>
			<view class="list">
				<template  v-for="item in dataArr">
					<view class="item" @click="detail(item.id)">
					<view class="imgbox">
						<image :src="item.image" mode="aspectFill" class="img"></image>
						<view class="label">
							<view class="labelbody">
								<image v-if="!them" src="../../../static/img/label.png" mode=""></image>
								<image v-else src="../../../static/img/label_l.png" mode=""></image>
								<view class="labelname">{{item.specName}}</view>
							</view>
						</view>
					</view>
					<view class="info">
						<view class="info-top">
							<view class="name">
								<view class="tip" :class="{'light-tip':them}">新</view>
								<view class="n_name" :class="{'light-open-more':them}">{{item.dramaName}}</view>
							</view>
							<view class="mark">
								<view class="markname" :class="{'light-markname':them}" v-for="items in item.typeNames">{{items}}</view>
							</view>
						</view>
						<view class="info-bot">
							<view class="menu">
								<view class="menuitem">
									<image v-if="!them" src="../../../static/img/icon/j_1.png" mode="aspectFit" class="menuimg"></image>
									<image v-else src="../../../static/img/icon/j_1l.png" mode="aspectFit" class="menuimg"></image>
									<text :class="{'light-open-more':them}">{{item.male}}男{{item.female}}女</text>
								</view>
								<view class="menuitem">
									<image v-if="!them" src="../../../static/img/icon/j_3.png" mode="aspectFit" class="menuimg"></image>
									<image v-else src="../../../static/img/icon/j_3l.png" mode="aspectFit" class="menuimg"></image>
									<text :class="{'light-open-more':them}">{{item.duration}}小时</text>
								</view>
								<view class="menuitem">
									<image v-if="!them" src="../../../static/img/icon/j_4.png" mode="aspectFit" class="menuimg"></image>
									<image v-else src="../../../static/img/icon/j_4l.png" mode="aspectFit" class="menuimg"></image>
									<text :class="{'light-open-more':them}">{{item.diffName}}</text>
								</view>
							</view>
							<view class="bom">
								<view class="price" :class="{'light-open-more':them}">
									<text>￥</text>
									{{item.price}}
									<text class="ren">/人</text>
								</view>
								<view class="yuyue" :class="{'light-yuyue':them}">
									<image v-if="!them" src="../../../static/img/icon/j_2.png" mode="aspectFit" class="yimg"></image>
									<image v-else src="../../../static/img/icon/j_2l.png" mode="aspectFit" class="yimg"></image>
									<text :class="{'light-yuyue1':them}">预约</text>
								</view>
							</view>
						</view>
						
						
					</view>
				</view>
				</template>
			</view>
		</view>
	</view>
</template>

<style scoped lang="scss" src="./index.scss"></style>
<script>
	
import{
	getmyFollow,
	getcates
}from '../../../static/js/api.js'
let app = getApp()
export default {
	components: {
		// 注册
		
	},
	data() {
		return {
			theme:[],
			ttindex:-1,
			pageNo:1,
			hsmore:false,
			dataArr:[],
			themenum:5,
			show:false,
			total:'',
			them:'',
		};
	},
	onReady() {
		app.setColor()
	},
	onLoad(){
		this.getList()
		this.getThema()
		this.them = uni.getStorageSync('them')
	},
	onPullDownRefresh(){
		this.dataArr = []
		this.getList()
		this.getThema()
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
		detail(id){
			uni.navigateTo({
				url:'../../juList/detail/index?id=' + id
			})
		},
		getList(){
			let that = this
			uni.showLoading({
				title:'加载中'
			})
			var types = []
			that.theme.map(res=>{
				if(res.state==2){
					types.push(res.id)
				}
			})
			function dedupe(array){
			 return Array.from(new Set(array));
			}
			types = dedupe(types).toString()
			var data = {}
			if(that.hsmore){
				data.page = that.pageNo
			}else{
				data.page = 1
				that.pageNo = 1
			}
			data.limit = 10
			data.types = types||''
			getmyFollow(data).then(res=>{
				if(res.code==0){
					if(res.data.records.length==10){
						that.hsmore = true
					}else{
						that.hsmore = false
					}
					that.dataArr = that.dataArr.concat(res.data.records)
					that.total = res.data.total
				}else{
					uni.showToast({
						icon:'none',
						title:res.msg
					})
				}
				uni.hideLoading({
					
				})
			})
		},
		getThema(){
			getcates().then(res=>{
				var data = res.data
				data.map(r=>{
					r.state = 1
				})
				this.theme = data
			})
		},
		showall()
		{
			var themeStr = []
			var themeEnd = []
			var newTheme = []
			if(this.themenum==5)
			{
				this.themenum = 9999
				this.show = true
			}else
			{
				this.theme.map((res,index)=>{
					if(res.state==2){
						themeStr.push(res)
					}else{
						themeEnd.push(res)
					}
				})
				this.theme = newTheme.concat(themeStr,themeEnd)
				this.themenum = 5
				this.show = false
			}
			
		},
		tagselect(e)
		{
			if(this.theme[e].state==1){
				this.theme[e].state = 2
				this.ttindex = 1
			}else{
				this.theme[e].state = 1
			}
			var flag = []
			this.theme.map(res=>{
				if(res.state==2){
					flag.push(res)
				}	
			})
			if(flag.length==0){
				this.ttindex = -1
			}
			this.dataArr = []
			this.getList()()
		},
	}
};
</script>

<style></style>
