package com.secure.repos;

import com.secure.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepos extends CrudRepository<Payment, Long> {
    Payment findByNd(Integer nd);
}
