package com.cooler.crm.settings.service;

import com.cooler.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/4
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();

}
