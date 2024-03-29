package com.zwj.blog.utils.backup;

public class FK {
    private String column;
    private Table referenceTable;
    private String referencePK;

    public FK(String column, Table referenceTable, String referencePK) {
        this.column = column;
        this.referenceTable = referenceTable;
        this.referencePK = referencePK;
    }

    public Table getReferenceTable() {
        return referenceTable;
    }

    @Override
    public String toString() {
        return "FK [column=" + column + ", referenceTable=" + referenceTable
                + ", referencePK=" + referencePK + "]";
    }


}
