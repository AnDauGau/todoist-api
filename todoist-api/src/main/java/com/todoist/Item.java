package com.todoist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Item {

    public enum Priority {
        /*
         * there's a bug in the Todoist API that switches the priorities, so we must switch them back in todoist it is: 1 = very urgent, 4 = natural, in the api
         * it is: 4 = very urgent, 1 = natural
         */
        // TODO rename Priority.TTT to ???
        VERY_URGENT(4), URGENT(3), TTT(2), NATURAL(1);

        private int value;

        private Priority(int value) {
            this.value = value;
        }

        public static Priority forValue(int value) {
            for (Priority priority : values()) {
                if (value == priority.value) {
                    return priority;
                }
            }
            throw new IllegalArgumentException("No priority found for value " + value);
        }

        public int getValue() {
            return value;
        }
    }

    @JsonProperty(value = "user_id")
    private long userId;

    @JsonProperty(value = "due_date")
    private Date dueDate;

    private boolean collapsed;

    @JsonProperty(value = "in_history")
    private boolean inHistory;

    private Priority priority;

    @JsonProperty(value = "item_order")
    private int itemOrder;

    private String content;

    private int indent;

    @JsonProperty(value = "project_id")
    private long projectId;

    private long id;
    private boolean checked;
    @JsonProperty(value = "date_string")
    private String dateString;

    // TODO Item.labels is in json but is never filled?
    private List<String> labels = new ArrayList<String>();
    private boolean is_dst;
    private boolean has_notifications;
    private Object children;
    private Object mm_offset;
    private Object chains;

    public long getUserId() {
        return userId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public boolean isInHistory() {
        return inHistory;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    public String getContent() {
        return content;
    }

    public int getIndent() {
        return indent;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getId() {
        return id;
    }

    public boolean isIs_dst() {
        return is_dst;
    }

    public boolean isHasNotifications() {
        return has_notifications;
    }

    public Object getChildren() {
        return children;
    }

    public Object getMm_offset() {
        return mm_offset;
    }

    public Object getChains() {
        return chains;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getDateString() {
        return dateString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item other = (Item) obj;

            return other.id == id;
        }
        return false;
    }
}
