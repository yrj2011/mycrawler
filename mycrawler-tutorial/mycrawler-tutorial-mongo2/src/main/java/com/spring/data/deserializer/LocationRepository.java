package com.spring.data.deserializer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Ghazi Naceur on 28/06/2017.
 */
public interface LocationRepository extends MongoRepository<PlaceCoordination, String> {
}
