package fr.elecomte.ci.look.data.model.converters;

import java.time.Year;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Year value) {
		return value == null ? null : Integer.valueOf(value.getValue());
	}

	@Override
	public Year convertToEntityAttribute(Integer value) {
		return value == null ? null : Year.of(value.intValue());
	}
}