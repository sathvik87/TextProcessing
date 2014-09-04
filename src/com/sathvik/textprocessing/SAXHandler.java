package com.sathvik.textprocessing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.sathvik.utils.Utils;

public class SAXHandler extends DefaultHandler {
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("row")) {
			String id = attributes.getValue("Id");
			String bodyText = attributes.getValue("Body");
			
			//Split the text into multiset to count the frequency.
			Multiset<String> bagOfWords = HashMultiset.create(
					  Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings()
					    .split(bodyText));
			int count = bagOfWords.count("the");
			
			Utils.println(id);
			Utils.println(bodyText);
			Utils.println("COUNT: "+count);
			
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
