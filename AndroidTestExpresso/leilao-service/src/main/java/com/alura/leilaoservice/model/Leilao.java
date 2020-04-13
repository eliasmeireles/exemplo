package com.alura.leilaoservice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

    private long id;
    private String descricao;
    private List<Lance> lances;

    public long getId() {
        return id;
    }

    public void proporLance(Lance lance) {
        if (this.lances == null) {
            this.lances = new ArrayList<>();
        }
        this.lances.add(lance);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Lance> getLances() {
        if (lances != null && !lances.isEmpty()) {
            Collections.sort(lances);
        }
        return lances;
    }

    public void setLances(List<Lance> lances) {
        this.lances = lances;
    }
}
