package com.ricardo.customerconnect.service;

import com.ricardo.customerconnect.controller.dto.CreateCustomerDto;
import com.ricardo.customerconnect.controller.dto.UpdateCustomerDto;
import com.ricardo.customerconnect.entity.CustomerEntity;
import com.ricardo.customerconnect.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(CreateCustomerDto dto) {
        var entity = new CustomerEntity();
        entity.setFullName(dto.fullName());
        entity.setCpf(dto.cpf());
        entity.setEmail(dto.email());
        entity.setPhoneNumber(dto.phoneNumber());

        customerRepository.save(entity);
    }

    public Page<CustomerEntity> findAll(Integer page, Integer pageSize, String orderBy, String cpf, String email) {
        var pageRequest = getPageRequest(page, pageSize, orderBy);
        return findWithFilter(cpf, email, pageRequest);
    }

    private Page<CustomerEntity> findWithFilter(String cpf, String email, PageRequest pageRequest) {
        if (hasText(email) && hasText(cpf)) {
            return customerRepository.findByEmailAndCpf(email, cpf, pageRequest);
        }

        if (hasText(cpf)) {
            return customerRepository.findByCpf(cpf, pageRequest);
        }

        if (hasText(email)) {
            return customerRepository.findByEmail(email, pageRequest);
        }

        return customerRepository.findAll(pageRequest);
    }


    private PageRequest getPageRequest(Integer page, Integer pageSize, String orderBy) {
        var direction = Sort.Direction.DESC;

        if (orderBy.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        return PageRequest.of(page, pageSize, Sort.by(direction, "createdAt"));
    }

    public Optional<CustomerEntity> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<CustomerEntity> updateById(Long customerId, UpdateCustomerDto dto) {
        var customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            updateFields(dto, customer);
            customerRepository.save(customer.get());
        }
        return customer;
    }

    private void updateFields(UpdateCustomerDto dto, Optional<CustomerEntity> customer) {
        if (hasText(dto.fullName())) {
            customer.get().setFullName(dto.fullName());
        }

        if (hasText(dto.email())) {
            customer.get().setEmail(dto.email());
        }

        if (hasText(dto.phoneNumber())) {
            customer.get().setPhoneNumber(dto.phoneNumber());
        }
    }


    public boolean deleteById(Long custumerId) {
        var customer = customerRepository.existsById(custumerId);

        if (customer) {
            customerRepository.deleteById(custumerId);
        }
        return false;
    }
}
