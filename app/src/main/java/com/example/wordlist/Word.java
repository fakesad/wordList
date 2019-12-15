package com.example.wordlist;
public class Word {
    private String word;
    private String mean;
    private String example;
    public Word() {

    }

    public Word(String word, String mean, String example) {
        this.word = word;
        this.mean = mean;
        this.example = example;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}