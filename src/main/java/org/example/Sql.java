package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql {
    private final StringBuilder sb;
    private final SimpleDb simpleDb;

    public Sql(SimpleDb simpleDb) {
        this.simpleDb = simpleDb;
        this.sb = new StringBuilder();
    }

    public Sql append(String sqlLine) {
        this.sb.append(sqlLine);
        this.sb.append(" ");
        return this;
    }

    public Sql append(String sqlLine, Object... args) {
        this.sb.append(sqlLine);
        this.sb.append(" ");
        return this;
    }

    public long insert() {
        return 1;
    }

    public int update() {
        return 3;
    }

    public int delete() {
        return 2;
    }

    public List<Map<String, Object>> selectRows() {
        return simpleDb.selectRows(sb.toString());
    }

    public Map<String, Object> selectRow() {
        return simpleDb.selectRow(sb.toString());
    }

    public LocalDateTime selectDatetime() {
        return simpleDb.selectDateTime(sb.toString());
//        return LocalDateTime.now();
    }

    public Long selectLong() {
        return simpleDb.selectLong(sb.toString());
    }

    public String selectString() {
        return simpleDb.selectString(sb.toString());
    }

    public Boolean selectBoolean() {
        return simpleDb.selectBoolean(sb.toString());
    }
}
