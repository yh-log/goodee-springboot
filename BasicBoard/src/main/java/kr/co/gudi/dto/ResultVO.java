package kr.co.gudi.dto;

// http 통신의 규약같은거
public class ResultVO {
	private int result_code;
	private String message;
	
	public int getResult_code() {
		return result_code;
	}
	public void setResult_code(int result_code) {
		this.result_code = result_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
