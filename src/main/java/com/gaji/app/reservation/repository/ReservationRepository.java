package com.gaji.app.reservation.repository;

import com.gaji.app.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 사용자가 구매 예약한 상품 개수
    int countByFkmemberseq(@Param("memberSeq") Long memberSeq);

    // 사용자가 구매 예약한 상품 가져오기
    @Query(value = "SELECT * " +
                   "FROM ( " +
                   "    select reservationseq, fkproductseq, fkmemberseq, " +
                   "           ROW_NUMBER() over(order by reservationseq desc) as RNUM " +
                   "    from tbl_reservation " +
                   "    where fkmemberseq = :memberSeq " +
                   ") " +
                   "WHERE rnum BETWEEN :start and :end " +
                   "ORDER BY rnum", nativeQuery = true)
    List<Reservation> findAllByFkmemberseq(Long memberSeq, int start, int end);
}
