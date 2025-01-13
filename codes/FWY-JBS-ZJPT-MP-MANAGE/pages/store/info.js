// pages/store/info.js
import {shopmanager,uploadimg,delnotice,addnotice} from '../../utils/http';
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fileList:[],
    fileList1:[],
    array:[],
    popshow:false,
    page:1,
    detail:{},
    upnum:0,
    noticecontent:'',
    sysmodel:app.globalData.sysmodel,
    hasmore:true,
    shopname:"",
    shopwx:"",
    storeAddress:'',
    addrinfo:{},
    lat:'',
    lng:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  
  onLoad: function (options) {
    wx.setStorageSync('storeAddress', null)
    this.getshopdetail()
   
  },

  getshopdetail:function()
  {
    wx.showLoading({
      title: '加载中...',
    })
    var that=this;
    shopmanager({'pageNo':that.data.page,'pageSize':that.data.sysmodel=='hor'?8:20}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        if(that.data.sysmodel=='hor')
        {
          that.setData({
            fileList:res.data.images?res.data.images.split(','):[],
            detail:res.data.basisShopNoticePage,
            fileList1:res.data.logo?[res.data.logo]:[],
            shopwx:res.data.wx,
            shopname:res.data.contact,
            addrinfo:res.data.addrs,
            storeAddress:res.data.addrs.addr,
            lat:res.data.addrs.lat,
            lng:res.data.addrs.lng
          })
        }else
        {
          var arr = res.data.basisShopNoticePage.records
          if(arr.length<20)
          {
            that.setData({
              hasmore:false
            })
          }
          that.setData({
            array:that.data.array.concat(arr),
            fileList:res.data.images?res.data.images.split(','):[],
            fileList1:res.data.logo?[res.data.logo]:[],
            shopwx:res.data.wx,
            shopname:res.data.contact,
            addrinfo:res.data.addrs,
            storeAddress:res.data.addrs.addr,
            lat:res.data.addrs.lat,
            lng:res.data.addrs.lng
          })
        }
        
      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });
  },



  delcell:function(e)
  {

    var that = this
    wx.showModal({
      cancelColor: '',
      confirmColor:'#1D4ABD',
      title:'删除确认',
      content:'是否确认删除相关通知',
      success(res)
      {
        if(res.confirm)
        {
          console.log('confirm')
          delnotice({id:e.currentTarget.dataset.id}).then(res=>{
            wx.hideLoading({
              success: (res) => {},
            })
            if(res.code==0)
            {
              that.getshopdetail()
            }else
            {
              wx.showToast({
                title: res.msg,
                icon:'none'
              })
            }
          });

        }else
        {
          console.log('cancel')
        }
      }
    })
  },
  getcontent:function(e)
  {
    this.setData({
      noticecontent:e.detail.value
    })
  },
  addconfirm:function()
  {
    if(this.data.noticecontent.length==0)
    {
      wx.showToast({
        title: '情输入通知内容',
        icon:'none'
      })
      return
    }
    var that = this
    wx.showLoading({
      title: '提交中...',
    })
    addnotice({content:that.data.noticecontent}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        that.setData({
          popshow:false,
          noticecontent:''
        })
        that.getshopdetail()
      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });

  },
  closepop:function()
  {
    this.setData({
      popshow:false
    })
  },
  addmessage:function()
  {
    this.setData({
      popshow:true
    })
  },

  gopre:function()
  {
    if(this.data.page==1)
    {
      return
    }
    this.data.page--
    this.getshopdetail()
  },
  gonext:function()
  {
    if(this.data.page==this.data.detail.pages)
    {
      return
    }
    this.data.page++
    this.getshopdetail()
  },

  onChangeTap:function(e)
  {
    let t_token = wx.getStorageSync('token')
    let t_shopid = wx.getStorageSync('shopid')

    console.log(e)
    var files = e.detail.current

    wx.showLoading({
      title: '上传中...',
    })
    var that = this
    for(var i=0;i<files.length;i++)
    {
      wx.uploadFile({
        url: app.globalData.baseurl+'/coreDm/coredm/updata/image',
        filePath: files[i],
        name: 'file',
        formData: {
          'file': 'banner'
        },
        header: {
          // "Content-Type": "multipart/form-data",
          'Authorization': 'Authorization_'+t_token,
          'SHOPID':t_shopid
        },
        success: function (res) {
          wx.hideLoading();
          if (res.statusCode == 200) {
            that.data.upnum++
            if(that.data.upnum == files.length)
            {
              wx.hideLoading({
                success: (res) => {},
              })
              that.setData({
                upnum:0
              })
            }
            console.log(res)
            var data = JSON.parse(res.data)
            var arr = that.data.fileList
            arr.push(data.data.kpath)
            that.setData({
              fileList:arr
            })
            
          } else {
            wx.showToast({
              title: '上传失败,'+data.msg,
              icon:'none'
            })
          }
        }, fail: function (res) {
          wx.hideLoading();
          console.log(res)
          wx.showToast({
            title: '上传失败'+res,
            icon:"none"
          })
        }
      })
    }

  },
  onRemoveTap:function(e)
  {
    console.log(e)
    var arr = this.data.fileList
    arr.splice(e.detail.index,1)
    this.setData({
      fileList:arr
    })
  },

  uploadbanner:function()
  {
    console.log(this.data.fileList)
    if(this.data.fileList.length==0)
    {
      wx.showToast({
        title: '请上传图片',
        icon:'none'
      })
      return
    }else if(this.data.storeAddress.length==0)
    {
      wx.showToast({
        title: '请输入店铺地址',
        icon:'none'
      })
      return
    }
    wx.showLoading({
      title: '提交中...',
    })
    var that=this;
    var s = that.data.fileList.join(',')
    uploadimg({figureImage:s,wx:that.data.shopwx,contact:that.data.shopname,addr:that.data.storeAddress,lat:that.data.lat,lng:that.data.lng}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        wx.hideLoading({
          success: (res) => {},
        })
        wx.showToast({
          title: '提交成功',
          icon:'success'
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



  onChangeTap1:function(e)
  {
    let t_token = wx.getStorageSync('token')
    let t_shopid = wx.getStorageSync('shopid')

    console.log(e)
    var files = e.detail.current

    wx.showLoading({
      title: '上传中...',
    })
    var that = this
      wx.uploadFile({
        url: app.globalData.baseurl+'/info/applets/uploadPortrait',
        filePath: files[0], 
        name: 'file',
        formData: {
          'file': 'logo'
        },
        header: {
          // "Content-Type": "multipart/form-data",
          'Authorization': 'Authorization_'+t_token,
          'SHOPID':t_shopid
        },
        success: function (res) {
          wx.hideLoading();
          if (res.statusCode == 200) {

              wx.hideLoading({
                success: (res) => {},
              })
            
            console.log(res.data)
            var data = JSON.parse(res.data)
            if(data.code==0)
            {
              wx.showToast({
                title: '上传成功',
                icon:'none'
              })
            }else
            {
              wx.showToast({
                title: '上传失败,'+data.msg,
                icon:'none'
              })
            }
            
            
          } else {
            var data = JSON.parse(res.data)
            wx.showToast({
              title: '上传失败,'+data.msg || '',
              icon:'none'
            })
          }
        }, fail: function (res) {
          wx.hideLoading();
          console.log(res)
          wx.showToast({
            title: '上传失败'+res.errMsg || '',
            icon:"none"
          })
        }
      })
    

  },
  onRemoveTap1:function(e)
  {
    console.log(e)
    var arr = this.data.fileList1
    arr.splice(e.detail.index,1)
    this.setData({
      fileList1:arr
    })
  },

  getshopname:function(e)
  {
    this.setData({
      shopname:e.detail.value
    })
  },
  getshopwx:function(e)
  {
    this.setData({
      shopwx:e.detail.value
    })
  },

  chooseloc:function()
  {
    wx.navigateTo({
      url: '../store/shopMap',
    })
  },
  getaddr:function(e)
  {
    this.setData({
      storeAddress:e.detail.value
    })
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
    var s = wx.getStorageSync('storeAddress')
    if(s)
    {
      this.setData({
        storeAddress:s.addr,
        lat :s.latitude,
        lng :s.longitude
      })
    }
    
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
        this.getshopdetail()
      }
    }
  },


})