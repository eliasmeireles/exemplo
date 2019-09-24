package com.exemplo.abastractclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.exemplo.abastractclasses.service.BaseService;

import java.util.ArrayList;
import java.util.List;

public class ListItemsActivity extends AppCompatActivity implements BaseService.ServiceDelegate {

    public static final String SERVICE_KEY = "SERVICE_KEY";
    public static final String ITEMS_KEY = "ITEMS_KEY";

    private BaseService service;
    private ArrayList<? extends BaseService.ItemRepresentation> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(SERVICE_KEY)) {
            service = (BaseService) intent.getSerializableExtra(SERVICE_KEY);
            if (service != null) {
                service.setDelegate(this);
            }
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.post();
            }
        });
    }


    @Override
    public void execute(ArrayList<? extends BaseService.ItemRepresentation> items) {
        this.items = items;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ITEMS_KEY, items);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void done(boolean success) {

    }
}
