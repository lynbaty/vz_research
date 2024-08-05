package org.example.es_module.tenant;

import org.example.es_module.repositories.jpa.JPATenantRepository;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MigrationTenantSchema {
    private final JPATenantRepository jpaTenantRepository;
    private final Flyway flyway;

    public MigrationTenantSchema(JPATenantRepository jpaTenantRepository, Flyway flyway) {

        this.jpaTenantRepository = jpaTenantRepository;
        this.flyway = flyway;
        migrateSchema();
    }

    public void migrateSchema() {
        List<String> tenantIds = jpaTenantRepository.getTenants();
        for (String tenantId : tenantIds){
            TenantContext.setCurrentTenant(tenantId);
            flyway.migrate();
        }
    }
}
