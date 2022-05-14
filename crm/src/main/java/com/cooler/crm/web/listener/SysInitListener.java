package com.cooler.crm.web.listener;

import com.cooler.crm.settings.domain.DicValue;
import com.cooler.crm.settings.service.DicService;
import com.cooler.crm.settings.service.impl.DicServiceImpl;
import com.cooler.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * @author CoolEr
 * @create 2022/3/5
 */
public class SysInitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存处理数据字典开始");

        ServletContext application = event.getServletContext();

        //取数据字典
        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map = ds.getAll();
        Set<String> set = map.keySet();
        for(String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");



        //解析properties文件
        Map<String,String> pMap = new HashMap<String,String>();

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){

            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);

            pMap.put(key, value);

        }

        //将pMap保存到服务器缓存中
        application.setAttribute("pMap", pMap);
    }

}
