package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@Slf4j
public class TagFieldIntegrationTest extends TestBase {

    private final TagField tagField = new TagField(null, "Select a state:");
    private final TagField selectTagField = new TagField(null, "Select/add location:");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#form-tag", tagField);
    }

//    @Test
//    public void testException() {
////        selectTagField.setBaseCls("blalala");
//        Utils.sleep(1);
//
////        Utils.retry(10, () -> {
////            selectTagField.setValue("test");
////        });
//        String strings = RetryUtils.retryIfNotContains(0, "Kansasd", () -> {
//            selectTagField.click();
//            return selectTagField.getValue();
//        });
//        Utils.sleep(1);
//    }

    @Test
    public void tagTest() {
        assertThat(tagField.remove("California"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(new ArrayList<>()));
        long startMs = System.currentTimeMillis();
        assertThat(tagField.select("California", "Alaska"), is(true));
        long endMs = System.currentTimeMillis();
        log.info(String.format("select took %s ms", endMs - startMs));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Alaska")));
        startMs = System.currentTimeMillis();
        tagField.select("Alaska", "Florida");
        endMs = System.currentTimeMillis();
        log.info(String.format("select1 took %s ms", endMs - startMs));
    }

    @Test(dependsOnMethods = "tagTest")
    public void selectValuesTagTest() {
        assertThat(tagField.select("Delaware", "Florida"), is(true));
        assertThatList(tagField.getAllSelectedValues(), containsInAnyOrder(List.of("California", "Alaska", "Delaware", "Florida").toArray()));
    }

    @Test(dependsOnMethods = "selectValuesTagTest")
    public void removeValuesTagTest() {
        assertThat(tagField.remove("Alaska", "Delaware"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Florida")));
    }

    @Test(dependsOnMethods = "removeValuesTagTest")
    public void getAllValuesTagTest() {
        assertThat(tagField.getAllValues(), is(Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming")));
    }

    @Test(dependsOnMethods = "getAllValuesTagTest")
    public void removeAllSelectedValuesTagTest() {
        assertThat(tagField.removeAll(), is(true));
        assertThat(tagField.getAllSelectedValues().isEmpty(), is(true));
    }

//    @Test(dependsOnMethods = "getAllValuesTagTest")
//    public void addTagTest() {
//        assertThat(selectTagField.setValue("test"), is(true));
//        assertThat(selectTagField.getAllSelectedValues(), is(Arrays.asList("test")));
//    }
}
