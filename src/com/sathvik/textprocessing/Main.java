package com.sathvik.textprocessing;

import java.io.File;
import java.io.IOException;

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

	static String query = "the protein drink";
	

	public static void main(String args[]) {

		StopWord stopword = new StopWord("resources/lucene_stopwords.txt");
		String clean_sentence = stopword.removeFrom(query);

		// Utils.print("Cleaned Sentence:");
		// Utils.println(clean_sentence);
		
		Utils.QUERY_WORDS = HashMultiset.create(
				  Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings()
				    .split(query));

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SAXHandler handler = new SAXHandler();
			saxParser.parse(new File("resources/Posts_small.xml"), handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
