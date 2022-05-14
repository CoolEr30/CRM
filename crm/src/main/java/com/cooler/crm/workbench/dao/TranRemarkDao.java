package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.TranRemark;
import com.cooler.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkDao {
    int saveRemark(TranRemark trr);

    List<TranRemark> getRemarkListByTid(String transactionId);

    int updateRemark(TranRemark trr);

    int deleteById(String id);

    int getTotalByIds(String[] ids);

    int deleteByIds(String[] ids);
}
