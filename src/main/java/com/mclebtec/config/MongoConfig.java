package com.mclebtec.config;

import com.mclebtec.props.MongoEndpoints;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.*;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient(@Autowired MongoEndpoints mongoProperties) {
        final MongoClientSettings.Builder builder = MongoClientSettings.builder();
        final SocketSettings.Builder socketBuilder = SocketSettings.builder();
        final ConnectionPoolSettings.Builder connectionPoolBuilder = ConnectionPoolSettings.builder();
        final ServerSettings.Builder serverBuilder = ServerSettings.builder();
        final SslSettings.Builder sslBuilder = SslSettings.builder();
        final ClusterSettings.Builder clusterBuilder = ClusterSettings.builder();
        final int connectTimeout = 120000;
        final int maxWaitTime = 120000;
        final int socketTimeout = 120000;
        final int maxConnectionLifeTime = 120000;
        final int maxConnectionIdleTime = 120000;
        final boolean sslEnabled = false;
        final int minHeartbeatFrequency = 120000;
        final int heartbeatFrequency = 120000;
        connectionPoolBuilder.maxWaitTime(maxWaitTime, TimeUnit.MILLISECONDS);
        socketBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        socketBuilder.readTimeout(socketTimeout, TimeUnit.MILLISECONDS);
        connectionPoolBuilder.maxConnectionLifeTime(maxConnectionLifeTime, TimeUnit.MILLISECONDS);
        connectionPoolBuilder.maxConnectionIdleTime(maxConnectionIdleTime, TimeUnit.MILLISECONDS);
        serverBuilder.minHeartbeatFrequency(minHeartbeatFrequency, TimeUnit.MILLISECONDS);
        serverBuilder.heartbeatFrequency(heartbeatFrequency, TimeUnit.MILLISECONDS);
        sslBuilder.enabled(sslEnabled);
        // clustering option
        List<ServerAddress> serverAddresses = new ArrayList<>();
        if (mongoProperties.getHost().contains(",")) {
            serverAddresses = Arrays.stream(mongoProperties.getHost().split(",")).map(serverAdd -> {
                String[] serverAddress = serverAdd.split(":");
                return new ServerAddress(serverAddress[0], Integer.parseInt(serverAddress[1]));
            }).collect(Collectors.toList());
        } else
            serverAddresses.add(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()));
        clusterBuilder.hosts(serverAddresses);
        final SocketSettings socketSettings = socketBuilder.build();
        final ConnectionPoolSettings connectionPoolSettings = connectionPoolBuilder.build();
        final ServerSettings serverSettings = serverBuilder.build();
        final SslSettings sslSettings = sslBuilder.build();
        final ClusterSettings clusterSettings = clusterBuilder.build();
        final MongoClientSettings settings;
        if (StringUtils.hasText(mongoProperties.getUsername()) && StringUtils.hasText(mongoProperties.getPassword())) {
            settings = builder.applyToSocketSettings(builder1 -> builder1.applySettings(socketSettings))
                    .applyToClusterSettings(builder1 -> builder1.applySettings(clusterSettings))
                    .applyToConnectionPoolSettings(builder1 -> builder1.applySettings(connectionPoolSettings))
                    .applyToServerSettings(builder1 -> builder1.applySettings(serverSettings))
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .applyToSslSettings(builder1 -> builder1.applySettings(sslSettings)).credential(MongoCredential
                            .createCredential(mongoProperties.getUsername(), "local",
                                    mongoProperties.getPassword().toCharArray()))
                    .build();
        } else {
            settings = builder.applyToSocketSettings(builder1 -> builder1.applySettings(socketSettings))
                    .applyToClusterSettings(builder1 -> builder1.applySettings(clusterSettings))
                    .applyToConnectionPoolSettings(builder1 -> builder1.applySettings(connectionPoolSettings))
                    .applyToServerSettings(builder1 -> builder1.applySettings(serverSettings))
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .applyToSslSettings(builder1 -> builder1.applySettings(sslSettings)).build();
        }
        return MongoClients.create(settings);
    }

}
