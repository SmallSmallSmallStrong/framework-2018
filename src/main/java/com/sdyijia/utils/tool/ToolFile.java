package com.sdyijia.utils.tool;

import com.sdyijia.utils.ECS.ToolEncDec;
import org.apache.commons.io.FileExistsException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.jboss.logging.Logger;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ToolFile {

    private static Logger logger = Logger.getLogger(ToolFile.class);

    public final static String ENCODING = "UTF-8";
    public final static String PRIVATE_KEY = "AsD`@0,o30[$f.->42";

    /**
     * 判断文件是否存在，存在则报错
     *
     * @param zipFile
     * @throws RuntimeException
     */
    private static void existeFile4name(String zipFile) {
        File zipFileTmp = new File(zipFile);
        if (zipFileTmp.exists()) {
            throw new RuntimeException("zip file:" + zipFile + " does exist.");
        }
    }

    /**
     * 生成随机文件名或指定文件名，含路径；再判断文件是否存在，如果存在则根据isCover决定是否重新生成
     *
     * @param srcName    原文件名
     * @param dateFormat 随机文件名格式化方式
     * @param max        随机数最大数：例如99
     * @param min        随机数最小数：例如10
     * @param path       保存路径
     * @param FILENAME   指定文件名
     * @param isCover    是否是有以存在的文件名，true：是；false：否
     * @throws RuntimeException
     */
    private static String rename(String srcName, String dateFormat, int max, int min, String path,
                                 final String FILENAME, Boolean isCover) {
        String fileName = null;
        try {
            fileName = generateFileName(srcName, dateFormat, max, min, path, FILENAME);
            existeFile4name(Tool.projectExsitsPath() + "/" + fileName);
            return fileName;
        } catch (RuntimeException e1) {
            // e1.printStackTrace();
            logger.error("file: " + fileName + "is exist");
            if (null != isCover && false == isCover) {
                return rename(srcName, dateFormat, max, min, path, FILENAME, isCover);
            } else {
                return fileName;
            }
        }
    }

    /**
     * 生成随机文件名或指定文件名，含路径
     *
     * @param srcName    原文件名
     * @param dateFormat 随机文件名格式化方式
     * @param max        随机数最大数：例如99
     * @param min        随机数最小数：例如10
     * @param path       保存路径
     * @param FILENAME   指定文件名
     * @return
     */
    public static String generateFileName(String srcName, String dateFormat, int max, int min, String path,
                                          final String FILENAME) {

        StringBuffer fileName = new StringBuffer();
        /* 取文件后缀名 */
        String extendName = srcName.substring(srcName.lastIndexOf("."));
        if (FILENAME != null && FILENAME.trim().length() > 0) {
            fileName.append(path + "/" + FILENAME + extendName);
        } else {
            /* 将生成的名字存入 fileNames[i] 中 */
            String formatStr = null;
            if (dateFormat == null || dateFormat.trim().length() < 0) {
                formatStr = "yyyyMMddHHmmss";
            } else {
                formatStr = dateFormat;
            }
            /* 为按日期"yyyy/MM/dd/HH/mm/ss/"格式自动生成的名字后加两位随机数 */
            // 随机数开始
            Random random = new Random();
            Integer s = random.nextInt(max) % (max - min + 1) + min;
            // 随机数结束
            fileName.append(path + "/" + ToolDate.formatTime(new Date(), formatStr, new Date().getTime() + "") + s.toString()
                    + extendName);
        }

        return fileName.toString();
    }

    /**
     * 根据filePath创建相应的目录
     *
     * @param filePath 要创建的文件路经
     * @return file 文件
     * @throws IOException
     */
    public static File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }

    /**
     * 将源文件src复制到指定位置local中
     *
     * @param src       源文件
     * @param srcName   原文件名
     * @param local     指定位置
     * @param UPLOADDIR 上传根位置(没有指定，请写null)
     * @param FILENAME  文件名
     * @return
     * @throws IOException
     */
    public static String fileCopy(File src, String srcName, String local, final String UPLOADDIR, final String FILENAME)
            throws IOException {
        return fileCopy(src, srcName, local, UPLOADDIR, FILENAME, null, 99, 10, true);
    }

    /**
     * 将源文件src复制到指定位置local中
     *
     * @param src             源文件
     * @param srcName         原文件名
     * @param local           指定位置
     * @param UPLOADDIR       上传根位置(没有指定，请写null)
     * @param FILENAME        文件名
     * @param dateFormat      随机文件名格式化方式-默认格式化方式：yyyyMMddHHmmss；
     * @param max             随机数最大数：例如99
     * @param min             随机数最小数：例如10。 max=99；min=10表示两位位随机数
     * @param isCover         是否覆盖已有文件 ；null||true：覆盖，false：不覆盖；如果false就再次生成一个文件名
     * @return
     * @throws IOException
     */
    public static String fileCopy(File src, String srcName, String local, final String UPLOADDIR, final String FILENAME,
                                  String dateFormat, int max, int min, Boolean isCover) throws IOException {
        if (src == null || !src.exists()) {
            throw new IOException("源文件src不存在");
        }
        String path = null;
        if (local != null && local.trim().length() > 0) {
            path = UPLOADDIR + "/" + local;
        } else {
            path = UPLOADDIR;
        }
        File uploadPath = new File(Tool.projectExsitsPath(), path);
        if (!uploadPath.exists()) {
            boolean isCreated = uploadPath.mkdirs();
            if (!isCreated) {
                // 目标上传目录创建失败,可做其他处理,例如抛出自定义异常等,一般应该不会出现这种情况。
                throw new IOException("目标上传目录创建失败");
            }
        }
        // ----------------产生文件名--开始
        String fileName = rename(srcName, dateFormat, max, min, path, FILENAME, isCover);
        // ----------------产生文件名--结束
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            /* 创建上传文件 */
            File uploadFile = new File(Tool.projectExsitsPath(), fileName);
            /* 创建输出管道 */
            out = new FileOutputStream(uploadFile);
            /* 建立缓存区大小 */
            byte[] buffer = new byte[1024 * 1024];
            int length;
            /* 判断是否继续写入 */
            while ((length = in.read(buffer)) > 0) {
                /* 将文件写入硬盘 */
                out.write(buffer, 0, length);
            }
        } catch (FileNotFoundException ex) {
            throw new IOException(fileName + ":创建文件失败");
        } catch (IOException ex) {
            throw new IOException(fileName + ":写入硬盘错误");
        } finally {
            if (null != in) {
                try {
                    /* 关闭输入流 */
                    in.close();
                } catch (IOException e) {
                    /* 关闭失败 */
                    logger.error("----------------------->保存文件时，输入流关闭失败");
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    /* 关闭输出流 */
                    out.close();
                } catch (IOException e) {
                    logger.error("----------------------->保存文件时，输出流关闭失败");
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    /**
     * 多文件上传
     *
     * @param files 文件数组 fileName 文件原名 UPLOADDIR 文件保存路径 FILENAME
     *              文件保存名，如果为null则按时间保存
     * @return result 是以Map&amp;ltString, String[]&gt;形式返回。</br>
     * success : 是否成功。 成功返回 true，失败返回false</br>
     * msg : 消息</br>
     * name : 成功保存后，在硬盘中的名字
     */
    public static Map<String, String[]> UploadFile(File[] files, String[] fileName, final String UPLOADDIR,
                                                   final String FILENAME) {
        /* 建立返回结果的储存集 */
        Map<String, String[]> result = new HashMap<String, String[]>();
        /* 默认按失败返回 */
        result.put("success", new String[]{"false"});
        /* 结果消息 */
        String[] msg = new String[files.length];
        if (files == null || files.length <= 0) {
            msg[0] = "没有可上传文件";
            result.put("msg", msg);
            return result;
        }
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                fileNames[i] = fileCopy(files[i], fileName[i], null, UPLOADDIR, FILENAME);
            } catch (IOException e1) {
                msg[i] = fileNames[i] + ":创建文件失败";
                logger.error("----------------------->" + msg[i]);
                e1.printStackTrace();
            }
        }
        /* 当完全没有错时，返回成功true */
        if (msg == null || msg.length <= 0 || msg[0] == null || msg[0].equals("null")) {
            result.put("success", new String[]{"true"});
        }
        /* 将生成的文件名放入map中 */
        result.put("name", fileNames);
        /* 将生成的消息msg放入map中 */
        result.put("msg", msg);

        return result;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    public static boolean deleteDir(File dir) {
        if (!dir.exists()) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    public static boolean deleteDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            return true;
        }
        return deleteDir(f);
    }

    /**
     * 压缩文件夹
     *
     * <pre>
     * &#64;param dirPath--目标文件夹
     * &#64;param zipFile--输出文件
     * &#64;param isDelete--是否删除原文件夹
     * &#64;param inclideFile--包括哪些文件或文件夹 eg:zip.setIncludes("*.java"); 或者 ("**\/*.java") 或者 "2012080617154683/**\/*"
     * &#64;param excludeFile--排除哪些文件或文件夹 eg:setExcludes(...)
     * </pre>
     *
     * @throws FileExistsException
     */
    public static void zipdir(String dirPath, String zipFile, boolean isDelete, boolean allowDeleteExistsFile,
                              String[] inclideFile, String[] excludeFile) throws FileExistsException {
        File srcdir = new File(dirPath);
        if (!srcdir.exists()) {
            throw new RuntimeException(dirPath + "不存在！");
        }

        File zipFileTmp = new File(zipFile);
        if (zipFileTmp.exists()) {
            if (false == allowDeleteExistsFile) {
                throw new FileExistsException("文件存在");
            } else {
                zipFileTmp.delete();
                logger.info("已删除存在的zip文件：" + zipFileTmp.getName());
            }
        }

        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(zipFile));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        if (srcdir.isFile()) {
            fileSet.setFile(srcdir);
        } else {
            fileSet.setDir(srcdir);
        }
        /**
         * <pre>
         *
         * 目标:打包时只选指定目录的文件夹A和文件夹B,对于下面的程序来讲,"79个..."和"过年写的.."就是指两个文件夹. 
         * 方法:为fileSet添加OrSelector,在OrSelector中添加两个FilenameSelector 
         * 实现效果:实现目标 
         * FilenameSelector a = new FilenameSelector();  
         *   a.setName("79个不可不知的生活潜规则_2012080617154683/**\/*");  
         *
         *  FilenameSelector b = new FilenameSelector();  
         *  b.setName("过年写的几篇家乡小吃片段_2012080617154651/**\/*");  
         *
         *  OrSelector or = new OrSelector();   
         *  or.addFilename(a);  
         *  or.addFilename(b);
         * </pre>
         */
        // OrSelector or = new OrSelector();
        // for(String str : inclideFile){
        // FilenameSelector fileNameSelector = new FilenameSelector();
        // fileNameSelector.setName(str);
        // or.addFilename(fileNameSelector);
        // }
        if (inclideFile != null && inclideFile.length > 0) {
            for (String inc : inclideFile) {
                fileSet.setIncludes(inc); // 包括哪些文件或文件夹
                // eg:zip.setIncludes("*.java");
            }
        }
        if (excludeFile != null && excludeFile.length > 0) {
            for (String exc : excludeFile) {
                fileSet.setExcludes(exc); // 排除哪些文件或文件夹
            }
        }
        // fileSet.addOr(or);
        zip.addFileset(fileSet);
        zip.setEncoding(ENCODING);
        zip.execute();
        if (true == isDelete) {
            deleteDir(dirPath);
            logger.info(("已删除存在的zip文件：" + zipFileTmp.getName()));
        }
    }

    /**
     * 压缩文件夹
     *
     * <pre>
     * &#64;param dirPath--目标文件夹
     * &#64;param zipFile--输出文件
     * &#64;param isDelete--是否删除原文件夹/源文件
     * &#64;param inclideFile--包括哪些文件或文件夹 eg:zip.setIncludes("*.java"); 或者 ("**\/*.java") 或者 "2012080617154683/**\/*"
     * &#64;param excludeFile--排除哪些文件或文件夹 eg:setExcludes(...)
     * &#64;param encrypt--是否加密
     * </pre>
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void zipdir(String dirPath, String zipFile, boolean isDelete, boolean allowDeleteExistsFile,
                              String[] inclideFile, String[] excludeFile, boolean encrypt) throws GeneralSecurityException, IOException {
        File srcdir = new File(dirPath);
        if (!srcdir.exists()) {
            throw new RuntimeException(dirPath + "不存在！");
        }

        allowDeleteFile(zipFile, allowDeleteExistsFile);

        if (true == encrypt) {
            String zipFileT = zipFile + ".tmp";
            allowDeleteFile(zipFileT, allowDeleteExistsFile);
            zipdir(dirPath, zipFileT, isDelete, allowDeleteExistsFile, inclideFile, excludeFile);
            String privateKey = PRIVATE_KEY;
            ToolEncDec.encrypt(zipFileT, zipFile, privateKey);
            new File(zipFileT).delete();
        } else {
            zipdir(dirPath, zipFile, isDelete, allowDeleteExistsFile, inclideFile, excludeFile);
        }
    }

    /**
     * 是否删除已存在文件
     *
     * @param zipFile
     * @param allowDeleteExistsFile
     * @throws FileExistsException
     */
    private static void allowDeleteFile(String zipFile, boolean allowDeleteExistsFile) throws FileExistsException {

        File zipFileTmp = new File(zipFile);
        if (zipFileTmp.exists()) {
            if (false == allowDeleteExistsFile) {
                throw new FileExistsException("文件存在");
            } else {
                zipFileTmp.delete();
                logger.info("已删除存在的zip文件：" + zipFileTmp.getName());
            }
        }

    }

    /**
     * 解压缩文件夹
     *
     * <pre>
     * &#64;param zipFile--源文件
     * &#64;param dirPath--目标文件夹
     * &#64;param isDelete--是否删除原文件夹
     * &#64;param overWrite--是否使用已存在的目录和已存在文件
     * &#64;param decrypt--是加密
     * </pre>
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void unzip(String zipFile, String dirPath, boolean isDelete, boolean overWrite, boolean decrypt)
            throws GeneralSecurityException, IOException {

        existeFile(zipFile);
        File zipFileTmp = new File(zipFile);

        if (true == decrypt) {
            String privateKey = PRIVATE_KEY;
            String zipFileT = zipFile + ".zip";
            ToolEncDec.decrypt(zipFile, zipFileT, privateKey);
            if (true == isDelete) {
                zipFileTmp.delete();
                logger.info("已删除存在的zip文件：" + zipFileTmp.getName());
            }
            unzip(zipFileT, dirPath, true, overWrite);
        } else {
            unzip(zipFile, dirPath, isDelete, overWrite);
        }
    }

    /**
     * 解压缩文件夹
     *
     * <pre>
     * &#64;param zipFile--源文件
     * &#64;param dirPath--目标文件夹
     * &#64;param isDelete--是否删除原文件夹
     * &#64;param overWrite--是否使用已存在的目录和已存在文件
     * </pre>
     */
    public static void unzip(String zipFile, String dirPath, boolean isDelete, boolean overWrite) {

        existeFile(zipFile);

        File zipFileTmp = new File(zipFile);
        if (!zipFileTmp.exists()) {
            throw new RuntimeException("zip file:" + zipFile + " does not exist.");
        }
        Project proj = new Project();
        Expand expand = new Expand();
        expand.setProject(proj);
        expand.setTaskType("unzip");
        expand.setTaskName("unzip");
        expand.setEncoding(ENCODING);
        expand.setSrc(new File(zipFile));
        expand.setDest(new File(dirPath));
        expand.setOverwrite(overWrite);
        expand.execute();
        if (true == isDelete) {
            zipFileTmp.delete();
            logger.info("已删除存在的zip文件：" + zipFileTmp.getName());
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param zipFile
     */
    private static void existeFile(String zipFile) {
        File zipFileTmp = new File(zipFile);
        if (!zipFileTmp.exists()) {
            throw new RuntimeException("zip file:" + zipFile + " does not exist.");
        }
    }

}
