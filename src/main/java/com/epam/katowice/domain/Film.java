package com.epam.katowice.domain;

import com.epam.katowice.domain.converter.RatingConverter;
import com.epam.katowice.domain.converter.SpecialFeaturesConverter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "film")
public class Film implements Serializable {

    public static final String TITLE = "title";
    public static final String RELEASE_YEAR = "releaseYear";
    public static final String LENGTH = "length";
    public static final String LANGUAGE = "language";
    public static final String CATEGORIES = "categories";
    public static final String ACTORS = "actors";
    static final int RENTAL_DURATION_DEFAULT_VALUE = 3;
    static final double RENTAL_RATE_DEFAULT_VALUE = 4.99;
    static final double REPLACEMENT_COST_DEFAULT_VALUE = 19.99;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "film_id")
    private Long id;

    @Column(name = "title",
        nullable = false)
    private String title;

    @Column(name = "description", length = 65535)
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id",
        nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "film_actor",
        joinColumns = {@JoinColumn(name = "film_id")},
        inverseJoinColumns = {@JoinColumn(name = "actor_id")})
    private Set<Actor> actors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "film_category",
        joinColumns = {@JoinColumn(name = "film_id")},
        inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories;

    @ColumnDefault(value = "3")
    @Column(name = "rental_duration",
        nullable = false)
    private byte rentalDuration = RENTAL_DURATION_DEFAULT_VALUE;

    @ColumnDefault(value = "4.99")
    @Column(name = "rental_rate",
        nullable = false)
    private double rentalRate = RENTAL_RATE_DEFAULT_VALUE;

    @Column(name = "length")
    private Short length;

    @ColumnDefault(value = "19.99")
    @Column(name = "replacement_cost",
        nullable = false)
    private double replacementCost = REPLACEMENT_COST_DEFAULT_VALUE;

    @Basic
    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating = Rating.G;

    @Convert(converter = SpecialFeaturesConverter.class)
    @Column(name = "special_features",
        nullable = false)
    private List<SpecialFeature> specialFeatures;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "film", optional = false)
    private FilmDescription filmDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public byte getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length =  length != null && length <=0 ? null : length;
    }

    public double getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(double replacementCost) {
        this.replacementCost = replacementCost;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating != null ? rating : Rating.G;
    }

    public List<SpecialFeature> getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(List<SpecialFeature> specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public FilmDescription getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(FilmDescription filmDescription) {
        this.filmDescription = filmDescription;
    }
}