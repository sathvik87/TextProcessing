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

	static String query = "is a spa town in North Rhine-Westphalia, Germany Geographically, Aachen is the westernmost city of Germany, located along its borders with Belgium and the Netherlands, 61 km (38 mi) west-southwest of Cologne.[4][5] It is located within a former coal-mining region, and this fact was important in its economic history.[5] RWTH Aachen University, one of Germany's Universities of Excellence, is located in the city.[nb 1][6] Aachen's predominant economic focus is on science, engineering, information technology and related sectors. In 2009, Aachen was ranked 8th among cities in Germany for innovation";
	

	public static void main(String args[]) {

		StopWord stopword = new StopWord("resources/lucene_stopwords.txt");
		String clean_sentence = stopword.removeFrom(query);

		// Utils.print("Cleaned Sentence:");
		// Utils.println(clean_sentence);
		
		Utils.query_words = HashMultiset.create(
				  Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings()
				    .split(query));

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SAXHandler handler = new SAXHandler();
			saxParser.parse(new File("resources/Posts.xml"), handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
