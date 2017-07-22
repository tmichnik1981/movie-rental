package com.epam.katowice.domain.converter;

import static org.fest.assertions.api.Assertions.assertThat;

import com.epam.katowice.domain.SpecialFeature;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpecialFeaturesConverterTest {
    private static final String COMMA_SEPARATED_FEATURES = "Behind the Scenes,Commentaries,Deleted Scenes,Trailers";
    private static final String COMMA_SEPARATED_FEATURES_WITH_SPACES = "Behind the Scenes, Commentaries, Trailers";

    private SpecialFeaturesConverter specialFeaturesConverter = new SpecialFeaturesConverter();

    @Test
    public void shouldConvertSpecialFeaturesStringToList() {
        //given
        //when
        List<SpecialFeature> featuresResult = specialFeaturesConverter.convertToEntityAttribute(
            COMMA_SEPARATED_FEATURES_WITH_SPACES);

        //then
        assertThat(featuresResult)
            .isNotNull()
            .hasSize(3).containsExactly(SpecialFeature.BEHIND_THE_SCENES, SpecialFeature.COMMENTARIES, SpecialFeature.TRAILERS);
    }

    @Test
    public void shouldConvertOneSpecialFeatureStringToList() {
        //given
        //when
        List<SpecialFeature> featuresResult = specialFeaturesConverter.convertToEntityAttribute(SpecialFeature.DELETED_SCENES.getName());

        //then
        assertThat(featuresResult)
            .isNotNull()
            .hasSize(1).containsExactly(SpecialFeature.DELETED_SCENES);

    }

    @Test
    public void shouldConvertNullSpecialFeatureToEmptyList() {
        //given
        //when
        List<SpecialFeature> featuresResult = specialFeaturesConverter.convertToEntityAttribute(null);

        //then
        assertThat(featuresResult)
            .isNotNull()
            .isEmpty();
    }

    @Test
    public void shouldConvertBlankSpecialFeatureToEmptyList() {
        //given
        //when
        List<SpecialFeature> featuresResult = specialFeaturesConverter.convertToEntityAttribute(" ");

        //then
        assertThat(featuresResult)
            .isNotNull()
            .isEmpty();
    }

    @Test
    public void shouldConvertSpecialFeaturesListToCommaSeparatedString() {
        //given
        List<SpecialFeature> features = Arrays.asList(SpecialFeature.BEHIND_THE_SCENES, SpecialFeature.COMMENTARIES, SpecialFeature.DELETED_SCENES, SpecialFeature.TRAILERS);
        //when
        String featuresResult = specialFeaturesConverter.convertToDatabaseColumn(features);

        //then
        assertThat(featuresResult)
            .isNotNull()
            .isEqualTo(COMMA_SEPARATED_FEATURES);
    }

    @Test
    public void shouldConvertEmptySpecialFeaturesListToNull() {
        //given
        List<SpecialFeature> features = Collections.emptyList();
        //when
        String featuresResult = specialFeaturesConverter.convertToDatabaseColumn(features);

        //then
        assertThat(featuresResult)
            .isNull();
    }

    @Test
    public void shouldConvertNullSpecialFeaturesListToNull() {
        //given
        List<SpecialFeature> features = Collections.emptyList();
        //when
        String featuresResult = specialFeaturesConverter.convertToDatabaseColumn(features);

        //then
        assertThat(featuresResult)
            .isNull();
    }

}