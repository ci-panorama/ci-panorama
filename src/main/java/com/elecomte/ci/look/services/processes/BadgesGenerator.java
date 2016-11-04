package com.elecomte.ci.look.services.processes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.elecomte.ci.look.data.model.CiEntity;
import com.elecomte.ci.look.data.model.Project;
import com.elecomte.ci.look.data.model.Result;
import com.github.zafarkhaja.semver.Version;

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

	@Value("${ci-look.badges.logo.height}")
	private int logoHeight;

	@Value("${ci-look.badges.logo.width}")
	private int logoWidth;

	@Value("${ci-look.badges.logo.type}")
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
	public <T extends CiEntity> String getBadge(BadgeType type, T entity)
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

			BufferedImage img = loadImage(resourcePath, this.logoHeight, this.logoWidth);
			encoded = getImageBase64Uri(img, this.logoType);

			this.imageResourcesCache.put(resourcePath, encoded);
		}

		return encoded;

	}

	/**
	 * @param img
	 * @param type
	 * @return
	 * @throws ProcessException
	 */
	private static String getImageBase64Uri(BufferedImage img, String type) throws ProcessException {

		StringBuilder output = new StringBuilder();

		// Standard picture in B64 start details
		output.append("data:image/" + type + ";base64,");

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			ImageIO.write(img, type, baos);

			// Get it as B64
			output.append(Base64.getEncoder().encodeToString(baos.toByteArray()));

			return output.toString();
		}

		catch (IOException e) {
			throw new ProcessException("Cannot encode image to type " + type, e);
		}
	}

	/**
	 * @param value
	 * @return
	 */
	private static BufferedImage loadImage(String resourcePath, int height, int width) throws ProcessException {

		try (InputStream is = BadgesGenerator.class.getClassLoader().getResourceAsStream(resourcePath)) {
			BufferedImage image = ImageIO.read(is);

			BufferedImage resized = new BufferedImage(height, width, image.getType());
			Graphics2D g = resized.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
			g.dispose();

			return resized;
		}

		catch (IOException e) {
			throw new ProcessException("Cannot load and resize image at " + resourcePath, e);
		}
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 * @param <T>
	 */
	@SuppressWarnings("unused")
	public static abstract class BadgeValueProvider<T extends CiEntity> {

		abstract boolean isLogo();

		BadgeValue getValue(T entity) {
			return null;
		}

		String getLogo(T entity) {
			return null;
		}
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class BadgeValue {

		private final String title;
		private final BadgeColor color;

		/**
		 * @param title
		 * @param color
		 */
		public BadgeValue(String title, BadgeColor color) {
			super();
			this.title = title;
			this.color = color;
		}

		/**
		 * @return the title
		 */
		public String getTitle() {
			return this.title;
		}

		/**
		 * @return the color
		 */
		public BadgeColor getColor() {
			return this.color;
		}

	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static enum BadgeColor {

		GREEN("#4c1"),
		GREY("grey"),
		DARK_GREY("#444"),
		RED("red"),
		ORANGE("orange"),
		NONE("#fff");

		private final String color;

		private BadgeColor(String color) {
			this.color = color;
		}

		/**
		 * @return the color
		 */
		public String getColor() {
			return this.color;
		}
	}

	static enum BadgeType {

		BUILD("build.svg", new BuildResultProvider()),
		VERSION("version.svg", new ProjectVersionProvider()),
		VERSION_PENDING("pending.svg", new ProjectVersionProvider()),
		VERSION_RELEASED("released.svg", new ProjectVersionProvider());

		private final String badgeFile;
		private final BadgeValueProvider<?> provider;

		/**
		 * @param badgeFile
		 * @param title
		 * @param titleColor
		 * @param provider
		 */
		private BadgeType(String badgeFile, BadgeValueProvider<?> provider) {
			this.badgeFile = badgeFile;
			this.provider = provider;
		}

		/**
		 * @return the badgeFile
		 */
		public String getBadgeFile() {
			return this.badgeFile;
		}

		/**
		 * @return the provider
		 */
		@SuppressWarnings("unchecked")
		public <T extends CiEntity> BadgeValueProvider<T> getProvider() {
			return (BadgeValueProvider<T>) this.provider;
		}
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class BuildResultProvider extends BadgeValueProvider<Result> {

		private static final BadgeValue SUCCESS = new BadgeValue("success", BadgeColor.GREEN);
		private static final BadgeValue PENDING = new BadgeValue("pending", BadgeColor.GREY);
		private static final BadgeValue FAILED = new BadgeValue("failed", BadgeColor.RED);

		@Override
		boolean isLogo() {
			return false;
		}

		@Override
		BadgeValue getValue(Result entity) {
			if (entity == null) {
				return PENDING;
			}
			return entity.isSuccess() ? SUCCESS : FAILED;
		}
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class ProjectVersionProvider extends BadgeValueProvider<Project> {

		private static final BadgeValue NONE = new BadgeValue("N/A", BadgeColor.RED);

		@Override
		boolean isLogo() {
			return false;
		}

		@Override
		BadgeValue getValue(Project entity) {
			if (entity == null) {
				return NONE;
			}

			Version v = Version.valueOf(entity.getVersion());

			int major = v.getMajorVersion();
			int minor = v.getMinorVersion();
			int patch = v.getPatchVersion();

			String value = major + "." + minor + "." + patch;

			if (v.getPreReleaseVersion() != null) {
				return new BadgeValue(value, BadgeColor.DARK_GREY);
			}

			return new BadgeValue(value, BadgeColor.ORANGE);
		}
	}

}
