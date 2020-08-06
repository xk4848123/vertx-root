//package com.wanke.common.util;
//
//
//import com.wanke.common.constant.CommonMessageEnum;
//import com.wanke.common.dto.ResultDTO;
//
//
//public class ResultUtil {
//
//	final static String CODE_404 = "404";
//
//	final static String CODE_500 = "500";
//
//    private static ResultDTO<Object> successResult = new ResultDTO<Object>(true, null,
//            CommonMessageEnum.SUCCESS.getMsg(), CommonMessageEnum.SUCCESS.getCode(), 0);
//
//    private static ResultDTO<Object> failResult = new ResultDTO<Object>(false, null, CommonMessageEnum.FAIL.getMsg(),
//            CommonMessageEnum.FAIL.getCode(), 0);
//
//    private static ResultDTO<Object> fail_404_Result = new ResultDTO<Object>(false, null, "",
//    		CODE_404, 0);
//
//
//    static ResultDTO<Object> defaultSuccess() {
//        return successResult;
//    }
//
//    static ResultDTO<Object> defaultFail() {
//        return failResult;
//    }
//
//    public static ResultDTO<Object> getSuccess(Object object, Integer count) {
//        if (object == null) {
//            return defaultSuccess();
//        }
//        if (count == null) {
//        	 return success(object);
//		}
//        return success(object,count);
//    }
//
//    public static ResultDTO<Object> getFail(CommonMessageEnum commonMessageEnum) {
//        if (commonMessageEnum == null) {
//            return defaultFail();
//        }
//        return fail(commonMessageEnum);
//    }
//
//    static ResultDTO<Object> success(Object object, Integer count) {
//        ResultDTO<Object> successResult = new ResultDTO<Object>();
//        successResult.setFlag(true);
//        successResult.setData(object);
//
//        successResult.setCount(count);
//        successResult.setErrorMessage(CommonMessageEnum.SUCCESS.getMsg());
//        successResult.setStatusCode(CommonMessageEnum.SUCCESS.getCode());
//        return successResult;
//    }
//
//    static ResultDTO<Object> success(Object object) {
//        ResultDTO<Object> successResult = new ResultDTO<Object>();
//        successResult.setFlag(true);
//        successResult.setData(object);
//        successResult.setErrorMessage(CommonMessageEnum.SUCCESS.getMsg());
//        successResult.setStatusCode(CommonMessageEnum.SUCCESS.getCode());
//        return successResult;
//    }
//
//    static ResultDTO<Object> fail(CommonMessageEnum commonMessageEnum) {
//        ResultDTO<Object> failResult = new ResultDTO<Object>();
//        failResult.setFlag(false);
//        failResult.setErrorMessage(commonMessageEnum.getMsg());
//        failResult.setStatusCode(commonMessageEnum.getCode());
//        return failResult;
//    }
//
//    public static ResultDTO<Object> fail_404() {
//        return fail_404_Result;
//    }
//
//    public static ResultDTO<Object> fail_500(String msg) {
//    	return new ResultDTO<Object>(false, null, msg,CODE_500, 0);
//    }
//
//    public static ResultDTO<Object> getFail(String code, String msg) {
//    	return new ResultDTO<Object>(false, null, msg,code, 0);
//    }
//}
