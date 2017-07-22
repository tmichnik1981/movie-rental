package com.epam.katowice.dto;

import java.util.Calendar;
import javax.validation.constraints.Size;

public class SearchFilmForm {

    @Size(max = 255)
    private String title;
    private int releaseYearFrom;
    private int releaseYearTo;
    private long category = -1;
    private long language = -1;
    private short lengthFrom;
    private short lengthTo;
    @Size(max = 45)
    private String actorName;

    public SearchFilmForm() {
        this.releaseYearTo = Calendar.getInstance().get(Calendar.YEAR);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYearFrom() {
        return releaseYearFrom;
    }

    public void setReleaseYearFrom(int releaseYearFrom) {
        this.releaseYearFrom = releaseYearFrom;
    }

    public int getReleaseYearTo() {
        return releaseYearTo;
    }

    public void setReleaseYearTo(int releaseYearTo) {
        this.releaseYearTo = releaseYearTo;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public long getLanguage() {
        return language;
    }

    public void setLanguage(long language) {
        this.language = language;
    }

    public short getLengthFrom() {
        return lengthFrom;
    }

    public void setLengthFrom(short lengthFrom) {
        this.lengthFrom = lengthFrom;
    }

    public short getLengthTo() {
        return lengthTo;
    }

    public void setLengthTo(short lengthTo) {
        this.lengthTo = lengthTo;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

}
