package com.yuanlrc.base.bean;
/**
 * 错误码统一处理类，所有的错误码统一定义在这里
 * @author Administrator
 *
 */
public class CodeMsg {

	private int code;//错误码
	
	private String msg;//错误信息
	
	/**
	 * 构造函数私有化即单例模式
	 * @param code
	 * @param msg
	 */
	private CodeMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//通用错误码定义
	//处理成功消息码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	//非法数据错误码
	public static CodeMsg DATA_ERROR = new CodeMsg(-1, "非法数据！");
	public static CodeMsg CPACHA_EMPTY = new CodeMsg(-2, "验证码不能为空！");
	public static CodeMsg VALIDATE_ENTITY_ERROR = new CodeMsg(-3, "");
	public static CodeMsg SESSION_EXPIRED = new CodeMsg(-4, "会话已失效，请刷新页面重试！");
	public static CodeMsg CPACHA_ERROR = new CodeMsg(-5, "验证码错误！");
	public static CodeMsg USER_SESSION_EXPIRED = new CodeMsg(-6, "还未登录或会话失效，请重新登录！");
	public static CodeMsg UPLOAD_PHOTO_SUFFIX_ERROR = new CodeMsg(-7, "图片格式不正确！");
	public static CodeMsg UPLOAD_PHOTO_ERROR = new CodeMsg(-8, "图片上传错误！");
	public static CodeMsg SAVE_ERROR = new CodeMsg(-11, "保存失败，请联系管理员！");
	public static CodeMsg ORDER_SN_ERROR = new CodeMsg(-12, "订单编号错误！");
	public static CodeMsg PHONE_ERROR = new CodeMsg(-13, "手机号错误！");
	public static CodeMsg ORDER_AUTH_ERROR = new CodeMsg(-14, "\u8ba2\u5355\u9a8c\u8bc1\u5931\u8d25\uff0c\u8ba2\u5355\u7f16\u53f7\u6216\u624b\u673a\u53f7\u8f93\u5165\u6709\u8bef\u6216\u8005\u53ef\u80fd\u4f60\u8d2d\u4e70\u7684\u662f\u76d7\u7248\uff0c\u8bf7\u8054\u7cfb\u3010\u733f\u6765\u5165\u6b64\u3011\u5ba2\u670d\uff01");
	
	//后台管理类错误码
	//用户管理类错误
	public static CodeMsg ADMIN_USERNAME_EMPTY = new CodeMsg(-2000, "用户名不能为空！");
	public static CodeMsg ADMIN_PASSWORD_EMPTY = new CodeMsg(-2001, "密码不能为空！");
	public static CodeMsg ADMIN_NO_RIGHT = new CodeMsg(-2002, "您所属的角色没有该权限！");
	
	//登录类错误码
	public static CodeMsg ADMIN_USERNAME_NO_EXIST = new CodeMsg(-3000, "该用户名不存在！");
	public static CodeMsg ADMIN_PASSWORD_ERROR = new CodeMsg(-3001, "密码错误！");
	public static CodeMsg ADMIN_USER_UNABLE = new CodeMsg(-3002, "该用户已被冻结，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_UNABLE = new CodeMsg(-3003, "该用户所属角色状态不可用，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_AUTHORITES_EMPTY = new CodeMsg(-3004, "该用户所属角色无可用权限，请联系管理员！");
	
	//后台菜单管理类错误码
	public static CodeMsg ADMIN_MENU_ADD_ERROR = new CodeMsg(-4000, "菜单添加失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_EDIT_ERROR = new CodeMsg(-4001, "菜单编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_ID_EMPTY = new CodeMsg(-4002, "菜单ID不能为空！");
	public static CodeMsg ADMIN_MENU_ID_ERROR = new CodeMsg(-4003, "菜单ID错误！");
	public static CodeMsg ADMIN_MENU_DELETE_ERROR = new CodeMsg(-4004, "改菜单下有子菜单，不允许删除！");
	//后台角色管理类错误码
	public static CodeMsg ADMIN_ROLE_ADD_ERROR = new CodeMsg(-5000, "角色添加失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_NO_EXIST = new CodeMsg(-5001, "该角色不存在！");
	public static CodeMsg ADMIN_ROLE_EDIT_ERROR = new CodeMsg(-5002, "角色编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_DELETE_ERROR = new CodeMsg(-5003, "该角色下存在用户信息，不可删除！");
	//后台用户管理类错误码
	public static CodeMsg ADMIN_USER_ROLE_EMPTY = new CodeMsg(-6000, "请选择用户所属角色！");
	public static CodeMsg ADMIN_USERNAME_EXIST = new CodeMsg(-6001, "该用户名已存在，请换一个试试！");
	public static CodeMsg ADMIN_USE_ADD_ERROR = new CodeMsg(-6002, "用户添加失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_NO_EXIST = new CodeMsg(-6003, "用户不存在！");
	public static CodeMsg ADMIN_USE_EDIT_ERROR = new CodeMsg(-6004, "用户编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_DELETE_ERROR = new CodeMsg(-6005, "该用户存在关联数据，不允许删除！");
	public static CodeMsg COMMUNITY_NULL_ERROR = new CodeMsg(-6006,"请给普通管理员选择地点");

	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_USER_UPDATE_PWD_ERROR = new CodeMsg(-7000, "旧密码错误！");
	public static CodeMsg ADMIN_USER_UPDATE_PWD_EMPTY = new CodeMsg(-7001, "新密码不能为空！");
	public static CodeMsg ADMIN_PASSWORD_LENGTH_ERROR = new CodeMsg(-7002, "密码长度须为4-32位！");

	//后台用户修改个人信息错误码
	public static CodeMsg USER_NAME_NULL_ERROR = new CodeMsg(-7100,"用户名不能为空");
	public static CodeMsg USER_NAME_LENGTH_ERROR = new CodeMsg(-7101,"用户名长度须为4-18位！");
	public static CodeMsg USER_SAVE_ERROR = new CodeMsg(-7102,"个人信息修改失败！");

	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_DATABASE_BACKUP_NO_EXIST = new CodeMsg(-8000, "备份记录不存在！");

	//
	public static CodeMsg ADMIN_COMMUNITY_DELETE_ERROR = new CodeMsg(-10000, "删除地址失败");
	public static CodeMsg ADMIN_COMMUNITY_ADD_ERROR = new CodeMsg(-10001, "添加地址失败");
	public static CodeMsg ADMIN_COMMUNITY_EDIT_ERROR = new CodeMsg(-10002, "编辑地址失败");
	public static CodeMsg ADMIN_COMMUNITY_NULL_ERROR = new CodeMsg(-10003, "请使用普通管理员添加");
	public static CodeMsg ADMIN_COMMUNITY_NOT_FOUND_ERROR = new CodeMsg(-10004, "地址不存在");
	public static CodeMsg ADMIN_COMMUNITY_EXIST_ERROR = new CodeMsg(-10005, "该地址已存在");


	//流动人口
	public static CodeMsg ADMIN_MIGRANT_ADD_ERROR = new CodeMsg(-10024, "流动人员添加失败");
	public static CodeMsg ADMIN_MIGRANT_IMPORT_ADD_ERROR = new CodeMsg(-10025, "请选择Excel文件");
	public static CodeMsg ADMIN_MIGRANT_IMPORT_EXCEL_ERROR = new CodeMsg(-10006, "请正确导入数据信息，查看导出模板再导入");
	public static CodeMsg ADMIN_IMPORT_EXCEL_NULL_ERROR = new CodeMsg(-10007, "单元格内容不能为空");
	public static CodeMsg ADMIN_EXCEL_IDCARD_REPEATED = new CodeMsg(-10008, "Excel中有重复的身份证号码");
	public static CodeMsg ADMIN_MIGRANT_LIST_ADD_ERROR = new CodeMsg(-10009, "导入失败");

	public static CodeMsg ADMIN_HAS_RESIDENT_ERROR = new CodeMsg(-10010, "已经是该市的常驻人员，不能再添加为该市流动人员");
	public static CodeMsg ADMIN_MIGRANT_DELETE_ERROR = new CodeMsg(-10011, "流动人员删除失败");
	public static CodeMsg ADMIN_SEX_FORMAT_ERROR = new CodeMsg(-10012, "请正确输入性别格式：男性  或  女性");
	public static CodeMsg ADMIN_EDIT_MIGRANT_ERROR = new CodeMsg(-10013, "流动人员编辑失败");
	public static CodeMsg ADMIN_ADD_MIGRANT_ERROR = new CodeMsg(-10014, "已经是该社区的流动人员了，无需添加了");


	//审核
	public static CodeMsg ADMIN_APPROVAL_ERROR = new CodeMsg(-10020, "该人员不在审核状态");
	public static CodeMsg ADMIN_APPROVAL_MSG_LENGTH_ERROR = new CodeMsg(-10021, "审批理由不能大于50字");
	public static CodeMsg ADMIN_APPROVAL_SAVE_ERROR = new CodeMsg(-10022, "审核失败");

	public static CodeMsg ADMIN_NOT_FOUND_INFO = new CodeMsg(-10030, "找不到你的信息");

	//常驻人口
	public static CodeMsg ACCOUNT_NUMBER_LENGTH_ERROR = new CodeMsg(-20000,"户号应为9位!");
	public static CodeMsg RESIDENT_SAVE_ERROR = new CodeMsg(-20001,"常驻人员添加失败!");
	public static CodeMsg RESIDENT_DELETE_ERROR = new CodeMsg(-20002,"常驻人员删除失败!");
	public static CodeMsg ADMIN_TYPE_FORMAT_ERROR = new CodeMsg(-20003, "请正确输入户口类型：城市  或  农村");

	//核酸记录
	public static CodeMsg ADMIN_IMG_NULL_ERROR = new CodeMsg(-21000,"请上传核酸检测图片!");
	public static CodeMsg ADMIN_DATE_ERROR = new CodeMsg(-21001,"采集时间不能大于当前时间!");
	public static CodeMsg ADMIN_TESTRECORD_SAVE_ERROR = new CodeMsg(-21002,"核酸检测记录添加失败!");
	public static CodeMsg DATE_NULL_ERROR = new CodeMsg(-21003,"请选择时间!");
	public static CodeMsg ADMIN_PLEASE_ADD_INFO_ERROR = new CodeMsg(-21004, "请先添加你在这个地址的流动信息或者常驻信息");
	public static CodeMsg TESET_RECORD_DELETE_ERROR = new CodeMsg(-21005, "删除失败");


	//通用
	public static CodeMsg COMMON_IDCARD_FORMAET_ERROR = new CodeMsg(-80000, "身份证格式错误");
	public static CodeMsg COMMON_IDCARD_FORMAET_ERROR2 = new CodeMsg(-80001, "身份证格式错误,或导入模板错误");
	public static CodeMsg ADMIN_CARDNUMBER_SESSION_ERROR = new CodeMsg(-81000, "会话失效，请重新通过身份证号查询个人信息");
	public static CodeMsg ADMIN_PHONE_FORMAET_ERROR = new CodeMsg(-82000, "手机号码格式有误");
}
