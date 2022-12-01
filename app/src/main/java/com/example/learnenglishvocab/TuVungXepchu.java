package com.example.learnenglishvocab;

public class TuVungXepchu {
    private String tuvung;
    private String nghia;

    public TuVungXepchu(String tuvung, String nghia) {
        this.tuvung = tuvung;
        this.nghia = nghia;
    }

    public String getTuvung() {
        return tuvung;
    }

    public void setTuvung(String tuvung) {
        this.tuvung = tuvung;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }
}
