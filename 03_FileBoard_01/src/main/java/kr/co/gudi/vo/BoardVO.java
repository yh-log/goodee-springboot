package kr.co.gudi.vo;

import java.sql.Date;

public class BoardVO {
	
	private int idx;
	private String user_name;
	private String subject;
	private String context;
	private int bHit;
	private Date reg_date;
	
	public int getIdx() {
		return idx;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getSubject() {
		return subject;
	}
	public String getContext() {
		return context;
	}
	public int getbHit() {
		return bHit;
	}
	public Date getReg_date() {
		return reg_date;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public void setbHit(int bHit) {
		this.bHit = bHit;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

}