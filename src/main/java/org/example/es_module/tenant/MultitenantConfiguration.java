package org.example.es_module.tenant;

import org.example.es_module.entities.TenantEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MultitenantConfiguration {

    @Value("${defaultTenant}")
    private String defaultTenant;
    private final String DRIVER_NAME = "org.postgresql.Driver";

    @Value("${spring.datasource.url}")
    private String masterUrl;

    @Value("${spring.datasource.username}")
    private String masterUsername;

    @Value("${spring.datasource.password}")
    private String masterPassword;

    @Bean
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() throws SQLException {
        Map<Object, Object> resolvedDataSources = new HashMap<>();
        resolvedDataSources.put("master", buildMasterDataSource());

        var connection = buildMasterDataSource().getConnection();
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM tenant");
        List<TenantEntity> tenantEntities =new ArrayList<TenantEntity>();

        while(rs.next()) {
            TenantEntity tenant = new TenantEntity();
            tenant.setTenantName(rs.getString("tenant_name"));
            tenant.setUrl(rs.getString("url"));
            tenant.setDriverName(rs.getString("driver_name"));
            tenant.setUsername(rs.getString("username"));
            tenant.setPassword(rs.getString("password"));
            tenantEntities.add(tenant);
        }

        for(TenantEntity tenantEntity : tenantEntities){
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            String tenantId = tenantEntity.getTenantName();

            dataSourceBuilder.driverClassName(tenantEntity.getDriverName());
            dataSourceBuilder.username(tenantEntity.getUsername());
            dataSourceBuilder.password(tenantEntity.getPassword());
            dataSourceBuilder.url(tenantEntity.getUrl());
            resolvedDataSources.put(tenantId, dataSourceBuilder.build());
        }

        AbstractRoutingDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

    private DataSource buildMasterDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER_NAME);
        dataSourceBuilder.username(masterUsername);
        dataSourceBuilder.password(masterPassword);
        dataSourceBuilder.url(masterUrl);
        return dataSourceBuilder.build();
    }
}
