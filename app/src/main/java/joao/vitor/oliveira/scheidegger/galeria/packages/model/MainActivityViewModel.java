package joao.vitor.oliveira.scheidegger.galeria.packages.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

//Criamos um ViewModel para armazenar os itens
public class MainActivityViewModel extends ViewModel {
    List<MyItem> itens = new ArrayList<>();
    //Uma função para ser capaz de pegar os itens
    public List<MyItem> getItens() {
        return itens;
    }

}
