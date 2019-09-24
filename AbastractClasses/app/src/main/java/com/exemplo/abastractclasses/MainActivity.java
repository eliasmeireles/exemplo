package com.exemplo.abastractclasses;

import android.content.Intent;
import android.os.Bundle;

import com.exemplo.abastractclasses.service.BaseService;
import com.exemplo.abastractclasses.service.EnumService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

import static com.exemplo.abastractclasses.ListItemsActivity.ITEMS_KEY;
import static com.exemplo.abastractclasses.ListItemsActivity.SERVICE_KEY;

public class MainActivity extends AppCompatActivity {

    ArrayList<Values> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Values> values = new ArrayList<>(Arrays.asList(Values.values()));
                EnumService enumService = new EnumService(values);
                Bundle bundle = new Bundle();
                bundle.putSerializable(SERVICE_KEY, enumService);
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 200 && data != null && data.hasExtra(ITEMS_KEY)) {
            items = data.getParcelableArrayListExtra(ITEMS_KEY);
        }
        
        if (items != null) {
            System.out.println(items.toString());
        }
    }
}
