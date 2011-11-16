package com.morningwoodsoftware.android.spuge.dto;

public class Venue 
{
	private String name;
	private String question;
	public Venue(String name, String question) {
		super();
		this.name = name;
		this.question = question;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
}
