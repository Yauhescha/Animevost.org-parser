package helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONArray;

public class Translator {
	private static Translator translator = new Translator();

	public static String translateRuToEn(String text) {
		try {
			return translator.translate("ru", "en", text);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String translateEnToRu(String text) {
		try {
			return translator.translate("en", "ru", text);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String translate(String langFrom, String langTo, String word) throws Exception {

		String url = "https://translate.googleapis.com/translate_a/single?" 
				+ "client=gtx&" 
				+ "sl=" + langFrom 
				+ "&tl=" + langTo 
				+ "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return parseResult(response.toString());
	}

	private String parseResult(String inputJson) throws Exception {
		String temp = "";
		JSONArray jsonArray = new JSONArray(inputJson);

		JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
		for (Iterator iterator = jsonArray2.iterator(); iterator.hasNext();) {
			JSONArray type = (JSONArray) iterator.next();
			temp += type.get(0);
		}

		return temp;
	}
}