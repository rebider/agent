package com.ryx.credit.common.util;

import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author chenliang
 */
public class GetMultipartFile {
    /**
     * 将文件转为获取MultipartFile
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static  MultipartFile createMultipartFile(String path) throws IOException {
        Logger logger = Logger.getLogger(MultipartFile.class);

        File pdfFile = new File(path);
        if (pdfFile == null) {
            logger.info("根据路径获取文件为空，请检查");
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

        return multipartFile;
    }
}
