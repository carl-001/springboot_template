package com.global.utils;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static final Log logger = LogFactory.getLog(HttpClientUtil.class);

    @SuppressWarnings({"finally"})
    public static String getMethod(String httpUrl) {
        List<String> list = getMethodByList(httpUrl);
        StringBuffer sb = new StringBuffer("");
        for (String item : list) {
            sb.append(item);
        }
        return sb.toString();
    }

    @SuppressWarnings({"deprecation", "finally"})
    public static String postMethod(String httpUrl, String requestInfo) {
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        String response = "";
        logger.info(">>>>>请求地址：" + httpUrl);
        logger.info(">>>>>请求参数：" + requestInfo);
        if (logger.isDebugEnabled()) {
            logger.debug(">>>>>请求内容：" + requestInfo);
        }
        try {
            httpClient = new HttpClient();
            // 设置超时时间
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(5 * 60 * 1000);
            postMethod = new PostMethod(httpUrl);
            if (!StringUtils.isEmpty(requestInfo)) {
                if (requestInfo.contains("{")) {
                    postMethod.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
                }
            }
            postMethod.setRequestBody(requestInfo);
            // 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里也可以设置成自定义的恢复策略
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            // 执行postMethod
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error(">>>>>" + httpUrl + ", 错误：" + postMethod.getStatusLine());
            } else {
                InputStream is = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String str;
                StringBuffer sb = new StringBuffer(100);
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                response = sb.toString();
            }
            String rs;
            if (response.length() > 100) {
                rs = response.substring(0, 100) + " ...";
            } else {
                rs = response;
            }
            logger.info(">>>>>返回内容：" + rs);
        } catch (Exception e) {
            logger.error(">>>>>异常：" + e.getLocalizedMessage(), e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
            return response;
        }
    }


    public static boolean downloadFile(String httpUrl, String filepath) {
        boolean rs = false;
        HttpClient httpClient = null;
        GetMethod getMethod = null;
        logger.info(">>>>>请求地址：" + httpUrl);
        BufferedInputStream bufferInput = null;
        BufferedOutputStream bufferOutput = null;
        try {
            httpClient = new HttpClient();
            // 设置超时时间
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
            getMethod = new GetMethod(httpUrl);
            // 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里也可以设置成自定义的恢复策略
            getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error(">>>>>" + httpUrl + ", 错误：" + getMethod.getStatusLine());
            } else {
                InputStream is = getMethod.getResponseBodyAsStream();
                if (is.available() > 0 && StringUtils.isNotEmpty(is.toString())) {
                    File file = new File(filepath);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    OutputStream os = new FileOutputStream(filepath);
                    bufferInput = new BufferedInputStream(is);
                    bufferOutput = new BufferedOutputStream(os);
                    byte[] bs = new byte[1024];
                    int len = 0;
                    while ((len = bufferInput.read(bs)) != -1) {
                        rs = true;
                        bufferOutput.write(bs, 0, len);
                    }
                    bufferOutput.flush();
                }
            }
            if (rs) {
                logger.info(">>>>>返回内容：文件" + filepath);
            } else {
                logger.info(">>>>>返回内容：无内容");
            }
        } catch (Exception e) {
            logger.error(">>>>>异常：" + e.getLocalizedMessage(), e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
            if (bufferInput != null) {
                try {
                    bufferInput.close();
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (bufferOutput != null) {
                try {
                    bufferOutput.close();
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }

        }
        return rs;
    }

    public static List<String> getMethodByList(String httpUrl) {
        HttpClient httpClient = null;
        GetMethod getMethod = null;
        List<String> list = new ArrayList<String>();
        logger.info(">>>>>请求地址：" + httpUrl);
        try {
            httpClient = new HttpClient();
            // 设置超时时间
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
            getMethod = new GetMethod(httpUrl);
            // 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里也可以设置成自定义的恢复策略
            getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

            // 解决 Too many open files 问题 by kosh 2017-03-31
            // 通过 修改 open files 为 65535 试试看效果，如果不行则启用下面的方式
//            getMethod.addRequestHeader("Connection","close");
//            httpClient.getParams().setBooleanParameter( "http.protocol.expect-continue" , false );

            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error(">>>>>" + httpUrl + ", 错误：" + getMethod.getStatusLine());
            } else {
                InputStream is = getMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String str;
                while ((str = br.readLine()) != null) {
                    list.add(str);
                }
            }
            String rs = "";
            int size = list.size();
            if (size == 1) {
                rs = substr(list.get(0));
            }
            if (size > 1) {
                rs = substr(list.get(0));
                rs += "  ...  ";
                rs += substr(list.get(size - 1));
                rs += " total lines: " + size;
            }
            logger.info(">>>>>返回内容：" + rs);
        } catch (Exception e) {
            logger.error(">>>>>异常：" + e.getLocalizedMessage(), e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
            return list;
        }
    }


    /**
     * 使用HttpClient4.5 post提交multipart/form-data数据实现多文件上传
     *
     * @param url            请求地址
     * @param multipartFiles post提交的文件列表
     * @param fileParName    fileKey
     * @param params         附带的文本参数
     * @param timeout        请求超时时间(毫秒)
     * @return
     * @author alexli
     * @date 2018年5月8日 上午10:26:15
     */
    public static Map<String, String> httpPostRequest2(String url, List<MultipartFile> multipartFiles, String fileParName,
                                                       Map<String, Object> params, int timeout) {
        Map<String, String> resultMap = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String fileName = null;
            MultipartFile multipartFile;
            for (int i = 0; i < multipartFiles.size(); i++) {
                multipartFile = multipartFiles.get(i);
                fileName = multipartFile.getOriginalFilename();
                builder.addBinaryBody(fileParName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            }
            //决中文乱码
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null)
                    continue;
                // 类似浏览器表单提交，对应input的name和value
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交

            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);

            HttpEntity responseEntity = response.getEntity();
            resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
            resultMap.put("data", "");
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                resultMap.put("data", result);
            }
        } catch (Exception e) {
            resultMap.put("scode", "error");
            resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());

            Writer w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("HTTP请求出现异常: " + w.toString());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public static Map<String, String> httpPostRequest(String url, Map<String, Object> params, int timeout) {
        Map<String, String> resultMap = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //决中文乱码
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null)
                    continue;
                // 类似浏览器表单提交，对应input的name和value
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交

            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);

            HttpEntity responseEntity = response.getEntity();
            resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
            resultMap.put("data", "");
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                resultMap.put("data", result);
            }
        } catch (Exception e) {
            resultMap.put("scode", "error");
            resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());

            Writer w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("HTTP请求出现异常: " + w.toString(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    private static String substr(String response) {
        String rs = "";
        if (response.length() > 100) {
            rs = response.substring(0, 100) + "...";
        } else {
            rs = response;
        }
        return rs;
    }

    public static void main(String[] args) {
        String url = "http://10.0.0.14:8080/sfsj/rceducensyn/saverceducen.sj?rcpersonid=3&educendate=2017-07-1112:03:22&educencontent=彩虹之间&educenhours=1&educenunit=广东佛山&workexam=远程教育&bookman=远程教育";
        getMethodByList(url);
    }


}
