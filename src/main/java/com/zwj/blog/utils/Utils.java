package com.zwj.blog.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.exception.TipException;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zwj.blog.controller.admin.UploadFileController;
import com.zwj.blog.model.Vo.User;

/**
 * Tale工具类
 * <p>
 */
public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    /**
     * 一个月
     */
    private static final int one_month = 30 * 24 * 60 * 60;
    /**
     * 匹配邮箱正则
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern SLUG_REGEX = Pattern.compile("^[A-Za-z0-9_-]{5,100}$", Pattern.CASE_INSENSITIVE);
    // 使用双重检查锁的单例方式需要添加 volatile 关键字
    private static volatile DataSource newDataSource;
    /**
     * markdown解析器
     */
    private static Parser parser = Parser.builder().build();
    /**
     * 获取文件所在目录
     */
    private static String location = Utils.class.getClassLoader().getResource("").getPath();

    /**
     * 判断是否是邮箱
     *
     * @param emailStr
     * @return
     */
    public static boolean isEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * @param fileName 获取jar外部的文件
     * @return 返回属性
     */
    private static Properties getPropFromFile(String fileName) {
        Properties properties = new Properties();
        try {
//            默认是classPath路径
            InputStream resourceAsStream = new FileInputStream(fileName);
            properties.load(resourceAsStream);
        } catch (TipException | IOException e) {
            LOGGER.error("get properties file fail={}", e.getMessage());
        }
        return properties;
    }

    /**
     * md5加密
     *
     * @param source 数据源
     * @return 加密字符串
     */
    public static String MD5encode(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] encode = messageDigest.digest(source.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte anEncode : encode) {
            String hex = Integer.toHexString(0xff & anEncode);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 获取新的数据源
     *
     * @return
     */
    public static DataSource getNewDataSource() {
        if (newDataSource == null) synchronized (Utils.class) {
            if (newDataSource == null) {
                Properties properties = Utils.getPropFromFile("application-dev.properties");
                if (properties.size() == 0) {
                    return newDataSource;
                }
                DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
                managerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
                managerDataSource.setPassword(properties.getProperty("spring.datasource.password"));
                String str = "jdbc:mysql://" + properties.getProperty("spring.datasource.url") + "/" + properties.getProperty("spring.datasource.dbname") + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                managerDataSource.setUrl(str);
                managerDataSource.setUsername(properties.getProperty("spring.datasource.username"));
                newDataSource = managerDataSource;
            }
        }
        return newDataSource;
    }

    /**
     * 返回当前登录用户
     *
     * @return
     */
    public static User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (User) session.getAttribute(BlogConst.LOGIN_SESSION_KEY);
    }


    /**
     * 获取cookie中的用户id
     *
     * @param request
     * @return
     */
    public static Integer getCookieUid(HttpServletRequest request) {
        if (null != request) {
            Cookie cookie = cookieRaw(BlogConst.USER_IN_COOKIE, request);
            if (cookie != null && cookie.getValue() != null) {
                try {
                    String uid = Tools.deAes(cookie.getValue(), BlogConst.AES_SALT);
                    return StringUtils.isNotBlank(uid) && Tools.isNumber(uid) ? Integer.valueOf(uid) : null;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 从cookies中获取指定cookie
     *
     * @param name    名称
     * @param request 请求
     * @return cookie
     */
    private static Cookie cookieRaw(String name, HttpServletRequest request) {
        javax.servlet.http.Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (javax.servlet.http.Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 设置记住密码cookie
     *
     * @param response
     * @param uid
     */
    public static void setCookie(HttpServletResponse response, Integer uid) {
        try {
            String val = Tools.enAes(uid.toString(), BlogConst.AES_SALT);
            boolean isSSL = false;
            Cookie cookie = new Cookie(BlogConst.USER_IN_COOKIE, val);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 30);
            cookie.setSecure(isSSL);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取html中的文字
     *
     * @param html
     * @return
     */
    public static String htmlToText(String html) {
        if (StringUtils.isNotBlank(html)) {
            return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
        }
        return "";
    }

    /**
     * markdown转换为html
     *
     * @param markdown
     * @return
     */
    public static String mdToHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return "";
        }
        java.util.List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        content = Commons.emoji(content);
        return content;
    }

    /**
     * 退出登录状态
     *
     * @param session
     * @param response
     */
    public static void logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute(BlogConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(BlogConst.USER_IN_COOKIE, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            response.sendRedirect(Commons.site_url());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 替换HTML脚本
     *
     * @param value
     * @return
     */
    public static String filtratingtHtml(String value) {
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    /**
     * 过滤XSS注入
     *
     * @param value
     * @return
     */
    public static String filterXSS(String value) {
        String cleanValue = null;
        if (value != null) {
            cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);
            // Avoid null characters
            cleanValue = cleanValue.replaceAll("\0", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
        }
        return cleanValue;
    }

    /**
     * 判断是否是合法路径
     *
     * @param slug
     * @return
     */
    public static boolean isPath(String slug) {
        if (StringUtils.isNotBlank(slug)) {
            if (slug.contains("/") || slug.contains(" ") || slug.contains(".")) {
                return false;
            }
            Matcher matcher = SLUG_REGEX.matcher(slug);
            return matcher.find();
        }
        return false;
    }


    public static String getFileKey(String name, String uuid) {
        String prefix = null;
        if (uuid != null) {
            prefix = "/upload/" + uuid;
        }
        else prefix = "/upload/" + DateKit.dateFormat(new Date(), "yyyy/MM");
        if (!new File(UploadFileController.CLASSPATH + prefix).exists()) {
            new File(UploadFileController.CLASSPATH + prefix).mkdirs();
        }

        name = StringUtils.trimToNull(name);
        if (name == null) {
            return prefix + "/" + UUID.UU32() + "." + null;
        } else {
            name = name.replace('\\', '/');
            name = name.substring(name.lastIndexOf("/") + 1);
            int index = name.lastIndexOf(".");
            String ext = null;
            if (index >= 0) {
                ext = StringUtils.trimToNull(name.substring(index + 1));
            }
            return prefix + "/" + name;
        }
    }

    /**
     * 判断文件是否是图片类型
     *
     * @param imageFile
     * @return
     */
    public static boolean isImage(InputStream imageFile) {
        try {
            Image img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String ReplaceImagePath(String content,String prefix) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "(<img.*src\\s*=\\s*\"\\s*)([^\"]*)(\\s*\"[^>]*?>)";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        StringBuffer buffer = new StringBuffer();
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
//            int left = m_image.start();
//            int right = m_image.end();
            String group1 = m_image.group(1);
            String src = m_image.group(2);
            String group2 = m_image.group(3);
            int index = src.lastIndexOf("/");
            String replaceString = "";
            if (index != -1 && index != src.length() - 1) {
                replaceString = src.substring(index + 1);
            } else {
                replaceString = src;
            }
            String fileKey = Utils.getFileKey(replaceString, prefix);

            m_image.appendReplacement(buffer, group1 + fileKey + group2);
        }

        m_image.appendTail(buffer);
        content = buffer.toString();
        buffer = new StringBuffer();
        Pattern pattern2;
        Matcher mather2;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String reg2 = "(!\\[.*?\\]\\()(.*?)(\\))";
        pattern2 = Pattern.compile
                (reg2, Pattern.CASE_INSENSITIVE);
        mather2 = pattern2.matcher(content);
        while (mather2.find()) {
            // 得到<img />数据
//            int left = m_image.start();
//            int right = m_image.end();
            String group1 = mather2.group(1);
            String src = mather2.group(2);
            String group2 = mather2.group(3);
            int index = src.lastIndexOf("/");
            String replaceString = "";
            if (index != -1 && index != src.length() - 1) {
                replaceString = src.substring(index + 1);
            } else {
                replaceString = src;
            }
            String fileKey = Utils.getFileKey(replaceString, prefix);

            mather2.appendReplacement(buffer, group1 + fileKey + group2);
        }

        mather2.appendTail(buffer);



        return buffer.toString();
    }

    /**
     * 随机数
     *
     * @param size
     * @return
     */
    public static String getRandomNumber(int size) {
        String num = "";

        for (int i = 0; i < size; ++i) {
            double a = Math.random() * 9.0D;
            a = Math.ceil(a);
            int randomNum = (new Double(a)).intValue();
            num = num + randomNum;
        }

        return num;
    }

    /**
     * 获取保存文件的位置,jar所在目录的路径
     *
     * @return
     */
    public static String getUploadFilePath() {
        String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File("");
        return file.getAbsolutePath() + "/";
    }
}
