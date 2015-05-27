package com.sdl.weblocator.extjs.grid;

import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.form.ComboBox;
import com.sdl.selenium.extjs3.form.TextArea;
import com.sdl.selenium.extjs3.form.TextField;
import com.sdl.selenium.extjs3.grid.EditorGridPanel;
import com.sdl.selenium.extjs3.grid.GridCell;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.utils.Utils;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class EditorGridPanelTest extends TestBase {

    private Window editorGridPanelWindow = new Window("EditorGridPanel Win");
    private EditorGridPanel editorGridPanel = new EditorGridPanel(editorGridPanelWindow, "common").setTitle("EditableGrid");
    private Button submitButton = new Button(editorGridPanelWindow, "Submit");

    @DataProvider
    public Object[][] createTestDP() {
        return new Object[][]{
                {1, TextField.class},
                {2, TextArea.class},
                {3, ComboBox.class}
        };
    }

    @BeforeMethod
    public void startTests() {
        showComponent("EditorGridPanel");
    }

    @AfterMethod
    public void endTests() {
        editorGridPanelWindow.close();
    }

    @Test
    public void testSelectRow() {
        assertTrue(editorGridPanel.selectRow(new GridCell(1, "Wake Robin", SearchType.EQUALS), new GridCell(2, "Trillium grandiflorum", SearchType.EQUALS)));
    }

    @Test
    public void testSelectRow1() {
        assertTrue(editorGridPanel.getGridCell(1, "Wake Robin").select());
        assertTrue(editorGridPanel.getGridCell(1, "Wake Robin", new GridCell(1, "Wake Robin", SearchType.EQUALS)).select());
    }

    @Test(dependsOnMethods = "testSelectRow", dataProvider = "createTestDP")
    public void testEditorType(int column, Class<? extends TextField> cls) {
        editorGridPanel.startEdit(1, column);
        TextField textField = editorGridPanel.getActiveEditor();
        assertTrue(cls.isAssignableFrom(textField.getClass()), textField.getClass() + " is not " + cls);
    }

    @Test(dependsOnMethods = "testEditorType")
    public void editGridPanel() {
        editorGridPanel.setRowValue(1, 1, "WebLocator Company");
        editorGridPanel.setRowValue(1, 2, "TestTest");
        editorGridPanel.setRowValue(1, 3, "Sun or Shade");
        String[] texRow = editorGridPanel.getRow(1);
        List<String> optionList = Arrays.asList(texRow);
        assertTrue(optionList.containsAll(Arrays.asList("WebLocator Company", "TestTest", "Sun or Shade")));
    }

    @Test(dependsOnMethods = "editGridPanel")
    public void scrollInGridPanel() {
        assertTrue(editorGridPanel.scrollBottom());
        assertTrue(editorGridPanel.scrollTop());
        assertTrue(editorGridPanel.scrollPageDown());
        assertTrue(editorGridPanel.scrollPageDown());
        assertTrue(editorGridPanel.scrollPageUp());
    }

    @Test(dependsOnMethods = "scrollInGridPanel")
    public void isSubmitButtonDisabled() {
        assertTrue(submitButton.isDisabled());
    }

    @Test(dependsOnMethods = "isSubmitButtonDisabled")
    public void deleteCharacters() {
        assertTrue(editorGridPanel.setRowValue(1, 2, "Este tare ce se in timpla"));
        assertTrue(editorGridPanel.deleteCharacters(1, 2, 4, 10));
        Utils.sleep(1000);
    }

    @Test(dependsOnMethods = "deleteCharacters")
    public void backspaceCharacters() {
        assertTrue(editorGridPanel.setRowValue(1, 2, "Essteee tare ce se in timpla"));
        assertTrue(editorGridPanel.backspaceCharacters(1, 2, 4, 0));
        Utils.sleep(1000);
    }

    @Test(dependsOnMethods = "backspaceCharacters")
    public void editGridPanelAndScrollWithoutClearCell() {
        for (int i = 1; i <= editorGridPanel.getCount(); i += 3) {
            assertTrue(editorGridPanel.appendRowValue(i, 1, "1"));
        }
        Utils.sleep(1000);
    }

    @Test(dependsOnMethods = "editGridPanelAndScrollWithoutClearCell")
    public void editGridPanelAndScrollClearCell() {
        for (int i = 1; i <= editorGridPanel.getCount(); i += 3) {
            assertTrue(editorGridPanel.setRowValue(i, 1, "1"));
        }
        Utils.sleep(1000);
    }

    @Test(dependsOnMethods = "editGridPanelAndScrollClearCell")
    public void assertRowEditorGridPanel() {
        ConditionManager conditionManager = new ConditionManager(1000);
        conditionManager.add(new RenderSuccessCondition(editorGridPanel.getRow(new GridCell(1, "Adder's-Tongue", SearchType.EQUALS), new GridCell(2, "Erythronium americanum", SearchType.EQUALS))));
        assertTrue(conditionManager.execute().isSuccess());
    }

    @Test(dependsOnMethods = "assertRowEditorGridPanel")
     public void rowSelect() {
        assertTrue(editorGridPanel.rowSelect("Spring-Beauty"));
    }

    @Test(dependsOnMethods = "rowSelect")
    public void rowSelectStartWith() {
        assertTrue(editorGridPanel.rowSelect("Spring", SearchType.STARTS_WITH));
    }

    @Test(dependsOnMethods = "rowSelectStartWith")
    public void rowSelectContains() {
        assertTrue(editorGridPanel.rowSelect("Beauty", 1, SearchType.CONTAINS));
    }

    @Test//(dependsOnMethods = "rowSelectContains")
    public void rowSelectMoreContains() {
        assertTrue(editorGridPanel.rowSelect("/Spring/Beauty", 1, SearchType.CONTAINS_ALL));
    }
}
