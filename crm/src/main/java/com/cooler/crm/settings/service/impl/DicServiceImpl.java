package com.cooler.crm.settings.service.impl;

import com.cooler.crm.settings.dao.DicTypeDao;
import com.cooler.crm.settings.dao.DicValueDao;
import com.cooler.crm.settings.domain.DicType;
import com.cooler.crm.settings.domain.DicValue;
import com.cooler.crm.settings.service.DicService;
import com.cooler.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/4
 */
public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();

        List<DicType> dtList = dicTypeDao.getTypeList();
        for(DicType dt : dtList){
            String code = dt.getCode();
           List<DicValue> dvList = dicValueDao.getListByCode(code);

           map.put(code+"List",dvList);
        }
        return map;
    }
}
