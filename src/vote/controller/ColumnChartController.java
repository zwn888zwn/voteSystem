package vote.controller;

import java.awt.Font;  

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.jfree.chart.ChartColor;  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.CategoryAxis;  
import org.jfree.chart.axis.ValueAxis;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.chart.renderer.category.BarRenderer;  
import org.jfree.chart.servlet.ServletUtilities;  
import org.jfree.chart.title.TextTitle;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;  
import org.springframework.stereotype.Controller;  
import org.springframework.ui.ModelMap;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.servlet.ModelAndView;  
  
@Controller  
public class ColumnChartController {  
      
    @RequestMapping("/resultmap.do")  
    public String resultmap(){  
        return "/resultmap";  
    }  
      
    //显示柱状图  
    @RequestMapping(value = "/getColumnChart.do")  
    public ModelAndView getColumnChart(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        //1. 获得数据集合  
        CategoryDataset dataset = getDataSet();  
        //2. 创建柱状图  
        JFreeChart chart = ChartFactory.createBarChart3D("学生对教师授课满意度", // 图表标题  
                "选项名", // 目录轴的显示标签  
                "", // 数值轴的显示标签  
                dataset, // 数据集  
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直  
                false, // 是否显示图例(对于简单的柱状图必须是false)  
                false, // 是否生成工具  
                false // 是否生成URL链接  
                );  
        //3. 设置整个柱状图的颜色和文字（char对象的设置是针对整个图形的设置）  
        chart.setBackgroundPaint(ChartColor.WHITE); // 设置总的背景颜色  
          
        //4. 获得图形对象，并通过此对象对图形的颜色文字进行设置  
        CategoryPlot p = chart.getCategoryPlot();// 获得图表对象  
        p.setBackgroundPaint(ChartColor.lightGray);//图形背景颜色  
        p.setRangeGridlinePaint(ChartColor.WHITE);//图形表格颜色  
          
        //5. 设置柱子宽度  
        BarRenderer renderer = (BarRenderer)p.getRenderer();  
        renderer.setMaximumBarWidth(0.06);  
          
        //解决乱码问题  
        getChartByFont(chart);  
          
        //6. 将图形转换为图片，传到前台  
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        modelMap.put("chartURL", chartURL);  
        return new ModelAndView("resultmap",modelMap);  
    }  
      
    //设置文字样式  
    private static void getChartByFont(JFreeChart chart) {  
        //1. 图形标题文字设置  
        TextTitle textTitle = chart.getTitle();     
        textTitle.setFont(new Font("宋体",Font.BOLD,20));  
          
        //2. 图形X轴坐标文字的设置  
        CategoryPlot plot = (CategoryPlot) chart.getPlot();  
        CategoryAxis axis = plot.getDomainAxis();  
        axis.setLabelFont(new Font("宋体",Font.BOLD,22));  //设置X轴坐标上标题的文字  
        axis.setTickLabelFont(new Font("宋体",Font.BOLD,15));  //设置X轴坐标上的文字  
          
      //2. 图形Y轴坐标文字的设置  
        ValueAxis valueAxis = plot.getRangeAxis();  
        valueAxis.setLabelFont(new Font("宋体",Font.BOLD,15));  //设置Y轴坐标上标题的文字  
        valueAxis.setTickLabelFont(new Font("sans-serif",Font.BOLD,12));//设置Y轴坐标上的文字  
    }  
  
    // 获取一个演示用的组合数据集对象  
    private static CategoryDataset getDataSet() {  
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        dataset.addValue(40, "", "普通动物学");  
        dataset.addValue(50, "", "生物学");  
        dataset.addValue(60, "", "动物解剖学");  
        dataset.addValue(70, "", "生物理论课");  
        dataset.addValue(80, "", "动物理论课");  
        return dataset;  
    }  
}  