<template>
	<view style="background-color: #f0f0f3;height: 100%;">
		<view class="index" v-if="shenqing">
		  <view class="index-tab">
		    <view @click="info" class="tab-left" :class="{'active':flag==true}" >Step 1 完善资料</view>
		    <view @click="creat" class="tab-right" :class="{'active':flag==false}">Step 2 创建小程序</view>
		  </view>
		  <view class="index-line"></view>
		  <template v-if="flag">
		  <view class="" style="height: 1380rpx;background: #f0f0f3;">
		    <view class="index-item">
		      <view class="item-left">
		        <image src="../../static/img/info.png"></image>
		      </view>
		      <view class="item-right">
		        <!-- <input type="text" bindinput="inputValue" bindblur="getcompany" placeholder="请输入营业执照名称" placeholder-style="color:#B1B3B6" ></input> -->
				<u-input v-model="comName" @blur="nameblur" placeholder="请输入营业执照名称" placeholder-style="color:#B1B3B6" :custom-style="inputStyle" :clearable="false" />
				<!-- <u-field
					v-model="comName"
					placeholder="请输入营业执照名称" 
					placeholder-style="color:#B1B3B6"
				>
				</u-field> -->
		      </view>
		    </view>
		    <view class="index-item">
		      <view class="item-left">
		        <image src="../../static/img/wx.png"></image>
		      </view>
		      <view class="item-right">
		        <!-- <input type="text" placeholder="请输入法人微信号" placeholder-style="color:#B1B3B6" ></input> -->
				<u-input v-model="wx" @blur="wxblur" placeholder="请输入法人微信号" placeholder-style="color:#B1B3B6" :custom-style="inputStyle" :clearable="false" />
		      </view>
		    </view>
		    <view class="index-item">
		      <view class="item-left">
		        <image src="../../static/img/phone.png"></image>
		      </view>
		      <view class="item-right btn">
		        <!-- <input type="text" placeholder="请输门店电话" placeholder-style="color:#B1B3B6" ></input> -->
				<u-input v-model="phone" @blur="wxblur" placeholder="请输门店电话" placeholder-style="color:#B1B3B6" :custom-style="inputStyle" :clearable="false" />
		      </view>
		      <view class="item-btn">
		        <button open-type="getPhoneNumber" @getphonenumber="getphonenum">点击获取</button>
		      </view>
		    </view>
		    <view class="follow">
		      <view class="follow-item">  
		        以下内容请关注「公众平台安全助手」
		      </view>
		    </view>
		    <view class="step">
		      <view class="step-top">
		        <view class="step-t-l">1</view>
		        <view class="step-t-r">复制名称【公众平台安全助手】</view>
				<view class="step-t-ok" v-if="stepok1">
					<image src="../../static/img/stepok.png" mode=""></image>
				</view>
		      </view>
		      <view class="step-bot" @click="copy">点击复制</view>
		    </view>
		    <view class="step">
		      <view class="step-top">
		        <view class="step-t-l">2</view>
		        <view class="step-t-r">打开微信关注【公众平台安全助手】</view>
				<view class="step-t-ok" v-if="stepok2">
					<image src="../../static/img/stepok.png" mode=""></image>
				</view>
		      </view>
		      <view class="step-bot w168" @click="follow">确认已关注</view>
		    </view>
		    <view class="step">
		      <view class="step-top">
		        <view class="step-t-l">3</view>
		        <view class="step-t-r">点击发送申请进行人脸识别认证</view>
				<view class="step-t-ok" v-if="stepok3" style="margin-left: 20rpx;">
					<image src="../../static/img/stepok.png" mode=""></image>
				</view>
		      </view>
		      <view class="step-bot" @click="send">发送申请</view>
		    </view>
		    <view class="step end">
		      <view class="step-top">
		        <view class="step-t-l">4</view>
		        <view class="step-t-r">进入【公众平台安全助手】确认认证信息</view>
		      </view>
		    </view>
		    <view class="sub" v-if="step">
				<view class="" @click="sub">
					下一步
				</view>
		    </view>
			</view>
		  </template>
		  
		  <template v-if="!flag">
			 
		    <view class="index-item">
		      <view class="item-left image">
		        <image src="../../static/img/name.png"></image>
		      </view>
		      <view class="item-right">
		        <!-- <input type="text" placeholder="请输入小程序名称" placeholder-style="color:#B1B3B6" ></input> -->
				<u-input v-model="mpName" placeholder="请输入小程序名称" placeholder-style="color:#B1B3B6" :custom-style="inputStyle" :clearable="false" />
		      </view>
		    </view>
		    <view class="index-upload">
		      <!-- <van-uploader> -->
		        <view class="upload-icon" @click="uploadlogo">
		          <view class="icon-text">
		            +
		          </view>
		        </view>
		      <!-- </van-uploader> -->
		      <view class="upload-r">
		        <view class="r-top">
		          点击+上传
		        </view>
		        <view class="r-top">
		          小程序LOGO
		        </view>
		      </view>
			  <view class="upload-img" v-if="logoImg">
			  	<image class="img-top" :src="logoImg" mode=""></image>
				<view class="upload-close" @click="close(1)">
					<image src="../../static/img/close.png" mode=""></image>
				</view>
			  </view>
		    </view>
		    <view class="index-upload">
		      <!-- <van-uploader> -->
		        <view class="upload-icon" @click="uploadlicense">
		          <view class="icon-text">
		            +
		          </view>
		        </view>
		      <!-- </van-uploader> -->
		      <view class="upload-r">
		        <view class="r-top">
		          点击+上传
		        </view>
		        <view class="r-top">
		          营业执照
		        </view>
		      </view>
			  <view class="upload-img" v-if="businessImg">
			  	<image class="img-top" :src="businessImg" mode=""></image>
					<view class="upload-close" @click="close(2)">
						<image src="../../static/img/close.png" mode=""></image>
					</view>
			  </view>
		    </view>
			<view v-if="creatview" class="index-shenqing">
				<view class="shouquan-l">点击获取授权二维码</view>
				<view class="shouquan-r" @click="auth">获取授权二维码</view>
			</view>
			<view v-if="creatbtn" class="upload-sub" @click="creatsub">
				创建小程序				
			</view>
			<u-popup v-model="saveimg" mode="center" border-radius="50" width="620rpx" height="740rpx" mask-custom-style="imgStyle">
				<view class="saveimg">
					<image class="imgCode" :src="qrcodeURL" @longpress="saveimgs" mode=""></image>
					<view class="img-text">长按保存二维码到本地</view>
					<view class="img-text">
						并使用微信识别授权
					</view>
					
				</view>
			</u-popup>
		  </template>
		</view>



		
		
		<view class="wait" v-if="wait">
			<view class="wait-img">
        <!-- TODO 线上图片 -->
				<image src="https://pic.hlt.hnpinxun.cn/jubensha/wait.png" mode=""></image>
			</view>
			<view class="wait-text">
				<view style="margin-bottom: 20rpx;">申请成功～</view>
				<view>请耐心等待审核！</view>
			</view>
			<view class="wait-bot">
				预计1至2个工作日内完成审核
			</view>
		</view>


		<view class="success" v-if="success">
			<view class="success-img">
				<image src="https://pic.hlt.hnpinxun.cn/jubensha/success.png" mode=""></image>
			</view>
			<view class="success-text">
				<view style="margin-bottom: 20rpx;">恭喜您创建成功！</view>
				<view>点击下方按钮进入后台配置小程序</view>
			</view>
			<view class="success-user">
				<view style="margin-bottom: 20rpx;">登录账号：12787244421</view>
				<view>默认密码：123456</view>
			</view>
			<view class="success-btn" @click="navigateTo">
				点击进入谜局管理端小程序
			</view>
		</view>
		
	</view>
</template>

<script>
	import{
		getCompany,
		getwxsave,
		getphone,
		getInfo,
		getsend,
		getusercreate,
		CommonUpload,
		getauthurl
	}from '../../static/js/api.js'
	let qrcode = require("../../utils/weapp-qrcode.js");
	export default {
		data() {
			return {
				flag:true,
				comName:'', //营业执照名称
				phone:'',
				wx:'',
				mpName:'',
				inputStyle:{
					width:'400rpx',
					height: '74rpx',
					lineHeight: '74rpx',
					color: '#808B9F',
				},
				errcode:'',
				appid:'',
				step:false,
				logoImg:'',
				businessImg:'',
				// creatview:true,
				creatview:false,
				creatbtn:true,
				shenqing:true,
				wait:false,
				success:false,
				stepok1:false,
				stepok2:false,
				stepok3:false,
				company:false,
				companymsg:'',
				saveimg:false,
				qrcodeURL:'',//二维码路径
				imgStyle:{
					// box-shadow: '20rpx 20rpx 60rpx 0rpx rgba(174,174,192,0.50), -20rpx -20rpx 60rpx 0rpx #ffffff'; 
					background: 'rgba(0, 0, 0, 0.5)'
				},
				id:'',
			}
		},
		onLoad() {
			// this.getinfo()
			// console.log('onload')
		},
		onShow(){
			this.getinfo()
			console.log('onShow')
			this.stepok1 = uni.getStorageSync('stepok1')
			this.stepok2 = uni.getStorageSync('stepok2')
			this.stepok3 = uni.getStorageSync('stepok3')
			this.company = uni.getStorageSync('company')||false
			this.companymsg = uni.getStorageSync('companymsg')||''
		},
		/* onPullDownRefresh(){
			this.getinfo()
			setTimeout(function () {
				uni.stopPullDownRefresh();
			}, 1000);
		}, */
		methods: {

      // TODO 需要跳转的小程序appid
			navigateTo(){
				uni.navigateToMiniProgram({
				  appId: 'wx****************',
				  success(res) {
				    // 打开成功
				  }
				})
			},
			info(){
			    this.flag = true
			},
			//navbar第二步按钮
			creat(){
				// this.flag = false
				if(this.errcode==0&&this.appid){
					this.flag = false
					this.getinfo()
				}else{
					uni.showToast({
						title:'请等待信息审核完毕方可进行下一步',
						icon:'none',
						duration:4000
					})
				}
			},
			//创建小程序按钮
			creatsub(){
				var data = {
					appid:this.appid,
					mpName:this.mpName,
					mpLogo:this.logoImg,
					business:this.businessImg
				}
				var that = this
				getusercreate(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'创建成功,请点击授权'
						})
						// uni.setStorageSync('creatview',true)
						// uni.setStorageSync('creatbtn',false)
						that.creatview = true //授权链接
						that.creatbtn = false //创建按钮
					}else{
						// uni.showToast({
						// 	title:res.msg,
						// 	icon:'none'
						// })
					}
				}).catch(error=>{
				})
			},
			//授权按钮复制链接
			auth(){
				uni.showLoading({
					title:'加载中'
				})
				var data = {
					id:this.id
				}
				getauthurl(data).then(res=>{
					if(res.code==0){
						this.saveimg = true
						this.getCode(res.data)
						uni.hideLoading({})
						// uni.setClipboardData({
						//     data: res.data,
						//     success: function () {
						// 		uni.showToast({
						// 			title:'复制成功'
						// 		})
						//     }
						// });
					}else{
						uni.showToast({
							title:res.msg,
							icon:'none',
							duration:4000
						})
					}
				})
			},
			//链接转二维码
			getCode(params) {
				// let params = 'https://www.baidu.com/'; // 二维码参数
				var imgData = qrcode.drawImg(params, {
					typeNumber: 4, // 密度
					errorCorrectLevel: 'L', // 纠错等级
					size: 800, // 白色边框
				})
				this.qrcodeURL = imgData
			},
			//保存图片到本地
			saveimgs() {
				uni.showActionSheet({
					itemList: ['保存到相册'],
					success: res => {
						if (res.tapIndex == 0) {
							console.log(this.qrcodeURL) //
							let base64 = this.qrcodeURL.replace(/^data:image\/\w+;base64,/,
							""); //去掉data:image/png;base64,
							let filePath = wx.env.USER_DATA_PATH + '/hym_pay_qrcode.png';
							var save = uni.getFileSystemManager();
							var number = Math.random();
							save.writeFile({
								filePath: filePath,
								data: base64,
								encoding: 'base64',
								success: res => {
									console.log(669699, res)
									uni.saveImageToPhotosAlbum({
										filePath: filePath,
										success: function(res) {
											uni.showToast({
												title: '保存成功',
												icon: "success",
												duration: 1000
											})
										},
										fail: function(err) {
											uni.showToast({
												title: '保存失败',
												icon: "success",
												duration: 1000
											})
											console.log(err)
										}
									})
									console.log(res)
								},
								fail: err => {
									console.log(err)
								}
							})
			
						}
					}
				});
			},
			close(e){
				var that = this
				uni.showModal({
					title: '提示',
					content:'确认移除图片',
					success: function (res) {
						if (res.confirm) {
							if(e==1){
								that.logoImg = ''
							}
							if(e==2){
								that.businessImg = ''
							}
						} else if (res.cancel) {
							
						}
					}
				})
				
			},
			uploadlogo(){
				var that = this
				CommonUpload().then(res=>{
					console.log(res)
					that.logoImg = res
					// that.mpnameblur()
				})
			},
			uploadlicense(){
				var that = this
				CommonUpload().then(res=>{
					console.log(res)
					that.businessImg = res
					// that.mpnameblur()
				})
			},
			sub(){
				this.flag = false
				this.getinfo()
			},
			//获取用户填写信息及状态
			getinfo(){
				uni.showLoading({
					title:'加载中'
				})
				var that = this
				getInfo().then(res=>{
					console.log(res.data)
					if(res.code==0){
						if(res.data){
							that.id = res.data.id
							var waitstate = '授权成功'
							var successstate = '发布成功'
							var errmsg = res.data.errmsg
							if(errmsg==waitstate){
								that.shenqing = false
								that.wait = true
								// that.success = true
							}
							if(errmsg==successstate){
								that.shenqing = false
								// that.wait = true
								that.success = true
							}
							that.comName = res.data.name
							if(res.data.name){
								uni.setStorageSync('company',true)
							}
							if(errmsg){
								uni.setStorageSync('companymsg',errmsg)
							}
							that.wx = res.data.wx
							that.phone = res.data.phone
							that.errcode = res.data.errcode
							that.appid = res.data.appid
							if(res.data.errcode==0&&res.data.appid){
								that.step = true
							}else{
								uni.showToast({
									title:res.data.errmsg,
									icon:'none',
									duration:4000
								})
							}
							if(res.data.mpName&&res.data.mpLogo&&res.data.business){
								that.creatview = true
								that.creatbtn = false
							}else{
								that.creatview = false
								that.creatbtn = true
							}
							if(res.data.errcode!=0){
								uni.showToast({
									title:res.data.errmsg,
									icon:'none',
									duration:4000
								})
							}
							uni.hideLoading({})
						}else{
							uni.hideLoading({})
						}
					}
					
				})
			},
			//发送人脸验证
			send(){
				var that = this
				var company = this.company
				var companymsg = this.companymsg
				var wx = that.wx
				var phone = that.phone
				if(company){
					if(wx){
						if(phone){
							getsend().then(r=>{
								if(r.code==0){
									uni.showToast({
										title:'发送成功',
										icon:"none"
									})
									that.stepok3 = true
									uni.setStorageSync('stepok3',true)
									getInfo().then(res=>{
										if(res.data.errcode==0&&res.data.appid){
											that.step = true
											that.errcode = res.data.errcode
											that.appid = res.data.appid
										}
									})
								}else{
									uni.showToast({
										title:res.msg,
										icon:"none",
										duration:4000
									})
								}
							})
						}else{
							uni.showToast({
								title:'请输入手机号',
								icon:"none",
								duration:4000
							})
						}
					}else{
						uni.showToast({
							title:'请输入微信号',
							icon:"none",
							duration:4000
						})
					}
				}else{
					uni.showToast({
						title:companymsg,
						icon:"none",
						duration:4000
					})
				}
				
			},
			//营业执照名称失焦
			nameblur(){
				var that = this
				uni.showLoading({
					title:'加载中'
				})
				var data = {
					name:that.comName
				}
				getCompany(data).then(res=>{
					if(res.code==0){
						that.company = true
						uni.setStorageSync('company',true)
						uni.showToast({
							title:'公司名称验证成功',
							icon:"none"
						})
						uni.hideLoading({})
					}else{
						uni.showToast({
							title:res.msg,
							icon:"none",
							duration:4000
						})
						uni.hideLoading({})
					}
				}).catch(error=>{
					that.company = false
					uni.setStorageSync('company',false)
					that.companymsg = error.msg
					uni.setStorageSync('companymsg',error.msg)
				})
			},
			//微信失焦
			wxblur(){
				if(this.wx.length==0)
				{
					uni.showToast({
						title:'请输入微信号',
						icon:'none',
						duration:4000
					})
					return
				}else if(this.phone.length==0)
				{
					uni.showToast({
						title:'请输入门店电话',
						icon:'none',
						duration:4000
					})
					return
				}else if (!/^1[3456789]\d{9}$/.test(this.phone))
				{
					uni.showToast({
						title:'电话格式不正确',
						icon:'none',
						duration:4000
					})
					return
				}

				uni.showLoading({
					title:'加载中'
				})
				var data = {
					phone:this.phone||"",
					wx:this.wx||"",
					productId:3,
				}
				getwxsave(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'验证成功',
							icon:"none"
						})
						uni.hideLoading({})
					}else{
						uni.showToast({
							title:res.msg,
							icon:"none",
							duration:4000
						})
						uni.hideLoading({})
					}
				})
			},
			//自动获取手机号
			getphonenum(e){
				uni.showLoading({
					title:'加载中'
				})
				var that = this
				var iv = e.detail.iv
				var encryptedData = e.detail.encryptedData
				uni.login({
					success:function(g){
						var data = {
							iv :iv,
							encryptedData :encryptedData,
							code :g.code
						}
						getphone(data).then(res=>{
							if(res.code==0){
								console.log(res.data)
								that.phone = res.data
								uni.showToast({
									title:'成功',
									icon:"none"
								})
								uni.hideLoading({})
							}else{
								uni.showToast({
									title:res.msg,
									icon:"none",
									duration:4000
								})
								uni.hideLoading({})
							}
						})
					}
				})
			},
			copy(){
				var that = this
				uni.setClipboardData({
				    data: '公众平台安全助手',
				    success: function () {
						uni.showToast({
							title:'复制成功'
						})
						that.stepok1 = true
						uni.setStorageSync('stepok1',true)
				    }
				});
			},
			follow(){
				var that = this
				uni.showModal({
					title:'提示',
					content:'确认已经关注公众平台安全助手？',
					success: function (res) {
						if (res.confirm) {
							console.log('用户点击确定');
							that.stepok2 = true
							uni.setStorageSync('stepok2',true)
						} else if (res.cancel) {
							
						}
					}
				})
			},
			
		}
	}
</script>

<style scoped lang="scss" src="./index.scss"></style>
