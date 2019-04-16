package com.global.utils;

import com.global.api.enums.ResponseStatus;
import com.global.api.vo.ResponseModel;
import org.springframework.util.StringUtils;

/**
 * @Author yanghanjin
 * @Description:
 * 1、口令长度必须达到8位，长度限制为8-20个字符
 * 2、口令应由大写字母、小写字母、数字、特殊符号中的3种及以上类型组成
 * 3、口令中不得包含3位及以上的相同数字或字母（如chrdw#11的最后2位，aa$HDHXT的前2位，sz&555pzc的中间3位）
 * 4、口令中不得包含与账号相同的字母组合，含大小写组合（如账号为guozw，密码为guoZW#16）
 * @Date 2019/3/19
 */
public class CheckPasswordUtils {

    /**
     * 验证密码合法性
     *
     * @param password
     * @return
     */
    public static ResponseModel verifyPassword(String username, String password) {
        ResponseModel responseModel = new ResponseModel();
        // 长度小于8位
        if (password == null || password.length() < 8) {
            responseModel.setCode(ResponseStatus.RESPONNSE_FAILD.getCode());
            responseModel.setMessage(ResponseStatus.RESPONNSE_PASSWORD_LENGTHLE8.getMessage());
            return responseModel;
        }
        // 长度大于20位
        if (password == null || password.length() > 20) {
            responseModel.setCode(ResponseStatus.RESPONNSE_FAILD.getCode());
            responseModel.setMessage(ResponseStatus.RESPONNSE_PASSWORD_LENGTHGE16.getMessage());
            return responseModel;
        }
        responseModel = checkCharTypes(password);
        if (responseModel.getCode() != 200) {
            return responseModel;
        }
        responseModel = checkRepeatSubstring(password);
        if (responseModel.getCode() != 200) {
            return responseModel;
        }
        responseModel = checkSaveChar(username, password);
        return responseModel;
    }

    /**
     * 包括大小写字母、数字、其它符号,以上四种至少三种
     *
     * @param password
     * @return
     */
    public static ResponseModel checkCharTypes(String password) {
        ResponseModel responseModel = new ResponseModel();
        int upperCase = 0, lowerCase = 0, digit = 0, other = 0;
        for (Character ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                upperCase = 1;
            } else if (Character.isLowerCase(ch)) {
                lowerCase = 1;
            } else if (Character.isDigit(ch)) {
                digit = 1;
            } else {
                other = 1;
            }
        }
        if (upperCase + lowerCase + digit + other >= 3) {
            responseModel.setCode(ResponseStatus.RESPONNSE_SUCCESS.getCode());
            responseModel.setMessage(ResponseStatus.RESPONNSE_SUCCESS.getMessage());
            return responseModel;
        }
        responseModel.setCode(ResponseStatus.RESPONNSE_PASSWORD_CONTAINSCHAR.getCode());
        responseModel.setMessage(ResponseStatus.RESPONNSE_PASSWORD_CONTAINSCHAR.getMessage());
        return responseModel;
    }

    /**
     * 不能有相同长度超2的子串重复
     *
     * @param password
     * @return
     */
    public static ResponseModel checkRepeatSubstring(String password) {
        ResponseModel responseModel = new ResponseModel();
        for (int i = 0; i < password.length() - 3; i++) {
            String s = password.substring(i, i + 3);
            String tempStr = password.substring(i + 3, password.length());
            if (tempStr.contains(s)) {
                responseModel.setCode(ResponseStatus.RESPONNSE_PASSWORD_CONTAINSSAMEMORECHAR.getCode());
                responseModel.setMessage(ResponseStatus.RESPONNSE_PASSWORD_CONTAINSSAMEMORECHAR.getMessage());
                return responseModel;
            }
        }
        responseModel.setCode(ResponseStatus.RESPONNSE_SUCCESS.getCode());
        responseModel.setMessage(ResponseStatus.RESPONNSE_SUCCESS.getMessage());
        return responseModel;
    }

    /***
     * 密码中不得包含与账号相同的字母组合，含大小写组合
     * @param username
     * @param password
     * @return
     */
    public static ResponseModel checkSaveChar(String username, String password) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(ResponseStatus.RESPONNSE_FAILD.getCode());
        responseModel.setMessage(ResponseStatus.RESPONNSE_FAILD.getMessage());
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            boolean contains = password.toUpperCase().contains(username.toUpperCase());
            responseModel.setCode(contains ? ResponseStatus.RESPONNSE_PASSWORD_CONTAINSSAMECHAR.getCode() : ResponseStatus.RESPONNSE_SUCCESS.getCode());
            responseModel.setMessage(contains ? ResponseStatus.RESPONNSE_PASSWORD_CONTAINSSAMECHAR.getMessage() : ResponseStatus.RESPONNSE_SUCCESS.getMessage());
        }
        return responseModel;
    }


}
