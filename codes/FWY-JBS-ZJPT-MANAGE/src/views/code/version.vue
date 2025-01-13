<template>
  <div class="versionBox">
    <basic-container>
      <h2>版本管理</h2>
      <div class="online box">
        <h4>线上版本</h4>
        <div class="list" v-if="xs && xs.length > 0">
          <div class="item" v-for="(item, index) in xs" :key="index">
            <div class="left">
              <label>版本号</label>
              <p>{{ item.appVersion }}</p>
            </div>
            <div class="middle">
              <div class="moddle-item">
                <label>发布时间</label>
                <p>{{ item.endTime }}</p>
              </div>
              <div class="moddle-item">
                <label>项目备注</label>
                <p>{{ item.appVersionDesc }}</p>
              </div>
            </div>
            <div class="right">
              <el-button :disabled="btnDisabled" type="success"
                >版本回退</el-button
              >
            </div>
          </div>
        </div>
        <div class="empty_box" v-else>
          <p class="empty_tips">您暂无提交线上的版本</p>
        </div>
      </div>
      <div class="audit box">
        <h4>审核版本</h4>
        <div class="list" v-if="sh && sh.length > 0">
          <div class="item" v-for="(item, index) in sh" :key="index">
            <div class="left">
              <label>版本号</label>
              <p>{{ item.appVersion }}</p>
              <el-tag v-if="item.status == 1" type="warning" effect="plain">
                待提交审核
              </el-tag>
              <el-tag v-if="item.status == 2" type="warning" effect="plain">
                审核中
              </el-tag>
              <el-tag v-if="item.status == 3" type="warning" effect="plain">
                审核通过
              </el-tag>
              <el-tag v-if="item.status == 4" type="warning" effect="plain">
                审核未通过
              </el-tag>
            </div>
            <div class="middle">
              <div class="moddle-item">
                <label>提交审核时间</label>
                <p>{{ item.endTime }}</p>
              </div>
              <div class="moddle-item">
                <label>项目备注</label>
                <p>{{ item.appVersionDesc }}</p>
              </div>
            </div>
            <div class="right">
              <el-button
                v-if="item.status == 1"
                type="success"
                :disabled="btnDisabled"
                @click="submit(item.id)"
                >提交审核</el-button
              >
              <el-button
                v-if="item.status == 2"
                @click="withdrawn(item.id)"
                type="success"
                :disabled="btnDisabled"
                >撤回审核</el-button
              >
              <el-button
                v-if="item.status == 3"
                type="success"
                :disabled="btnDisabled"
                @click="release(item.id)"
                >上传发布</el-button
              >
              <el-button
                v-if="item.status == 4"
                :disabled="btnDisabled"
                type="success"
                >删除</el-button
              >
            </div>
          </div>
        </div>
        <div class="empty_box" v-else>
          <p class="empty_tips">您暂无提交审核的版本或者版本已发布上线</p>
        </div>
      </div>
      <div class="development box">
        <h4>开发版本</h4>
        <div class="list" v-if="kf && kf.length > 0">
          <div class="item" v-for="(item, index) in kf" :key="index">
            <div class="left">
              <label>版本号</label>
              <p>{{ item.appVersion }}</p>
              <div class="cursor tyb" >
                <el-button
                  type="primary"
                  plain
                  size="mini"
                  :disabled="btnDisabled"
                  :data-id="item.id"
                  @click.stop="getTyb(item)"
                >
                  体验版 <i class="smallerwei"></i>
                </el-button>
              </div>
            </div>
            <div class="middle">
              <div class="moddle-item">
                <label>提交时间</label>
                <p>{{ item.createTime }}</p>
              </div>
              <div class="moddle-item">
                <label>项目备注</label>
                <p>{{ item.appVersionDesc }}</p>
              </div>
            </div>
            <div class="right">
              <!-- <el-button
                :disabled="btnDisabled"
                @click="(item.id)"
                type="success"
                >上传代码</el-button
              > -->
              <!-- <div class="menu">
                <el-dropdown type="success" trigger="click">
                  <span class="el-dropdown-link"
                    ><i class="el-icon-arrow-down el-icon--right"></i
                  ></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>取消体验</el-dropdown-item>
                    <el-dropdown-item>选为体验版本</el-dropdown-item>
                    <el-dropdown-item>修改页面路径</el-dropdown-item>
                    <el-dropdown-item>删除</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div> -->
            </div>
          </div>
        </div>
        <div class="empty_box" v-else>
          <p class="empty_tips">您暂无提交开发的版本</p>
        </div>
      </div>
    </basic-container>
  </div>
</template>

<style scoped lang="scss" src="./scss/version.scss"></style>
<script>
import { codeTaskByAppid, ExperienceQrCode, release, undocodeAudit, submitAudit } from '@/api/version'
import { getObj } from '@/api/codetaskexample'

export default {
  name: 'version',
  data() {
    return {
      kf: [],
      sh: [],
      xs: [],
      btnDisabled: false,
      path: null,  //页面路径



    }
  },
  mounted() {
    // console.log('-----------------------')
    this.getData();
  },
  computed: {

  },

  methods: {
    getData() {
      let vm = this;
      if (vm.$route.query.appid) {
        codeTaskByAppid({
          appid: vm.$route.query.appid
        }).then(res => {
          vm.kf = [];
          vm.sh = [];
          vm.xs = [];
          var r = res.data;
          console.log(r);
          if (r.data.length > 0) {
            r.data.forEach(i => {
              vm.kf.push(i);
              if (i.useStatus == 2) {
                vm.xs.push(i);
              }
              if (i.useStatus == 1 && (i.status == 1 || i.status == 2 || i.status == 3 && i.status == 4)) {
                vm.sh.push(i);
              }

            })

          }

        })
      }
    },

    // 获取体验版二维码
    getTyb(e) {
      console.log(e.id);
      let vm = this;
      vm.btnDisabled = true;
      ExperienceQrCode({
        id: e.id,path:''
      }).then(res => {
        if (res.data.data) {
          vm.$alert('<div style="text-align:center;padding:50px"><img style="width:150px;" src="' + res.data.data + '" alt=""></div>', '体验版二维码', {
            dangerouslyUseHTMLString: true
          });
        }
        vm.btnDisabled = false;
      }).catch(r => {
        vm.btnDisabled = false;
      })
    },

    // 提交审核
    submit(id) {
      let vm = this;
      getObj(id).then(r => {
        // vm.submit2(id, r.data.data.page)
        vm.submit2(id, 111)
      }).catch(r => {
        vm.submit2(id)
      })

    },
    submit2(id, path) {
      let vm = this;
      this.$prompt('请输入页面路径', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: path
      }).then(({ value }) => {
        vm.btnDisabled = true;
        submitAudit({
          id: id,
          path: value,
        }).then(res => {
          vm.btnDisabled = false;
          this.$message.success("提交审核成功");
          this.getData();
        }).catch(r => {
          vm.btnDisabled = false;
        })
        // this.$message({
        //   type: 'success',
        //   message: '你的邮箱是: ' + value
        // });
      }).catch(() => {

      });
    },

    // 撤回审核
    withdrawn(id) {
      let vm = this;
      vm.$confirm("是否确认撤回审核", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        vm.btnDisabled = true;
        undocodeAudit({
          id: id
        }).then(res => {
          if (res.data.data) {
            vm.$alert('<div style="text-align:center;padding:50px"><img style="width:150px;" src="' + res.data.data + '" alt=""></div>', '体验版二维码', {
              dangerouslyUseHTMLString: true
            });
          }

          this.$message.success("撤销成功");
          this.getData();
          vm.btnDisabled = false;
        }).catch(r => {
          vm.btnDisabled = false;
        })
      });

    },
    release(id) {
      let vm = this;
      vm.$confirm("是否确认发布上线", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        vm.btnDisabled = true;
        release({
          id: id
        }).then(res => {
          this.$message.success("发布上线成功");
          this.getData();
          vm.btnDisabled = false;
        }).catch(r => {
          vm.btnDisabled = false;
        })
      });
    }
  }
}
</script>

<style lang="scss">
.versionBox .el-card {
  background: none;
}

.versionBox .tyb {
  .el-button--primary {
    background: none;
  }
  .el-button--primary.is-plain:focus,
  .el-button--primary.is-plain:hover {
    background: none;
    color: #409eff;
  }
}
.el-icon-check {
  display: none;
}
</style>
