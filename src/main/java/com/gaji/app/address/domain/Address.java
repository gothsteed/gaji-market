package com.gaji.app.address.domain;

import com.gaji.app.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "TBL_ADDRESS")
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESSSEQ")
    @SequenceGenerator(name = "ADDRESSSEQ", sequenceName = "ADDRESSSEQ", allocationSize = 1)
    @Column(name = "ADDRESSSEQ", nullable = false)
    private Long addressseq;

    @Nationalized
    @Column(name = "POSTCODE", nullable = false, length = 30)
    private String postcode;

    @Nationalized
    @Column(name = "ADDRESS", nullable = false, length = 100)
    private String address;

    @Nationalized
    @Column(name = "ADDRESSDETAIL", nullable = false, length = 100)
    private String addressdetail;

    @Nationalized
    @Column(name = "ADDRESSEXTRA", nullable = false, length = 100)
    private String addressextra;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "FKMEMBERSEQ")
    private Member member;
    
    public Address(String postcode, String address, String addressdetail, String addressextra, Member member) {
        this.postcode = postcode;
        this.address = address;
        this.addressdetail = addressdetail;
        this.addressextra = addressextra;
        this.member = member;
    }
}