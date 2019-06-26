package vote.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VoteContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4489385346881860982L;
	long contentId;
	VoteType contentType;
	String subTitle;
	VoteContent(){}
	public VoteContent(long contentId){
		this.contentId=contentId;
	}
	List<String> contentText =new ArrayList<String>();
	public long getContentId() {
		return contentId;
	}
	public void setContentId(long contentId) {
		this.contentId = contentId;
	}
	public VoteType getContentType() {
		return contentType;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public void setContentType(VoteType contentType) {
		this.contentType = contentType;
	}
	public List<String> getContentText() {
		return contentText;
	}
	public void setContentText(List<String> contentText) {
		this.contentText = contentText;
	}

}
