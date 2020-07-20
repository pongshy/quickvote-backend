package com.shu.votetool;

import static org.junit.Assert.assertTrue;

import com.shu.votetool.dao.UserDOMapper;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.service.MessageService;
import com.shu.votetool.tool.WXApiTool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.IOException;
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
    private MessageService messageService;


    @Test
    public void shouldAnswerWithTrue() throws IOException, AllException {

        assertTrue( true );
    }
}
