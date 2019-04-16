package com.global.utils;

import com.global.api.enums.ResponseStatus;
import com.global.api.vo.ResponseModel;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.Date;

/**
 * @Author yanghanjin
 * @Description: Sign 令牌验证
 * @Date 2019/3/19
 */
public class SignUtils {

    private static final String hexDigIts[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5加密
     *
     * @param origin      字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charsetname || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    /***
     * 验证当前请求是否合法
     * @param cachetoken
     * @param token
     * @param timestap
     * @param sign   生成方式 MD5(token + timestap + timestap.substring(timestap.length()-5,timestap.length()))
     * @return
     */
    public static ResponseModel verifySign(String cachetoken, String token, Long timestap, String sign) {
        ResponseModel model = new ResponseModel();
        if (StringUtils.isEmpty(token)){
            model.setCode(ResponseStatus.RESPONNSE_NOTOKEN.getCode());
            model.setMessage(ResponseStatus.RESPONNSE_NOTOKEN.getMessage());
            return model;
        }
        if (StringUtils.isEmpty(cachetoken) || !cachetoken.equals(token)) {
            model.setCode(ResponseStatus.RESPONNSE_SIGNINVALID.getCode());
            model.setMessage(ResponseStatus.RESPONNSE_SIGNINVALID.getMessage());
            return model;
        }
        String timestr = String.valueOf(timestap);
        String createsign = token + "[" + timestap + "]" + timestr.substring(timestr.length() - 5, timestr.length());
        createsign = MD5Encode(createsign, "UTF-8");
        if (!sign.equals(createsign)) {
            model.setCode(ResponseStatus.RESPONNSE_SIGNFAILD.getCode());
            model.setMessage(ResponseStatus.RESPONNSE_SIGNFAILD.getMessage());
            return model;
        }
        Long nowTime = new Date().getTime();
        long seconds = (nowTime - timestap) / 1000;
        if (seconds > 20) {
            model.setCode(ResponseStatus.RESPONNSE_SIGNTIMEOUT.getCode());
            model.setMessage(ResponseStatus.RESPONNSE_SIGNTIMEOUT.getMessage());
            return model;
        }
        model.setCode(ResponseStatus.RESPONNSE_SUCCESS.getCode());
        return model;
    }

    public static void main(String[] args) {
        String createsign = "f24674d7-e1cf-43e7-bf0b-5ccee705f79d" + "[1553138773510]" + "1553138773510".substring("1553138773510".length() - 5, "1553138773510".length());
        System.out.println(createsign);
        String s = MD5Encode(createsign, "UTF-8");
        System.out.println(s);
    }

}
