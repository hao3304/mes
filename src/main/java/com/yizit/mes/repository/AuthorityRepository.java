package com.yizit.mes.repository;

import com.yizit.mes.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

     boolean existsByPid(Long pid);
}
