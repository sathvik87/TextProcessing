package com.sathvik.textprocessing;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.sathvik.models.Resource;
import com.sathvik.utils.Utils;

public class SAXHandler extends DefaultHandler {

	private int totalNoOfPost = 0;
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		Resource resource;
		if (qName.equalsIgnoreCase("row")) {
			totalNoOfPost++;
			String id = attributes.getValue("Id");
			String bodyText = attributes.getValue("Body");

			// Split the text into multiset to count the frequency.
			Multiset<String> bagOfWords = HashMultiset.create(Splitter
					.on(CharMatcher.WHITESPACE).omitEmptyStrings()
					.split(bodyText));
			int count = 0;
			// Iterate the Query terms.
			for (Object word : Utils.QUERY_WORDS.elementSet()) {
				count = bagOfWords.count(word);
				if (count > Utils.THRESHOLD_WORD_FREQ) {
					resource = new Resource(new Integer(id), "");
					resource.setTerm((String) word);
					resource.setTermFreq(count);
					Utils.TERM_FREQ_MAP.put((String) word, resource);
				}

				//Gson gson = new Gson();
				//Utils.println("JSON STR "+gson.toJson(Utils.TERM_FREQ_MAP.asMap()));
				// Utils.println(word+"::"+count);
			}

			// Utils.println(id);
			// Utils.println(bodyText);
			// Utils.println("COUNT: "+count);

			// Maybe Create resource Obj.
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("row")) {
			// Not required now
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		String text = new String(ch, start, length);
		// Not required now.
	}

	public void endDocument() throws SAXException {
		Utils.println("Document reached its end");
		HashMap<String, Integer> idfmap = new HashMap<String, Integer>();
		
		// Find Term freq for all the posts in inverted index.
		for (Object term : Utils.TERM_FREQ_MAP.keys()) {
			Collection<Resource> collections = Utils.TERM_FREQ_MAP
					.get((String) term);
			float irfweight = (float) Math.log(totalNoOfPost/collections.size());
			//int irfweight = (int) Math.pow((int) (totalNoOfPost/collections.size()),2);
			
			Utils.println("TERM::"+term);
			Utils.println("WEIGHT::"+irfweight);
			
			//idfmap.put((String)term, (int)idfweight);
			for (Resource r : collections) {
				//With irf weight
				Utils.TERM_FREQ_MAP1.put(r.getPostId(), r.getTermFreq() * (int)irfweight);
				
				//Without irf weight.
				//Utils.TERM_FREQ_MAP1.put(r.getPostId(), r.getTermFreq());
			}

		}

		Map<Integer, Integer> termfreq_map = new HashMap<Integer, Integer>();
		for (Integer postid : Utils.TERM_FREQ_MAP1.keys()) {
			Collection<Integer> term_freq = Utils.TERM_FREQ_MAP1.get(postid);
			int sum = 0;
			for (Integer freq : term_freq) {
				sum = sum + freq;
			}
			termfreq_map.put(postid, sum);
			// Utils.println("Sum of Term Freq of postId: "+postid+": "+sum);
		}

		Map<Integer, Integer> sorted_termfreq_map = Utils.sortMapByValue(
				termfreq_map, true);

		for (Integer postid : sorted_termfreq_map.keySet()) {
			//Utils.println("Sum of Term Freq of postId: " + postid + ": "
			//		+ termfreq_map.get(postid));
		}

	}

}
