package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory th);

    List<TranHistory> getHistoryListById(String tranId);

    int getTotalById(String[] ids);

    int delete(String[] ids);
}
