package com.mclebtec.components;

import com.mclebtec.dto.records.LeniMongoTemplate;
import com.mclebtec.handler.ValidationErrors;
import com.mclebtec.handler.exception.GenericException;
import com.mongodb.client.MongoClient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MongoDB {

  private static final Long SOCKET_TIME_OUT = 119000L;

  private final Map<String, LeniMongoTemplate> mongoTemplatePerDomain = new ConcurrentHashMap<>();

  private final MongoClient mongoClient;

  public MongoDB(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public MongoTemplate getTemplate(String domainName) {
    try {
      if (mongoTemplatePerDomain.containsKey(domainName)) {
        LeniMongoTemplate leniMongoTemplate = mongoTemplatePerDomain.get(domainName);
        final long diff = Instant.now().toEpochMilli() - (leniMongoTemplate.socketTime() - 10);
        if (diff < SOCKET_TIME_OUT)
          return leniMongoTemplate.mongoTemplate();
        else
          mongoTemplatePerDomain.remove(domainName);
      }
      return this.getMongoTemplate(domainName).mongoTemplate();
    } catch (Exception e) {
      throw new GenericException(ValidationErrors.MONGO_CONNECTIVITY_ISSUE, e.getLocalizedMessage(), e.getCause());
    }
  }

  private LeniMongoTemplate getMongoTemplate(final String dbName) {
    final SimpleMongoClientDatabaseFactory databaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient, dbName);
    final DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
    final MongoMappingContext mappingContext = new MongoMappingContext();
    mappingContext.setAutoIndexCreation(true);
    MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
    mongoConverter.setMapKeyDotReplacement(".");
    mongoConverter.setCustomConversions(customTimeConversions());
    mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    mongoConverter.afterPropertiesSet();
    LeniMongoTemplate leniMongoTemplate =
        new LeniMongoTemplate(new MongoTemplate(databaseFactory, mongoConverter), Instant.now().toEpochMilli());
    mongoTemplatePerDomain.put(dbName, leniMongoTemplate);
    return leniMongoTemplate;
  }

  public MongoCustomConversions customTimeConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(DateToZonedDateTimeConverter.INSTANCE);
    converters.add(ZonedDateTimeToDateConverter.INSTANCE);
    return new MongoCustomConversions(converters);
  }


  @ReadingConverter
  enum DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
    INSTANCE;

    public ZonedDateTime convert(Date source) {
      return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }
  }


  @WritingConverter
  enum ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, LocalDateTime> {
    INSTANCE;

    public LocalDateTime convert(ZonedDateTime source) {
      return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }
  }

}
