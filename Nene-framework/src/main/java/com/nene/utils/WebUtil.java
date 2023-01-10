package com.nene.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName WebUtil
 * @Author Protip
 * @Date 2023/1/10 12:53
 * @Version 1.0
 */
public class WebUtil {

    public static void renderString(HttpServletResponse response, String s) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
