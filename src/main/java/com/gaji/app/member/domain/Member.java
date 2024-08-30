package com.gaji.app.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "TBL_MEMBER")
@SequenceGenerator(
        name = "MEMBERSEQ",
        sequenceName = "MEMBERSEQ",
        allocationSize = 1
)
public class Member {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "MEMBERSEQ"
    )
    @Column(name = "MEMBERSEQ")
    private Long memberSeq;

    @Column(name = "USERID", nullable = false, length = 200)
    private String userId;

    @Column(name = "NAME", nullable = false, length = 10)
    private String name;

    @Column(name = "NICKNAME", nullable = false, length = 20)
    private String nickname;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;

    @Column(name = "TEL", length = 200)
    private String tel;

    @Column(name = "PROFILEPIC", length = 200)
    private String profilePic;

    @Column(name = "MANNERTEMP", nullable = false)
    private Double mannerTemp;

    public Member(String userId, String name, String nickname, String password, String email, String tel, String profilepic) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.profilePic = profilepic;
    }

}
