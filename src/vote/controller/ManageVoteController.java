package vote.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import vote.util.PaginationUtil;

@Controller
@RequestMapping("/manageVote")
public class ManageVoteController {
	@Autowired
	ShowVoteService showVoteService;

	@RequestMapping("/show.do")
	public ModelAndView showAll(HttpServletRequest req ){
		ModelAndView mv=new ModelAndView("showManage");

		return mv;
	}
	@RequestMapping(value="/get.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getData(HttpServletRequest req){
		List list=showVoteService.getMyVote();
		JSONObject json=new JSONObject();
		JSONArray arry = JSONArray.fromObject(list);
		json.put("Rows", arry);
		json.put("Total", list.size());
		return json.toString();
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest req){
		String id=req.getParameter("id");
		this.showVoteService.deleteVote(Long.parseLong(id));
		return null;
	}
	@RequestMapping("/update.do")
	@ResponseBody
	public String update(HttpServletRequest req){
		String id=req.getParameter("id");
		String status=req.getParameter("status");
		this.showVoteService.updateVote(Long.parseLong(id), Integer.parseInt(status));
		return null;
	}

	
	

}
