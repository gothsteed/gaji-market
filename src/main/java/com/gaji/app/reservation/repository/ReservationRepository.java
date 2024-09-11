package com.gaji.app.reservation.repository;

import com.gaji.app.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 사용자가 구매 예약한 상품 개수
    int countByFkmemberseq(@Param("memberSeq") Long memberSeq);
}
