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
import com.cooler.crm.workbench.service.ClueService;
import com.cooler.crm.workbench.service.CustomerService;
import com.cooler.crm.workbench.service.TranService;
import com.cooler.crm.workbench.service.impl.ClueServiceImpl;
import com.cooler.crm.workbench.service.impl.CustomerServiceImpl;
import com.cooler.crm.workbench.service.impl.TranServiceImpl;

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
 * @create 2022/3/7
 */
public class CustomerController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入客户控制器");

        String path = request.getServletPath();

        if("/workbench/customer/pageList.do".equals(path)) {

            pageList(request, response);
        }else if("/workbench/customer/getUserList.do".equals(path)) {

            getUserList(request, response);
        }else if("/workbench/customer/save.do".equals(path)) {

            save(request, response);
        }else if ("/workbench/customer/getUserListAndCustomer.do".equals(path)) {
            getUserListAndCustomer(request,response);
        }else if ("/workbench/customer/update.do".equals(path)) {
            update(request,response);
        }else if ("/workbench/customer/detail.do".equals(path)) {
            detail(request,response);
        }else if ("/workbench/customer/getRemarkListByCid.do".equals(path)) {
            getRemarkListByCid(request, response);
        }else if ("/workbench/customer/saveRemark.do".equals(path)) {
            saveRemark(request, response);
        }else if ("/workbench/customer/updateRemark.do".equals(path)) {
            updateRemark(request, response);
        }else if ("/workbench/customer/deleteRemark.do".equals(path)) {
            deleteRemark(request, response);
        }else if ("/workbench/customer/getTranListByCid.do".equals(path)) {
            getTranListByCid(request, response);
        }else if ("/workbench/customer/deleteTran.do".equals(path)) {
            deleteTran(request, response);
        }else if ("/workbench/customer/getContactsListByCid.do".equals(path)) {
            getContactsListByCid(request, response);
        }else if ("/workbench/customer/deleteContacts.do".equals(path)) {
            deleteContacts(request, response);
        }else if ("/workbench/customer/saveContacts.do".equals(path)) {
            saveContacts(request, response);
        }else if ("/workbench/customer/delete.do".equals(path)) {
            delete(request, response);
        }

    }


    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行客户的删除操作");

        String ids[] =request.getParameterValues("id");//这里的id是地址栏中传过来的id！！！！！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!！！！！！！！！！！！

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());


        boolean flag=cs.delete(ids);

        PrintJson.printJsonFlag(response,flag);
    }

    private void saveContacts(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到联系人的保存操作");
        String id = UUIDUtil.getUUID();
        String owner=request.getParameter("owner");
//        System.out.println(owner+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String source=request.getParameter("source");
        String fullname=request.getParameter("fullname");
        String appellation=request.getParameter("appellation");
        String job=request.getParameter("job");
        String mphone=request.getParameter("mphone");
        String email=request.getParameter("email");
        String birth=request.getParameter("birth");
        //而且我们前端传入的也是中文名字。但是我们后端需要的是id
        //所以我们这里需要处理
        String customerName=request.getParameter("customerName");//这个很特殊！！要传走，看是否存在这个客户！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！1

        String description=request.getParameter("description");
        String contactSummary=request.getParameter("contactSummary");
        String nextContactTime=request.getParameter("nextContactTime");
        String address=request.getParameter("address");

        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();

        Contacts contacts=new Contacts();

        contacts.setId(id);
        contacts.setOwner(owner);
        contacts.setSource(source);
        contacts.setFullname(fullname);
        contacts.setAppellation(appellation);
        contacts.setJob(job);
        contacts.setMphone(mphone);
        contacts.setEmail(email);
        contacts.setBirth(birth);

        contacts.setDescription(description);
        //t.setCustomerId();这里这个需要特殊处理。去判断。我们并没有从前传过来这个id
        contacts.setContactSummary(contactSummary);
        contacts.setNextContactTime(nextContactTime);
        contacts.setAddress(address);

        contacts.setCreateTime(createTime);
        contacts.setCreateBy(createBy);


        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        boolean flag=cs.saveContactsAndPanDuan(contacts,customerName);//这里不用上传createBy，因为t一定存在。而之前的convert方法中，t是不确定是否为null

        PrintJson.printJsonFlag(response,flag);
    }

    private void deleteContacts(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到联系人的删除操作");
        String id= request.getParameter("id");

        // System.out.println(id+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        boolean flag= cs.deleteContacts(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getContactsListByCid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到展现与该客户相关的所有联系人列表");
        String id=request.getParameter("id");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<Contacts> conList=cs.getContactsListByCid(id);
        PrintJson.printJsonObj(response,conList);
    }

    //这里使用特殊。使用了transervice的删除方法。。。。。。。。。。。!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void deleteTran(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到交易的删除操作");
        String id= request.getParameter("id");
        String ids[]={id};
        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag=ts.delete(ids);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getTranListByCid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到展现与该客户相关的所有交易列表");
        String id=request.getParameter("id");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<Tran> tList=cs.getTranListByCid(id);
        PrintJson.printJsonObj(response,tList);


    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注的操作");
        String id=request.getParameter("id");

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        boolean flag=cs.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行修改备注的操作");

        String id= request.getParameter("id");
        String noteContent=request.getParameter("noteContent");

        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();

        String editFlag="1";

        CustomerRemark cr=new CustomerRemark();

        cr.setId(id);//这里之所以设置id是为了将ar传回后端数据库，根据id进行修改操作
        cr.setNoteContent(noteContent);
        cr.setEditTime(editTime);
        cr.setEditBy(editBy);
        cr.setEditFlag(editFlag);

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        boolean flag=cs.updateRemark(cr); //这里使用动态代理，所以接着要去Service接口中写方法


        Map<String,Object> map=new HashMap<String, Object>();
        map.put("cr",cr);
        map.put("success",flag);

        PrintJson.printJsonObj(response,map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String noteContent = request.getParameter("noteContent");
        String customerId = request.getParameter("customerId");
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String id = UUIDUtil.getUUID();
        String editFlag="0";

        CustomerRemark cr=new CustomerRemark();

        cr.setCustomerId(customerId);
        cr.setNoteContent(noteContent);
        cr.setCreateTime(createTime);
        cr.setCreateBy(createBy);
        cr.setEditFlag(editFlag);
        cr.setId(id);

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        boolean flag=cs.saveRemark(cr);//这里使用动态代理，所以接着要去Service接口中写方法
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("cr",cr);
        map.put("success",flag);

        PrintJson.printJsonObj(response,map);
    }

    private void getRemarkListByCid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据客户id，取得备注信息列表");
        String customerId=request.getParameter("customerId");

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<CustomerRemark> crList =cs.getRemarkListByCid(customerId);
        PrintJson.printJsonObj(response,crList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到客户详细信息页面的操作");
        String  id =request.getParameter("id");

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());


        Customer c=cs.getCustomerById(id);


        request.setAttribute("c",c);

        request.getRequestDispatcher("/workbench/customer/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行客户修改后的保存操作");
        String id=request.getParameter("id");
        String owner = request.getParameter("owner");
        String  name = request.getParameter("name");
        String website = request.getParameter("website");
        String phone = request.getParameter("phone");
        String  description= request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String  address= request.getParameter("address");

        String editTime= DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();

        Customer customer=new Customer();

        customer.setId(id);
        customer.setOwner(owner);
        customer.setName(name);
        customer.setWebsite(website);
        customer.setPhone(phone);
        customer.setDescription(description);
        customer.setContactSummary(contactSummary);
        customer.setNextContactTime(nextContactTime);
        customer.setAddress(address);

        customer.setEditTime(editTime);
        customer.setEditBy(editBy);

        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        boolean flag=cs.update(customer);
        PrintJson.printJsonFlag(response,flag);

    }

    //下面的方法是为了修改客户信息。其中得到的单个customer是没有处理的。
    // 其owner是在前端通过下拉菜单比较处理得到owner的
    //中文，sql是没有处理的
    private void getUserListAndCustomer(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到修改客户信息的操作");

        String id=request.getParameter("id");
        //这里使用的还是Activity代理。正在这个代理的方法里面再使用接口中的方法
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        Map<String,Object> map=cs.getUserListAndCustomer(id);
        PrintJson.printJsonObj(response,map);

    }
    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行客户的添加操作");

        String id = UUIDUtil.getUUID();
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String createTime = DateTimeUtil.getSysTime();
        String createBy =((User) request.getSession().getAttribute("user")).getName();
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        String description = request.getParameter("description");

        Customer c = new Customer();
        c.setId(id);
        c.setAddress(address);
        c.setWebsite(website);
        c.setPhone(phone);
        c.setOwner(owner);
        c.setNextContactTime(nextContactTime);
        c.setName(name);
        c.setCreateTime(createTime);
        c.setCreateBy(createBy);
        c.setContactSummary(contactSummary);
        c.setDescription(description);

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response,uList);

    }


    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入查询线索信息列表的操作");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");


        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);

        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);

        int skipCount = (pageNo - 1) * pageSize;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        map.put("website",website);

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        PaginationVO<Customer> vo = cs.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }


}
