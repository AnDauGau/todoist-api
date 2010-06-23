package com.todoist;


public enum Color {

    COLOR0(0, "#bde876"), COLOR1(1, "#ff8581"), COLOR2(2, "#ffc472"), COLOR3(3, "#faed75"), COLOR4(4, "#a8c9e5"), COLOR5(5, "#999999"), COLOR6(6, "#e3a8e5"), COLOR7(
            7, "#dddddd"), COLOR8(8, "#fc603c"), COLOR9(9, "#ffcc00"), COLOR10(10, "#74e8d4"), COLOR11(11, "#3cd6fc");

    private int index;
    private String value;

    private Color(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

}
