package com.todoist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.codehaus.jackson.type.TypeReference;

import com.todoist.Item.Priority;

/**
 * Entrypoint.
 * 
 * @author Peter De Bruycker
 */
public class Todoist extends TodoistBase {

    private User user;

    private void checkLoggedIn() {
        if (!isLoggedIn()) {
            throw new TodoistException("Not logged in");
        }
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public User login(final String email, final String password) throws TodoistException {
        return user = execute(request("login", new HashMap<String, Object>() {
            {
                put("email", email);
                put("password", password);
            }
        }, true), User.class);
    }

    public List<Project> getProjects() {
        checkLoggedIn();

        return execute(request("getProjects", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
            }
        }), new TypeReference<List<Project>>() {
        });
    }

    public Project getProject(final Long id) {
        checkLoggedIn();

        return execute(request("getProject", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("project_id", id);
            }
        }), Project.class);
    }

    // with name
    public Project addProject(final String name) {
        return addProject(name, null, null, null);
    }

    // with optional parameters
    public Project addProject(final String name, final Color color, final Integer indent, final Integer order) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        checkLoggedIn();

        return execute(request("addProject", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("name", name);
                if (name != null && name.trim().length() > 0) {
                    put("name", name);
                }
                if (color != null) {
                    put("color", color.getIndex());
                }
                if (indent != null) {
                    put("indent", indent);
                }
                if (order != null) {
                    put("order", order);
                }
            }
        }), Project.class);
    }

    public Project updateProject(final long id, final String name, final Color color, final Integer indent) {
        checkLoggedIn();

        return execute(request("updateProject", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("project_id", "" + id);
                if (name != null && name.trim().length() > 0) {
                    put("name", name);
                }
                if (color != null) {
                    put("color", color.getIndex());
                }
                if (indent != null) {
                    put("indent", indent);
                }
            }
        }), Project.class);
    }

    public Project updateProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }

        return updateProject(project.getId(), project.getName(), project.getColor(), project.getIndent());
    }

    public void deleteProject(final long id) {
        checkLoggedIn();

        execute(request("deleteProject", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("project_id", id);
            }
        }));
    }

    public void deleteProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }

        deleteProject(project.getId());
    }

    // labels
    public List<Label> getLabels() {
        TreeMap<String, Label> labels = execute(request("getLabels", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
            }
        }), new TypeReference<TreeMap<String, Label>>() {
        });

        return new ArrayList<Label>(labels.values());
    }

    // TODO getLabels by project?
    public List<Label> getLabels(long projectId) {
        return null;
    }

    public void updateLabel(final String oldName, final String newName) {
        execute(request("updateLabel", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("old_name", oldName);
                put("new_name", newName);
            }
        }));
    }

    public void deleteLabel(final String name) {
        execute(request("deleteLabel", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("name", name);
            }
        }));
    }

    public void deleteLabel(final Label label) {
        if (label == null) {
            throw new IllegalArgumentException("label cannot be null");
        }

        deleteLabel(label.getName());
    }

    // items
    public Item addItem(final long projectId, final String content) {
        return addItem(projectId, content, null, null);
    }

    // with optional parameters
    public Item addItem(final long projectId, final String content, final String dateString, final Priority priority) {
        return execute(request("addItem", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("project_id", projectId);
                put("content", content);
                if (dateString != null && dateString.trim().length() > 0) {
                    put("date_string", dateString);
                }
                put("priority", priority == null ? Priority.NATURAL.getValue() : priority.getValue());
            }
        }), Item.class);
    }

    public void deleteItems(final long... ids) {
        execute(request("deleteItems", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("ids", asJson(ids));
            }
        }));
    }

    public void deleteItems(final Item... items) {
        if (items == null) {
            throw new IllegalArgumentException("items cannot be null");
        }

        // TODO use JsonView to transform ids
        long[] ids = new long[items.length];
        for (int i = 0; i < items.length; i++) {
            ids[i] = items[i].getId();
        }

        deleteItems(ids);
    }

    public List<Item> getUncompletedItems(final long projectId) {
        return execute(request("getUncompletedItems", new HashMap<String, Object>() {
            {
                put("token", user.getApiToken());
                put("project_id", projectId);
            }
        }), new TypeReference<List<Item>>() {
        });
    }

    public List<Item> getUncompletedItems(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }

        return getUncompletedItems(project.getId());
    }
}
