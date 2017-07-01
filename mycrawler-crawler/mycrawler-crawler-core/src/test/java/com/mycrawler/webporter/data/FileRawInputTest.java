package com.mycrawler.webporter.data;

import org.junit.Assert;
import org.junit.Test;

import com.github.yrj.autotest.base.BaseTest;
import com.mycrawler.webporter.data.FileRawInput;
import com.mycrawler.webporter.data.RawInput;

public class FileRawInputTest extends BaseTest {
    @Test
    public void testListFiles() {
        RawInput raw = new FileRawInput(rootDir);
        int expectedLeft = 2;
        int left = raw.getLeftCount();
        Assert.assertEquals(expectedLeft, left);
    }
}
