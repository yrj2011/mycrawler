package com.spring.data.geospatial.features;

import org.springframework.data.geo.Point;

/**
 * Created by Ghazi Naceur on 28/06/2017.
 */
public class Library {

    private String libraryId;

    private String name;

    private Point coords;

    public Library(String libraryId, String name, Point coords) {
        this.libraryId = libraryId;
        this.name = name;
        this.coords = coords;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    @Override
    public String toString() {
        return "Library{" +
                "libraryId='" + libraryId + '\'' +
                ", name='" + name + '\'' +
                ", coords=" + coords +
                '}';
    }
}
