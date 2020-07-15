package com.shu.votetool.dao;

import com.shu.votetool.model.entity.CandidateDO;
import com.shu.votetool.model.entity.CandidateDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CandidateDOMapper {
    int countByExample(CandidateDOExample example);

    int deleteByExample(CandidateDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CandidateDO record);

    int insertSelective(CandidateDO record);

    List<CandidateDO> selectByExample(CandidateDOExample example);

    CandidateDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CandidateDO record, @Param("example") CandidateDOExample example);

    int updateByExample(@Param("record") CandidateDO record, @Param("example") CandidateDOExample example);

    int updateByPrimaryKeySelective(CandidateDO record);

    int updateByPrimaryKey(CandidateDO record);
}