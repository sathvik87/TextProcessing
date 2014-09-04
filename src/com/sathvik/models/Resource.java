package com.sathvik.models;

public class Resource {
	int postid;
	String text;
	int term_count;
	String term;
	
	Resource(int id, String text, int count, String term) {
		this.postid = id;
		this.text = text;
		this.term_count = count;
		this.term = term;
	}
	
	public int getPostId() {
		return postid;
	}
	
	public String getText() {
		return text;
	}
	
	public int getTermFreq() {
		return term_count;
	}
	
	public String getTerm() {
		return term;
	}
}
