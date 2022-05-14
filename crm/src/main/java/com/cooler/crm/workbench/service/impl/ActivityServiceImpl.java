package com.cooler.crm.workbench.service.impl;

import com.cooler.crm.settings.dao.UserDao;
import com.cooler.crm.settings.domain.User;
import com.cooler.crm.utils.SqlSessionUtil;
import com.cooler.crm.vo.PaginationVO;
import com.cooler.crm.workbench.dao.ActivityDao;
import com.cooler.crm.workbench.dao.ActivityRemarkDao;
import com.cooler.crm.workbench.dao.ClueActivityRelationDao;
import com.cooler.crm.workbench.dao.ContactsActivityRelationDao;
import com.cooler.crm.workbench.domain.Activity;
import com.cooler.crm.workbench.domain.ActivityRemark;
import com.cooler.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    private ClueActivityRelationDao clueActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    public boolean save(Activity a) {

        boolean flag = true;
        int count = activityDao.save(a);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotalByCondition(map);

        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    public boolean delete(String[] ids) {

        boolean flag = true;

        int count1 = activityRemarkDao.getCountByAids(ids);

        int count2 = activityRemarkDao.deleteByAids(ids);

        if(count1 != count2){

            flag = false;

        }
        int count3 = activityDao.delete(ids);
        if(count3 != ids.length){
            flag = false;
        }
        return flag;
    }

    public Map<String, Object> getUserListAndActivity(String id) {

        List<User> uList = userDao.getUserList();

        Activity a = activityDao.getById(id);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("uList",uList);
        map.put("a",a);

        return map;
    }

    public boolean update(Activity a) {
        boolean flag = true;
        int count = activityDao.update(a);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    public Activity detail(String id) {

        Activity a = activityDao.detail(id);

        return a;
    }

    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(activityId);

        return arList;
    }

    public boolean deleteRemark(String id) {

        boolean flag = true;

        int count = activityRemarkDao.deleteById(id);

        if(count != 1){
            flag = false;
        }
        return flag;
    }

    public boolean saveRemark(ActivityRemark ar) {

        boolean flag = true;
        int count = activityRemarkDao.saveRemark(ar);

        if(count!=1){
            flag = false;
        }

        return flag;
    }

    public boolean updateRemark(ActivityRemark ar) {

        boolean flag = true;
        int count = activityRemarkDao.updateRemark(ar);

        if(count!=1){
            flag = false;
        }

        return flag;
    }

    public List<Activity> getActivityListByClueId(String clueId) {

        List<Activity> aList = activityDao.getActivityListByClueId(clueId);

        return aList;
    }

    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {

        List<Activity> aList = activityDao.getActivityListByNameAndNotByClueId(map);

        return aList;
    }

    public List<Activity> getActivityListByName(String name) {

        List<Activity> aList = activityDao.getActivityListByName(name);

        return aList;
    }
}
