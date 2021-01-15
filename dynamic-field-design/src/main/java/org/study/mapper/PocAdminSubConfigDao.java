package org.study.mapper;

import org.study.mapper.PocAdminSubConfig;

import java.util.List;
import java.util.Map;

public interface PocAdminSubConfigDao {
    int deleteByPrimaryKey(Long id);

    int insert(PocAdminSubConfig record);

    int insertSelective(PocAdminSubConfig record);

    PocAdminSubConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PocAdminSubConfig record);

    int updateByPrimaryKey(PocAdminSubConfig record);

    List<Map<String,Object>> getModelResult(List<String> model);
}