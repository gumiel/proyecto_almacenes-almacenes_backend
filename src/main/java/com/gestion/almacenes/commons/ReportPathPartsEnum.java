package com.gestion.almacenes.commons;

import java.util.HashMap;
import java.util.Map;

public enum ReportPathPartsEnum {
    REPORT_FOLDER("reports"),
    SUB_REPORT_FOLDER("subReports"),
    HEADER_FOOTER_REPORT_FOLDER("headerFooter"),
    JASPER(".jasper");

    private final String value;
    private static final Map<String, ReportPathPartsEnum> lookup = new HashMap<>();

    static {
        for (ReportPathPartsEnum d : ReportPathPartsEnum.values()) {
            lookup.put(d.getValue(), d);
        }
    }

    private ReportPathPartsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ReportPathPartsEnum get(int value) {
        return lookup.get(value);
    }
}
