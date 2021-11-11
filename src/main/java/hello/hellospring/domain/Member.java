package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {

    // DB 에서 기본키 값을 생성해주고 있다.
    // 이와 같이 DB 에서 기본 키 값을 자동으로 생성시키는 기본키 매핑 전략을 identity 전략이라고 한다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column 어노테이션을 통해 생성시킬 테이블에서 각 컬럼의 이름을 지정해 줄 수 있다.
    @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
