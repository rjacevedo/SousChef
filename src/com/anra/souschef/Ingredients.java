package com.anra.souschef;

public class Ingredients {
	static final String [] veggies = {"Tomato","Mushroom","Onion","Bell Pepper","Brocolli"};
	static final String [] fruits = {"Apple", "Orange", "Grape", "Pineapple", "Pear", "Peach"};
	static final String [] dairy = {"Milk", "Cheese", "Yogurt", "Ice cream"};
	static final String [] meat = {"Chicken", "Beef", "Pork", "Egg", "Fish"};
	static final String [] grains = {"Bread", "Pasta", "Rice"};
	static final String [] spices = {"Oregano", "Cumin", "Garlic"};
	
	static String [] getList (int page){
		switch (page) {
		case 0:
			return veggies;
		case 1:
			return meat;
		case 2:
			return dairy;
		case 3:
			return grains;
		case 4:
			return fruits;
		case 5:
			return spices;
		default:
			return null;
		}
	}
}
