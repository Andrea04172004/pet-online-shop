package com.sazonov.mainonlineshop.utils;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Random;

public class UserGenerator {

    public static String setEmailAddress () {
        Faker usFaker = new Faker(new Locale("en-US"));
        return usFaker.internet().emailAddress();
    }
    public static String setPassword () {
        Faker faker = new Faker();
        Integer pass=faker.number().numberBetween(500, 100000);
        return pass.toString();
    }

    public static String setFirstName () {
        Faker faker = new Faker(new Locale("en-US"));
        return faker.name().firstName();
    }
    public static String setLastName () {
        Faker faker = new Faker(new Locale("en-US"));
        return faker.name().lastName();
    }
    public static String setAddress () {
        Faker faker = new Faker(new Locale("en-US"));
        return faker.address().fullAddress();
    }

    public static String setPhoneNumber () {
        Random random = new Random();
        int num1=random.nextInt(7)+1;
        int num2 = random.nextInt(8);
        int set1 = random.nextInt(643) + 100;
        int set2 = random.nextInt(9)+1;
        int set3 = random.nextInt(643) + 100;
        int set4 = random.nextInt(643) + 100;

        return "+"+num1+""+num2+" "+set1+" "+set2+" "+set3+" "+set4;
    }

}
