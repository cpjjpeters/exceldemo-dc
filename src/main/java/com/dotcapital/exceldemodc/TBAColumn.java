package com.dotcapital.exceldemodc;

public enum TBAColumn {

    INTERNAL_CODE(0, "Internal Code"),
    ISSUER(4, "Issuer Code"),
    ISSUER_CODE(5, "Issuer Code"),
    ISSUER_IP_NB(6, "Issuer IP Nb"),
    VALUE_DATE(9, "Value Date"),
    END_DATE(10, "End Date"),
    NOMINAL(12, "Nominal"),
    SLICE(19, "Tranche"),
    NOMINAL_2(23, "Nominal");

    private int columnIndex;
    private String columnName;

    TBAColumn(int columnIndex, String columnName) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public String getColumnName() {
        return columnName;
    }
}
