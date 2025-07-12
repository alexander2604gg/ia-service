package com.ia.alexander.repository;

import com.ia.alexander.entity.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecRepository extends JpaRepository<UserSec, Long> {

    Optional<UserSec> findUserSecByEmail (String email);

}
