package kr.co.gudi.dto;

public class FileDTO {
	
	private Integer file_idx;
	private Integer idx;
	private String ori_filename;
	private String new_filename;
	private String type;
	private String file_yn = "Y";
	
	
	public String getFile_yn() {
		return file_yn;
	}
	public void setFile_yn(String file_yn) {
		this.file_yn = file_yn;
	}
	public Integer getFile_idx() {
		return file_idx;
	}
	public void setFile_idx(Integer file_idx) {
		this.file_idx = file_idx;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "FileDTO [file_idx=" + file_idx + ", idx=" + idx + ", ori_filename=" + ori_filename + ", new_filename="
				+ new_filename + ", type=" + type + ", file_yn=" + file_yn + "]";
	}

}
