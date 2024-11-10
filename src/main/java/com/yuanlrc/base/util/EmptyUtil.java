package com.yuanlrc.base.util;

/**
 * 判空工具类
 * @date Nov 24, 20078:01:20 AM
 */
public class EmptyUtil {

	/**
	 * 判断字符串是否为空或者为null 是返回"0"
	 * 
	 * @param str
	 * @return
	 */
	public static String isEmpty_2(String str) {
		if (str.equals("") || str == null) {
			return "0.0000";
		}
		return str;

	}

	/**
	 * 判断字符串是否为空，长度为0被认为是空字符串.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str != null){
			return str.trim().length() == 0||"undefined".equals(str);
		}else{
			return true;
		}
	}

	/**
	 * 判断字符串是否为空，字符串前后空白被截断，截断后长度为0被认为是空字符串.
	 * 
	 * @param str
	 * @param isTrimed
	 *            是否截去空白
	 * @return
	 */
	public static boolean isEmpty(String str, boolean isTrimed) {
		if(isTrimed){
			return str == null || str.trim().length() == 0;
		}else{
			return str == null || str.length() == 0;
		}
	}

	/**
	 * 判断列表是否为空，列表没有元素也被认为是空

	 * 
	 * @param
	 * @return
	 */
	public static boolean isEmpty(java.util.Collection collection) {
		return collection == null || collection.size() == 0;
	}
	
	/**
	 * 判断数组是否为空
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array){
		return array == null || array.length == 0;
	}	
	
	/**
	 * 判断对象是否为空
	 * @param
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return obj == null;
	}		

}
