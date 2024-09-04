package com.gaji.app.member.repository;


import com.gaji.app.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> { // 영속성 레이어랑 소통해주는게 repository이다.
	
}
