<template>
	<view>
		<view style="color: #fff;" class="" v-for="item in dataArr" @click="receive(item.id)">
			{{item.title}}
		</view>
	</view>
</template>

<script>
	import{
		getlistcoupon,
		getreceivecoupon
	}from '../../../static/js/api.js'
	export default {
		data() {
			return {
				dataArr:[],
			}
		},
		onLoad() {
			this.getlist()
		},
		methods: {
			getlist(){
				var data = {
					limit:10,
					page:1,
				}
				getlistcoupon(data).then(res=>{
					console.log(res.data)
					this.dataArr = res.data.records
				})
			},
			receive(id){
				var data = {
					id:id
				}
				getreceivecoupon(data).then(res=>{
					if(res.code==0){
						uni.showToast({
							title:'领取成功'
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
	}
</script>

<style>

</style>
