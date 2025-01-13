// pages/coupon/index.js
import {couponList,addCoupon} from '../../utils/http';
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array:[],
    sysmodel:app.globalData.sysmodel,
    hasmore:true,
    popshow:false,
    shoparr:[{name:'全部',type:''},{name:'免单券',type:'1'},{name:'折扣券',type:'2'},{name:'代金券',type:'3'}],
    shoparr0:[{name:'全部',type:''},{name:'使用中',type:'1'},{name:'已结束',type:'3'}],
    shoparr1:[{name:'免单券',type:'1'},{name:'折扣券',type:'2'},{name:'代金券',type:'3'}],
    dname:'',
    shopindex:0,
    shopindex1:-1,
    shopindex0:0,

    selfchecked:false,//仅限自己使用
    lingchecked:false,//领取时间限制
    usechecked:false,//使用时间限制

    minDate: new Date().getTime(),
    maxDate: new Date(2030, 10, 1).getTime(),
    currentDate: new Date().getTime(),
    tiempickertype:1,//1领取开始时间2领取截至时间3使用到期时间
    timepopshow:false,

    lingstime:'',//领取开始时间
    lingetime:'',//领取截至时间
    usetime:'',//使用截至时间
    
    cname:'',//优惠券名称
    freemoney:'',//免单金额
    zkmoney:'',//折扣额度
    quanmoney:'',//代金券金额
    limitmoney:'',//使用门槛
    ccount:'',//发放数量
    signlenum:'',//单人领取数量

    page:1,


    darray:[],
    dpopshow:false,
    dpage:1,
    dhasmore:true,
    dtotal:0,

    pickshow:false,
    pickshow0:false,
    pickshow1:false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList()
    this.getdownlist()
  },

  getdname:function(e)
  {
    this.setData({
      dname:e.detail.value
    })
  },

  bindPickerChange:function(e)
  {
    this.setData({
      shopindex: e.detail.value
    })
  },
  bindPickerChange0:function(e)
  {
    this.setData({
      shopindex0: e.detail.value
    })
  },

  addclick:function()
  {
    this.setData({
      popshow:true
    })
  },

  selfbindchange:function(e)
  {
    this.setData({
      selfchecked:e.detail.value
    })
  },
  lingbindchange:function(e)
  {
    this.setData({
      lingchecked:e.detail.value
    })
  },
  usebindchange:function(e)
  {
    this.setData({
      usechecked:e.detail.value
    })
  },

  bindPickerChange1:function(e)
  {
    this.setData({
      shopindex1: e.detail.value
    })
  },

  showtimepicker:function(e)
  {
    this.setData({
      tiempickertype:e.currentTarget.dataset.type,
      timepopshow:true
    })
  },

  pickerconfirm:function(e)
  {
    console.log(e)

    var time = this.js_date_time(e.detail)
    console.log(time)
    this.setData({
      timepopshow:false
    })
    if(this.data.tiempickertype==1)
    {
      this.setData({
        lingstime:time
      })
    }else if(this.data.tiempickertype==2)
    {
      this.setData({
        lingetime:time
      })
    }else
    {
      this.setData({
        usetime:time
      })
      
    }
  },

  closepop:function()
  {
    this.setData({
      popshow:false,
      dpopshow:false
    })
  },

  getc_name:function(e)
  {
    this.setData({
      cname:e.detail.value
    })
  },
  getfreemoney:function(e)
  {
    this.setData({
      freemoney:e.detail.value
    })
  },
  getzkmoney:function(e)
  {
    this.setData({
      zkmoney:e.detail.value
    })
  },
  getquanmoney:function(e)
  {
    this.setData({
      quanmoney:e.detail.value
    })
  },
  getlimitmoney:function(e)
  {
    this.setData({
      limitmoney:e.detail.value
    })
  },
  getcount:function(e)
  {
    this.setData({
      ccount:e.detail.value
    })
  },
  getsignlenum:function(e)
  {
    this.setData({
      signlenum:e.detail.value
    })
  },
  addrequest:function()
  {
    if(this.data.cname.length==0)
    {
      wx.showToast({
        title: '请输入优惠券名称',
        icon:'none'
      })
      return
    }
    else if(this.data.shopindex1==-1)
    {
      wx.showToast({
        title: '请输入优惠券类型',
        icon:'none'
      })
      return
    }
    
    else if(this.data.shopindex1==0 && this.data.freemoney.length==0)
    {
      wx.showToast({
        title: '请输入最高免单金额',
        icon:'none'
      })


      
      return
    }else if(this.data.shopindex1==1 && this.data.zkmoney.length==0)
    {
      wx.showToast({
        title: '请输入折扣额度',
        icon:'none'
      })
      return
    }else if(this.data.shopindex1==2 && this.data.quanmoney.length==0)
    {
      wx.showToast({
        title: '请输入代金券金额',
        icon:'none'
      })
      return
    }else if(this.data.shopindex1==2 && this.data.limitmoney.length==0)
    {
      wx.showToast({
        title: '请输入代金券使用门槛金额',
        icon:'none'
      })
      return
    }else if(this.data.ccount.length==0)
    {
      wx.showToast({
        title: '请输入发放数量',
        icon:'none'
      })
      return
    }else if(this.data.signlenum.length==0)
    {
      wx.showToast({
        title: '请输入单人领取次数',
        icon:'none'
      })
      return
    }else if(this.data.lingchecked==true && this.data.lingstime.length==0)
    {
      wx.showToast({
        title: '请输入领取开始时间',
        icon:'none'
      })
      return
    }else if(this.data.lingchecked ==true && this.data.lingetime.length==0)
    {
      wx.showToast({
        title: '请输入领取截止时间',
        icon:'none'
      })
      return
    }else if(this.data.usechecked==true && this.data.usetime.length==0)
    {
      wx.showToast({
        title: '请输入使用截止时间',
        icon:'none'
      })
      return
    }
    
    var that = this
    wx.showLoading({
      title: '创建中...',
    })
    var dic = {}
    dic.title = that.data.cname
    dic.type = that.data.shoparr1[that.data.shopindex1].type
    if(that.data.shopindex1==0)
    {
      dic.num = that.data.freemoney
    }else if(that.data.shopindex1==1)
    {
      dic.num = that.data.zkmoney
      dic.selfLimit = that.data.selfchecked?1:0
    }else
    {
      dic.num = that.data.quanmoney
      dic.numLimit = that.data.limitmoney
    }
    dic.state = 1
    dic.total = that.data.ccount
    dic.totalLimit = that.data.ccount==0?0:1
    dic.dateLimit = that.data.lingchecked?1:0
    if(that.data.lingchecked)
    {
      dic.stime = that.data.lingstime+':00'
      dic.etime = that.data.lingetime+':00'
    }
    if(that.data.usechecked)
    {
      dic.vtime = that.data.usetime+':00'
    }
    dic.validLimit = that.data.usechecked?1:0
    dic.receive = that.data.signlenum

    
    addCoupon(dic).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        wx.showToast({
          title: '创建成功',
          icon:'none'
        })
        that.setData({
          page:1,
          hasmore:true,
          array:[],
          popshow:false
        })
        setTimeout(() => {
          that.getList()
        }, 2000);
        
      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });

  },


  gosearch:function()
  {
    this.setData({
      page:1,
      hasmore:true,
      array:[]
    })
    this.getList()
  },
  getList:function()
  {
    wx.showLoading({
      title: '加载中...',
    })
    var dic = {}
    dic.title = this.data.dname
    dic.type = this.data.shoparr[this.data.shopindex].type
    dic.state = this.data.shoparr0[this.data.shopindex0].type
    dic.pageNo = this.data.page
    dic.pageSize = this.data.sysmodel=='ver'?10:6
    var that = this
    couponList(dic).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        if(that.data.sysmodel=='hor')
        {
          that.setData({
            array:res.data.records,
            total:res.data.pages
          })
        }else
        {
          var arr = res.data.records
          if(arr.length<10)
          {
            that.setData({
              hasmore:false
            })
          }
          that.setData({
            array:that.data.array.concat(arr),
            loadend:true
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

  gopre:function()
  {
    if(this.data.page==1)
    {
      return
    }
    this.data.page--
    this.setData({
      page:this.data.page
    })
    this.getList()
  },
  gonext:function()
  {
    if(this.data.page==this.data.total)
    {
      return
    }
    this.data.page++
    this.setData({
      page:this.data.page
    })
    this.getList()
  },




  js_date_time:function(unixtime) {
    var date = new Date(unixtime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;

    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    // return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;//年月日时分秒
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute;
  
  },

  getdownlist:function()
  {
    var dic = {}
    dic.state = 2
    dic.pageNo = this.data.page
    dic.pageSize = 20
    var that = this
    couponList(dic).then(res=>{

      if(res.code==0)
      {
        var arr = res.data.records
        if(arr.length<20)
        {
          that.setData({
            dhasmore:false
          })
        }
        that.setData({
          darray:that.data.darray.concat(arr),
          dtotal:res.data.total
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
  showdown:function()
  {
    this.setData({
      dpopshow:true
    })
  },
  getmore:function()
  {
    if(this.data.dhasmore)
    {
      this.data.dpage++
      this.getdownlist()
    }
  },

  pickonShow:function()
  {
    this.setData({
      pickshow:true
    })
  },
  pickonShow0:function()
  {
    this.setData({
      pickshow0:true
    })
  },
  pickonShow1:function()
  {
    this.setData({
      pickshow1:true
    })
  },
  onCancel:function()
  {
    this.setData({
      pickshow1:false,
      pickshow:false,
      pickshow0:false
    })
  },
  pickonChange:function(e)
  {
    console.log(e)
    this.setData({
      shopindex :e.detail.index,
      pickshow:false
    })
  },
  pickonChange0:function(e)
  {
    this.setData({
      shopindex0 :e.detail.index,
      pickshow0:false
    })
  },
  pickonChange1:function(e)
  {
    this.setData({
      shopindex1 :e.detail.index,
      pickshow1:false
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
        this.getList()
      }
    }
  },

})