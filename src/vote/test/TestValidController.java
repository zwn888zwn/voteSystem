package vote.test;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestValidController {
	
	
    @RequestMapping("/valids.do")  
    public ModelAndView valid(@ModelAttribute("vm") @Valid ValidModel vm, BindingResult result) {  
        if (result.hasErrors()) {  
            return new ModelAndView("success");  
        }  
      
        return new ModelAndView("validResult");  
    }  
 
}
