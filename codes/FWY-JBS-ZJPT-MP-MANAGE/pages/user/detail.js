// pages/user/detail.js
import {userdetail} from '../../utils/http';
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array:[],
    uid:'',
    detail:{},
    page:1,
    total:1,
    wallet:0,
    sysmodel:app.globalData.sysmodel,
    hasmore:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      uid:options.id
    })
    this.getdetail()
  },

  getdetail:function()
  {
    wx.showLoading({
      title: '加载中...',
    })
    var that=this;
    userdetail({uid:that.data.uid,pageNo:that.data.page,pageSize:that.data.sysmodel=='hor'?6:20}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        var arr = res.data.basisOrderPage.records
        if(that.data.sysmodel=='hor')
        {
          that.setData({
            array:res.data.basisOrderPage.records,
          })
        }else
        {
           if(arr.length<20)
           {
             that.setData({
               hasmore:false
             })
           }
           that.setData({
             array:that.data.array.concat(arr)
           })
        }
        that.setData({
          
          total:res.data.basisOrderPage.pages,
          detail:res.data.basisUsers,
          wallet:res.data.wallet
        })
      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });
  },


  gopre:function()
  {
    if(this.data.page==1)
    {
      return
    }
    this.data.page--
    this.getdetail()
  },
  gonext:function()
  {
    if(this.data.page==this.data.total)
    {
      return
    }
    this.data.page++
    this.getdetail()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if(this.data.sysmodel=='ver')
    {
      if(this.data.hasmore)
      {
        this.data.page++
        this.getdetail()
      }
    }
  },

  
})