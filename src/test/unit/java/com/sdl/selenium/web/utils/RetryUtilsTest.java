package com.sdl.selenium.web.utils;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetryUtilsTest {

    @Test
    public void test0() {
        Boolean actual = RetryUtils.retryIfNotSame(2, false, () -> isLive());
        Utils.sleep(1);
    }

    @Test
    public void test1() {
        List<String> list = Arrays.asList("a", "b");
        List<String> actual = RetryUtils.retryIfNotSame(2, list, () -> getList());
        Utils.sleep(1);
    }

    @Test
    public void test2() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list = Arrays.asList("a", "b");
        lists.add(list);
        List<String> list2 = Arrays.asList("a1", "b1");
        lists.add(list2);
        List<List<String>> actual = RetryUtils.retryIfNotSame(2, lists, () -> getListOfList());
        Utils.sleep(1);
    }

    public static boolean isLive() {
        return true;
    }

    public static List<String> getList() {
        List<String> list = Arrays.asList("a", "b1");
        return list;
    }

    public static List<List<String>> getListOfList() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list = Arrays.asList("a", "b");
        lists.add(list);
        List<String> list2 = Arrays.asList("a1", "b2");
        lists.add(list2);
        return lists;
    }
}
