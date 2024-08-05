package org.example.es_module.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.es_module.utils.TimeUtil;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tenant")
public class TenantEntity {
    private Timestamp createTimestamp = TimeUtil.now();
    private Timestamp updateTimestamp = TimeUtil.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tenantName;
    private String username;
    private String password;
    private String url;
    private String driverName;
}
