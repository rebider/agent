package com.ryx.credit.common.util.enumUtil;

import java.math.BigDecimal;

public class BigDecimalUtil {

	/**
	 * 当参数为空，返回0
	 * @param x
	 * @return
	 */
	public static BigDecimal isNull(BigDecimal x){
		if(null==x)
			return new BigDecimal("0");
		
		return x;
	}
}
