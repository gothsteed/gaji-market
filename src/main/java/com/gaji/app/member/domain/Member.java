package com.gaji.app.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

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

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;

    @Column(name = "TEL", length = 200)
    private String tel;

    @Column(name = "PROFILEPIC", length = 200)
    private String profilePic;

    @Column(name = "MANNERTEMP", nullable = false)
    private Integer mannerTemp;
}
