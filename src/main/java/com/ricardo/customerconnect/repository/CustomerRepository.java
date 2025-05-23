package com.ricardo.customerconnect.repository;

import com.ricardo.customerconnect.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Page<CustomerEntity> findByCpf(String cpf, PageRequest pageRequest);

    Page<CustomerEntity> findByEmailAndCpf(String email, String cpf, PageRequest pageRequest);

    Page<CustomerEntity> findByEmail(String email, PageRequest pageRequest);
}
