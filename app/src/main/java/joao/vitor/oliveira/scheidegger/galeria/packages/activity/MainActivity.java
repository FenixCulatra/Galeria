package joao.vitor.oliveira.scheidegger.galeria.packages.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import joao.vitor.oliveira.scheidegger.galeria.R;
import joao.vitor.oliveira.scheidegger.galeria.packages.adapter.MyAdapter;
import joao.vitor.oliveira.scheidegger.galeria.packages.model.MyItem;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<>();
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fabAddNewItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });

        RecyclerView rvItens = findViewById(R.id.rvItens);
        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);


}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST && resultCode == Activity.RESULT_OK) {
            MyItem myItem = new MyItem();
            myItem.title = data.getStringExtra("title");
            myItem.description = data.getStringExtra("description");
            Uri selectedPhotoUri = data.getData();

            //Aqui abaixo é recebido o a imagem bitmap e adicionado o bitmap ao myItem
            try {
                Bitmap photo = com.example.produtos.util.Util.getBitmap(MainActivity.this, selectedPhotoUri, 100, 100);
                myItem.photo = photo;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



            itens.add(myItem);
            myAdapter.notifyItemInserted(itens.size() - 1);
        }
    }


    }
