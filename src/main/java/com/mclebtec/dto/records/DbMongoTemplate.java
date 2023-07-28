package com.mclebtec.dto.records;

import org.springframework.data.mongodb.core.MongoTemplate;

public record DbMongoTemplate(MongoTemplate mongoTemplate, long socketTime) {

}
