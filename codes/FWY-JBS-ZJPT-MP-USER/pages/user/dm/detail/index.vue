<template>
	<view class="index" :class="them?'light-index':''">
		<view class="index-top">
			<view class="top-left">
				<image :src="dataArr.image" mode=""></image>
			</view>
			<view class="top-right">
				<view class="top-right-title">
					<view class="title-type" :class="them?'light-title-type':''">
						新
					</view>
					<view class="title-text" :class="them?'light-title-text':''">
						{{dataArr.dramaName}}
					</view>
				</view>
				<view class="top-right-tag">
					<!-- <view class="tag-one" style="color: #FFF29A;background: rgba(255,242,154,0.20);">
						店铺独家
					</view> -->
					<template v-for="(item,index) in dataArr.typeNames">
						<view class="tag-one" :class="them?'light-tag-one':''">
							{{item}}
						</view>
					</template>
				</view>
				<view class="top-right-address">
					<!-- 发行：在洛阳运营部出版 -->
				</view>
				<view class="top-right-bot">
					<view class="r-b-num" :class="them?'light-r-b-num':''">
						￥<text style="font-size: 40rpx;">{{dataArr.price}}</text>/人
					</view>
					<view class="r-b-play">
						<view class="play-img">
							<image v-if="!them" src="../../../../static/img/icon/j_7.png" mode=""></image>
							<image v-else src="../../../../static/img/icon/j_7l.png" mode=""></image>
						</view>
						<view class="play-text" :class="them?'light-r-b-num':''">
							{{dataArr.wants}}人想玩
						</view>
					</view>
					<view class="r-b-play">
						<view class="play-img">
							<image v-if="!them" src="../../../../static/img/icon/j_8.png" mode=""></image>
							<image v-else src="../../../../static/img/icon/j_8l.png" mode=""></image>
						</view>
						<view class="play-text" :class="them?'light-play-text':''">
							{{dataArr.played}}人玩过
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="index-content">
			<view class="content-top" :class="them?'light-content-top':''">
				<view class="content-one">
					<view class="one-top">
						<view class="one-top-img">
							<image v-if="!them" src="../../../../static/img/icon/j_1.png" mode=""></image>
							<image v-else src="../../../../static/img/icon/j_1l.png" mode=""></image>
						</view>
						<view class="one-top-text" :class="them?'light-title-text':''">
							组局人数
						</view>
					</view>
					<view class="one-bot" :class="them?'light-title-text':''">
						{{dataArr.male}}男{{dataArr.female}}女
					</view>
				</view>
				<view class="content-one">
					<view class="one-top">
						<view class="one-top-img">
							<image v-if="!them" src="../../../../static/img/icon/j_3.png" mode=""></image>
							<image v-else src="../../../../static/img/icon/j_3l.png" mode=""></image>
						</view>
						<view class="one-top-text" :class="them?'light-title-text':''">
							剧本时长
						</view>
					</view>
					<view class="one-bot" :class="them?'light-title-text':''">
						{{dataArr.duration}}小时
					</view>
				</view>
				<view class="content-one">
					<view class="one-top">
						<view class="one-top-img">
							<image v-if="!them" src="../../../../static/img/icon/j_4.png" mode=""></image>
							<image v-else src="../../../../static/img/icon/j_4l.png" mode=""></image>
						</view>
						<view class="one-top-text" :class="them?'light-title-text':''">
							剧本难度
						</view>
					</view>
					<view class="one-bot" :class="them?'light-title-text':''">
						{{dataArr.diffName}}
					</view>
				</view>
				<!-- <image class="image" v-if="!them" src="https://pic.hlt.hnpinxun.cn/jubensha/detail_bg.png" mode=""></image>
				<image class="image" v-if="them" src="https://pic.hlt.hnpinxun.cn/jubensha/detail_bgl.png" mode=""></image> -->
			</view>
			<view class="content-bot">
				<view class="content-bot-one" :class="them?'light-content-bot-one':''">
					<view class="bot-one-img">
						<image :src="dataArr.user.avatar?dataArr.user.avatar:''" mode=""></image>
					</view>
					<view class="bot-one-text" :class="them?'light-title-text':''">
						{{dataArr.user.name}}
					</view>
					<view class="bot-one-peo" :class="them?'light-title-text':''">
						发起人
					</view>
				</view>
				<view class="content-bot-one" @click="author" :class="them?'light-content-bot-two':''">
					<view class="bot-one-img">
						<image :src="dataArr.dm.image" mode=""></image>
					</view>
					<view class="bot-one-text" :class="them?'light-title-text':''">
						{{dataArr.dm.name}}
					</view>
					<view class="bot-one-peo host">
						主持人
					</view>
				</view>
			</view>
		</view>
		<view class="timestamp" v-if="timestamp>0">
			<view class="timestamp-left" :class="them?'light-title-text':''">
				本场次开局倒计时：
			</view>
			<u-count-down v-if="!them" :timestamp="timestamp" separator="zh" bg-color="#FFF29A" color="#312C39" separator-color="#fff"></u-count-down>
			<u-count-down v-else :timestamp="timestamp" separator="zh" bg-color="#FFA552" color="#fff" separator-color="#333"></u-count-down>
		</view>
		<view class="index-bot">
			<view class="bot-left" :class="them?'light-bot-left':''">
				组局成员
			</view>
			<view @click="userOver" :class="them?'light-bot-right':''" class="bot-right" v-if="dataArr.state==1&&dmUser.isDm==2">
				解散场次
			</view>
		</view>
		<view class="index-list">
			<template v-for="(item,index) in dataArr.users">
				<view class="list-one">
					<view class="list-top">
						<view class="list-left">
							<view class="list-l-img">
								<image :src="item.avatar?item.avatar:''" mode=""></image>
							</view>
							<view class="list-l-text" :class="them?'light-title-text':''">
								{{item.name}}
							</view>
							<view class="list-l-imgs">
								<image v-if="item.sex==1&&!them" src="../../../../static/img/icon/boy.png" mode=""></image>
								<image v-if="item.sex==2&&!them" src="../../../../static/img/icon/g.png" mode=""></image>
								<image v-if="item.sex==1&&them" src="../../../../static/img/icon/boy_l.png" mode=""></image>
								<image v-if="item.sex==2&&them" src="../../../../static/img/icon/g_l.png" mode=""></image>
							</view>
							<view class="list-l-state" :class="{'active':item.state==0,'light-l-state':them}" v-if="dataArr.state==1&&dmUser.isDm==2">
								{{item.state==0?'':'已支付'}}
							</view>
						</view>
						<view @click="settlement(index,item.state)" class="list-right1" :class="{'active':item.state==0,'light-list-right':them,'light-list-r-l':them&&item.state==0}" v-if="dataArr.state==2&&dmUser.isDm==2">
							{{item.state==0?'结款':'已支付'}}
						</view>
						<view @click="removeUser(item.id)" class="list-right" v-if="dataArr.state==1&&dmUser.isDm==2" :class="{'light-list-r-l':them}">
							移除组员
						</view>
					</view>
					<view class="list-bot" v-if="item.state==0&&item.settle==2">
						<view @click="balancepay(item.id)" class="bot-btn" :class="{'light-bot-btn':them}">从会员余额支付</view>
						<view @click="offlinepay(item.id)" class="bot-btn" :class="{'light-bot-btn1':them}">已经线下支付</view>
					</view>
				</view>
			</template>
		</view>
		<view class="index-btn" :class="{'active':dataArr.state==1&&dmUser.isDm==2,'light-index-btn':them}" @click="paypop" v-if="(dataArr.state==2||dataArr.state==1)&&userpay">
			立即支付
		</view>
		<view @click="startUser" class="index-btn" v-if="dataArr.state==1&&dmUser.isDm==2" :class="{'light-index-btn':them}">
			点击开局
		</view>
		<view class="index-btn" @click="evaluatePop" v-if="dataArr.state==3&&dm&&juben" :class="{'light-index-btn':them}">
			评价剧本
		</view>
		<u-popup v-model="popShow" mode="center"  border-radius="40" width="670rpx" height="376rpx">
			<view class="rate" :class="{'light-rate':them}">
				<view class="rate-top">
					<view class="top-title" :class="them?'light-title-text':''">剧本评价</view>
					<view class="top-text" :class="them?'light-999':''">满意请给5星哦</view>
				</view>
				<view class="rate-drame">
					<view class="drame-text" :class="them?'light-title-text':''">剧本评价</view>
					<view>
						<u-rate v-if="!them" v-model="score" :min-count="1" active-color="#FFF29A" inactive-color="#908C96" size="40"></u-rate>
						<u-rate v-else v-model="score" :min-count="1" active-color="#FFB26A" inactive-color="#999999" size="40"></u-rate>
					</view>
				</view>
				<view class="rate-drame">
					<view class="drame-text" :class="them?'light-title-text':''">主持人评价</view>
					<view>
						<u-rate v-if="!them" v-model="dmScore" :min-count="1" active-color="#FFF29A" inactive-color="#908C96" size="40"></u-rate>
						<u-rate v-else v-model="dmScore" :min-count="1" active-color="#FFB26A" inactive-color="#999999" size="40"></u-rate>
					</view>
				</view>
				<view class="rate-bot">
					<view class="bot-btn" @click="scoreBtn" :class="{'light-bot-btn2':them}">提交</view>
					<view class="bot-cancle" @click="popShow=false" :class="{'light-bot-cancle':them}">取消</view>
				</view>
			</view>
		</u-popup>
		<u-popup v-model="payUser" mode="bottom" border-radius="10" height="420rpx">
			<view class="paytype" :class="{'light-paytype':them}">
				<view class="paytype-title" :class="{'light-paytype-title':them}">
					支付本场费用
				</view>
				<view class="paytype-content">
					<view class="paytype-self" @click="paySelf" :class="{'light-paytype-self':them}">
						<view class="self-img">
							<image src="../../../../static/img/forself.png" mode=""></image>
						</view>
						<view class="self-text">
							为自己支付
						</view>
					</view>
					<view class="paytype-self user" @click="payforfriend=true" :class="{'light-paytype-user':them}">
						<view class="self-img">
							<image src="../../../../static/img/foruser.png" mode=""></image>
						</view>
						<view class="self-text">
							替同场好友支付
						</view>
					</view>
				</view>
			</view>
		</u-popup>
		
		<u-popup v-model="payforfriend" mode="bottom" border-radius="10" height="682rpx">
			<view class="pay-friend" :class="{'light-paytype':them}">
				<view class="friend-title" :class="{'light-paytype-title':them}">
					支付本场费用
				</view>
				<view class="friend-content">
					<scroll-view scroll-y="true" v-if="dataArr.users.length" class="friend-content" >
						<block v-for="(item,index) in dataArr.users" :key="index">
							<view class="friend-one" @click="friendclick(index)">
								<view class="one-left">
									<view class="friend-img">
										<image :src="item.avatar?item.avatar:''" mode=""></image>
									</view>
									<view class="friend-name" :class="them?'light-title-text':''">
										{{item.name}}
									</view>
								</view>
								
								<view v-if="item.states==2&&item.state!=1" class="friend-state active" :class="{'light-friend-state active':them}">
									<image src="../../../../static/img/ok.png" mode=""></image>
								</view>
								<view v-if="item.states==1&&item.state!=1" class="friend-state" :class="{'light-friend-state':them}">
									
								</view>
								<view v-if="item.state==1" class="friend-pay" :class="{'light-friend-pay':them}">
									已支付
								</view>
							</view>
						</block>
					</scroll-view>
				</view>
				<view class="friend-sub" @click="payforFri" :class="{'light-tag-one':them}">
					已选择{{payPeo.length}}位，共{{payNum?payNum:'0'}}元，点击支付
				</view>
			</view>
		</u-popup>
		<!--  -->
		<!-- <u-popup v-model="firendpaytypePop" mode="bottom" closeable="true" height="400rpx">
			<view class="auto">
				<view class="auto-title">
					请选择付款方式
				</view>
				<view class="yue" @click="yuefirendpay">
					<image src="../../../../static/img/yue.png" mode=""></image>
					<view class="">
						账户余额 (剩余：￥{{wallet}})
					</view>
				</view>
				<view class="yue" @click="weixinfirendpay">
					<image src="../../../../static/img/weixin.png" mode=""></image>
					<view class="">
						微信支付
					</view>
				</view>
			</view>
		</u-popup> -->
		<!-- 选择优惠券 -->
		<u-popup v-model="autoPop" mode="bottom" closeable="true" height="500rpx">
			<view class="auto" :class="{'light-paytype':them}">
				<view class="auto-title" :class="{'light-paytype-title':them}">
					请选择付款方式
				</view>
				<!-- <view class="yue" @click="pay">
					<image src="../../../../static/img/yue.png" mode=""></image>
					<view class="">
						账户余额 (剩余：￥{{wallet}})
					</view>
				</view>
				<view class="yue" @click="weixinpay">
					<image src="../../../../static/img/weixin.png" mode=""></image>
					<view class="">
						微信支付
					</view>
				</view> -->
				<view class="paytype-top">
					<view class="paytype-t-l" :class="{'light-title-text':them}">请选则支付方式：</view>
					<view class="paytype-t-r" :class="type==1?'active':''" @click="type=1">
						<view class="t-r-img">
							<image src="../../../../static/img/yue_1.png" mode=""></image>
						</view>
						<view>余额支付</view>
					</view>
					<view class="paytype-t-wx" :class="type==2?'active':''" @click="type=2">
						<view class="t-r-img">
							<image src="../../../../static/img/wx_1.png" mode=""></image>
						</view>
						<view>微信支付</view>
					</view>
				</view>
				<view class="paytype-top">
					<view :class="{'light-title-text':them}">店铺优惠券：</view>
					<view v-if="couponArr.length>0&&!coupon.cid" style="color: #9F9F9F;" @click="choosecoupon">
						选择优惠券<u-icon name="arrow-right" color="#9F9F9F" size="28"></u-icon>
					</view>
					<view style="color: #FFA7B3;" @click="choosecoupon" v-if="coupon.cid&&coupon.type==2">
						{{coupon.num}}折
					</view>
					<view style="color: #FFA7B3;" @click="choosecoupon" v-if="coupon.cid&&(coupon.type==1||coupon.type==3)">
						-{{coupon.num}}
					</view>
					<view v-if="couponArr.length==0" style="color: #9F9F9F;">
						暂无可用优惠券
					</view>
				</view>
				<view class="paytype-pay" @click="payBtn" :class="{'light-tag-one':them}">
					确认支付{{getpaynum}}元
				</view>
			</view>
		</u-popup>
		<u-popup v-model="choosecouponpop" mode="bottom" closeable="true" height="520rpx">
			<view class="coupon" :class="{'light-paytype':them}">
				<view class="coupon-title" :class="{'light-paytype-title':them}">
					请选择优惠券
				</view>
				<view class="coupon-content">
					<scroll-view scroll-y="true" v-if="couponArr.length>0" class="coupon-content" >
						<view class="coupon-one" :class="{'light-coupon-one':them}" v-for="item in couponArr" @click="couponBtn(item)">
							<view :class="{'light-one-title':them}" class="one-title" v-if="item.type==1||item.type==3">满¥{{item.numLimit}}减¥{{item.num}}</view>
							<view :class="{'light-one-title':them}" class="one-title" v-if="item.type==2">{{item.num}}折</view>
							<view :class="{'light-title-text':them}" style="margin-top: 20rpx;" v-if="item.validLimit==1">使用时间：{{item.vtime}}之前</view>
							<view :class="{'light-title-text':them}" style="margin-top: 20rpx;" class="" v-if="item.validLimit==0"> 
								使用时间：不限
							</view>
						</view>
					</scroll-view>
				</view>
				<!-- <view style="text-align: center;color: #fff;font-size: 34rpx;" v-if="couponArr.length==0">
					暂无可用优惠券~
				</view> -->
			</view>
		</u-popup>
	</view>
</template>

<script>
	import{
		getcarDetail,
		getuserremove,
		getuserover,
		getuserstart,
		getdramaScore,
		getdmScore,
		getuserpay,
		getcarye,
		getuserInfo,
		getmultipay,
		getpayuser,//重构支付，微信/余额（单人多人）都用这一个
		getbalancepay,
		getofflinepay,
		getmylistcoupon
	}from '../../../../static/js/api.js'
	let app = getApp()
	export default {
		data() {
			return {
				dataArr:{},
				options:{},
				dmUser:{},
				popShow:false,
				score:'',
				dmScore:'',
				autoPop:false,
				wallet:'',
				juben:true,
				dm:true,
				userpay:true,
				payUser:false,//支付类型（好友/自己）弹窗
				payforfriend:false,//替好友支付弹窗
				list:[],
				firendpaytypePop:false,
				timestamp:86400,
				payPeo:'',
				payNum:'',
				type:'',//微信2余额1
				choosecouponpop:false,
				coupon:{},//优惠券信息
				couponArr:[],
				them:'',
			}
		},
		onReady() {
			app.setColor()
		},
		computed: {
			getpaynum(){
				if(this.payforfriend){
					var numAll = this.payNum
					if(this.coupon.type==2){
						if(this.coupon.selfLimit==1){
							var discountNum = this.dataArr.price * this.coupon.num/10
							return discountNum
						}else{
							var discountNum = numAll * this.coupon.num/10
							return discountNum
						}
					}else{
						if(numAll-this.coupon.num>=0){
							return  numAll-this.coupon.num
						}else if(numAll-this.coupon.num<0){
							  return 0
						}else{
							return numAll  
						}
					}
					
				}else if(this.autoPop){
					if(this.coupon.type==2){
						var discountNum = this.dataArr.price * this.coupon.num/10
						return discountNum
					}else{
						if(this.dataArr.price-this.coupon.num>=0){
							return  this.dataArr.price-this.coupon.num
						}else if(this.dataArr.price-this.coupon.num<0){
							  return 0
						}else{
							return this.dataArr.price  
						}
					}
					
				}
			  
			}
		},
		onPullDownRefresh(){
			this.getList(this.options)
			setTimeout(function () {
				uni.stopPullDownRefresh();
			}, 1000);
		}, 
		onLoad(options){
			var data = {
				id:options.id
			}
			// that.dmUser = uni.getStorageSync('dmUser')
			this.them = uni.getStorageSync('them')
			var that = this
			if(options.isShare){
				app.getlogin().then(res=>{
					var openid = res.openid
					getuserInfo().then(t=>{
						var dmid = t.dmid
						getcarDetail(data).then(r=>{
							var openidArr = []
							r.data.users.map(g=>{
								openidArr.push(g.openid)
							})
							if(openidArr.indexOf(openid)>-1||dmid==r.data.dm.id){
									that.dataArr = r.data
									that.wallet = t.data.wallet
							}else{
								uni.showToast({
									title:'您还没有加入该组局，无法查看',
									icon:'none'
								})
								// setTimeout(function(){
									uni.reLaunch({
										url:'/pages/pin/detail/index?id=' + options.id
									})
								// },1000)
								
							}
						})
						
					})
				})
			}else{
				that.getList(data)
				that.dmUser = uni.getStorageSync('dmUser')
			}
			that.options = data
		},
		onShareAppMessage(){
			var title = this.dataArr.dramaName
			var id = this.dataArr.id
			return {
			    title: title,
			    path: '/pages/user/dm/detail/index?id=' + id + '&isShare=' + true
			}
		},
		methods: {
			payBtn(){
				var that = this
				var ids = []
				that.dataArr.users.map(res=>{
					if(res.states==2&&res.state==0){
						ids.push(res.id)
					}
				})
				ids = ids.join(",")
				var cid = this.coupon.cid||''
				var id = that.dataArr.id
				var type = this.type
				var data = {
					id:id,
					type:type,
					ids:ids,
					cid:cid
				}
				var datas = {
					id:id
				}
				if(type==1){
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
										if(res.code==0){
											uni.showToast({
												title:'支付成功'
											})
											that.getList(datas).then(g=>{
												that.firendpaytypePop = false
												that.payforfriend = false
												that.payUser = false
												that.autoPop = false
											})
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
				}else if(type==2){
					getpayuser(data).then(res=>{
						if(res.code==0){
							uni.requestPayment({
								provider: 'wxpay',
								timeStamp: res.data.timeStamp,
								nonceStr: res.data.nonceStr,
								package: res.data.packageValue,
								signType: res.data.signType,
								paySign: res.data.paySign,
								success: function (r) {
									that.getList(datas).then(g=>{
										that.firendpaytypePop = false
										that.payforfriend = false
										that.payUser = false
										that.autoPop = false
									})
								}
							});
						}
					})
				}else{
					uni.showToast({
						title:'请选择支付方式',
						icon:'none'
					})
				}
			},
			//选中优惠券
			couponBtn(item){
				this.coupon = item
				this.choosecouponpop = false
			},
			//选择优惠券
			choosecoupon(){
				var data = {
					state:0,
					limit:20,
					page:1
				}
				getmylistcoupon(data).then(res=>{
					this.couponArr = res.data.records
					this.choosecouponpop = true
				})
			},
			//dm点击用户余额支付
			balancepay(id){
				var data = {
					id:id
				}
				var datas = {
					id:this.dataArr.id
				}
				var that = this
				uni.showModal({
					title:'确认使用该用户余额支付本次费用吗？',
					success(res) {
						if(res.confirm){
							getbalancepay(data).then(r=>{
								if(r.code==0){
									uni.showToast({
										title:'支付成功！'
									})
									that.getList(datas)
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
			},
			//dm点击用户线下支付
			offlinepay(id){
				var data = {
					id:id
				}
				var datas = {
					id:this.dataArr.id
				}
				var that = this
				uni.showModal({
					title:'确认该用户已经线下支付？',
					success(res) {
						if(res.confirm){
							getofflinepay(data).then(r=>{
								if(r.code==0){
									uni.showToast({
										title:'支付成功！'
									})
									that.getList(datas)
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
			},
			//dm点击结款
			settlement(index,state){
				if(state==0){
					if(this.dataArr.users[index].settle == 1){
						this.dataArr.users.map(res=>{
							res.settle = 1
						})
						this.dataArr.users[index].settle = 2
					}else if(this.dataArr.users[index].settle == 2){
						this.dataArr.users[index].settle = 1
					}
				}
				this.$forceUpdate()
			},
			//使用余额帮好友支付
			yuefirendpay(){
				var that = this
				var id = this.dataArr.id
				var ids = []
				that.dataArr.users.map(res=>{
					if(res.states==2&&res.state==0){
						ids.push(res.id)
					}
				})
				ids = ids.join(",")
				var data = {
					id:id,
					type:1,
					ids:ids
				}
				var datas = {
					id:id
				}
				if(ids){
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
										if(res.code==0){
											uni.showToast({
												title:'支付成功'
											})
											that.getList(datas).then(g=>{
												that.firendpaytypePop = false
												that.payforfriend = false
												that.payUser = false
											})
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
				}else{
					uni.showToast({
						title:'请选择好友',
						icon:'none'
					})
				}
				
			},
			//使用微信帮好友支付
			weixinfirendpay(){
				var that = this
				uni.showLoading({
					title:'加载中'
				})
				var ids = []
				var id = that.dataArr.id
				that.dataArr.users.map(res=>{
					if(res.states==2&&res.state==0){
						ids.push(res.id)
					}
				})
				ids = ids.join(",")
				if(ids){
					var data = {
						ids:ids,
						id:id,
						type:2
					}
					var datas = {
						id:id
					}
					getpayuser(data).then(res=>{
						if(res.code==0){
							uni.requestPayment({
								provider: 'wxpay',
								timeStamp: res.data.timeStamp,
								nonceStr: res.data.nonceStr,
								package: res.data.packageValue,
								signType: res.data.signType,
								paySign: res.data.paySign,
								success: function (r) {
									that.getList(datas).then(g=>{
										that.firendpaytypePop = false
										that.payforfriend = false
										that.payUser = false
									})
									uni.hideLoading({})
								},
								fail: function (err) {
									uni.hideLoading({})
									console.log('fail:' + JSON.stringify(err));
								}
							});
						}
					})
				}else{
					uni.showToast({
						title:'请选择好友',
						icon:'none'
					})
				}
				
			},
			//确认帮好友支付
			payforFri(){
				var ids = []
				var that = this
				that.dataArr.users.map(res=>{
					if(res.states==2&&res.state==0){
						ids.push(res.id)
					}
				})
				ids = ids.join(",")
				if(ids){
					that.autoPop = true
				}else{
					uni.showToast({
						title:'请选择好友',
						icon:'none'
					})
				}
				
			},
			//选择好友
			friendclick(index){
				let t =this;
				if(t.dataArr.users[index].states==1){
					t.dataArr.users[index].states=2
				}else if(t.dataArr.users[index].states==2){
					t.dataArr.users[index].states=1
				}
				var payPeo = []
				t.dataArr.users.map(res=>{
					if(res.states==2&&res.state==0){
						payPeo.push(res.id)
					}
				})
				var payNum = payPeo.length*t.dataArr.price
				t.payPeo = payPeo
				t.payNum = payNum
				this.$forceUpdate()
			},
			//为自己支付
			paySelf(){
				this.autoPop = true
			},
			//支付弹窗
			paypop(){
				// this.autoPop = true
				this.payUser = true
			},
			//为自己微信支付
			weixinpay(){
				uni.showLoading({
					title:'加载中'
				})
				var that = this
				var ids = ''
				var id = that.dataArr.id
				var type = this.type
				var data = {
					id:id,
					type:type,
					ids:ids
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
								that.getList(data).then(g=>{
									that.autoPop = false
									that.userpay = false
									that.payUser = false
								})
								uni.hideLoading({})
							},
							fail: function (err) {
								uni.hideLoading({})
								console.log('fail:' + JSON.stringify(err));
							}
						});
					}
				})
				
			},
			//为自己余额支付
			pay(){
				var that = this
				var id = this.dataArr.id
				var ids = ''
				var type = this.type
				var data = {
					id:id,
					type:type,
					ids:ids
					
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
									if(res.code==0){
										uni.showToast({
											title:'支付成功'
										})
										that.getList(data).then(g=>{
											that.autoPop = false
											that.userpay = false
											that.payUser = false
										})
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
			//评价剧本
			scoreBtn(){
				var score = this.score * 2
				var data = {
					id:this.dataArr.sdid,
					score:score,
					dmid:this.dmUser.dmid
				}
				var dmScore = this.dmScore * 2
				var datas = {
					score:dmScore,
					dmid:this.dataArr.dm.id
				}
				if(score>0&&dmScore>0){
					getdmScore(datas).then(res=>{
						if(res.code==0){
							uni.showToast({
								title:'DM评价成功'
							})
							this.popShow = false
							this.dmScore = 0
							this.juben = false
						}else{
							uni.showToast({
								title:res.msg,
								icon:'none'
							})
						}
					})
					getdramaScore(data).then(res=>{
						if(res.code==0){
							uni.showToast({
								title:'剧本评价成功'
							})
							this.popShow = false
							this.score = 0
							this.dm = false
						}else{
							uni.showToast({
								title:res.msg,
								icon:'none'
							})
						}
					})
				}else if(dmScore<=0){
					uni.showToast({
						title:'请给主持人评分',
						icon:'none'
					})
				}else{
					uni.showToast({
						title:'请给剧本评分',
						icon:'none'
					})
				}
			},
			evaluatePop(){
				this.popShow = true
			},
			getList(options){
				uni.showLoading({
					title:'加载中'
				})
				return new Promise((resolve,rej) =>{
					getcarDetail(options).then(res=>{
						console.log(res.data)
						this.dataArr = res.data
						this.timestamp = res.data.currentTimmeMillis
						res.data.users.map(g=>{
							g.states = 1
							g.settle = 1
						})
						this.list = res.data.users
						resolve()
						uni.hideLoading({})
					})
					var data = {
						state:0,
						limit:20,
						page:1
					}
					getmylistcoupon(data).then(res=>{
						this.couponArr = res.data.records
						uni.hideLoading({})
					})
					getuserInfo().then(res=>{
						this.wallet = res.data.wallet
						uni.hideLoading({})
					})
				})
			},
			//开局
			startUser(){
				if(this.dataArr.users.length>0){
					var that = this
					var options = this.options
					uni.showModal({
						content:'请确保所有人员已进入组局中，未进入的无法缴费',
						success:function(res){
							if(res.confirm){
								getuserstart(options).then(res=>{
									if(res.code==0){
										uni.showToast({
											title:'成功',
											icon:'none'
										})
										setTimeout(function(){
											that.getList(options)
										},400)
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
				}else{
					uni.showToast({
						title:'暂无组局人员，不能开局',
						icon:'none'
					})
				}
			},
			//解散
			userOver(){
				var options = this.options
				uni.showModal({
					content:'是否确认解散，如有成员已支付，将原路退还',
					success:function(res){
						if(res.confirm){
							getuserover(options).then(res=>{
								if(res.code==0){
									uni.showToast({
										title:'解散成功,即将跳转，请下拉刷新',
										icon:'none'
									})
									setTimeout(function(){
										/* uni.redirectTo({
											url:'../myPlay/index'
										}) */
										uni.navigateBack({
											
										})
									},1000)
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
			},
			//移除组员
			removeUser(id){
				var data ={
					id:id
				}
				var that = this
				var options = this.options
				uni.showModal({
					content:'确认移除该组员吗,如该成员已支付，将原路退还',
					success:function(res){
						if(res.confirm){
							getuserremove(data).then(res=>{
								if(res.code==0){
									uni.showToast({
										title:'移除成功',
										icon:'none'
									})
									setTimeout(function(){
										that.getList(options)
									},200)
								}else{
									uni.showToast({
										title:res.msg,
										icon:'none'
									})
								}
							})
						}
					},
				})
			},
		}
	}
</script>

<style scoped lang="scss" src="./index.scss"></style>
