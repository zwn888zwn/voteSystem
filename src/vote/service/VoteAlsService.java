package vote.service;

import java.util.List;
import java.util.Map;

public interface VoteAlsService {
	public Map<String, Integer> importDataFromDBAndAls(int voteid);
	public List<Map.Entry<String, Integer>> getConfidence(int voteId,String target,String str);
	public int getOneItemCount(int voteId,String vName,String vResult);
	public int getVoteCount(int voteId) ;
}
