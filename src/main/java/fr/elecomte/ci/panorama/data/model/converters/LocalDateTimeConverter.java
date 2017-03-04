
package fr.elecomte.ci.panorama.data.model.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime value) {
		return value == null ? null : Timestamp.valueOf(value);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp value) {
		return value == null ? null : value.toLocalDateTime();
	}
}