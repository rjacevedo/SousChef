package com.anra.souschef;

import java.util.List;

public class Recipe {
	List<String> Ingredients;
	String url;
	String imgUrl;
	double timeInSeconds;
	int rating;
	
	public Recipe(List<String> Ingredients, String url, String imgUrl, double timeInSeconds, int rating){
		this.Ingredients=Ingredients;
		this.url = url;
		this.imgUrl = imgUrl;
		this.timeInSeconds=timeInSeconds;
		this.rating = rating;
	}
}
