package com.bunsaned3thinking.mogu.common.util;

import com.bunsaned3thinking.mogu.common.config.S3Config;

public class S3Util {
	public static String toS3ImageUrl(String imageLink) {
		return S3Config.ImageURL + imageLink;
	}
}
