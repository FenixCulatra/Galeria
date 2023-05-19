package joao.vitor.oliveira.scheidegger.galeria.packages.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
import joao.vitor.oliveira.scheidegger.galeria.packages.model.MainActivityViewModel;
import joao.vitor.oliveira.scheidegger.galeria.packages.model.MyItem;
import joao.vitor.oliveira.scheidegger.galeria.packages.util.Util;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST = 1;

    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fabAddNewItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            //Cria um evento para o botao, onde o cria e te manda para uma nova atividade, esperando um resultado vindo dela
            //O resultado vai ser um titulo
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });

        //Aqui o recycler view é configurado e coloca qual objeto ele vai repetir, recebendo as informações que já existirem no ViewModel
        RecyclerView rvItens = findViewById(R.id.rvItens);
        MainActivityViewModel vm = (MainActivityViewModel) new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);


}
    @Override
    //Aqui eu obtenho o resultado da atividades
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST && resultCode == Activity.RESULT_OK) {
            MyItem myItem = new MyItem();
            myItem.title = data.getStringExtra("title");
            myItem.description = data.getStringExtra("description");
            Uri selectedPhotoUri = data.getData();

            //Aqui abaixo é recebido o a imagem bitmap e adicionado o bitmap ao myItem
            try {
                Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoUri, 100, 100);
                myItem.photo = photo;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            MainActivityViewModel vm = (MainActivityViewModel) new ViewModelProvider(this).get(MainActivityViewModel.class);
            List<MyItem> itens = vm.getItens();
            //O item é adicionado à lista de itens
            itens.add(myItem);
            myAdapter.notifyItemInserted(itens.size() - 1);
        }
    }


    }
