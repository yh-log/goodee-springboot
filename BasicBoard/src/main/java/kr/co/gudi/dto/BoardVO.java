package kr.co.gudi.dto;

import java.io.File;
import java.sql.Date;

public class BoardVO extends ResultVO{
	private int idx;
	private String user_name;
	private String subject;
	private String content;
	private int bHit;
	private Date reg_date;
	private Date update_date;
	private String bbs_yn = "Y";
	private int like = 0;
	private File file;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getbHit() {
		return bHit;
	}
	public void setbHit(int bHit) {
		this.bHit = bHit;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getBbs_yn() {
		return bbs_yn;
	}
	public void setBbs_yn(String bbs_yn) {
		this.bbs_yn = bbs_yn;
	}
}
