package parsers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper.Saver;

public class Animevost {
	Document pageWithAnime, pageAnime;
	ArrayList<String> oneAnimeImages = new ArrayList<String>();

	String temp, urlToAnimePage = "", title = "";

	String SITE = "https://animevost.org/page/";
	String SELECTOR_TITLE_ANIME_ON_PAGE = ".shortstory .shortstoryHead a";
	String SELECTOR_MAIN_AVATAR_ON_EACH_PAGE = ".shortstoryContent .imgRadius";
	String SELECTOR_SCREENS_ON_EACH_PAGE = ".skrin a";
	String SELECTOR_DESCRIPTION = "#dle-content  div.shortstory  div.shortstoryContent  span[itemprop=description]";

	String description = "";
	int currentPage;
	private String pathToSaveFolder;
	private String descriptionFilename;
	private boolean saveDescription;
	private boolean saveImage;
	private int startPage;
	private int endPage;

	public static void main(String[] args) {
		System.out.println("Starting");
		System.out.println("______________________________________________________");

		Animevost animevost = new Animevost();
		animevost.getSaveInfo();
		animevost.parse();
		
		System.out.println("______________________________________________________");
		System.out.println("The End");
	}

	private void getSaveInfo() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Please, input path to save folder");
		pathToSaveFolder = sc.nextLine();

		System.out.println("Please, input filename.txt to description file");
		descriptionFilename = sc.nextLine();

		System.out.println("Is description save? (true/false)");
		saveDescription = sc.nextBoolean();

		System.out.println("Is photo save? (true/false)");
		saveImage = sc.nextBoolean();
		
		System.out.println("Please, input number of start page");
		startPage=sc.nextInt();
		
		System.out.println("Please, input number of end page");
		endPage=sc.nextInt();
	}

	public void parse() {
		forEachPage();
	}

	private void forEachPage() {
		for (currentPage = startPage; currentPage <= endPage; currentPage++) {
			System.out.println("PAGE:  " + currentPage);
			gotoPageWith10Anime(SITE + currentPage);
		}
	}

	public void gotoPageWith10Anime(String url) {
		try {
			pageWithAnime = Jsoup.connect(url).get();

			findAllAnimeOnPage(pageWithAnime);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void findAllAnimeOnPage(Document pageWithList) {
		int count = 1;
		for (Element element : pageWithList.select(SELECTOR_TITLE_ANIME_ON_PAGE)) {
			System.out.println("\t" + count + "\t" + element.text());
			goToOneAnimePage(element);
			count++;
		}
	}

	private void goToOneAnimePage(Element element) {
		try {
			urlToAnimePage = element.attr("href");

			pageAnime = Jsoup.connect(element.attr("href")).get();

			parseOnePage(pageAnime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseOnePage(Document page) {
		getAvatarImage(page);
		getScreensImage(page);
		description = getDescriptionText(page);
		String animePageTitle = page.title();

		animePageTitle = getNormalFolderName(animePageTitle);

		save(animePageTitle);

		oneAnimeImages.clear();
	}

	private String getDescriptionText(Document page) {
		Elements select = page.select(SELECTOR_DESCRIPTION);
		if (select != null) {
			temp = select.text();
			if (temp.length() > 0)
				return temp;
		}
		return null;
	}

	private String getNormalFolderName(String animePageTitle) {
		if (animePageTitle.contains("["))
			animePageTitle.substring(0, animePageTitle.indexOf("[")).trim();
		if (animePageTitle.contains("/"))
			animePageTitle = animePageTitle.substring(0, animePageTitle.indexOf("/")).trim();

		animePageTitle = animePageTitle.replaceAll("(?U)[\\pP\\s]", " ").trim();

		animePageTitle = animePageTitle.replace("второй сезон", "").replace("третий сезон", "")
				.replace("четвертый сезон", "").replace("пятый сезон", "").replace("шестой сезон", "")
				.replace("седьмой сезон", "").replace("восьмой сезон", "").trim();

		return animePageTitle;
	}

	private void save(String animePageTitle) {
		if(saveImage)
		saveImages(animePageTitle);
		if(saveDescription)
		saveDescription(animePageTitle);

	}

	private void saveDescription(String folderName) {
		Saver.SaveText(description, pathToSaveFolder+Saver.SEPARATOR+folderName, descriptionFilename);
	}

	private void saveImages(String folderName) {
		for (int i = 0; i < oneAnimeImages.size(); i++) 
			Saver.SaveImageFromUrl(oneAnimeImages.get(i), pathToSaveFolder+Saver.SEPARATOR+folderName, folderName+i);
	}

	private void getScreensImage(Document page) {
		for (Element element : page.select(SELECTOR_SCREENS_ON_EACH_PAGE)) {
			temp = element.attr("href");
			if (!temp.contains("animevost.org"))
				temp = "https://animevost.org" + temp;
			oneAnimeImages.add(temp);
		}
	}

	private void getAvatarImage(Document page) {
		for (Element element : page.select(SELECTOR_MAIN_AVATAR_ON_EACH_PAGE)) {
			temp = element.attr("src");
			if (!temp.contains("animevost.org"))
				temp = "https://animevost.org" + temp;
			oneAnimeImages.add(temp);
		}
	}

}
