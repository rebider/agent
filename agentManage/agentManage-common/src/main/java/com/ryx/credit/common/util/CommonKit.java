package com.ryx.credit.common.util;

import org.apache.commons.lang3.StringUtils;

public class CommonKit {

	
	public static String[] split2DirAndFile(String fullPath) {
		if (StringUtils.isBlank(fullPath)) {
			throw new RuntimeException();
		}
		fullPath = StringUtils.replaceEach(fullPath, new String[] { "\\", "//" }, new String[] { "/", "/" });
		String[] paths = split(fullPath, "/");
		String directory = StringUtils.join(paths, "/", 0, paths.length - 1);
		String fileName = paths[(paths.length - 1)];
		return new String[] { directory, fileName };
	}
	
	public static String[] split(String str, String regex) {
		if (str == null) {
			return new String[0];
		}
		return str.split(regex);
	}
}
