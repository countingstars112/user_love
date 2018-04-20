package com.xxwy.redisping.tools;

import com.alibaba.fastjson.JSONObject;
import com.xxwy.redisping.tools.bean.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.ftp.Handler;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;


/**
 *  From  xxwy
 *  发送Https请求
 *  尝试2：忽略证书验证过程
 */
public class ConnectionUtil {

    private static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    /**
     * 得到open_id session_key
     * @param code
     * @return
     */
    public static ResultData getSomeToLogin(String code){
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", "wx905787610e720f15");
        requestUrl = requestUrl.replace("SECRET", "68e50b77ab74269904f24b0907ca3f00");
        requestUrl = requestUrl.replaceAll("JSCODE", code);
        ResultData result = commontWeixin(requestUrl, "GET", null);
        //
        if(result.getCode().equals("200") && result.getData() == null){
            return result;
        }
        JSONObject jsonObject = (JSONObject)result.getData();
        return parserJson(jsonObject);
    }


    /**
     * 微信检查--是否有错
     */
    public static ResultData parserJson(JSONObject jsonObject){
        try{
            //错误
            if(jsonObject.getString("errcode")!=null){
                String errcode=jsonObject.getString("errcode");
                String errmsg=jsonObject.getString("errmsg");
                logger.warn(errcode+"-------"+errmsg);
                return ResultUtil.error(errmsg);
            }
            //得到信息
            String errmsg=jsonObject.getString("openid");
            return ResultUtil.success(jsonObject);
        }catch (Exception e) {
            return ResultUtil.error("解析失败");
        }

    }

    /**
     * https连接
     * @param httpsUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static ResultData commontWeixin(String httpsUrl, String requestMethod,
                                           String outputStr){
        BufferedReader reader = null;
        HttpsURLConnection content = null;
        JSONObject jsonObject = null;
        try {
            //准备的参数
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            //url
            URL url = new URL(httpsUrl);
            content = (HttpsURLConnection) url.openConnection();
            content.setSSLSocketFactory(ssf);
            content.setDoOutput(true); //默认是false,不支持写入？
            content.setDoInput(true); //默认就是true
            content.setUseCaches(false);
            System.getProperties().setProperty("ProxyHost","");
            //get , post .....
            content.setRequestMethod(requestMethod);
            // 支持post 的请求
            if(outputStr != null){
                OutputStream outputStream = content.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            //解析输出，输出是json格式
            InputStream in= content.getInputStream();
            reader = new BufferedReader( new InputStreamReader(in, "utf-8"));
            String line = null;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            jsonObject = JSONObject.parseObject(response.toString());
            logger.info("cool" , jsonObject);
            return ResultUtil.success(jsonObject);
        } catch (Exception e) {
            logger.error("https connection error");
            return ResultUtil.error("https连接失败");
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(content != null){
                content.disconnect();
            }
        }


    }
}
