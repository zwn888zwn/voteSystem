package vote.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import vote.pojo.Vote;

public interface ShowVoteService {
	public List getNewestVote();
	public List getHotVote();
	public List getImageUrl(HttpServletRequest req,Vote vote,List outList) throws Exception;
	public List getMyVote();
	public boolean updateVote(long id,int status);
	public boolean deleteVote(long id);
	public int getCreateCount();
	public int getJoinCount();
	
	public List queryTalbe(int voteId,int subT1,int subT2);
}
