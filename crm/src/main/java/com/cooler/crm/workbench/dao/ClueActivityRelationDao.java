package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
    int getCountByCids(String[] ids);

    int deleteByCids(String[] ids);

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);
}
