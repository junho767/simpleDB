package org.example;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Sql {
    private final StringBuilder sb;
    private final List<Object> params;
    private final SimpleDb simpleDb;

    public Sql(SimpleDb simpleDb) {
        this.simpleDb = simpleDb;
        this.params = new ArrayList<>();
        this.sb = new StringBuilder();
    }

    public Sql append(String sqlLine) {
        this.sb.append(sqlLine);
        this.sb.append(" ");
        return this;
    }

    public Sql append(String sqlLine, Object... args) {
        this.params.addAll(Arrays.asList(args));
        this.sb.append(sqlLine);
        this.sb.append(" ");
        return this;
    }

    public long insert() {
        return simpleDb.insert(sb.toString(), params);
    }

    public int update() {
        return simpleDb.update(sb.toString(), params);
    }

    public int delete() {
        return simpleDb.delete(sb.toString(), params);
    }

    public List<Map<String, Object>> selectRows() {
        return simpleDb.selectRows(sb.toString(), params);
    }

    public List<Article> selectRows(Class<?> cls) {
        return simpleDb.selectRows(sb.toString(), params, cls);
    }

    public Map<String, Object> selectRow() {
        return simpleDb.selectRow(sb.toString(), params);
    }

    public LocalDateTime selectDatetime() {
        return simpleDb.selectDateTime(sb.toString(), params);
    }

    public Long selectLong() {
        return simpleDb.selectLong(sb.toString(), params);
    }

    public String selectString() {
        return simpleDb.selectString(sb.toString(), params);
    }

    public Boolean selectBoolean() {
        return simpleDb.selectBoolean(sb.toString(), params);
    }

    public Sql appendIn(String sql, Object... args) {
        String inCluase = Arrays.stream(args)
                .map(o -> "?")
                .collect(Collectors.joining(", "));

        String replacedSql = sql.replaceAll("\\?", inCluase);
        this.params.addAll(Arrays.stream(args).toList());
        this.sb.append(replacedSql);

        return this;
    }

    public List<Long> selectLongs() {
        return simpleDb.selectLongs(sb.toString(), params);
    }
}
