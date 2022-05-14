package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);

    int updateRemark(ClueRemark cr);

    int deleteById(String id);

    int saveRemark(ClueRemark cr);
    List<ClueRemark> getRemarkListByCid(String clueId);

    int getCountByCids(String[] ids);
    int deleteByCids(String[] ids);
}
