package fr.elecomte.ci.panorama.services.badges.providers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import fr.elecomte.ci.panorama.services.badges.BadgeValue;
import fr.elecomte.ci.panorama.services.badges.BadgeValue.BadgeColor;
import fr.elecomte.ci.panorama.services.payloads.extracts.TestResultPayloadExtract;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class TestResultEvolutionProvider extends PolylineBadgeValueProvider<List<TestResultPayloadExtract>> {

	private static final int STARTX = 25;
	private static final int STARTY = 95;
	private static final int MAXX = 195;
	private static final int VERSIONX = 135;
	private static final int MAXY = 15;
	private static final int STROKE = 2;
	private static final BadgeColor SUCCESS_COLOR = BadgeColor.GREEN;
	private static final BadgeColor FAILED_COLOR = BadgeColor.RED;
	private static final BadgeColor IGNORED_COLOR = BadgeColor.DARK_GREY;
	private static final BadgeColor BKG_COLOR = BadgeColor.LIGHT_GREY;

	/**
	 * @param entities
	 * @return
	 * @see fr.elecomte.ci.panorama.services.badges.providers.BadgeValueProvider#getValue(java.lang.Object)
	 */
	@Override
	@SuppressWarnings({ "boxing", "cast" })
	public BadgeValue getValue(List<TestResultPayloadExtract> entities) {

		if (entities == null || entities.size() == 0) {
			return new BadgeValue("N/A", BKG_COLOR);
		}

		Collections.reverse(entities);

		final float maxTotal = (float) TestResultPayloadExtract.getMaxTotal(entities);

		List<Float> successes = entities.stream()
				.map(e -> new BigDecimal(e.getSuccess() / maxTotal).floatValue()).collect(Collectors.toList());

		List<Float> failures = entities.stream()
				.map(e -> new BigDecimal(e.getFailed() / maxTotal).floatValue()).collect(Collectors.toList());

		List<Float> ignoreds = entities.stream()
				.map(e -> new BigDecimal(e.getIgnored() / maxTotal).floatValue()).collect(Collectors.toList());

		StringBuilder polylines = new StringBuilder();

		polylines.append(getPolyline(STARTX, MAXX, STARTY, MAXY, SUCCESS_COLOR, STROKE, successes));
		polylines.append(getPolyline(STARTX, MAXX, STARTY, MAXY, FAILED_COLOR, STROKE, failures));
		polylines.append(getPolyline(STARTX, MAXX, STARTY, MAXY, IGNORED_COLOR, STROKE, ignoreds));

		int maxMarkY = getAdapted(STARTY, MAXY, 1);

		polylines.append(getLegends(BadgeColor.BLACK,
				new HashMap<AdaptedPosition, String>() {
					{
						// Add version number
						put(new AdaptedPosition(VERSIONX, MAXY - 6),
								"Version " + entities.get(0).getAssociatedResult().getProject().getVersion());

						// Add max count at top left
						put(new AdaptedPosition(STARTX - 2, maxMarkY - 5), String.valueOf(Float.valueOf(maxTotal).intValue()));
					}
				}));

		// Max count mark
		polylines.append(getPolyline(BadgeColor.BLACK, 2,
				Arrays.asList(
						new AdaptedPosition(STARTX - 2, maxMarkY),
						new AdaptedPosition(STARTX + 2, maxMarkY))));

		return new BadgeValue(polylines.toString(), BKG_COLOR);
	}

}
