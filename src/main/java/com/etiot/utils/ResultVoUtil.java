/**
 * @company 西安一体物联网科技有限公司
 * @file ResultVoUtil.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.utils;

import com.etiot.vo.ResultVo;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ResultVoUtil {
	public static ResultVo success(Object object){
    	ResultVo resultVo = new ResultVo();
    	resultVo.setData(object);
    	resultVo.setCode(0);
    	resultVo.setMsg("成功");
        return resultVo;
    }

	public static ResultVo<Object> success(){
        return success(null);
    }

	public static ResultVo error(Integer code,String msg){
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
