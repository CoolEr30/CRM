package com.cooler.crm.workbench.dao;

import com.cooler.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public interface ActivityRemarkDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);

}
