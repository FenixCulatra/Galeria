package joao.vitor.oliveira.scheidegger.galeria.packages.activity;

import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import joao.vitor.oliveira.scheidegger.galeria.R;
import joao.vitor.oliveira.scheidegger.galeria.packages.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);

        Uri photoSelected = vm.getSelectedPhotoSelection();
        if (photoSelected != null) {
            ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
            imvPhotoPreview.setImageURI(photoSelected);
        }

        ImageButton imgCI = findViewById(R.id.imbCl);
        Button btnAdd = findViewById(R.id.btnAddItem);

        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "È necessário colocar uma image", Toast.LENGTH_SHORT).show();
                    return;
                }


                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário colocar um título", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();
                if (desc.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "é necessário colocar uma descição", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", desc);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Essa função busca ter uma uri da foto como resultado, passar ela para o viewMode e setar a imagem para o preview
        if (requestCode == PHOTO_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Uri photoSelected = data.getData();
                ImageView imvPreview = findViewById(R.id.imvPhotoPreview);
                imvPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                vm.setSelectedPhotoSelection(photoSelected);

            }
        }
    }

}