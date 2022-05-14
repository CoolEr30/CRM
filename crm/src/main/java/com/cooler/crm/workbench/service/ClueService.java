package com.cooler.crm.workbench.service;

import com.cooler.crm.vo.PaginationVO;
import com.cooler.crm.workbench.domain.Activity;
import com.cooler.crm.workbench.domain.Clue;
import com.cooler.crm.workbench.domain.ClueRemark;
import com.cooler.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/4
 */
public interface ClueService {

    boolean save(Clue c);

    PaginationVO<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    boolean delete(String[] ids);

    Clue getSingleClue(String id);

    Map<String, Object> getUserListAndClue(String id);

    boolean update(Clue c);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);


    List<ClueRemark> getRemarkListByCid(String clueId);

    boolean updateRemark(ClueRemark cr);

    boolean deleteRemark(String id);

    boolean saveRemark(ClueRemark cr);


    List<Activity> getActivityByNameAndNotByClueId(Map<String, String> map);


    List<Activity> getActivityByName(String aname);

    Map<String, Object> getCharts();
}
