package com.sdl.selenium.bootstrap.table;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.utils.Storage;
import com.sdl.selenium.web.table.Table;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.Matchers.contains;

@Slf4j
public class TableSteps extends TestBase {

    @Autowired
    private Storage storage;
    private final Form form = new Form(null, "Form Table");
    private final Table table = new Table(form);

    @Given("I open bootstrap app")
    public void iOpenBootstrapApp() {
//        storage.set("email", "johncarter@mail.com");
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Then("I verify if table has values:")
    public void iVerifyIfTableHasValues(List<List<String>> values) {
//        Object url = storage.get("url");
//        log.info("url from storage: {}", url);
        List<List<String>> cellsText = table.getCellsText(1, 5);
        assertThatList("Actual values: ", cellsText, contains(values.toArray()));
    }

    @Then("I verify if table has object values:")
    public void iVerifyIfTableHasObjectValues(List<UserTO> values) {
        List<UserTO> cellsText = table.getCellsText(UserTO.class, 1, 5);
        assertThatList("Actual values: ", cellsText, contains(values.toArray()));
    }
}
