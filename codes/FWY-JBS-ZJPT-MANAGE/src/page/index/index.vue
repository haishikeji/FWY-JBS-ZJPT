<template>
	<div class="avue-contail" :class="{ 'avue--collapse': isCollapse }">
		<div class="avue-header">
			<!-- 顶部导航栏 -->
			<top/>
		</div>

		<div v-if="pxPage == 1" class="avue-layout">
			<div class="avue-left">
				<!-- 左侧导航栏 -->
				<sidebar/>
			</div>
			<div class="avue-main">
				<!-- 顶部标签卡 -->
				<tags/>
				<!-- 主体视图层 -->
				<el-scrollbar style="height: 100%">
					<keep-alive>
						<router-view class="avue-view" v-if="$route.meta.$keepAlive"/>
					</keep-alive>
					<router-view class="avue-view" v-if="!$route.meta.$keepAlive"/>
				</el-scrollbar>
			</div>
		</div>


		<div v-if="pxPage == 2" class="avue-layout">
			<!-- <div class="avue-header">
			  <top />
			</div> -->
			<div class="avue-left2">
				<sidebar/>
			</div>
			<!-- 顶部标签卡 -->
			<!-- <tags /> -->
			<!-- 主体视图层 -->
			<div style="margin-top:10px;" class="avue-main avue-main-2">
				<el-scrollbar>
					<keep-alive>
						<router-view
							class="avue-view"
							v-if="$route.meta.$keepAlive"
							@change_leftFlag="change_leftFlag($event)"
						/>
					</keep-alive>
					<router-view
						class="avue-view"
						v-if="!$route.meta.$keepAlive"
						@change_leftFlag="change_leftFlag($event)"
					/>
				</el-scrollbar>
			</div>
		</div>


		<div class="avue-shade" @click="showCollapse"></div>
	</div>
</template>
<script>
import {mapGetters} from 'vuex'
import tags from './tags'
import top from './top/'
import sidebar from './sidebar/'
import shSidebar from './sidebar-sh'
import admin from '@/util/admin';
import {validatenull} from '@/util/validate';
import {calcDate} from '@/util/date.js';
import {getStore} from '@/util/store.js';

var pageDjs;
export default {
	components: {
		top,
		tags,
		sidebar,
		shSidebar
	},
	name: 'index',
	data() {
		return {
			//刷新token锁
			refreshLock: false,
			//刷新token的时间
			refreshTime: '',
			pxPage: 1
		}
	},
	created() {

	},
	destroyed() {
		clearInterval(this.refreshTime)
		clearInterval(pageDjs)
	},
	mounted() {
		// this.pxPage = sessionStorage.getItem('px-page')
		this.init()
	},
	computed: mapGetters(['userInfo', 'isLock', 'isCollapse', 'website', 'expires_in']),
	props: [],
	methods: {
		showCollapse() {
			this.$store.commit("SET_COLLAPSE")
		},
		// 屏幕检测
		init() {
			this.$store.commit('SET_SCREEN', admin.getScreen())
			window.onresize = () => {
				setTimeout(() => {
					this.$store.commit('SET_SCREEN', admin.getScreen())
				}, 0);
			}
		},
	}
}
</script>
