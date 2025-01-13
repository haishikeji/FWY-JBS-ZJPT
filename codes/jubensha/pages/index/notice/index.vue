<template>
	<view class="list" :class="{'light-index':them}">
		<view class="item" v-for="item in notices" :class="{'light-item':them}">
			<view class="content" :class="{'light-content':them}">{{item.content}}</view>
			<view class="time">
				<view class="time-left">
					<view class="left-left">
						<image v-if="!them" src="../../../static/img/notice.png" mode=""></image>
						<image v-if="them" src="../../../static/img/notice_l.png" mode=""></image>
					</view>
					<view :class="{'light-333':them}">通知信息</view>
				</view>
				<view class="time-right" :class="{'light-333':them}">
					{{item.createTime}}
				</view>
			</view>	
		</view>
	</view>
</template>

<script>
	import{
		getnotices
	}from '../../../static/js/api.js'
	let app = getApp()
	export default {
		components: {
			// 注册
		},
		
		data() {
			return {
				notices:[],
				page:1,
				hsmore:false,
				them:''
			};
		},
		onReady() {
			app.setColor()
		},
		onLoad() {
			this.getList()
			this.them = uni.getStorageSync('them')
		},
		onReachBottom(){
			if(this.hsmore){
				this.page++
				this.getList()
			}else{
				
			}
		},
		methods: {
			getList(){
				var data = {
					page:this.page,
					limit:10
				}
				getnotices(data).then(res=>{
					if(res.code==0){
						if(res.data.records.length==10){
							this.hsmore = true
						}else{
							this.hsmore = false
						}
						this.notices = this.notices.concat(res.data.records)
					}else{
						uni.showToast({
							title:res.msg,
							icon:'none'
						}) 
					}
				})
			}
		}
	};
	
	
</script>
<style scoped lang="scss" src="./index.scss"></style>
