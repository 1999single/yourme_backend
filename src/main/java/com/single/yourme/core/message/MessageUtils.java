package com.single.yourme.core.message;

import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-12-24
 */
public class MessageUtils {

    private MessageUtils() {}

    private static final String HOST = "http://dingxin.market.alicloudapi.com";
    private static final String PATH = "/dx/sendSms";
    private static final String METHOD = "POST";
    private static final String APP_CODE = "0fc637b930b14f4f913a07ecee8e1f92";

    public static void sendMessage(String code, String use) {
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APP_CODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "17758717836");
        querys.put("param", "code:" + code + ',' + "use:" + use);
        querys.put("tpl_id", "TP19080213");
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = MessageHttpUtils.doPost(HOST, PATH, METHOD, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
