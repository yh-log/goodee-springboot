package kr.co.gudi.dto;

public class FileDTO {
	/* 공부용으로만 남기고 업무에서는 이렇게 하면 안됨!
	 * create table files(
	file_idx int(8) auto_increment primary key
	,idx int(8)
	,ori_filename varchar(50)
	,new_filename varchar(100)
	,constraint fk_bbs_idx foreign key(idx) references bbs(idx) on delete cascade
);
	 * */
	private int file_idx;
	private int idx;
	private String ori_filename;
	private String new_filename;
	public int getFile_idx() {
		return file_idx;
	}
	public void setFile_idx(int file_idx) {
		this.file_idx = file_idx;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getOri_filename() {
		return ori_filename;
	}
	public void setOri_filename(String ori_filename) {
		this.ori_filename = ori_filename;
	}
	public String getNew_filename() {
		return new_filename;
	}
	public void setNew_filename(String new_filename) {
		this.new_filename = new_filename;
	}
	
	

}
