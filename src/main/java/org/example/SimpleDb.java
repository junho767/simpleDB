package org.example;

import lombok.Setter;

import java.sql.*;

@Setter
public class SimpleDb {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private Connection connection;
    private boolean devMode = false;

    // 생성자: 데이터베이스 연결 정보 초기화
    public SimpleDb(String host, String user, String password, String dbName) {
        this.dbUrl = "jdbc:mysql://" + host + ":3306/" + dbName; // JDBC URL
        this.dbUser = user;                                    // 사용자 이름
        this.dbPassword = password;                            // 비밀번호

        // 연결 초기화
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            if (devMode) {
                System.out.println("데이터베이스에 성공적으로 연결되었습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패: " + e.getMessage());
        }
    }

    public boolean selectBoolean(String sql) {
        return (boolean) _run(sql,0);
    }

    public void run(String sql, Object... params) {
        _run(sql, 1 , params);
    }

    // SQL 실행 (PreparedStatement와 파라미터)
    public Object _run(String sql, int type, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (sql.startsWith("SELECT")) {
                ResultSet rs = stmt.executeQuery(); // 실제 반영된 로우수
                rs.next();

                if(type == 0) return rs.getBoolean(1);
                else if(type == 1) return rs.getString(1);
            }

            setParams(stmt, params); // 파라미터 설정
            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("SQL 실행 실패: " + e.getMessage());
        }
    }

    // PreparedStatement에 파라미터 바인딩
    private void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]); // '?' 위치에 값 설정
        }
    }

    // 데이터베이스 연결 종료
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                if (devMode) {
                    System.out.println("데이터베이스 연결 종료.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("데이터베이스 연결 종료 실패: " + e.getMessage());
            }
        }
    }

    public Sql genSql() {
        return new Sql(this);
    }

    public String selectString(String sql, Object... params) {
        return (String)_run(sql,1);
    }
}