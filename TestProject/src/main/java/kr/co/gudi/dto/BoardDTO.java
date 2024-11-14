package kr.co.gudi.dto;

import java.sql.Date;
import java.util.List;

public class BoardDTO {
    private int idx;
    private String user_name;
    private String subject;
    private String content;
    private int bHit;
    private Date reg_date;
    private Date update_date;
    private String bbs_yn;
    private List<FileDTO> imgs;
    private String days;
    private String tags;
    private int person;
    private int minute;
    private int km;
    private String roadAddr;
    private String sigungu;
    private String sido;
    private String shotsido;
    
    
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getPerson() {
		return person;
	}
	public void setPerson(int person) {
		this.person = person;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public String getRoadAddr() {
		return roadAddr;
	}
	public void setRoadAddr(String roadAddr) {
		this.roadAddr = roadAddr;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getShotsido() {
		return shotsido;
	}
	public void setShotsido(String shotsido) {
		this.shotsido = shotsido;
	}
	public List<FileDTO> getImgs() {
		return imgs;
	}
	public void setImgs(List<FileDTO> imgs) {
		this.imgs = imgs;
	}
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
	@Override
	public String toString() {
		return "BoardDTO [idx=" + idx + ", user_name=" + user_name + ", subject=" + subject + ", content=" + content
				+ ", bHit=" + bHit + ", reg_date=" + reg_date + ", update_date=" + update_date + ", bbs_yn=" + bbs_yn
				+ ", imgs=" + imgs.toString() + ", days=" + days + ", tags=" + tags + ", person=" + person + ", minute=" + minute
				+ ", km=" + km + ", roadAddr=" + roadAddr + ", sigungu=" + sigungu + ", sido=" + sido + ", shotsido="
				+ shotsido + "]";
	}
	

	
    
    

    // Getters and Setters
}

