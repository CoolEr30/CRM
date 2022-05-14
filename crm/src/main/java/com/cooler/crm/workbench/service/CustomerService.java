package com.cooler.crm.workbench.service;

import com.cooler.crm.vo.PaginationVO;
import com.cooler.crm.workbench.domain.*;

import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/7
 */
public interface CustomerService {

//    List<String> getCustomerName(String name);

    PaginationVO<Customer> pageList(Map<String, Object> map);

    boolean save(Customer c);

    Map<String, Object> getUserListAndCustomer(String id);

    boolean update(Customer customer);

    Customer getCustomerById(String id);

    List<CustomerRemark> getRemarkListByCid(String customerId);

    boolean saveRemark(CustomerRemark cr);

    boolean updateRemark(CustomerRemark cr);

    boolean deleteRemark(String id);

    List<Tran> getTranListByCid(String id);

    List<Contacts> getContactsListByCid(String id);

    boolean deleteContacts(String id);

    boolean saveContactsAndPanDuan(Contacts contacts, String customerName);

    boolean delete(String[] ids);
}
