package com.spring.data.deserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Ghazi Naceur on 28/06/2017.
 */
@Document
public class PlaceCoordination {

    @Id
    private String placeId;

    @JsonDeserialize(using=PointDeserializer.class)
    private Point point;

    public PlaceCoordination(String placeId, Point point) {
        this.placeId = placeId;
        this.point = point;
    }
}
