package com.example.learnenglishvocab;

public class Game1 {
    private int img;
    private String tuvung;
    private String Nghiatuvung;
    private String loai;

    public Game1(int img, String tuvung, String nghiatuvung, String loai) {
        this.img = img;
        this.tuvung = tuvung;
        this.Nghiatuvung = nghiatuvung;
        this.loai = loai;
    }

    public Game1(int img, String Nghiatuvung) {
        this.img = img;
        this.Nghiatuvung = Nghiatuvung;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
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

    public void setNghiatuvung(String Nghiatuvung) {
        Nghiatuvung = Nghiatuvung;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
