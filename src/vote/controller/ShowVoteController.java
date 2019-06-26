package vote.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import vote.pojo.User;
import vote.pojo.Vote;
import vote.pojo.VoteType;
import vote.pojo.dao.VoteDao;
import vote.pojo.dao.VoteResultDao;
import vote.service.ShowVoteService;
import vote.service.VoteAlsService;
import vote.util.HibernateUtil;
import vote.util.PaginationUtil;
import vote.util.UserHolder;

@Controller
@RequestMapping("/showVote")
public class ShowVoteController {
	@Autowired
	VoteDao voteDao;
	@Autowired
	PaginationUtil pagination;
	@Autowired
	VoteResultDao voteResultDao;
	@Autowired
	ShowVoteService showVoteService;
	@Autowired
	VoteAlsService voteAlsService;
	
	@RequestMapping("/showAll.do")
	public ModelAndView showAll(HttpServletRequest req ){
		ModelAndView mv=new ModelAndView("showAll");
		String nowPage=req.getParameter("currentPage");
		String hql="select voteID,mainTitle from  Vote where status=1 order by voteID desc";
		pagination.pagination(mv, hql, nowPage);
		return mv;
	}
	@RequestMapping("/showAllResult.do")
	public ModelAndView showAllResult(HttpServletRequest req ){
		User user=UserHolder.getUser();
		String id =user.getId();
		ModelAndView mv=new ModelAndView("showAllResult");
		String nowPage=req.getParameter("currentPage");
		String sql="SELECT t.voteID,t.mainTitle FROM vote.t_vote t  join vote.t_voteresult t1   where t.voteID=t1.voteID and t1.userid='"+id+"' group by t.voteID order by t.voteID desc";
		pagination.SQLpagination(mv, sql, nowPage);
		return mv;
	}
	@RequestMapping("/showContent.do")
	public ModelAndView show(HttpServletRequest req,@RequestParam Long id){
		ModelAndView mv=new ModelAndView("showContent");
		Vote vote=voteDao.get(id);
		mv.addObject("vote",vote);
		return mv;
		
	}
	@RequestMapping("/saveVote.do")
	public ModelAndView saveVote(HttpServletRequest req){
		ModelAndView mv=new ModelAndView("redirect:showAll.do");
		Enumeration<String> enums = req.getParameterNames();
		String voteID=req.getParameter("voteID");
		String user=((User)req.getSession().getAttribute("userSession")).getId();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		//check非法投票
		while(enums.hasMoreElements()){
			String name=enums.nextElement();
			if(name.equals("voteID")){
				continue;
			}
			String[] values=req.getParameterValues(name);
			for(int i=0;i<values.length;i++){
				voteResultDao.save(Long.parseLong(voteID),uuid,user,name,values[i]);
			}
		}
		return mv;
		
	}
	@RequestMapping("/showResult.do")
	public ModelAndView showResult(HttpServletRequest req,@RequestParam Long id){
		ModelAndView mv=new ModelAndView("showResult");
		Vote vote=voteDao.get(id);
		List outList=voteResultDao.getVoteCountList(id);
/*		try {
			List imgUrlList=showVoteService.getImageUrl(req, vote, outList);
			mv.addObject("imgUrlList",imgUrlList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		mv.addObject("vote",vote);
		mv.addObject("countList", outList);
		

		return mv;
		
	}
	@RequestMapping("/showBlankResult.do")
	public ModelAndView showBlankResult(HttpServletRequest req,@RequestParam Long id){
		ModelAndView mv=new ModelAndView("showBlankResult");
		List outList=voteResultDao.getVoteBlankList(id);
		mv.addObject("blankList", outList);
		return mv;
		
	}
	@RequestMapping(value="/getCountOfVote.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCountOfVote(HttpServletRequest req){
		List list=showVoteService.getMyVote();
		JSONObject json=new JSONObject();
		JSONArray arry = JSONArray.fromObject(list);
		json.put("Rows", arry);
		json.put("Total", list.size());
		return json.toString();
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	//以下为分析模块所需请求
	@RequestMapping("/showAnalysisPage.do")
	public ModelAndView showAnalysisPage(HttpServletRequest req,@RequestParam Long id){
		ModelAndView mv=new ModelAndView("showAnalysisPage");
/*		List outList=voteResultDao.getVoteBlankList(id);
		mv.addObject("blankList", outList);*/
		return mv;
		
	}
	@RequestMapping(value="/getVoteSubtitle.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getVoteSubtitle(HttpServletRequest req){
		String id=req.getParameter("id");
		Vote vote=voteDao.get(Long.parseLong(id));
		JSONObject json=new JSONObject();
//		JSONArray arry = JSONArray.fromObject(list);
		json.put("vote", vote);
		return json.toString();
	}
	/** sql
	*  select tt1.voteName,tt1.voteResult, tt2.voteName, tt2.voteResult,count(*) from (SELECT * FROM `t_voteresult` t WHERE t.voteId=1 and (t.voteName=1 or t.voteName=2) ) tt1 join (SELECT * FROM `t_voteresult` t WHERE t.voteId=1 and (t.voteName=1 or t.voteName=2) ) tt2 where tt1.uuid=tt2.uuid and (tt1.voteName=1 and tt2.voteName=2) GROUP BY  tt1.voteResult, tt2.voteResult
	*
	*/
	@RequestMapping(value="/showVoteTable.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String showVoteTable(HttpServletRequest req){
		String id=req.getParameter("id");
		String subtitle1=req.getParameter("subtitle1");
		String subtitle2=req.getParameter("subtitle2");
		Vote vote=voteDao.get(Long.parseLong(id));
		JSONObject json=new JSONObject();
		int idToInt=Integer.parseInt(id)  ,  subT1=Integer.parseInt(subtitle1)  ,   subT2=Integer.parseInt(subtitle2);
		if(!subtitle1.equals(subtitle2)) {
			List list=this.showVoteService.queryTalbe(idToInt, subT1, subT2);
			json.put("vote", vote);
			json.put("table", list);
			

		}
		
		return json.toString();
	}
	//-------Apriori分析----------------------------------------------------------------------------------------
	
	@RequestMapping(value="/showVoteApiMap.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String showVoteApiMap(HttpServletRequest req){
		String id=req.getParameter("id");
		Vote vote=voteDao.get(Long.parseLong(id));
		JSONObject json=new JSONObject();
		Map<String, Integer> apriMap=this.voteAlsService.importDataFromDBAndAls(Integer.parseInt(id));
		json.put("apriMap", apriMap);
		json.put("size", this.voteAlsService.getVoteCount(Integer.parseInt(id)));
		json.put("vote", vote);
		
		return json.toString();
	}
	
	@RequestMapping(value="/showConfidence.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String showConfidence(HttpServletRequest req){
		String id=req.getParameter("id");
		String target=req.getParameter("target");
		String originStr=req.getParameter("originStr");
		Vote vote=voteDao.get(Long.parseLong(id));
		JSONObject json=new JSONObject();
		json.put("vote", vote);
		json.put("targetName", target);
		json.put("targetValue", this.voteAlsService.getOneItemCount(Integer.parseInt(id), target.split("-")[0], target.split("-")[1]));
		json.put("confidence", this.voteAlsService.getConfidence(Integer.parseInt(id), target, originStr));
		return json.toString();
	}

}
