package com.todoist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.todoist.User.DateFormat;
import com.todoist.User.SortOrder;
import com.todoist.User.TimeFormat;

public class TodoistTest {

    private Todoist todoist;
    private User user;

    @Before
    // make sure the account is empty before we start testing
    public void deleteAllProjects() {
        todoist = new Todoist();
        user = todoist.login("peter.de.bruycker+todoist@gmail.com", "todoist-api-test");

        // delete all projects
        for (Project project : todoist.getProjects()) {
            todoist.deleteProject(project);
        }

        // delete all labels
        for (Label label : todoist.getLabels()) {
            todoist.deleteLabel(label);
        }
    }

    @Test
    public void user() {
        assertEquals("peter.de.bruycker+todoist@gmail.com", user.getEmail());
        assertEquals("todoist-api-test", user.getFullName());
        assertEquals(TimeFormat._24_HOUR, user.getTimeFormat());
        assertEquals(DateFormat.DDMMYYYY, user.getDateFormat());
        assertEquals(SortOrder.OLDEST_DATES_FIRST, user.getSortOrder());
        assertEquals(TimeZone.getTimeZone("Europe/Brussels"), user.getTimeZone());
        assertNull(user.getDefaultReminder());
    }

    @Test
    public void addProject_withName_defaultValues() {
        Project project = todoist.addProject("test");
        assertNotNull(project);
        assertEquals("test", project.getName());

        // default values
        assertEquals(Color.COLOR0, project.getColor());
        assertEquals(1, project.getIndent());
        assertEquals(1, project.getItem_order());

        // other properties
        assertFalse(project.isCollapsed());
    }

    @Test
    public void addProject_withOptionalParameters() {
        Project project = todoist.addProject("test", Color.COLOR4, 3, 2);
        assertNotNull(project);
        assertEquals("test", project.getName());

        // optional values
        assertEquals(Color.COLOR4, project.getColor());
        assertEquals(3, project.getIndent());
        assertEquals(2, project.getItem_order());

        // other properties
        assertFalse(project.isCollapsed());
    }

    @Test
    public void getProject_withUnknownId_returnsNull() {
        Project project = todoist.getProject(1L);
        assertNull(project);
    }

    @Test
    public void deleteProject() {
        Project project = todoist.addProject("test");

        todoist.deleteProject(project.getId());

        assertTrue(todoist.getProjects().isEmpty());
    }

    // label tests
    @Test
    public void getLabels() {
        Project project = todoist.addProject("test");
        todoist.addItem(project.getId(), "@label1 first item");
        todoist.addItem(project.getId(), "@label2 second item");
        todoist.addItem(project.getId(), "@label1 @label2 third item");

        List<Label> labels = todoist.getLabels();
        assertEquals(2, labels.size());
        assertEquals("label1", labels.get(0).getName());
        assertEquals("label2", labels.get(1).getName());
    }

    @Test
    public void deleteLabel() {
        Project project = todoist.addProject("test");
        Item item = todoist.addItem(project.getId(), "@label1 first item");
        todoist.deleteItems(item);

        todoist.deleteLabel("@label1");
        assertTrue(todoist.getLabels().isEmpty());
    }

    // item tests
    @Test
    public void addItem() {
        Project project = todoist.addProject("test");
        Item item = todoist.addItem(project.getId(), "this is the content!");
        System.out.println(item);
    }

    @Test
    public void deleteItems() {
        Project project = todoist.addProject("test");
        Item item0 = todoist.addItem(project.getId(), "first");
        Item item1 = todoist.addItem(project.getId(), "second");
        Item item2 = todoist.addItem(project.getId(), "third");

        assertEquals(3, todoist.getUncompletedItems(project).size());

        todoist.deleteItems(item0, item2);

        List<Item> items = todoist.getUncompletedItems(project);
        assertEquals(1, items.size());
        assertTrue(items.contains(item1));
    }

}
