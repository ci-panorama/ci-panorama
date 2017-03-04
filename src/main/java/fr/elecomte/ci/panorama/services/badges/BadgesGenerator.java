package fr.elecomte.ci.panorama.services.badges;

import static fr.elecomte.ci.panorama.services.badges.ImageUtils.getImageBase64Uri;
import static fr.elecomte.ci.panorama.services.badges.ImageUtils.loadImage;
import static fr.elecomte.ci.panorama.services.badges.ImageUtils.resizeImage;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.elecomte.ci.panorama.services.processes.ProcessException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class BadgesGenerator {

	private static final String VALUE_KEY = "value";
	private static final String VALUE_COLOR_KEY = "color";
	private static final String LOGO_KEY = "logo";

	@Value("${panorama.badges.logo.height}")
	private int logoHeight;

	@Value("${panorama.badges.logo.width}")
	private int logoWidth;

	@Value("${panorama.badges.logo.type}")
	private String logoType;

	private Map<String, String> imageResourcesCache = new HashMap<>();

	@Autowired
	private Configuration freemarker;

	/**
	 * @param type
	 * @param entity
	 * @return
	 * @throws ProcessException
	 */
	public <T> String getBadge(BadgeType type, T entity)
			throws ProcessException {

		Map<String, String> dataMap = new HashMap<>();

		if (type.getProvider().isLogo()) {
			dataMap.put(LOGO_KEY, base64imageResource(type.getProvider().getLogo(entity)));
		} else {
			BadgeValue val = type.getProvider().getValue(entity);
			dataMap.put(VALUE_KEY, val.getTitle());
			dataMap.put(VALUE_COLOR_KEY, val.getColor().getColor());
		}

		return getFormatedBadge(type.getBadgeFile(), dataMap);
	}

	/**
	 * @param name
	 * @param dataMap
	 * @return
	 */
	private String getFormatedBadge(String name, Map<String, String> dataMap) throws ProcessException {

		try {
			Template freemarkerTemplate = this.freemarker.getTemplate(name);
			StringWriter output = new StringWriter();
			freemarkerTemplate.process(dataMap, output);
			return output.toString();
		} catch (IOException | TemplateException e) {
			throw new ProcessException(e);
		}
	}

	/**
	 * @param resourcePath
	 * @return
	 */
	private String base64imageResource(String resourcePath) throws ProcessException {

		String encoded = this.imageResourcesCache.get(resourcePath);

		if (encoded == null) {

			// Load and resize
			encoded = getImageBase64Uri(
					resizeImage(
							loadImage(resourcePath),
							this.logoHeight, this.logoWidth),
					this.logoType);

			this.imageResourcesCache.put(resourcePath, encoded);
		}

		return encoded;

	}
}
