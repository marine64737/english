package com.shkim.english;

import jakarta.persistence.*;

@Entity
public class english {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String english;
    private String meaning;
    private int number;
//    private boolean state;
    private boolean anki;
    private int difficulty;
    private boolean loop;

    public english() {
    }

    public english(int id, String english, String meaning, boolean anki, int difficulty, boolean loop){
        this.id = id;
        this.english = english;
        this.meaning = meaning;
        this.anki = anki;
        this.difficulty = difficulty;
        this.loop = loop;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public int getNumber() {
        return number;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setNumber(int number) {
        this.number = number;
    }

//    public boolean isState() {
//        return state;
//    }
//
//    public void setState(boolean state) {
//        this.state = state;
//    }

    public boolean isAnki() {
        return anki;
    }

    public void setAnki(boolean anki) {
        this.anki = anki;
    }

    public boolean isLoop() {
        return loop;
    }
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
