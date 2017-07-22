package com.epam.katowice.domain.converter;

import com.epam.katowice.domain.SpecialFeature;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SpecialFeaturesConverter implements AttributeConverter<List<SpecialFeature>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<SpecialFeature> specialFeatures) {
        return CollectionUtils.isEmpty(specialFeatures) ? null : StringUtils.join(specialFeatures.stream()
                                                                                      .map(feature -> feature.getName())
                                                                                      .collect(Collectors.toList()),
                                                                                  DELIMITER);
    }

    @Override
    public List<SpecialFeature> convertToEntityAttribute(String specialFeaturesString) {
        if (StringUtils.isBlank(specialFeaturesString)) {
            return Collections.emptyList();
        }
        String[] features = specialFeaturesString.split(DELIMITER);
        return Arrays.stream(features)
            .map(featureName -> SpecialFeature.fromName(featureName.trim()))
            .collect(Collectors.toList());

    }
}
