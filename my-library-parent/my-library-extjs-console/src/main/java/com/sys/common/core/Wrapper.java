package com.sys.common.core;

/**
 * 系统执行结果封装对象
 * 
 * @version 1.0
 */
public class Wrapper {
	// 结果对象封装,包含所有的数据结构
	private Object result;

	// 执行结果信息
	private String message;

	// 执行sum统计
	private String sumMessage;

	// 是否执行成功
	private boolean isSuccess = true;

	// 执行失败所抛出的异常
	private Exception e;

	public Object getResult() {
		return result;
	}

	public Wrapper() {}

	public Wrapper(Object result, String message) {
		this.result = result;
		this.message = message;
	}

	public Wrapper(Object result, String message, String sumMessage) {
		super();
		this.result = result;
		this.message = message;
		this.sumMessage = sumMessage;
	}

	public String getSumMessage() {
		return sumMessage;
	}

	public void setSumMessage(String sumMessage) {
		this.sumMessage = sumMessage;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
}
