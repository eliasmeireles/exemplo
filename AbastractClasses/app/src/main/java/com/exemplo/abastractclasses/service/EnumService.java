package com.exemplo.abastractclasses.service;

import java.util.ArrayList;

public class EnumService extends BaseService {


    public EnumService(ArrayList<? extends ItemRepresentation> items) {
        super(items);
    }

    @Override
    public void post() {
        delegate.execute(items);
    }
}
