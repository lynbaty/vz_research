package org.example.es_module.repositories.jpa;

import org.example.es_module.entities.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JPATenantRepository extends JpaRepository <TenantEntity, Long> {
    @Query(value = "SELECT tenant_name FROM tenant", nativeQuery = true)
    List<String> getTenants();
}
