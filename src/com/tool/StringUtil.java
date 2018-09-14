package com.tool;
/**
 * @description 字符串处理工具类<br/>
 * @ClassName StringUtil<br/>
 * @author jack<br/>
 * @createTime 2017年5月25日下午1:58:54<br/>
 * @version 1.0.0<br/>
 */
public class StringUtil {
	/**
	 * @description 判断字符串是否为空<br/>
	 * @methodName isEmpty<br/>
	 * @author jack<br/>
	 * @createTime 2017年4月22日下午2:16:23<br/>
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("")
				|| str.matches("\\s*");
	}

	/**
	 * @description 字符串非空判断<br/>
	 * @methodName isNotEmpty<br/>
	 * @author jack<br/>
	 * @createTime 2017年5月25日下午1:57:17<br/>
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}
