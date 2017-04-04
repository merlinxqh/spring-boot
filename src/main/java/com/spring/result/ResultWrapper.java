package com.spring.result;

public class ResultWrapper<T>{
   
	//返回结果 状态码 0成功,1失败
	private Integer code;
	
	//提示信息
	private String msg;
	
	//结果信息
	private T data;
	
	public ResultWrapper(Integer code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public ResultWrapper(Integer code,String msg,T data){
		this.code=code;
		this.msg=msg;
		this.data=data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
