package com.getrecepto.receptoparking.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.host:#{null}}")
    private String host;
    @Value("${spring.data.mongodb.database:#{null}}")
    private String database;
    @Value("${spring.data.mongodb.user:#{null}}")
    private String user;
    @Value("${spring.data.mongodb.password:#{''}}")
    private String password;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClient mongoClient = mongoClient();
        return new MongoTemplate(mongoClient, this.database);
    }

    @NotNull
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(getDatabaseName());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

        return MongoClients.create(mongoClientSettings);
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        if (StringUtils.hasText(host) && StringUtils.hasText(database)) {
            if (StringUtils.hasText(password)) {
                return String.format("mongodb://%s:%s@%s/%s", user, password, host, database);
            }
            return String.format("mongodb://%s@%s/%s", user, host, database);
        }
        return "";
    }
}
