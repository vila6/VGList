package com.example.vic.vglist;

/**
 * Created by Mau on 05/12/2017.
 */

public class Game {
    int id;
    String name, coverurl;
    double rating, personalrating;

    public Game(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Game(int id, String name, double rating, String coverurl){
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
    public double getRating(){
        return rating;
    }

    public void setRating(int id){
        this.id = id;
    }

    public String toString(){
        String toret="";
        toret += "Game id: " + getId() + "\nName: " + getName();
        return toret;
    }

}
