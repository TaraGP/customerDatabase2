package com.example.customerDatabase1.ConfigCassandra;/*
package com.example.CustomerDatabase1.ConfigCassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories

public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    protected String getContactPoints()
    {
        return contactPoints;
    }
    @Override
    protected int getPort()
    {
        return port;
    }
    @Override
    protected String getKeyspaceName()
    {
        return keyspaceName;
    }
}
*/