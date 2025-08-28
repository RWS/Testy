package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

    public static Function<String, Boolean> select(Duration duration, SearchType... searchTypes) {
        return value -> {
            WebLocator boundList = new WebLocator("x-boundlist").setExcludeClasses("x-masked").setVisibility(true);
            WebLocator option = new WebLocator(boundList).setTag("li").setText(value, searchTypes).setRender(duration).setInfoMessage(value);
            Boolean click = RetryUtils.retry(2, () -> {
                boolean doClick = option.doClick();
                return doClick && !option.ready(Duration.ofMillis(200));
            });
            return click;
        };
    }

    @Test
    public void testException() {
        tagField.doSelect(List.of("California"), select(Duration.ofSeconds(1), SearchType.EQUALS));

    }

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
