package com.ia.alexander.repository;

import com.ia.alexander.entity.LoteCultivo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteCultivoRepository extends CrudRepository<LoteCultivo , Long> {
}
