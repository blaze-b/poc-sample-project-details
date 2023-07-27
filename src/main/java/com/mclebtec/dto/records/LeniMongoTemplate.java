package com.mclebtec.dto.records;

import org.springframework.data.mongodb.core.MongoTemplate;

public record LeniMongoTemplate(MongoTemplate mongoTemplate, long socketTime) {

}
