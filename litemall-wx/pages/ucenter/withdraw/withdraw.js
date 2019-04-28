var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
        amountAll:0,//
        applyAmount:'',
        channel:'1'
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            amountAll: options.money
        });
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

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    },

    bindinputMoney(event) {
        this.setData({
            applyAmount: event.detail.value
        });
    },

    withdrawApply() {
        console.log(this.data.address)
        let applyAmount = this.data.applyAmount;
        let amountAll = this.data.amountAll;
        let channel =  this.data.channel;

        if (applyAmount == '') {
            util.showErrorToast('请输入提现金额');

            return false;
        }

        if (applyAmount>amountAll) {
            util.showErrorToast('金额已超过');
            return false;
        }

        let that = this;
        util.request(api.WithdrawApply, {
            amount: applyAmount,
            channel: channel
        }, 'POST').then(function(res) {
            if (res.errno === 0) {
                wx.showToast({
                    title: '申请成功'
                });
            }else {
                wx.showToast({
                    image: '/static/images/icon_error.png',
                    title: _res.errmsg,
                    mask: true
                });
            }
        });

    },

    getIssue: function () {

        let that = this;
        that.setData({
            showPage: false,
            issueList: []
        });

        util.request(api.IssueList, {
            page: that.data.page,
            size: that.data.size
        }).then(function (res) {
            if (res.errno === 0) {

                that.setData({
                    issueList: res.data.data,
                    showPage: true,
                    count: res.data.count
                });
            }
        });

    },
    prevPage: function (event) {
        if (this.data.page <= 1) {
            return false;
        }

        var that = this;
        that.setData({
            page: that.data.page - 1
        });
        this.getIssue();
    }
})