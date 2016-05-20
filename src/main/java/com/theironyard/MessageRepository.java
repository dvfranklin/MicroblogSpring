package com.theironyard;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    public Iterable<Message> findAllByOrderByIdAsc();
}
