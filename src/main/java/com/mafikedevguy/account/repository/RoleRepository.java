package com.mafikedevguy.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mafikedevguy.account.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
