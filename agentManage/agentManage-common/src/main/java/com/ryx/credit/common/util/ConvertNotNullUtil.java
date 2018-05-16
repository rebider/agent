package com.ryx.credit.common.util;

import java.math.BigDecimal;


/**
 * 非空转换工具类
 * @author ryx
 *
 */
public class ConvertNotNullUtil {
	
	public static BigDecimal getNotNullUtil(BigDecimal rate){
		if (rate == null) {
			return BigDecimal.ZERO;
		} else {
			return rate;
		}
	}
}
