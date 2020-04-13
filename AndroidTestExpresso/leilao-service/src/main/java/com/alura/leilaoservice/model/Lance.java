package com.alura.leilaoservice.model;

import org.jetbrains.annotations.NotNull;

public class Lance implements Comparable<Lance> {

    private Usuario usuario;
    private double valor;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    @Override
    public int compareTo(@NotNull Lance o) {
        return Double.compare(o.getValor(), valor);
    }
}
