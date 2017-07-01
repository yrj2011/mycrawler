package com.mycrawler.webporter.collector;

import java.io.File;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.mycrawler.collector.zhihu.processor.MemberURLTokenGenerator;

public class MemberURLTokenGeneratorTest extends BaseTest {

    @Test
    public void testGenerateURLTokens() {
        String folder = rootDir + "followee-folder/";
        String filePath = folder + MemberURLTokenGenerator.URLTOKEN_FILENAME;
        MemberURLTokenGenerator generator = new MemberURLTokenGenerator(folder, filePath);
        int tokenCount = 20;

        Set<String> tokens = generator.generateURLTokens();
        File file = new File(filePath);
        Assert.assertTrue(file.exists());
        Assert.assertEquals(tokenCount, tokens.size());
    }

}
