package com.mycrawler.crawler.core.webporter.data;

import org.junit.Assert;
import org.junit.Test;

import com.mycrawler.autotest.base.BaseTest;

public class FileRawInputTest extends BaseTest {
    @Test
    public void testListFiles() {
        RawInput raw = new FileRawInput(rootDir);
        int expectedLeft = 2;
        int left = raw.getLeftCount();
        Assert.assertEquals(expectedLeft, left);
    }
}
