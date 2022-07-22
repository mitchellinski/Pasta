package com.pastaebasta.eist;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class FoundationTests {
	@Test
	public void testCreateReservation() {

	}

	@Test
	public void test(@Autowired MongoTemplate mongoTemplate) {
		DBObject objectToSave = BasicDBObjectBuilder.start()
				.add("key", "value")
				.get();

		// when
		mongoTemplate.save(objectToSave, "collection");


	}
}
