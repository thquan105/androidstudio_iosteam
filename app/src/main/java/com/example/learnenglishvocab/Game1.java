package com.example.learnenglishvocab;

public class Game1 {
    private String img;
    private String tuvung;
    private String Nghiatuvung;

    public Game1( String tuvung, String nghiatuvung, String img) {
        this.img = img;
        this.tuvung = tuvung;
        this.Nghiatuvung = nghiatuvung;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTuvung() {
        return tuvung;
    }

    public void setTuvung(String tuvung) {
        this.tuvung = tuvung;
    }

    public String getNghiatuvung() {
        return Nghiatuvung;
    }

    public void setNghiatuvung(String nghiatuvung) {
        this.Nghiatuvung = nghiatuvung;
    }
}
