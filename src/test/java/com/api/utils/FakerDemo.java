package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {
		Locale locale=new Locale("en-IND");
		Faker faker=new Faker(locale);
		String firstname=faker.name().firstName();
		String lastName=faker.name().lastName();
		System.out.println(firstname+" "+lastName);
		
		System.out.println(faker.address().buildingNumber());
		System.out.println(faker.numerify("+91-8390######"));
	}
	
}
