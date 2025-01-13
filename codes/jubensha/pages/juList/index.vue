<template>
	<view class="pages" :class="them?'light':''">
		<u-navbar :is-back="false" :border-bottom="false" title=" " :background="background">
			<view class="slot-wrap">
				<view class="searchbar" :class="them?'light-searchbar':''">
					<image v-if="!them" src="/static/img/icon/search.png" mode="" class="search"></image>
					<image v-if="them" src="/static/img/icon/search_l.png" mode="" class="search"></image>
					<!-- <input type="text" value="searchkey" placeholder="请输入剧本关键字"/> -->
					<view class="input">
						<u-input v-if="!them" v-model="searchkey" @confirm="confirmInp" :type="type" :border="border" :clearable="clearable" :custom-style="inputstyle"/>
						<u-input v-if="them" v-model="searchkey" @confirm="confirmInp" :type="type" :border="border" :clearable="clearable" :custom-style="inputstyle1"/>
					</view>
					
					<!-- <image src="/static/img/icon/clear.png" mode="" class="clear" @click="clearinput"></image> -->
				</view>
			</view>
		</u-navbar>
		<view class="filterview" :class="them?'light-filterview':''" :style="themenum==5?'':them?'height:auto;background: rgba(249, 249,249, 0.85);box-shadow: 0px 4rpx 4rpx 0 rgba(0, 0, 0, 0.15);border-bottom-left-radius: 40rpx;border-bottom-right-radius: 40rpx;':'height:auto;background: rgba(51, 51, 51, 0.8);box-shadow: 0px 2rpx 6rpx rgba(32, 32, 32, 0.5);backdrop-filter: blur(8rpx);border-bottom-left-radius: 40rpx;border-bottom-right-radius: 40rpx;'">
			<view class="item">
				<view class="title" :class="{'active':ggindex==-1,'light-title':them}" @click="getAll">全部规格</view>
				<view class="content">
					<view class="name" :class="{'active':item.state==2,'light-name':them}" v-for="(item,i) in guige" @click="ggselect(i)">{{item.name}}</view>
				</view>
			</view>
			<view class="item">
				<view class="title" :class="{'active':ppindex==-1,'light-title':them}" @click="getAll1">全部人数</view>
				<view class="content">
					<view class="name ppnum" :class="{'active':item.state==2,'light-name':them}" v-for="(item,i) in people" @click="ppselect(i)">{{item.name==4?'≤'+item.name:(item.name==10?'≥'+item.name:item.name)}}人</view>
				</view>
			</view>
			<view class="item">
				<view class="title" :class="{'active':rrindex==-1,'light-title':them}" @click="getAll2">全部难度</view>
				<view class="content">
					<view class="name" :class="{'active':item.state==2,'light-name':them}" v-for="(item,i) in rank" @click="rrselect(i)">{{item.name}}</view>
				</view>
			</view>
			<view class="item">
				<view class="title" :class="{'active':ttindex==-1,'light-title':them}" @click="getAll3">全部题材</view>
				<view class="content maxHeight" :class="{'h60':themenum==5}">
					<view class="name theme" :class="{'active':item.state==2,'light-name':them}" v-for="(item,i) in theme" @click="tagselect(i)">{{item.name}}</view>
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
				<view class="open-more" :class="them?'light-open-more':''" @click="showall">
					{{themenum==5?'展开更多':'收起'}}
				</view>
				<view class="open-line">
					
				</view>
			</view>
		</view>
		<!-- <view v-if="themenum!=5" class="popView"></view> -->
		<u-mask :show="show" z-index='98'></u-mask>
		<view class="juben" :class="them?'light-juben':''">
			<view class="result" :class="them?'light-result':''">共检索到{{total?total:'0'}}个结果</view>
			<view class="nodata" v-if="dataArr.length==0">
				<image v-if="!them" src="../../static/img/noinfo.png" mode=""></image>
				<image v-if="them" src="../../static/img/noinfo_l.png" mode=""></image>
				<view class="" :class="them?'light-result':''">
					暂无相关信息
				</view>
			</view>
			<view class="list">
				<template v-for="item in dataArr">
				<view class="item" @click="detailBtn(item.id)">
					<view class="imgbox">
						<image :src="item.image" mode="aspectFill" class="img"></image>
						<view class="label">
							<view class="labelbody">
								<image v-if="!them" src="../../static/img/label.png" mode=""></image>
								<image v-if="them" src="../../static/img/label_l.png" mode=""></image>
								<view class="labelname">{{item.specName}}</view>
							</view>
						</view>
					</view>
					<view class="info">
						<view class="info-top">
							<view class="name">
								<view class="tip" :class="them?'light-tip':''">新</view>
								<view class="n_name" :class="them?'light-n_name':''">{{item.dramaName}}</view>
							</view>
							<view class="mark">
								<view class="markname" :class="them?'light-markname':''" v-for="items in item.typeNames">{{items}}</view>
							</view>
						</view>
						<view class="info-bot">
							<view class="menu">
								<view class="menuitem">
									<image v-if="!them" src="../../static/img/icon/j_1.png" mode="aspectFit" class="menuimg"></image>
									<image v-if="them" src="../../static/img/icon/j_1l.png" mode="aspectFit" class="menuimg"></image>
									<text :class="them?'light-n_name':''">{{item.male}}男{{item.female}}女</text>
								</view>
								<view class="menuitem">
									<image v-if="!them" src="../../static/img/icon/j_3.png" mode="aspectFit" class="menuimg"></image>
									<image v-if="them" src="../../static/img/icon/j_3l.png" mode="aspectFit" class="menuimg"></image>
									<text :class="them?'light-n_name':''">{{item.duration}}小时</text>
								</view>
								<view class="menuitem">
									<image v-if="!them" src="../../static/img/icon/j_4.png" mode="aspectFit" class="menuimg"></image>
									<image v-if="them" src="../../static/img/icon/j_4l.png" mode="aspectFit" class="menuimg"></image>
									<text :class="them?'light-n_name':''">{{item.diffName}}</text>
								</view>
							</view>
							<view class="bom">
								<view class="price" :class="them?'light-n_name':''">
									<text>￥</text>
									{{item.price}}
									<text class="ren" :class="them?'light-n_name':''">/人</text>
								</view>
								<view v-if="dmUser.isDm==2" class="yuyue" :class="them?'light-yuyue':''">
									<image v-if="!them" src="../../static/img/icon/j_2.png" mode="aspectFit" class="yimg"></image>
									<image v-if="them" src="../../static/img/icon/j_2l.png" mode="aspectFit" class="yimg"></image>
									<text :class="them?'light-yuyue-text':''">预约</text>
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
	getspecs,
	getdiff,
	getcates,
	getdramas,
	getuserInfo
}from '../../static/js/api.js'
let app =  getApp()
export default {
	components: {
		// 注册
		
	},
	data() {
		return {
			show: false,
			pageNo:1,
			background: {
				backgroundColor: '#312C39',
			},
			guige:[],
			people:[{'name':'4',state:1,id:1},{'name':'5',state:1,id:2},{'name':'6',state:1,id:3},{'name':'7',state:1,id:4},{'name':'8',state:1,id:5},{'name':'9',state:1,id:6},{'name':'10',state:1,id:7}],
			rank:[],
			theme:[{'name':'盒装',state:1,id:1},{'name':'城限',state:1,id:2},{'name':'独家',state:1,id:3},{'name':'实景',state:1,id:4},{'name':'微剧本',state:1,id:5},{'name':'桌游',state:1,id:6},{'name':'盒装',state:1,id:1},{'name':'城限',state:1,id:2},{'name':'独家',state:1,id:3},{'name':'实景',state:1,id:4},{'name':'微剧本',state:1,id:5},{'name':'桌游',state:1,id:6},{'name':'盒装',state:1,id:1},{'name':'城限',state:1,id:2},{'name':'独家',state:1,id:3},{'name':'实景',state:1,id:4},{'name':'微剧本',state:1,id:5},{'name':'桌游',state:1,id:6},{'name':'盒装',state:1,id:1},{'name':'城限',state:1,id:2},{'name':'独家',state:1,id:3},{'name':'实景',state:1,id:4},{'name':'微剧本',state:1,id:5},{'name':'桌游',state:1,id:6},{'name':'盒装',state:1,id:1},{'name':'城限',state:1,id:2},{'name':'独家',state:1,id:3},{'name':'实景',state:1,id:4},{'name':'微剧本',state:1,id:5},{'name':'桌游',state:1,id:6}],
			
			themenum:5,
			ggindex:-1,
			ppindex:-1,
			rrindex:-1,
			ttindex:-1,
			searchkey:'',
			type: 'text',
			border: false,
			clearable:true,
			inputstyle:{
				color:'#ffffff',
				width:'360rpx',
			},
			inputstyle1:{
				color:'#333',
				width:'360rpx',
			},
			hsmore:false,
			dataArr:[],
			total:'',
			dmUser:{},
			them:'',
		};
	},
	onReady() {
		app.setColor()
	},
	onLoad(){
		this.dataArr = []
		this.getList()
		this.getDramas()
		
		
	},
	onShow() {
		app.setColor()
		this.them = uni.getStorageSync('them')
		if(this.them){
			this.background = {
				backgroundColor: '#fff',
			}
		}else
		{
			this.background = {
				backgroundColor: '#312C39',
			}
		}
	},
	onPullDownRefresh(){
		this.dataArr = []
		this.getList()
		this.getDramas()
		setTimeout(function () {
			uni.stopPullDownRefresh();
		}, 1000);
	},
	onReachBottom(){
		if(this.hsmore){
			this.pageNo++
			this.getDramas()
		}else{
			/* uni.showToast({
				title:"没有更多了",
				icon:'none'
			}) */
		}
	},
	onShareAppMessage(){
		return {
		    title: '本店剧本',
		    path: '/pages/juList/index'
		}
	},
	onShareTimeline(){
		return {
		    title: '本店剧本',
		    path: '/pages/juList/index'
		}
	},
	methods: {
		
		confirmInp(){
			this.dataArr = []
			this.getList()
			this.getDramas()
		},
		detailBtn(id){
			if(!this.dmUser.name){
				uni.showToast({
					title:'您还没有登录，请去个人档案登录',
					icon:'none'
				})
			}else
			{
				uni.navigateTo({
					url:'./detail/index?id=' + id
				})
			}
			
		},
		getList(){
			getspecs().then(res=>{
				var data = res.data
				data.map(r=>{
					r.state = 1
				})
				this.guige = data
			})
			getdiff().then(res=>{
				var data = res.data
				data.map(r=>{
					r.state = 1
				})
				this.rank = data
			})
			getcates().then(res=>{
				var data = res.data
				data.map(r=>{
					r.state = 1
				})
				this.theme = data
			})
			getuserInfo().then(res=>{
				this.dmUser = res.data
			})
		},
		getDramas(){
			uni.showLoading({
				title:'加载中'
			})
			var number
			var types = []
			var spec 
			var diff
			this.people.map(res=>{
				if(res.state==2){
					number = res.name
				}
			})
			this.theme.map(res=>{
				if(res.state==2){
					types.push(res.id)
				}
			})
			this.guige.map(res=>{
				if(res.state==2){
					spec = res.id
				}
			})
			this.rank.map(res=>{
				if(res.state==2){
					diff = res.id
				}
			})
			function dedupe(array){
			 return Array.from(new Set(array));
			}
			types = dedupe(types).toString()
			var data = {}
			if(this.hsmore){
				data.page = this.pageNo
			}else{
				data.page = 1
				this.pageNo = 1
			}
			data.limit = 10,
			data.name = this.searchkey
			data.number = number||''
			data.types = types||''
			data.diff = diff||''
			data.spec = spec||''
			getdramas(data).then(res=>{
				if(res.code==0){
					if(res.data.records.length==10){
						this.hsmore = true
					}else{
						this.hsmore = false
					}
					this.total = res.data.total
					console.log(res.data.records)
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
		},
		getAll(){
			// -1选中
			if(this.ggindex==-1){
				// this.ggindex = 1
			}else{
				this.ggindex = -1
				var data = this.guige
				data.map(res=>{
					res.state = 1
				})
			}
			this.dataArr = []
			this.getDramas()
		},
		getAll1(){
			if(this.ppindex==-1){
				// this.ppindex = 1
			}else{
				this.ppindex = -1
				var data = this.people
				data.map(res=>{
					res.state = 1
				})
			}
			this.dataArr = []
			this.getDramas()
		},
		getAll2(){
			if(this.rrindex==-1){
				// this.rrindex = 1
			}else{
				this.rrindex = -1
				var data = this.rank
				data.map(res=>{
					res.state = 1
				})
			}
			this.dataArr = []
			this.getDramas()
		},
		getAll3(){
			if(this.ttindex==-1){
				// this.ttindex = 1
			}else{
				this.ttindex = -1
				var data = this.theme
				data.map(res=>{
					res.state = 1
				})
			}
			this.dataArr = []
			this.getDramas()
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
			/* var flag = false
			this.theme.map(res=>{
				if(res.state==2){
					flag = false
				}else{
					flag = true
				}
			})
			if(flag==true){
				this.ttindex = -1
			}else{
				this.ttindex = 1
			} */
			this.dataArr = []
			this.getDramas()
		},
		ggselect(e)
		{
			this.ggindex = 1
			var data = this.guige
			data.map((res)=>{
				res.state = 1
			})
			data[e].state = 2
			this.dataArr = []
			this.getDramas()
		},
		ppselect(e)
		{
			this.ppindex = 1
			var data = this.people
			data.map((res)=>{
				res.state = 1
			})
			data[e].state = 2
			this.dataArr = []
			this.getDramas()
		},
		rrselect(e)
		{
			this.rrindex = 1
			var data = this.rank
			data.map((res)=>{
				res.state = 1
			})
			data[e].state = 2
			this.dataArr = []
			this.getDramas()
		},
		/* clearinput()
		{
			var that = this
			that.searchkey = ''
			setTimeout(function(){
				that.dataArr = []
				that.getList()
				that.getDramas()
			},300)
		} */
	}
};
</script>
