package com.cooler.crm.settings.dao;

import com.cooler.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @author CoolEr
 * @create 2022/3/4
 */
public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
