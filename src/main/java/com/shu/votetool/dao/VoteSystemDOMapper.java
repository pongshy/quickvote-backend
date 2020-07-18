package com.shu.votetool.dao;

import com.shu.votetool.model.entity.VoteSystemDO;
import com.shu.votetool.model.entity.VoteSystemDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteSystemDOMapper {
    int countByExample(VoteSystemDOExample example);

    int deleteByExample(VoteSystemDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteSystemDO record);

    int insertSelective(VoteSystemDO record);

    List<VoteSystemDO> selectByExample(VoteSystemDOExample example);

    VoteSystemDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteSystemDO record, @Param("example") VoteSystemDOExample example);

    int updateByExample(@Param("record") VoteSystemDO record, @Param("example") VoteSystemDOExample example);

    int updateByPrimaryKeySelective(VoteSystemDO record);

    int updateByPrimaryKey(VoteSystemDO record);

    List<Integer> selectVoteSystemIdByOpenid(String openid);
}