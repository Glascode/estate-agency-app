package io.github.glascode.estateagency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PropertyListActivity extends AppCompatActivity {

    List<Property> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        RecyclerView recyclerView = findViewById(R.id.layout_property_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new PropertyAdapter(this.objectList));
    }

    public void viewItem(View v) {
        TextView clicked = (TextView) v;
        Toast.makeText(this, "Click sur item" + clicked.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
