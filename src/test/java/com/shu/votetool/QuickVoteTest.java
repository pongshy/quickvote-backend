package com.shu.votetool;

import static org.junit.Assert.assertTrue;

import com.shu.votetool.dao.UserDOMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class QuickVoteTest
{
    /**
     * Rigorous Test :-)
     */
    @Resource
    private UserDOMapper userDOMapper;

    @Test
    public void shouldAnswerWithTrue()
    {
        List<String> openid = new ArrayList<>();

        openid.add("oQKKb5RSR3SAbjxbHJnzMxmCnt1k");
        openid.add("oQKKb5Z-ogyR8_hH9bK-fUA2I5j0");

        assertTrue( true );
    }
}
