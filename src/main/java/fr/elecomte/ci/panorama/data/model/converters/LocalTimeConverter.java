package fr.elecomte.ci.panorama.data.model.converters;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

	@Override
	public Time convertToDatabaseColumn(LocalTime value) {
		return value == null ? null : Time.valueOf(value);
	}

	@Override
	public LocalTime convertToEntityAttribute(Time value) {
		return value == null ? null : value.toLocalTime();
	}
}