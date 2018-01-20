package com.example.victo.ntiapp;

public class Snacks {

    private int _id;
    private String _namn;
    private String _info;
    private int _pris;
    private int _nyttighet;

    public Snacks() {
    }

    public Snacks(String namn, String info, int pris, int nyttighet) {
        this._namn = namn;
        this._info = info;
        this._pris = pris;
        this._nyttighet = nyttighet;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_namn(String _namn) {
        this._namn = _namn;
    }

    public void set_info(String _info) {
        this._info = _info;
    }

    public void set_pris(int _pris) {
        this._pris = _pris;
    }

    public void set_nyttighet(int _nyttighet) {
        this._nyttighet = _nyttighet;
    }

    public int get_id() {
        return _id;
    }

    public String get_namn() {
        return _namn;
    }

    public String get_info() {
        return _info;
    }

    public int get_pris() {
        return _pris;
    }

    public int get_nyttighet() {
        return _nyttighet;
    }
}
