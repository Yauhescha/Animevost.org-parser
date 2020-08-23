package helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Saver {
	public static final String SEPARATOR = "\\";

	public static void SaveImageFromUrl(String imageUrl, String pathToSaveFolder, String filename) {
		new File(pathToSaveFolder).mkdirs();

		try {
			BufferedImage image = null;
			URL url = new URL(imageUrl);
			image = ImageIO.read(url);
			if (image != null) {
				ImageIO.write(image, "jpg", new File(pathToSaveFolder + SEPARATOR + filename + ".jpg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void SaveText(String text, String pathToSaveFolder, String filename) {
		new File(pathToSaveFolder).mkdirs();

		try (FileWriter writer = new FileWriter(pathToSaveFolder + SEPARATOR + filename, false)) {
			writer.write(text);
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
