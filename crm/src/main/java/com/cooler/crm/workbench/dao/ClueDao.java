package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.Activity;
import com.cooler.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int save(Clue c);

    List<Clue> getClueListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    Clue detail(String id);

    int delete(String[] ids);

    Clue getById(String id);

    int update(Clue c);

    int deleteClue(String clueId);
    int deleteNoConvert(String[] ids);

    Clue getSingleClue(String id);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
