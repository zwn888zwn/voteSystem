package vote.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vote.pojo.User;
import vote.pojo.Vote;
import vote.pojo.VoteContent;
import vote.service.UserService;
import vote.service.VoteService;

@Controller
@RequestMapping("/createVote")
public class CreateVoteController {
	@Autowired
	UserService userService;
	@Autowired
	VoteService voteService;
	@RequestMapping("/index.do")
	public ModelAndView createVote(HttpServletRequest req){
		ModelAndView mv= new ModelAndView("createVote");
		return mv;
	}
	@RequestMapping("/add.do")
	public ModelAndView addVote(HttpServletRequest req,@ModelAttribute("VoteContent") VoteContent content){
		voteService.addContent(content, req);
		ModelAndView mv= new ModelAndView("redirect:/createVote/index.do");
		return mv;
	}
	@RequestMapping("/delete.do")
	public ModelAndView deleteVote(HttpServletRequest req,@RequestParam("listIndex") int listIndex ){
		voteService.removeContent(listIndex, req);
		ModelAndView mv= new ModelAndView("redirect:/createVote/index.do");
		//ModelAndView mv= new ModelAndView("createVote");	
		return mv;
	}
	@RequestMapping("/submit.do")
	public ModelAndView submitVote(HttpServletRequest req,@ModelAttribute("Vote")Vote vote){
		ModelAndView mv= new ModelAndView("success");
		vote.setVoteCreater(((User)req.getSession().getAttribute("userSession")).getId());
		voteService.saveCreateVote(vote,req);
		return mv;
	}
	
}
