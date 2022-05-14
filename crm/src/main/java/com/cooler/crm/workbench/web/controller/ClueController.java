package com.cooler.crm.workbench.web.controller;

import com.cooler.crm.settings.domain.User;
import com.cooler.crm.settings.service.UserService;
import com.cooler.crm.settings.service.impl.UserServiceImpl;
import com.cooler.crm.utils.DateTimeUtil;
import com.cooler.crm.utils.PrintJson;
import com.cooler.crm.utils.ServiceFactory;
import com.cooler.crm.utils.UUIDUtil;
import com.cooler.crm.vo.PaginationVO;
import com.cooler.crm.workbench.domain.*;
import com.cooler.crm.workbench.service.ActivityService;
import com.cooler.crm.workbench.service.ClueService;
import com.cooler.crm.workbench.service.impl.ActivityServiceImpl;
import com.cooler.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进线索控制器");

        String path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)) {

            getUserList(request, response);
        }else if ("/workbench/clue/save.do".equals(path)) {

            save(request, response);
        }else if ("/workbench/clue/pageList.do".equals(path)) {

            pageList(request, response);
        }else if ("/workbench/clue/detail.do".equals(path)) {

            detail(request, response);
        }else if ("/workbench/clue/delete.do".equals(path)) {

            delete(request, response);
        }else if ("/workbench/clue/getUserListAndClue.do".equals(path)) {

            getUserListAndClue(request, response);
        }else if ("/workbench/clue/update.do".equals(path)) {

            update(request, response);
        }else if ("/workbench/clue/getActivityListByClueId.do".equals(path)) {

            getActivityListByClueId(request, response);
        }else if ("/workbench/clue/unbund.do".equals(path)) {

            unbund(request, response);
        }else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {

            getActivityListByNameAndNotByClueId(request, response);
        }else if ("/workbench/clue/bund.do".equals(path)) {

            bund(request, response);
        }else if ("/workbench/clue/getActivityListByName.do".equals(path)) {

            getActivityListByName(request, response);
        }else if ("/workbench/clue/convert.do".equals(path)) {

            convert(request, response);
        }else if ("/workbench/clue/getRemarkListByCid.do".equals(path)) {
            getRemarkListByCid(request,response);
        }else if ("/workbench/clue/updateRemark.do".equals(path)) {
            updateRemark(request,response);
        }else if ("/workbench/clue/deleteRemark.do".equals(path)) {
            deleteRemark(request,response);
        }else if ("/workbench/clue/saveRemark.do".equals(path)) {
            saveRemark(request,response);
        }else if ("/workbench/clue/getActivityByNameAndNotByClueId.do".equals(path)) {
            getActivityByNameAndNotByClueId(request,response);
        }else if ("/workbench/clue/getActivityByName.do".equals(path)) {
            getActivityByName(request,response);
        }else if ("/workbench/clue/getChar.do".equals(path)) {
            getChar(request,response);
        }

    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        System.out.println("执行线索转换的操作");

        String clueId = request.getParameter("clueId");
        String flag = request.getParameter("flag");
        Tran t = null;
        String createBy =((User) request.getSession().getAttribute("user")).getName();
        if("a".equals(flag)){
            t = new Tran();

            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectDate= request.getParameter("expectDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
        }
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag1 = cs.convert(clueId,t,createBy);

        if(flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("查询市场活动列表（根据名称模糊查）");

        String aname = request.getParameter("aname");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByName(aname);

        PrintJson.printJsonObj(response,aList);

    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行关联市场活动的操作");

        String cid = request.getParameter("cid");
        String aids[] = request.getParameterValues("aid");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.bund(cid,aids);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("查询市场活动列表（根据名称模糊查询）");

        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");

        Map<String,String> map = new HashMap<String, String>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response,aList);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行解除关联的操作");

        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据线索ID查询关联的市场活动列表");

        String clueId = request.getParameter("clueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByClueId(clueId);

        PrintJson.printJsonObj(response,aList);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入修改线索的执行操作");

        String id = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String editTime = DateTimeUtil.getSysTime();
        String editBy =((User) request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setAddress(address);
        c.setWebsite(website);
        c.setState(state);
        c.setSource(source);
        c.setPhone(phone);
        c.setOwner(owner);
        c.setNextContactTime(nextContactTime);
        c.setMphone(mphone);
        c.setJob(job);
        c.setFullname(fullname);
        c.setEmail(email);
        c.setDescription(description);
        c.setEditBy(editBy);
        c.setEditTime(editTime);
        c.setContactSummary(contactSummary);
        c.setCompany(company);
        c.setAppellation(appellation);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.update(c);

        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserListAndClue(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询用户信息和根据线索id查询单条线索的操作");
        String id = request.getParameter("id");

        //userList可以使用之前写过的方法，所以这里只用通过id得到单条线索就够了
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());//!!!!!!!!!!!!!!!!特殊!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        List<User> uList = us.getUserList();

        ClueService as = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c = as.getSingleClue(id);

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("uList",uList);
        map.put("c",c);

        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行线索的删除操作");

        String ids[] = request.getParameterValues("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.delete(ids);

        PrintJson.printJsonFlag(response,flag);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        System.out.println("跳转到线索的详细信息页");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c = cs.detail(id);

        request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入查询线索信息列表的操作");

        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String phone = request.getParameter("phone");
        String mphone = request.getParameter("mphone");
        String company = request.getParameter("company");
        String source = request.getParameter("source");
        String state = request.getParameter("state");


        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);

        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);

        int skipCount = (pageNo - 1) * pageSize;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fullname",fullname);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("mphone",mphone);
        map.put("company",company);
        map.put("source",source);
        map.put("state",state);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        map.put("appellation",appellation);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        PaginationVO<Clue> vo = cs.pageList(map);
        PrintJson.printJsonObj(response,vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行线索的添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createTime = DateTimeUtil.getSysTime();
        String createBy =((User) request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setAddress(address);
        c.setWebsite(website);
        c.setState(state);
        c.setSource(source);
        c.setPhone(phone);
        c.setOwner(owner);
        c.setNextContactTime(nextContactTime);
        c.setMphone(mphone);
        c.setJob(job);
        c.setFullname(fullname);
        c.setEmail(email);
        c.setDescription(description);
        c.setCreateTime(createTime);
        c.setCreateBy(createBy);
        c.setContactSummary(contactSummary);
        c.setCompany(company);
        c.setAppellation(appellation);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response,uList);

    }

    private void getChar(HttpServletRequest request, HttpServletResponse response) {
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Map<String,Object> map=cs.getCharts();
        PrintJson.printJsonObj(response,map);
    }

    private void getActivityByName(HttpServletRequest request, HttpServletResponse response) {
        String aname=request.getParameter("aname");
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> aList=cs.getActivityByName(aname);
        PrintJson.printJsonObj(response,aList);
    }

    private void getActivityByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到绑定活动中的查询活动的操作");
        String clueId =request.getParameter("clueId");
        String  aname=request.getParameter("aname");

        Map<String ,String> map=new HashMap<String, String>();
        map.put("clueId",clueId);
        map.put("aname",aname);

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> list=cs.getActivityByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response,list);

    }


    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到线索中的 备注添加操作");
        String noteContent = request.getParameter("noteContent");
        String clueId = request.getParameter("clueId");
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String id = UUIDUtil.getUUID();
        String editFlag="0";

        ClueRemark cr=new ClueRemark();

        cr.setClueId(clueId);
        cr.setNoteContent(noteContent);
        cr.setCreateTime(createTime);
        cr.setCreateBy(createBy);
        cr.setEditFlag(editFlag);
        cr.setId(id);

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag=cs.saveRemark(cr);//这里使用动态代理，所以接着要去Service接口中写方法
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("cr",cr);
        map.put("success",flag);

        PrintJson.printJsonObj(response,map);

    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注的操作");
        String id=request.getParameter("id");

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=cs.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到更改线索的备注信息操作");
        String id= request.getParameter("id");
        String noteContent=request.getParameter("noteContent");

        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();

        String editFlag="1";

        ClueRemark cr=new ClueRemark();

        cr.setId(id);//这里之所以设置id是为了将ar传回后端数据库，根据id进行修改操作
        cr.setNoteContent(noteContent);
        cr.setEditTime(editTime);
        cr.setEditBy(editBy);
        cr.setEditFlag(editFlag);

        ClueService as= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=as.updateRemark(cr); //这里使用动态代理，所以接着要去Service接口中写方法


        Map<String,Object> map=new HashMap<String, Object>();
        map.put("cr",cr);
        map.put("success",flag);

        PrintJson.printJsonObj(response,map);




    }

    private void getRemarkListByCid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到根据线索id找到对应的全部备注信息");

        String clueId=request.getParameter("clueId");

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<ClueRemark> crList =cs.getRemarkListByCid(clueId);
        PrintJson.printJsonObj(response,crList);
    }


    //总结一下：使用超链接跳转到详细信息页面的思路。思路是很重要的
    /*
    在某些字上面加上超链接之后，然后就去web.xml注册路径。
    然后去controller中判断路径，然后调用新定义的大方法，然后在大方法里面写小方法和请求转发
    其中小方法是调用数据库得到你需要的值。
     */

}
