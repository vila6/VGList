package com.example.vic.vglist;

import java.io.Serializable;

/**
 * Created by Mau on 05/12/2017.
 */

public class Game implements Serializable {
    int id;
    String name, coverurl, description;
    double rating;
    float ratinguser;
    boolean state;


    public Game(int id, String name, double rating, String coverurl, String description, float ratinguser){
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.coverurl = coverurl;
        this.description = description;
        this.ratinguser = ratinguser;
        this.state = false;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(int id){
        this.id = id;
    }

    public void setCoverurl(String url){
        this.coverurl = url;
    }

    public String getCoverUrl(){
        return this.coverurl;
    }

    public String getDescription() { return this.description; }

    public void setDescription(String description){ this.description = description; }

    public void setRatinguser(int rating){
        this.ratinguser = rating;
    }

    public float getRatinguser(){
        return this.ratinguser;
    }

    public String getNameState(){
        if(this.state) return "Completed ";
        else return "Playing ";
    }

    public boolean getState(){
        return this.state;
    }

    public void setState(boolean state){
        this.state = state;
    }

    public String toString(){
        String toret="";
        toret += "Game id: " + getId() + "\nName: " + getName() + "\nrating: " + getRating() + "\ncover url: " + getCoverUrl() + "\n";
        return toret;
    }

}
