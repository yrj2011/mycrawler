package com.brianway.webporter.data;

import com.brianway.webporter.BaseTest;
import com.mycrawler.webporter.data.FileRawInput;
import com.mycrawler.webporter.data.RawInput;

import org.junit.Assert;
import org.junit.Test;

public class FileRawInputTest extends BaseTest {
    @Test
    public void testListFiles() {
        RawInput raw = new FileRawInput(rootDir);
        int expectedLeft = 2;
        int left = raw.getLeftCount();
        Assert.assertEquals(expectedLeft, left);
    }
}
