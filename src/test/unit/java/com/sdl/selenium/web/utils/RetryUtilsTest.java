package com.sdl.selenium.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class RetryUtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtilsTest.class);

    @Test
    public void test0() {
        Boolean actual = RetryUtils.retryIfNotSame(2, false, RetryUtilsTest::isLive);
        assertThat(actual, is(true));
    }

    public static boolean isLive() {
        return true;
    }

    @Test
    public void test1() {
        List<String> list = Arrays.asList("a", "b", "c");
        long startMs = System.currentTimeMillis();
        List<String> actual = RetryUtils.retryIfNotSame(3, list, RetryUtilsTest::getList);
        long endMs = System.currentTimeMillis();
        long time = endMs - startMs;
        LOGGER.info(String.format("retryIfNotSame took %s ms", time));
        assertThat(actual, containsInAnyOrder(getList().toArray()));
        assertThat("This test need during more time!", time, greaterThan(11L));
    }

    public static List<String> getList() {
        return Arrays.asList("a", "b");
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("a", "b", "c");
        long startMs = System.currentTimeMillis();
        List<String> actual = RetryUtils.retryIfNotSame(3, list, RetryUtilsTest::getList1);
        long endMs = System.currentTimeMillis();
        long time = endMs - startMs;
        LOGGER.info(String.format("retryIfNotSame took %s ms", time));
        assertThat(actual, containsInAnyOrder(getList1().toArray()));
        assertThat("This test need during more time!", time, greaterThan(11L));
    }

    public static List<String> getList1() {
        return Arrays.asList("a", "b", "c", "d");
    }

    @Test
    public void testListOfListEqualsPassed() {
        List<List<String>> expectedList = new ArrayList<>();
        expectedList.add(Arrays.asList("a", "c"));
        expectedList.add(Arrays.asList("b", "e"));
        List<List<String>> actual = RetryUtils.retryIfNotSame(2, expectedList, RetryUtilsTest::getListOfList);
        Utils.sleep(1);
    }

    public static List<List<String>> getListOfList() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("a", "c"));
        lists.add(Arrays.asList("b", "e"));
        return lists;
    }

    @Test
    public void testListOfListEqualsFailed() {
        List<List<String>> expectedList = new ArrayList<>();
        expectedList.add(Arrays.asList("a", "c"));
        expectedList.add(Arrays.asList("b", "e"));
        List<List<String>> actual = RetryUtils.retryIfNotSame(2, expectedList, RetryUtilsTest::getListOfList1);
        Utils.sleep(1);
    }

    public static List<List<String>> getListOfList1() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("a", ""));
        lists.add(Arrays.asList("b", "e"));
        return lists;
    }

    @Test
    public void testListOfList2EqualsPassed() {
        List<List<String>> expectedList = new ArrayList<>();
        expectedList.add(Arrays.asList("a", "c"));
        expectedList.add(Arrays.asList("b", "e"));
        List<List<String>> actual = RetryUtils.retryIfNotSame(2, expectedList, RetryUtilsTest::getListOfList2);
        Utils.sleep(1);
    }

    public static List<List<String>> getListOfList2() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("a", "c"));
        lists.add(Arrays.asList("b", "e"));
        lists.add(Arrays.asList("c", "d"));
        return lists;
    }

    @Test
    public void testListOfList3EqualsFailed() {
        List<List<String>> expectedList = new ArrayList<>();
        expectedList.add(Arrays.asList("a", "c"));
        expectedList.add(Arrays.asList("b", "e"));
        List<List<String>> actual = RetryUtils.retryIfNotSame(2, expectedList, RetryUtilsTest::getListOfList3);
        Utils.sleep(1);
    }

    public static List<List<String>> getListOfList3() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("a", "c"));
        lists.add(Arrays.asList("b", "d"));
        lists.add(Arrays.asList("b", "d"));
        return lists;
    }

    public static class Tet {
        public String t1;
        public String t2;
        public String t3;

        public Tet(String t1, String t2, String t3) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Tet)) return false;
            final Tet other = (Tet) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$t1 = this.t1;
            final Object other$t1 = other.t1;
            if (this$t1 == null ? other$t1 != null : !this$t1.equals(other$t1)) return false;
            final Object this$t2 = this.t2;
            final Object other$t2 = other.t2;
            if (this$t2 == null ? other$t2 != null : !this$t2.equals(other$t2)) return false;
            final Object this$t3 = this.t3;
            final Object other$t3 = other.t3;
            if (this$t3 == null ? other$t3 != null : !this$t3.equals(other$t3)) return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Tet;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $t1 = this.t1;
            result = result * PRIME + ($t1 == null ? 43 : $t1.hashCode());
            final Object $t2 = this.t2;
            result = result * PRIME + ($t2 == null ? 43 : $t2.hashCode());
            final Object $t3 = this.t3;
            result = result * PRIME + ($t3 == null ? 43 : $t3.hashCode());
            return result;
        }

        public String toString() {
            return "Tet(t1=" + this.t1 + ", t2=" + this.t2 + ", t3=" + this.t3 + ")";
        }
    }

    @Test
    public void test3() {
        List<Tet> list = List.of(
                new Tet("a", "b", "c"),
                new Tet("a", "b", "c")
        );
        List<Tet> actual = RetryUtils.retryIfNotSame(2, list, RetryUtilsTest::getListOfObject);
        Utils.sleep(1);
    }

    public static List<Tet> getListOfObject() {
        List<Tet> list = List.of(
                new Tet("a", "b", "c1"),
                new Tet("a", "b", "c")
        );
        return list;
    }

    @Test
    public void test4() {
        List<Tet> list = List.of(
                new Tet("a", "b", "c"),
                new Tet("a2", "b2", "c2"),
                new Tet("a1", "b1", "c1")
        );
        List<Tet> actual = RetryUtils.retryIfNotSame(2, list, RetryUtilsTest::getListOfObject1);
        Utils.sleep(1);
    }

    public static List<Tet> getListOfObject1() {
        return List.of(
                new Tet("a", "b", "c"),
                new Tet("a1", "b1", "c1"),
                new Tet("a2", "b2", "c2")
        );
    }
}
