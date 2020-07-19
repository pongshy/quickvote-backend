package com.shu.votetool.dao;

import com.shu.votetool.model.entity.VoterDO;
import com.shu.votetool.model.entity.VoterDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoterDOMapper {
    int countByExample(VoterDOExample example);

    int deleteByExample(VoterDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoterDO record);

    int insertSelective(VoterDO record);

    List<VoterDO> selectByExample(VoterDOExample example);

    VoterDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoterDO record, @Param("example") VoterDOExample example);

    int updateByExample(@Param("record") VoterDO record, @Param("example") VoterDOExample example);

    int updateByPrimaryKeySelective(VoterDO record);

    int updateByPrimaryKey(VoterDO record);

    List<String> selectUnvoteBy(Integer id);
}