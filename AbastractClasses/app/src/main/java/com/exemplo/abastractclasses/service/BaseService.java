package com.exemplo.abastractclasses.service;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

abstract public class BaseService implements Serializable {

    ArrayList<? extends ItemRepresentation> items;

    BaseService(ArrayList<? extends ItemRepresentation> items) {
        this.items = items;
    }

    ServiceDelegate delegate;

    public abstract void post();

    public interface ServiceDelegate {
        void execute(ArrayList<? extends ItemRepresentation> items);

        void done(boolean success);
    }

    public void setDelegate(ServiceDelegate delegate) {
        this.delegate = delegate;
    }

    public interface ItemRepresentation<T> extends Parcelable {
        String getName();

        T id();
    }


}
