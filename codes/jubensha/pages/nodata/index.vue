<template>
	<view class="nodata" :class="{'light-index':them}">
		<image v-if="!them" src="../../static/img/nofun.png" mode=""></image>
		<image v-if="them" src="../../static/img/nofun_l.png" mode=""></image>
		<view class="" :class="{'light-index-fff':them}">
			正在开发上线～
		</view>
	</view>
</template>

<script>
	let app = getApp()
	export default {
		data() {
			return {
				them:''
			}
		},
		onReady() {
			app.setColor()
		},
		onLoad(options) {
			this.them = uni.getStorageSync('them')
		},
		methods: {
			
		}
	}
</script>
<style scoped lang="scss" src="./index.scss"></style>
