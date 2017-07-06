package com.at.bd_dictionary.model;

public class Bean {
    private int id;
    private String engWord;
    private String bangWord;
    private String status;
    private String user;


    public Bean(String engWord){

        this.engWord = engWord;
    }


    public Bean( String engWord, String bangWord){
        this.engWord = engWord;
        this.bangWord = bangWord;
    }

    public Bean(int id, String engWord, String bangWord, String status,
                String user) {
        this.id = id;
        this.engWord = engWord;
        this.bangWord = bangWord;
        this.status = status;
        this.user = user;
    }

    public Bean(String engWord, String bangWord, String status, String user) {
        this.engWord = engWord;
        this.bangWord = bangWord;
        this.status = status;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    public String getBangWord() {
        return bangWord;
    }

    public void setBangWord(String bangWord) {
        this.bangWord = bangWord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    @Override
    public String toString() {
        return "Bean [id=" + id + ", engWord=" + engWord + ", bangWord="
                + bangWord + ", status=" + status + ", user=" + user + "]";
    }

}