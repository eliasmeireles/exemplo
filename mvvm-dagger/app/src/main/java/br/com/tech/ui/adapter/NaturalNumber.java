package br.com.tech.ui.adapter;

public abstract class NaturalNumber<T extends ItemViewAdapter> {

    private T n;

    public abstract <U extends BaseViewHolder> BaseViewHolder get();
}