package com.spring.data.geospatial.features;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Ghazi Naceur on 28/06/2017.
 */
public interface FakeBookRepository extends MongoRepository<FakeBook, String> {

    public List<FakeBook> findByLocationCoordsNear(Point point, Distance distance);

}
