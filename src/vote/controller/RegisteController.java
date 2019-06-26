package vote.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vote.pojo.User;
import vote.service.UserService;

@Controller
public class RegisteController  { 

	@Autowired
	UserService userSerive;

	@RequestMapping(value="/registe.do",method=RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse resp) throws Exception {  
       ModelAndView mv = new ModelAndView();  
       mv.setViewName("registe");  
       return mv;  
    }  
	@RequestMapping(value="/registe.do",method=RequestMethod.POST)
	public ModelAndView submit(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute("User") @Valid User user) throws Exception{
		ModelAndView mv = new ModelAndView();  
		userSerive.saveUser(user);
	    mv.setViewName("success");  
	    return mv;
	}
	
	@ResponseBody
	@RequestMapping("/registe/check.do")
    public String check(HttpServletRequest req) throws Exception {
		String id=req.getParameter("id");
		User user=userSerive.getUserById(id);
		Map map=new HashMap();
		if(user==null){
			map.put("result", "0");
		}
		else{
			map.put("result", "1");
		}
		JSONObject json=JSONObject.fromObject(map);
		return json.toString();
	} 
	
}  
