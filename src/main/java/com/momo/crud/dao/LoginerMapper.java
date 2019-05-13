package com.momo.crud.dao;

import com.momo.crud.bean.Loginer;
import com.momo.crud.bean.LoginerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoginerMapper {
    int countByExample(LoginerExample example);

    int deleteByExample(LoginerExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(Loginer record);

    int insertSelective(Loginer record);

    List<Loginer> selectByExample(LoginerExample example);

    Loginer selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") Loginer record, @Param("example") LoginerExample example);

    int updateByExample(@Param("record") Loginer record, @Param("example") LoginerExample example);

    int updateByPrimaryKeySelective(Loginer record);

    int updateByPrimaryKey(Loginer record);
}