package parsers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Animevost {
//	Document pageWith10Anime, eachAnime;
//	ArrayList<String> oneAnimeImages = new ArrayList<String>();
//
//	String temp = "", hrefToOneAnimePage = "", title = "";
//
//	String SITE = "https://animevost.org/page/";
//	String SELECTOR_TITLE_ANIME_ON_PAGE = ".shortstory .shortstoryHead a";
//	String SELECTOR_MAIN_AVATAR_ON_EACH_PAGE = ".shortstoryContent .imgRadius";
//	String SELECTOR_SCREENS_ON_EACH_PAGE = ".skrin a";
//	String SELECTOR_DESCRIPTION = "#dle-content  div.shortstory  div.shortstoryContent  span[itemprop=description]";
//
//	String description = "";
//	int MIN_PAGE = 1;
//	int MAX_PAGE = 246;
//	int currentPage;
//
//	public static void main(String[] args) {
//		System.out.println("Hello World!");
//		new Animevost().parse();
//		System.out.println("The End");
//	}
//
//	public void parse() {
//		forEachPage();
//	}
//
//	private void forEachPage() {
//		for (currentPage = MIN_PAGE; currentPage <= MAX_PAGE; currentPage++) {
//			System.out.println("PAGE:  " + currentPage);
//			gotoPageWith10Anime(SITE + currentPage);
//		}
//	}
//
//	public void gotoPageWith10Anime(String url) {
//		try {
//			pageWith10Anime = Jsoup.connect(url).get();
//
//			findAllAnimeOnPage(pageWith10Anime);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void findAllAnimeOnPage(Document pageWithList) {
//		int count = 1;
//		for (Element element : pageWithList.select(SELECTOR_TITLE_ANIME_ON_PAGE)) {
//			System.out.println("\t" + count + "\t" + element.text());
//			goToOneAnimePage(element);
//			count++;
//		}
//	}
//
//	private void goToOneAnimePage(Element element) {
//		try {
//			hrefToOneAnimePage = element.attr("href");
////			System.out.println("\t\t\t" + hrefToOneAnimePage);
//
//			eachAnime = Jsoup.connect(element.attr("href")).get();
//
//			parseOnePage(eachAnime);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void parseOnePage(Document page) {
//		getAvatarImage(page);
//		getScreensImage(page);
//		description = getDescriptionText(page);
//		String animePageTitle = page.title();
//
//		animePageTitle = getNormalFolderName(animePageTitle);
//
//		save(animePageTitle);
//
//		oneAnimeImages.clear();
//	}
//
//	private String getDescriptionText(Document page) {
//		Elements select = page.select(SELECTOR_DESCRIPTION);
//		if (select != null) {
//			temp = select.text();
//			if (temp.length() > 0)
//				return temp;
//		}
//		return null;
//	}
//
//	private String getNormalFolderName(String animePageTitle) {
//		if (animePageTitle.contains("["))
//			animePageTitle.substring(0, animePageTitle.indexOf("[")).trim();
//		if (animePageTitle.contains("/"))
//			animePageTitle = animePageTitle.substring(0, animePageTitle.indexOf("/")).trim();
//
//		animePageTitle = animePageTitle.replaceAll("(?U)[\\pP\\s]", " ").trim();
//
//		animePageTitle = animePageTitle.replace("второй сезон", "").replace("третий сезон", "")
//				.replace("четвертый сезон", "").replace("пятый сезон", "").replace("шестой сезон", "")
//				.replace("седьмой сезон", "").replace("восьмой сезон", "").trim();
//
//		return animePageTitle;
//	}
//
//	private void save(String animePageTitle) {
//		saveImages(animePageTitle);
//		saveDescription(animePageTitle);
//
//	}
//
//	private void saveDescription(String folder) {
//		new File("anime\\" + folder).mkdirs();
//		System.out.println("\t\t"+description);
//		if (description != null && description.length() > 0)
//			try (FileWriter writer = new FileWriter("anime\\" + folder + "\\description.txt", false)) {
//				writer.write(description);
//				writer.flush();
//			} catch (IOException ex) {
//				System.out.println(ex.getMessage());
//			}
//
//	}
//
//	private void saveImages(String folder) {
//		new File("anime\\" + folder).mkdirs();
//
//		for (int i = 0; i < oneAnimeImages.size(); i++) {
//			try {
//				BufferedImage image = null;
//				URL url = new URL(oneAnimeImages.get(i));
//				image = ImageIO.read(url);
//				if (image != null) {
//					ImageIO.write(image, "jpg", new File("anime\\" + folder + "\\" + i + ".jpg"));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private void getScreensImage(Document page) {
//		for (Element element : page.select(SELECTOR_SCREENS_ON_EACH_PAGE)) {
//			temp = element.attr("href");
//			if (!temp.contains("animevost.org"))
//				temp = "https://animevost.org" + temp;
//			oneAnimeImages.add(temp);
//		}
//	}
//
//	private void getAvatarImage(Document page) {
//		for (Element element : page.select(SELECTOR_MAIN_AVATAR_ON_EACH_PAGE)) {
//			temp = element.attr("src");
//			if (!temp.contains("animevost.org"))
//				temp = "https://animevost.org" + temp;
//			oneAnimeImages.add(temp);
//		}
//	}

}
