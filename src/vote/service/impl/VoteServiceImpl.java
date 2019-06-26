package vote.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.Vote;
import vote.pojo.VoteContent;
import vote.pojo.dao.VoteDao;
import vote.pojo.dao.impl.VoteDaoImpl;
import vote.service.VoteService;
@Transactional
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	VoteDao voteDao;
	@Override
	public void saveCreateVote(Vote vote,HttpServletRequest req) {
		vote.setAllContent(getVoteContent(req));
		voteDao.save(vote);
		req.getSession().removeAttribute("mainTitles");
		req.getSession().removeAttribute("voteContentlist");
	}
	public List<VoteContent> getVoteContent(HttpServletRequest req){
		List<VoteContent> voteContentlist=null;
		Object obj=req.getSession().getAttribute("voteContentlist");
		if(obj!=null){
			voteContentlist=(List)obj;
		}
		else{
			voteContentlist=new ArrayList<VoteContent>();	
		}
		return voteContentlist;
	}
	public void addContent(VoteContent content,HttpServletRequest req){
		req.getSession().setAttribute("mainTitles", req.getParameter("mainTitle1"));
		List<VoteContent> voteContentlist =getVoteContent(req);
		voteContentlist.add(content);
		req.getSession().setAttribute("voteContentlist", voteContentlist);
		
	}
	public void removeContent(int index,HttpServletRequest req){
		List<VoteContent> voteContentlist  =getVoteContent(req);
		if(index<voteContentlist.size())
			voteContentlist.remove(index);	
	}
	/*public void showVote(HttpServletRequest req,long id){
		Vote vote=voteDao.get(id);
			
	}*/

}
