package com.zwj.blog.utils.backup;

public class Column {
    private String name;
    private String typeName;
    private int dataType;

    public Column(String name, String typeName, int dataType) {
        super();
        this.name = name;
        this.typeName = typeName;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public int getDataType() {
        return dataType;
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", typeName=" + typeName
                + ", dataType=" + dataType + "]";
    }

}
