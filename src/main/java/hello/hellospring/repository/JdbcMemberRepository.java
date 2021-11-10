package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; // 결과를 반환받는 객체
        try {
            conn = getConnection(); // 데이터베이스 커넥션을 받아옴
            // 첫번째 파라미터에서 sql 을 넣음
            // 두번째 파라미터 에서는 데이터를 삽입해야 데이터베이스에서 id 값을 자동으로 지정해주는 방식으로 만들었기 때문에,
            // 데이터베이스 에서 자동으로 지정해준 키 값을 반환받게끔 해주는 옵션이다.
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, member.getName()); // 파라미터 인덱스 : 번호 별로 sql 에서 values 에 작성해준 ? 에 매칭된다. 매칭된 ? 에 member.getName() 반환값을 넣어준다.
            pstmt.executeUpdate(); // DB 에 쿼리 전달(데이터가 삽입되기 때문에 update 메소드 수행)
            rs = pstmt.getGeneratedKeys(); // DB 에서 삽입한 값에 생성해준 키 값을 반환받는다.
            if (rs.next()) { // rs 에 결과값이 존재할 경우 실행하는 조건문
                member.setId(rs.getLong(1)); // rs 에 반환받은 id 값을 member 객체에 세팅해준다.
            } else {
                throw new SQLException("id 조회 실패"); // rs 에 결과값이 존재하지 않을 경우 익셉션 발생
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); 
            // 데이터베이스 연결은 외부 네트워크와 어플리케이션간 접속을 하는것이기 때문에
            // 작업이 끝나고 나면 접속(자원)을 끊어주는 것이 좋다.
            // 연결이 계속 유지되고 있으면 그만큼 어플리케이션 상에 부하가 많이 발생해서 그런듯 하다.
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery(); // 단순 데이터 조회이기 때문에 일반적인 query 메소드 수행
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member); // 반환값이 있을 경우 member 객체에 값 세팅 후 반환
            } else {
                return Optional.empty(); // 값이 없을 경우 empty 반환
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member); // 값이 존재하는 동안 객체에 값 세팅 후 리스트에 추가
            }
            return members; // 리스트 반환
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name); // 파라미터로 넘어온 이름 값을 기준으로 검색
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        // 스프링을 통해서 데이터베이스 연결을 수행할 때는 DataSourceUtils 를 통해서 커넥션을 획득해야 한다.
        // (사실 이런 코드를 쓸 일이 없기는 한데 그냥 참고 차원에서 알고만 있자.)
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        // 역순으로 close() 메소드를 수행해준다.
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        // 최종적으로 데이터베이스 커넥션을 끊어줄때도 마찬가지로 DataSourceUtils 를 활용한다.
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
