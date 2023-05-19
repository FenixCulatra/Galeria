package joao.vitor.oliveira.scheidegger.galeria.packages.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

//criamos um viewModel para armazenar as informações da atividade que vai criar um item
public class NewItemActivityViewModel extends ViewModel {
    Uri selectedPhotoSelection = null;

    //Função que retorna uri da foto que será armazenada
    public Uri getSelectedPhotoSelection() {
        return selectedPhotoSelection;
    }

    //Função que permiti colocar qual a foto
    public void setSelectedPhotoSelection(Uri selectedPhotoSelection) {
        this.selectedPhotoSelection = selectedPhotoSelection;
    }
}
