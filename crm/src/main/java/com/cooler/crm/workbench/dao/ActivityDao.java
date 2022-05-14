package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String name);

    List<Activity> getActivityListByCid(String id);

    List<Activity> getActivityListByAids(String[] ids);

    List<Activity> getActivityByName(String aname);

    List<Activity> getActivityByNameAndNotByClueId(Map<String, String> map);
}
