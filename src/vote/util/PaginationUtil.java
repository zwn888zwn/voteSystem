package vote.util;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class PaginationUtil extends HibernateUtil{
	int pageSize=8;
	public ModelAndView pagination(ModelAndView mv,String hql,String nowPage){
		int page;
		try{
			if(nowPage==null){
				page=1;
			}
			else{
				page=Integer.parseInt(nowPage);
			}
		}catch(NumberFormatException e){
			page=1;
		}

		
		mv.addObject("pageList",this.queryPage(hql, page, pageSize));
		mv.addObject("pageSize",Math.floor(this.listSize(hql)/(double)pageSize));
		mv.addObject("nowPage",page);
		mv.addObject("pageLink",mv.getViewName()+".do");
		
		return mv;
	}
	public ModelAndView SQLpagination(ModelAndView mv,String sql,String nowPage){
		int page;
		try{
			if(nowPage==null){
				page=1;
			}
			else{
				page=Integer.parseInt(nowPage);
			}
		}catch(NumberFormatException e){
			page=1;
		}

		
		mv.addObject("pageList",this.querySQLPage(sql, page, pageSize));
		mv.addObject("pageSize",Math.floor(this.SQLlistSize(sql)/(double)pageSize));
		mv.addObject("nowPage",page);
		mv.addObject("pageLink",mv.getViewName()+".do");
		
		return mv;
	}

}
