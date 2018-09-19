package com.ngeartstudio.puskesmasmentaya.surveypuskesmasmentaya;

public class Data {
    private String no, survey, jam, tanggal, puas, tidakpuas;

    public Data() {
    }

    public Data(String no, String survey, String jam, String tanggal, String puas, String tidakpuas) {
        this.no = no;
        this.survey = survey;
        this.jam = jam;
        this.tanggal = tanggal;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setPuas(String puas) {
        this.puas = puas;
    }

    public String getPuas() {
        return puas;
    }

    public void setTidakpuas(String tidakpuas) {
        this.tidakpuas = tidakpuas;
    }

    public String getTidakpuas() {
        return tidakpuas;
    }
}
