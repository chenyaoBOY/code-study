package org.study.mapper;

import org.study.mapper.PocAdminConfig;

public interface PocAdminConfigDao {
    int deleteByPrimaryKey(Long id);

    int insert(PocAdminConfig record);

    int insertSelective(PocAdminConfig record);

    PocAdminConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PocAdminConfig record);

    int updateByPrimaryKey(PocAdminConfig record);
}