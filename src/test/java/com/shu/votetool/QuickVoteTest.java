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

    private String template_id = "sXy0g2ARYkRLlN1tlSFDsm-H5F1oOEj4kS0UhWYRp2s";

    private String AppId = "wx98f3a01b37dc2567";

    private String secret = "c73adbeb232f81ea604d554e8c4fabdf";

    @Test
    public void shouldAnswerWithTrue() throws IOException, AllException {
//        String access_token = WXApiTool.getAccessToken(AppId, secret);
//        String openid = "oQKKb5RSR3SAbjxbHJnzMxmCnt1k";
//        System.out.println(messageService.sendMsg(access_token, openid));

        assertTrue( true );
    }
}
