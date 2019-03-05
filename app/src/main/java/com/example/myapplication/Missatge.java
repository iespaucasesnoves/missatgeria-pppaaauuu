package com.example.myapplication;

public class Missatge {
    private int codi;
    private String msg;
    private String datahora;
    private int fkuser;
    private int pendent;
    private String usuari;

    public Missatge() {
    }

    public Missatge(int codi, String msg, String datahora, int fkuser, int pendent) {
        this.codi = codi;
        this.msg = msg;
        this.datahora = datahora;
        this.fkuser = fkuser;
        this.pendent = pendent;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public int getFkuser() {
        return fkuser;
    }

    public void setFkuser(int fkuser) {
        this.fkuser = fkuser;

    }

    public int getPendent() {
        return pendent;
    }

    public void setPendent(int pendent) {
        this.pendent = pendent;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getUsuari() {
        return usuari;
    }
}
