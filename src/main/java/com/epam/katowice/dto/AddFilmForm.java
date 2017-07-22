package com.epam.katowice.dto;

import com.epam.katowice.domain.Rating;
import com.epam.katowice.domain.SpecialFeature;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddFilmForm {

    @NotEmpty
    @Size(max = 255)
    private String title;
    @Size(max = 65535)
    private String description;
    @Min(value = 1895)
    private int releaseYear;
    @NotNull
    private Long languageId;
    private Set<Long> actorsIds = new HashSet<>();
    private Set<Long> categoriesIds = new HashSet<>();
    private short length;
    private Rating rating;
    private List<SpecialFeature> specialFeatures = new ArrayList<>();

    public AddFilmForm() {
        this.releaseYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    public AddFilmForm(String title, String description, int releaseYear, Long languageId,
                       Set<Long> actorsIds, Set<Long> categoriesIds, short length, Rating rating,
                       List<SpecialFeature> specialFeatures) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.actorsIds = actorsIds;
        this.categoriesIds = categoriesIds;
        this.length = length;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
    }

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

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Set<Long> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(Set<Long> actorsIds) {
        this.actorsIds = actorsIds;
    }

    public Set<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(Set<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
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

    public List<SpecialFeature> getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(List<SpecialFeature> specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public static class AddFilmFormBuilder{

        private String title;
        private String description;
        private int releaseYear;
        private Long languageId;
        private Set<Long> actorsIds = new HashSet<>();
        private Set<Long> categoriesIds = new HashSet<>();
        private short length;
        private Rating rating;
        private List<SpecialFeature> specialFeatures = new ArrayList<>();

        private AddFilmFormBuilder() {
        }

        public static AddFilmFormBuilder getInstance(){
            return new AddFilmFormBuilder();
        }

        public AddFilmFormBuilder withTitle(String title){
            this.title = title;
            return this;
        }
        public AddFilmFormBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public AddFilmFormBuilder withReleaseYear(int releaseYear){
            this.releaseYear = releaseYear;
            return this;
        }
        public AddFilmFormBuilder withLanguageId(Long languageId){
            this.languageId = languageId;
            return this;
        }
        public AddFilmFormBuilder withActorsIds(Set<Long> actorsIds ){
            this.actorsIds = actorsIds;
            return this;
        }
        public AddFilmFormBuilder withCategoriesIds(Set<Long> categoriesIds){
            this.categoriesIds = categoriesIds;
            return this;
        }
        public AddFilmFormBuilder withLength(short length){
            this.length = length;
            return this;
        }
        public AddFilmFormBuilder withRating(Rating rating){
            this.rating = rating;
            return this;
        }
        public AddFilmFormBuilder withSpecialFeatures(List<SpecialFeature> specialFeatures){
            this.specialFeatures = specialFeatures;
            return this;
        }

        public AddFilmForm build() {
            return new AddFilmForm(title, description, releaseYear, languageId, actorsIds, categoriesIds, length,
                                   rating, specialFeatures);
        }
    }
}
