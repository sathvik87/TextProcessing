package com.sathvik.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.common.collect.Multiset;
import com.sathvik.textprocessing.PorterStemmer;

/**
 * @author sathvik, sathvikmail@gmail.com
 * 
 * This is a prototype implementation of ERS module for thesis.
 *
 */

public class Utils {
	public static Multiset query_words;
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static void print(String str) {
		System.out.print(str);
	}
	public static void println(String str) {
		System.out.println(str);
	}
	
	public static String stem(String sencetence) {
		
		PorterStemmer stemmer = new PorterStemmer();
		String[] words = sencetence.split("\\s+");
		String stemmed_sentence ="";
		for(String word : words) {
			stemmed_sentence += stemmer.stem(word)+" ";
		}
		return stemmed_sentence;
	}
}
