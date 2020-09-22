package MySystem;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class impeEcran {

	public static void main(String[] args) {
		impeEcran.screenShot(

				// Les Dimensions de l'ecran que tu veux
				new Rectangle(0, 0, 1600, 900),

				// Les Dimensions de l'image de l'ecran
				new Dimension(1600, 900),

				// format de l'image resultante
				"test.png",

				impeEcran.IMAGE_TYPE_PNG);
	}
	public final static String IMAGE_TYPE_PNG = "png";

	public static void screenShot(

		Rectangle screenArea, Dimension screenshotFinalDimension, String pictureName, String compressionType) {

		// la capture d'écran originale
		BufferedImage buf = null;

		// la capture d'écran redimensionnée
		BufferedImage bufFinal = null;

		try {

			// Création de notre capture d'écran
			buf = new Robot().createScreenCapture(screenArea);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		// Création de la capture finale
		bufFinal = new BufferedImage(screenshotFinalDimension.width, screenshotFinalDimension.height,
				BufferedImage.TYPE_INT_RGB);

		// Redimensionnement de la capture originale
		Graphics2D g = (Graphics2D) bufFinal.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(buf, 0, 0, screenshotFinalDimension.width, screenshotFinalDimension.height, null);
		g.dispose();

		// Ecriture de la capture d'écran redimensionnée
		try {
			ImageIO.write(bufFinal, compressionType, new File(pictureName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}