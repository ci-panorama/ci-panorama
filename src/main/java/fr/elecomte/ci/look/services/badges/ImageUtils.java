package fr.elecomte.ci.look.services.badges;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import fr.elecomte.ci.look.services.processes.ProcessException;
import net.coobird.thumbnailator.Thumbnails;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ImageUtils {

	private static final String URI_START = "data:image/";
	private static final String URI_TYPE = ";base64,";

	/**
	 * @param img
	 * @param type
	 * @return
	 * @throws ProcessException
	 */
	public static String getImageBase64Uri(BufferedImage img, String type) throws ProcessException {

		StringBuilder output = new StringBuilder();

		// Standard picture in B64 start details
		output.append(URI_START + type + URI_TYPE);

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
	 * @param base64uri
	 * @return
	 * @throws ProcessException
	 */
	public static BufferedImage loadBase64Uri(String base64uri) throws ProcessException {

		String[] uriParts = base64uri.split(URI_TYPE);

		if (uriParts.length != 2) {
			throw new ProcessException("URI type is not compliant");
		}

		String encodedData = uriParts[1];
		String type = uriParts[0].substring(URI_START.length());

		byte[] data = Base64.getDecoder().decode(encodedData);

		try (InputStream is = new ByteArrayInputStream(data)) {
			return ImageIO.read(is);
		}

		catch (IOException e) {
			throw new ProcessException("Cannot load uri image of type " + type, e);
		}
	}

	/**
	 * @param uri
	 * @return
	 */
	public static String getImageTypeFromBase64Uri(String uri) {

		String[] uriParts = uri.split(URI_TYPE);

		if (uriParts.length != 2) {
			throw new ProcessException("URI type is not compliant");
		}

		return uriParts[0].substring(URI_START.length());
	}

	/**
	 * @param value
	 * @return
	 */
	public static BufferedImage loadImage(String resourcePath) throws ProcessException {

		try (InputStream is = BadgesGenerator.class.getClassLoader().getResourceAsStream(resourcePath)) {
			return ImageIO.read(is);
		}

		catch (IOException e) {
			throw new ProcessException("Cannot load and resize image at " + resourcePath, e);
		}
	}

	/**
	 * @param value
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage image, int height, int width) throws ProcessException {

		try {
			return Thumbnails.of(image).forceSize(width, height).asBufferedImage();
		} catch (IOException e) {
			throw new ProcessException("Cannot resize", e);
		}
	}

}
