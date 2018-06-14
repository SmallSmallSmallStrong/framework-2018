package com.sdyijia.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SpringUtil {

    /**
     * 根据参数获取ID值
     * 
     * @param request
     * @param id
     *            出错后返回0
     * @return
     */
    public static Long RedirectAttributesToId(HttpServletRequest request, Long id) {
        return SpringUtil.RedirectAttributesToId(request, "id", id, 0l);
    }

    /**
     * 根据参数获取ID值
     * 
     * @param request
     * @param id
     * @param defaultNum
     *            出错后返回 defaultNum
     * @return
     */
    public static Long RedirectAttributesToId(HttpServletRequest request, Long id, Long defaultNum) {
        return SpringUtil.RedirectAttributesToId(request, "id", id, defaultNum);
    }

    /**
     * 根据参数获取ID值
     * 
     * @param request
     * @param key
     * @param id
     *            出错后返回0
     * @return
     */
    public static Long RedirectAttributesToId(HttpServletRequest request, String key, Long id) {
        return SpringUtil.RedirectAttributesToId(request, key, id, 0l);
    }

    /**
     * 根据参数获取ID值
     * 
     * @param request
     * @param id
     * @param defaultNum
     *            出错后返回 defaultNum
     * @return
     */
    public static Long RedirectAttributesToId(HttpServletRequest request, String key, Long id, Long defaultNum) {
        @SuppressWarnings("unchecked")
        Map<String, Object> modelMap = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
        Long idTmp = defaultNum;
        try {
            if (id != null) {
                idTmp = id;
            } else {
                idTmp = (Long) modelMap.get(key);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return idTmp;
    }

    /**
     * spring文件上传
     * 
     * @param multipartFile
     *            multipartFile
     * @param fileUrl
     *            文件保存的URL
     * @param fileName
     *            生成的文件名
     * @param request
     *            HttpServletRequest
     * @return
     */
    public static String saveFile(MultipartFile multipartFile, String fileUrl, String fileName,
                                  HttpServletRequest request) {
        String dirPath = request.getServletContext().getRealPath("/");
        String contextPath = request.getServletContext().getContextPath();

        if (contextPath.length() <= 1) {
            contextPath = "/ROOT";
        }

        contextPath = contextPath.substring(1, contextPath.length());
        dirPath = dirPath.substring(0, dirPath.lastIndexOf(contextPath));
        File dir = new File(dirPath, fileUrl);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().equals("")) {
            return null;
        }
        /** 获取文件的后缀* */
        String suffix = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        /** 使用UUID生成文件名称* */
        String sfileName = fileName + suffix;
        File dstFile = new File(dir, sfileName);
        try {
            multipartFile.transferTo(dstFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sfileName;
    }
}
