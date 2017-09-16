package io.nem.mpspdf.msg.response;

public class ErrorMessageResponse {

	private String message;
	private int errorCode;
	private String stackTraceResponse;

	public ErrorMessageResponse(int status, String message) {
		this.errorCode = status;
		this.message = message;
	}

	public String getStackTraceResponse() {
		return stackTraceResponse;
	}

	public void setStackTraceResponse(String stackTraceResponse) {
		this.stackTraceResponse = stackTraceResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
