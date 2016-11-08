package fr.elecomte.ci.look.services.badges.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.elecomte.ci.look.services.badges.BadgeValue.BadgeColor;

/**
 * @author elecomte
 * @since 0.1.0
 * @param <T>
 */
@SuppressWarnings("boxing")
public class PolylineBadgeValueProvider<T> extends BadgeValueProvider<T> {

	private static final String POLYLINE_SET = "<polyline fill=\"none\" stroke=\"%s\" stroke-width=\"%s\" points=\"%s\"/>";
	private static final String LEGENDS_GROUP_SET = "<g fill=\"%s\" text-anchor=\"left\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"8px\">%s</g>";
	private static final String LEGEND_TEXT = "<text x=\"%s\" y=\"%s\">%s</text>";

	public PolylineBadgeValueProvider() {
		super();
	}

	@Override
	public boolean isLogo() {
		return false;
	}

	/**
	 * Points are between 0 and 1
	 * 
	 * @param startX
	 * @param maxX
	 * @param startY
	 * @param maxY
	 * @param weight
	 * @param color
	 * @param values
	 * @return
	 */
	protected String getPolyline(int startX, int maxX, int startY, int maxY, BadgeColor color, int weight, List<Float> values) {

		List<AdaptedPosition> points = new ArrayList<>();

		int idx = 0;

		if (values.size() == 1) {
			// Single line if only one value
			return getPolyline(color, weight,
					Arrays.asList(
							new AdaptedPosition(startX, getAdapted(startY, maxY, values.get(0).floatValue())),
							new AdaptedPosition(maxX, getAdapted(startY, maxY, values.get(0).floatValue()))));
		}

		for (Float y : values) {
			float x = idx / ((float)values.size() - 1);
			points.add(new AdaptedPosition(getAdapted(startX, maxX, x), getAdapted(startY, maxY, y.floatValue())));
			idx++;
		}

		return getPolyline(color, weight, points);
	}

	/**
	 * Compose a polyline
	 * 
	 * @param color
	 * @param weight
	 * @param points
	 * @return
	 */
	protected static String getPolyline(BadgeColor color, int weight, List<AdaptedPosition> points) {
		return String.format(POLYLINE_SET,
				color.getColor(),
				String.valueOf(weight),
				points.stream().map(AdaptedPosition::toString).collect(Collectors.joining(" ")));
	}

	/**
	 * Generate legend text
	 * 
	 * @param color
	 * @param legends
	 * @return
	 */
	protected static String getLegends(BadgeColor color, Map<AdaptedPosition, String> legends) {
		return String.format(LEGENDS_GROUP_SET,
				color.getColor(),
				legends.entrySet().stream().map(e -> String.format(LEGEND_TEXT, e.getKey().getX(), e.getKey().getY(), e.getValue()))
						.collect(Collectors.joining()));
	}

	/**
	 * @param min
	 * @param max
	 * @param value
	 *            between 0/1
	 * @return
	 */
	protected static int getAdapted(int min, int max, float value) {
		return new Float(min + ((max - min) * value)).intValue();
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	protected static class AdaptedPosition {

		private final int x;
		private final int y;

		/**
		 * @param x
		 * @param y
		 */
		public AdaptedPosition(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		/**
		 * @return the x
		 */
		public int getX() {
			return this.x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
			return this.y;
		}

		/**
		 * @return
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.x + "," + this.y;
		}

	}

}
