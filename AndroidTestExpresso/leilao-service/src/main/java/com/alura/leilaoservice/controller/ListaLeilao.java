package com.alura.leilaoservice.controller;

import com.alura.leilaoservice.model.Lance;
import com.alura.leilaoservice.model.Leilao;

import java.util.ArrayList;
import java.util.List;

public class ListaLeilao {

    private static final List<Leilao> leiloes = new ArrayList<>();

    private static ListaLeilao instance;


    private ListaLeilao() {

    }

    public static ListaLeilao getGetInstance() {
        if (instance == null) {
            instance = new ListaLeilao();
        }
        return instance;
    }

    public List<Leilao> leiloes() {
        return leiloes;
    }

    public Leilao add(Leilao leilao) {
        long id = System.currentTimeMillis();
        leilao.setId(id);

        for (Lance lance : leilao.getLances()) {
            if (lance.getUsuario().getId() == 0) {
                lance.getUsuario().setId(id);
            }
        }

        leiloes.add(leilao);
        return leilao;
    }

    public void reset() {
        leiloes.clear();
    }
}
