package com.example.android.utils;

public class Apiurls {

    //服务器地址
    //宿舍wifi
    //public static final String server = "http://192.168.2.235:8084";
    //热点
    public static final String server = "http://192.168.43.181:8084";

    /*登录注册相关*/
    //注册
    public static final String register = "/community/login-user/register";

    //登录
    public static final String login = "/community/login-user/login";

    //删除用户
    public static final String delUser = "/community/login-user/delUser";

    //社区管理员登录
    public static final String adminLogin = "/community/login-user/adminLogin";

    //登出
    public static final String logout = "/community/login-user/logout";

    //修改密码
    public static final String changePassword = "/community/login-user/changePassword";

    //获取用户类型
    public static final String getUserType = "/community/login-user/getUserType";

    /*个人信息相关*/
    //获取个人信息
    public static final String getInformation = "/community/user-information/getUserInformation";

    //修改个人信息
    public static final String completeInformation = "/community/user-information/changeUserInformation";

    //上传头像
    public static final String uploadHeadPic = "/community/user-information/uploadHeadPic";

    /*通知公告方面*/
    //显示公告
    public static final String showNotice = "/community/notice/showNotice";

    //添加公告
    public static final String addNotice = "/community/notice/addNotice";

    //删除公告
    public static final String deleteNotice = "/community/notice/deleteNotice";

    //修改公告
    public static final String modifyNotice = "/community/notice/modifyNotice";

    //查看公告详情
    public static final String showNoticeDeatil = "/community/notice/showNoticeDeatil";


    /*报修 反馈相关*/
    //添加交互单
    public static final String addMultiple = "/community/multiple/addMultiple";
    //显示详情
    public static final String showMultipleDetail = "/community/multiple/showMultipleDetail";
    //查看列表
    public static final String showMultiple = "/community/multiple/showMultiple";

    /*电话相关*/
    //显示电话
    public static final String showTel = "/community/telephone/showTel";

    /*消息相关*/
    //增加消息记录
    public static final String addMessage = "/community/message/addMessage";
    //逻辑删除全部消息记录
    public static final String delMessage = "/community/message/delMessage";
    //逻辑删除单个消息记录
    public static final String delMessageById = "/community/message/delMessageById";
    //查看消息记录
    public static final String selectMessage = "/community/message/selectMessage";
    //获取未读消息数
    public static final String countUnreadMessage = "/community/message/countUnreadMessage";


    /*活动相关*/
    //获取活动
    public static final String showActivity = "/community/activity/showActivity";
    //报名活动
    public static final String applyActivity = "/community/activity/applyActivity";
    //显示活动详情
    public static final String showActivityDetail = "/community/activity/showActivityDetail";
    //显示自己已报名活动
    public static final String getMyActivity = "/community/activity/getMyActivity";

}
