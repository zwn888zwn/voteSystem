package vote.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vote.pojo.User;
import vote.service.ShowVoteService;
import vote.service.UserService;
import vote.util.UserHolder;

@Controller
public class LoginController  { 

	@Autowired
	UserService userSerive;
	@Autowired
	ShowVoteService showVoteService;
	@RequestMapping(value="/index.do")
	public ModelAndView show(HttpServletRequest req){
		ModelAndView mv = new ModelAndView("index");  
		mv.addObject("newestVoteList", showVoteService.getNewestVote());
		mv.addObject("hotVoteList", showVoteService.getHotVote());
		mv.addObject("createCount", showVoteService.getCreateCount());
		mv.addObject("joinCount", showVoteService.getJoinCount());
		return mv; 
	}
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public ModelAndView showlogin(HttpServletRequest req){

		return new ModelAndView("login"); 
	}
	
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req,@RequestParam("username") String username,@RequestParam("password") String password) throws Exception {  
	   User user = userSerive.getUserById(username.trim());
       ModelAndView mv = new ModelAndView();  
       if(user==null){
    	   req.setAttribute("tips", "用户不存在");
    	   mv.setViewName("login");
       }
       else{
    	   if(!user.getPassword().equals(password)){
    		   req.setAttribute("tips", "密码错误");
        	   mv.setViewName("login");
    	   }
    	   else{
    		   req.getSession().setAttribute("userSession",user);
    		  
    		   mv.setViewName("redirect:index.do");
    		   //mv.addObject("newestVoteList", showVoteService.getNewestVote());
    		  // mv.addObject("hotVoteList", showVoteService.getHotVote());
    	   }
    	   
       }
       return mv;  
    }  
	@RequestMapping(value="/logout.do")
	public ModelAndView logout(HttpServletRequest req){
		if(req.getSession().getAttribute("userSession")!=null){
			req.getSession().removeAttribute("userSession");
		}
			
		return new ModelAndView("login");
	}

	

	
}  
