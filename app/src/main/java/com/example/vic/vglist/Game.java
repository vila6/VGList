package com.example.vic.vglist;

/**
 * Created by Mau on 05/12/2017.
 */

public class Game {
    int id;
    String name, coverurl;
    float rating, personalrating;

    public Game(int id, String name, float rating, String coverurl){
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.coverurl = coverurl;
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
    public float getRating(){
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
    public String toString(){
        String toret="";
        toret += "Game id: " + getId() + "\nName: " + getName() + "\nrating: " + getRating() + "\ncover url: " + getCoverUrl() + "\n";
        return toret;
    }

}
