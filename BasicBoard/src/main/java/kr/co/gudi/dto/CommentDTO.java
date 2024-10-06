package kr.co.gudi.dto;

import java.sql.Date;

public class CommentDTO {
	
	private int com_idx;
	private int board_idx;
	private String member_id;
	private String comment;
	private Date reg_date;
	
	public int getCom_idx() {
		return com_idx;
	}
	public void setCom_idx(int com_idx) {
		this.com_idx = com_idx;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "CommentDTO [com_idx=" + com_idx + ", board_idx=" + board_idx + ", member_id=" + member_id + ", comment="
				+ comment + ", reg_date=" + reg_date + "]";
	}
	
	

}
