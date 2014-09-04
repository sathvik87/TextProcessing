package com.sathvik.textprocessing;

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
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		Resource resource;
		if (qName.equalsIgnoreCase("row")) {
			
			String id = attributes.getValue("Id");
			String bodyText = attributes.getValue("Body");
			
			//Split the text into multiset to count the frequency.
			Multiset<String> bagOfWords = HashMultiset.create(
					  Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings()
					    .split(bodyText));
			int count = 0;
			//Iterate the Query terms.
			for (Object word : Utils.QUERY_WORDS.elementSet()) {
				count = bagOfWords.count(word);
				if(count > Utils.THRESHOLD_WORD_FREQ) {
					resource = new Resource(new Integer(id),"");
					resource.setTerm((String)word);
					resource.setTermCount(count);
					Utils.TERM_FREQ_MAP.put((String)word,resource);
				}
				
				Gson gson = new Gson();  
				//Utils.println("JSON STR "+gson.toJson(Utils.TERM_FREQ_MAP.get("protein")));
				//Utils.println(word+"::"+count);
			}
			
			
			//Utils.println(id);
			//Utils.println(bodyText);
			//Utils.println("COUNT: "+count);
			
			//Maybe Create resource Obj.
		} 
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("row")) {
			//Not required now
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		String text = new String(ch, start, length);
		//Not required now.
	}
}
