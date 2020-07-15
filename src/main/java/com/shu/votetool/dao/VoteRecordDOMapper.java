package com.shu.votetool.dao;

import com.shu.votetool.model.entity.VoteRecordDO;
import com.shu.votetool.model.entity.VoteRecordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteRecordDOMapper {
    int countByExample(VoteRecordDOExample example);

    int deleteByExample(VoteRecordDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteRecordDO record);

    int insertSelective(VoteRecordDO record);

    List<VoteRecordDO> selectByExample(VoteRecordDOExample example);

    VoteRecordDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteRecordDO record, @Param("example") VoteRecordDOExample example);

    int updateByExample(@Param("record") VoteRecordDO record, @Param("example") VoteRecordDOExample example);

    int updateByPrimaryKeySelective(VoteRecordDO record);

    int updateByPrimaryKey(VoteRecordDO record);
}