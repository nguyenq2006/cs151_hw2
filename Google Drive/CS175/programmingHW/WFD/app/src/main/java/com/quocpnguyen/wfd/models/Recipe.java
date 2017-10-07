package com.quocpnguyen.wfd.models;

/**
 * Created by cs on 10/1/17.
 */

public class Recipe {
    private String name;
    private String ingredients;
    private String img_src;
    private String description;

    public Recipe(String name, String ingredients, String img_src, String description){
        this.name = name;
        this.ingredients = ingredients;
        this.img_src = img_src;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getImg_src() {
        return img_src;
    }

    public String toString(){
        String result =  name + "\n\n" + "Ingredients:\n";
        for(String s : ingredients.split(",")){
            result += "- " + s.trim() + "\n";
        }
        return result + "\nDescription\n" + description;
    }
}
