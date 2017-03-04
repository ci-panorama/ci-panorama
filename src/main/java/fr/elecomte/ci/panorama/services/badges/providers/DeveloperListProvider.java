package fr.elecomte.ci.panorama.services.badges.providers;

import static fr.elecomte.ci.panorama.services.badges.ImageUtils.*;

import java.util.List;
import java.util.stream.Collectors;

import fr.elecomte.ci.panorama.data.model.Developer;
import fr.elecomte.ci.panorama.services.badges.BadgeValue;
import fr.elecomte.ci.panorama.services.badges.Resources;
import fr.elecomte.ci.panorama.services.badges.BadgeValue.BadgeColor;

/**
 * @author elecomte
 * @since 0.1.0
 */
@SuppressWarnings("boxing")
public class DeveloperListProvider extends BadgeValueProvider<List<Developer>> {

	private static final String IMAGE_FORMAT = "<image width=\"%d\" height=\"%d\" y=\"%d\" x=\"%d\" xlink:href=\"%s\" />";

	private int imgBoxSize = 40;
	private int startCorner = 5;
	private int imgGridSpace = 1;
	private int gridImgLineSize = 5;
	private int maxCount = this.gridImgLineSize * 2;

	private String noDeveloperPictoCache;
	private String unknownDeveloperPictoCache;

	private static final String NO_DEVELOPER_PICTO = Resources.LOGO_ROOT + "/other/developer-none.png";
	private static final String UNKNOWN_DEVELOPER_PICTO = Resources.LOGO_ROOT + "/other/developer-unknown.png";

	/**
	 * @return
	 * @see fr.elecomte.ci.panorama.services.badges.providers.BadgeValueProvider#isLogo()
	 */
	@Override
	public boolean isLogo() {
		return false;
	}

	/**
	 * @param entity
	 * @return
	 * @see fr.elecomte.ci.panorama.services.badges.providers.BadgeValueProvider#getValue(java.lang.Object)
	 */
	@Override
	public BadgeValue getValue(List<Developer> developers) {

		if (developers == null) {
			return new BadgeValue(String.format(IMAGE_FORMAT, this.imgBoxSize,
					this.imgBoxSize, this.startCorner, this.startCorner, getNoDeveloperPicto()),
					BadgeColor.LIGHT_GREY);
		}

		List<String> images = developers.stream()
				.filter(d -> d != null)
				.map(this::getDeveloperImage)
				.limit(this.maxCount)
				.collect(Collectors.toList());

		String grid = generateImageGrid(
				images,
				this.startCorner,
				this.startCorner,
				this.imgBoxSize,
				this.imgBoxSize,
				this.imgGridSpace,
				this.gridImgLineSize);

		return new BadgeValue(grid, BadgeColor.NONE);
	}

	/**
	 * Developer Base64 encoded img or anon image
	 * 
	 * @param developer
	 * @return
	 */
	private String getDeveloperImage(Developer developer) {

		if (developer.getImageUrl() == null) {
			return getUnknownDeveloperPicto();
		}

		String uri = new String(developer.getImageUrl());

		String type = getImageTypeFromBase64Uri(uri);

		return getImageBase64Uri(resizeImage(loadBase64Uri(uri), this.imgBoxSize, this.imgBoxSize), type);
	}

	/**
	 * @return
	 */
	private String getNoDeveloperPicto() {

		if (this.noDeveloperPictoCache == null) {
			this.noDeveloperPictoCache = getImageBase64Uri(
					resizeImage(loadImage(NO_DEVELOPER_PICTO), this.imgBoxSize, this.imgBoxSize), "png");
		}

		return this.noDeveloperPictoCache;
	}

	/**
	 * @return
	 */
	private String getUnknownDeveloperPicto() {

		if (this.unknownDeveloperPictoCache == null) {
			this.unknownDeveloperPictoCache = getImageBase64Uri(
					resizeImage(loadImage(UNKNOWN_DEVELOPER_PICTO), this.imgBoxSize, this.imgBoxSize), "png");
		}

		return this.unknownDeveloperPictoCache;
	}

	/**
	 * Produces a grid of pictures to include in a SVG
	 * 
	 * @param imageBase64Uris
	 *            a list of images as BASE64 uris
	 * @param startX
	 *            : start in SVG (px), X axis
	 * @param startY
	 *            : start in SVG (px), Y axis
	 * @param imgHeight
	 *            : height (px) for each img
	 * @param imgWidth
	 *            : width (px) for each img
	 * @param space
	 *            : space (px) between each img (in height and width)
	 * @param gridWidth
	 *            : width (in nbr of img) of the grid. New lines are created automatically
	 * @return image svg tags for the specified grid
	 */
	protected String generateImageGrid(List<String> imageBase64Uris, int startX, int startY, int imgHeight, int imgWidth, int space,
			int gridWidth) {

		StringBuilder grid = new StringBuilder();

		int idx = 0;

		int x = startX;
		int y = startY;

		for (String img : imageBase64Uris) {

			if (idx > 0) {
				if (idx % gridWidth == 0) {
					x = startX;
					y = y + space + imgWidth;
				} else {
					x = x + imgWidth + space;
				}
			}

			grid.append(String.format(IMAGE_FORMAT, imgWidth, imgHeight, y, x, img));

			idx++;
		}

		return grid.toString();

	}
}
