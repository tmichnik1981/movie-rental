package com.epam.katowice.domain.converter;

import com.epam.katowice.domain.Rating;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        if (rating == null) {
            return null;
        }
        return rating.getRatingCode();
    }

    @Override
    public Rating convertToEntityAttribute(String rating) {
        return StringUtils.isBlank(rating) ? null : Rating.fromCode(rating);
    }
}
