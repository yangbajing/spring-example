package me.yangbajing.springreactive.data;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableR2dbcRepositories
@EnableTransactionManagement
public class DataConfiguration extends AbstractR2dbcConfiguration {
    @Autowired
    private R2dbcProperties r2dbcProperties;

    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(r2dbcProperties.getUrl());
    }

    @Bean(name = {"r2dbcTransactionManager", "transactionManager"})
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

}
