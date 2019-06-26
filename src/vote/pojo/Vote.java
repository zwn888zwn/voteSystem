package vote.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Vote {
	long voteID;
	String voteCreater;
	String mainTitle;
	Date date;
	int status ; //0:close 1:open 2:deleted
	List<VoteContent> allContent = new ArrayList<VoteContent>();
	public Vote(){
		this.status=1;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Vote(long id){
		this.voteID=id;
	}
	public long getVoteID() {
		return voteID;
	}
	public void setVoteID(long voteID) {
		this.voteID = voteID;
	}
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public String getVoteCreater() {
		return voteCreater;
	}
	public void setVoteCreater(String voteCreater) {
		this.voteCreater = voteCreater;
	}
	public List<VoteContent> getAllContent() {
		return allContent;
	}
	public void setAllContent(List<VoteContent> allContent) {
		this.allContent = allContent;
	}
}
