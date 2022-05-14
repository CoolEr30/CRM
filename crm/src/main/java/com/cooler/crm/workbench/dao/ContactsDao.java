package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {

    int save(Contacts con);

    List<Contacts> getContactsListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    List<Contacts> getContactsByName(String cname);


    List<Contacts> getContactsListByCid(String id);

    int deleteContacts(String id);

    int getCountByCids(String[] ids);

    int deleteByCids(String[] ids);

    Contacts getById(String id);

    int update(Contacts contacts);

    int delete(String[] ids);

    Contacts detail(String id);

}
