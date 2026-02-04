package com.codegnan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegnan.entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

}
