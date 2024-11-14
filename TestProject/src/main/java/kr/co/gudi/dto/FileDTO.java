package kr.co.gudi.dto;

public class FileDTO {
    private int file_idx;
    private int idx;  // 게시글 번호 (foreign key)
    private String ori_filename;
    private String new_filename;
    private String type;
    private String file_yn;
    private String file_url;
    
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFile_yn() {
		return file_yn;
	}
	public void setFile_yn(String file_yn) {
		this.file_yn = file_yn;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	@Override
	public String toString() {
		return "FileDTO [file_idx=" + file_idx + ", idx=" + idx + ", ori_filename=" + ori_filename + ", new_filename="
				+ new_filename + ", type=" + type + ", file_yn=" + file_yn + ", file_url=" + file_url + "]";
	}
    
    
    
    
    // Getters and Setters
}
