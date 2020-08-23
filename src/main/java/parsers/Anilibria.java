package parsers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper.Saver;

public class Anilibria {
	Document pageWithAnime, pageAnime;

	final String SITE = "https://www.anilibria.tv/pages/alphabet.php";
	final String SELECTOR_URLS_ANIME = " div.main  div.content  div.news-block  tbody  tr  td a";
	final String SELECTOR_MAIN_AVATAR_ON_ANIME_PAGE = "#adminPoster";
	final String SELECTOR_ANIME_NAME_ON_ANIME_PAGE = "#xreleaseInfo > h1.release-title";
	final String SELECTOR_DESCRIPTION_ON_ANIME_PAGE = "#xreleaseInfo > p.detail-description";

	private String pathToSaveFolder, descriptionFilename;
	private boolean saveDescription, saveImage;

	public static void main(String[] args) {
		System.out.println("Starting");
		System.out.println("______________________________________________________");

		Anilibria anilibria = new Anilibria();
		anilibria.getSaveInfo();
		anilibria.parse();

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
	}

	public void parse() {
		int count = 1;

		loadSite();

		Elements urlsToAnimePage = getCollectionUrlsToAnimePages();
		for (Iterator iterator = urlsToAnimePage.iterator(); iterator.hasNext();) {
			System.out.println(count);
			count++;

			Element urlToAnimePage = (Element) iterator.next();
			gotoAnimePage(urlToAnimePage);
		}

	}

	private Elements getCollectionUrlsToAnimePages() {
		return pageWithAnime.select(SELECTOR_URLS_ANIME);
	}

	private void loadSite() {
		try {
			pageWithAnime = Jsoup.connect(SITE).get();
		} catch (IOException e) {
			e.printStackTrace();
			Runtime.getRuntime().exit(0);
		}
	}

	private void gotoAnimePage(Element element) {
		try {
			String urlToAnimePage = "https://www.anilibria.tv" + element.attr("href");
			System.out.println("urlToAnimePage:" + urlToAnimePage);

			pageAnime = Jsoup.connect(urlToAnimePage).get();
			parseOnePage(pageAnime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseOnePage(Document page) {
		String animeName, description, urlToAvatar;

		animeName = getAnimeName(page);
		animeName = getNormalFolderName(animeName);
		description = getDescriptionText(page);
		urlToAvatar = getAvatarUrl(page);

		save(animeName, description, urlToAvatar);

	}

	private void save(String animeName, String description, String urlToAvatar) {
		System.out.println("Anime: " + animeName + "\n" + "description: " + description + "\n" + "avatar's url: "
				+ urlToAvatar + "\n");

		if (saveImage && urlToAvatar != null)
			saveImage(urlToAvatar, pathToSaveFolder + Saver.SEPARATOR + animeName, animeName);
		if (saveDescription && description != null && !description.equals("null"))
			saveDescription(description, pathToSaveFolder + Saver.SEPARATOR + animeName, descriptionFilename);
	}

	private void saveImage(String urlToAvatar, String pathToSaveFolder, String filename) {
		Saver.SaveImageFromUrl(urlToAvatar, pathToSaveFolder, filename);
	}

	private void saveDescription(String description, String pathToSaveFolder, String filename) {
		Saver.SaveText(description, pathToSaveFolder, filename);
	}

	private String getAnimeName(Document page) {
		return getTextFromSelector(page, SELECTOR_ANIME_NAME_ON_ANIME_PAGE);
	}

	private String getDescriptionText(Document page) {
		return getTextFromSelector(page, SELECTOR_DESCRIPTION_ON_ANIME_PAGE);
	}

	private String getTextFromSelector(Document page, String selector) {
		Elements select = page.select(selector);
		if (select == null)
			return null;
		return select.text().length() > 0 ? select.text() : null;
	}

	private String getNormalFolderName(String animePageTitle) {
		if (animePageTitle.contains("/"))
			animePageTitle = animePageTitle.substring(0, animePageTitle.indexOf("/")).trim();
		animePageTitle = animePageTitle.replaceAll("(?U)[\\pP\\s]", " ").trim();
		return animePageTitle;
	}

	private String getAvatarUrl(Document page) {
		for (Element element : page.select(SELECTOR_MAIN_AVATAR_ON_ANIME_PAGE)) {
			return element.attr("src");
		}
		return null;
	}

}
