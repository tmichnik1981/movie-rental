package com.epam.katowice.dto;

import com.epam.katowice.domain.Rating;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FilmTo {

    private long id;
    private String title;
    private String description;
    private int releaseYear;
    private short length;
    private Rating rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilmTo filmTo = (FilmTo) o;

        return new EqualsBuilder()
            .append(id, filmTo.id)
            .append(releaseYear, filmTo.releaseYear)
            .append(length, filmTo.length)
            .append(title, filmTo.title)
            .append(description, filmTo.description)
            .append(rating, filmTo.rating)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 31)
            .append(id)
            .append(title)
            .append(description)
            .append(releaseYear)
            .append(length)
            .append(rating)
            .toHashCode();
    }
}
