package kr.co.gudi.vo;

import org.apache.ibatis.type.Alias;

// 여기에 추가적인 내용이 없으면 클래스명이 곧 단축명이 된다.
@Alias("fileVo")
public class FilesVO {
	
	private int file_idx;
	private int idx;
	private String ori_filename;
	private String new_filename;
	
	public int getFile_idx() {
		return file_idx;
	}
	public int getIdx() {
		return idx;
	}
	public String getOri_filename() {
		return ori_filename;
	}
	public String getNew_filename() {
		return new_filename;
	}
	
	public void setFile_idx(int file_idx) {
		this.file_idx = file_idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setOri_filename(String ori_filename) {
		this.ori_filename = ori_filename;
	}
	public void setNew_filename(String new_filename) {
		this.new_filename = new_filename;
	}

}