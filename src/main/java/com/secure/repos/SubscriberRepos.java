package com.secure.repos;

import com.secure.domain.Subscriber;
import org.springframework.data.repository.CrudRepository;


public interface SubscriberRepos extends CrudRepository<Subscriber, Integer> {
    Subscriber findByNd(Integer nd);
}
