package vote.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import vote.pojo.Vote;
import vote.pojo.VoteContent;

public interface VoteService {
	void saveCreateVote(Vote vote,HttpServletRequest req);
	List<VoteContent> getVoteContent(HttpServletRequest req);
	void addContent(VoteContent content,HttpServletRequest req);
	void removeContent(int index,HttpServletRequest req);
}
