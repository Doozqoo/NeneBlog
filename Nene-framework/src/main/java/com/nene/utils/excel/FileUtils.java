package com.nene.utils.excel;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {

    /**
     * 校验文件
     *
     * @param len  文件大小
     * @param size 限制大小
     * @param unit 单位：B、K、M、G
     * @return
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
        boolean flag = true;
        double fileSize = 0;
        switch (unit.toUpperCase()) {
            case "B":
                fileSize = (double) len;
                break;
            case "K":
                fileSize = (double) len / 1024;
                break;
            case "M":
                fileSize = (double) len / 1048576;
                break;
            case "G":
                fileSize = (double) len / 1073741824;
                break;
            default:
                flag = false;
                break;
        }
        if (fileSize > size) {
            flag = false;
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param filePath 源文件路径
     * @param fileName 下载文件名
     * @param response 响应页面
     */
    public static void downLoadFile(String filePath, String fileName, HttpServletResponse response) {
        try (InputStream inStream = Files.newInputStream(Paths.get(filePath));
             OutputStream os = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            byte[] b = new byte[1024];
            int len;
            while ((len = inStream.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
