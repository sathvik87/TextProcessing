package com.sathvik.textprocessing;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.sathvik.utils.Utils;

/**
 * @author sathvik, sathvikmail@gmail.com
 * 
 *         This is a prototype implementation of ERS module for thesis.
 *
 */

public class Main {

	static String query = "Who is the expert to know about ligaments?";

	public static void main(String args[]) {

		if (args.length > 2) {
			query = args[3];
		}
		StopWord stopword = new StopWord("res/webconfs_stopwords.txt");
		//URL url = Main.class.getClassLoader().getResource("src/res/webconfs_stopwords.txt");
		//StopWord stopword = new StopWord(url.toString());
		String clean_sentence = stopword.removeFrom(query);

		Utils.println(clean_sentence);
		Utils.QUERY_WORDS = HashMultiset.create(Splitter
				.on(CharMatcher.WHITESPACE).omitEmptyStrings()
				.split(clean_sentence));

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		ClassLoader classLoader = Main.class.getClassLoader();
		
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SAXHandler handler = new SAXHandler();
			
			
			saxParser.parse(classLoader.getResourceAsStream("res/Posts.xml"), handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
