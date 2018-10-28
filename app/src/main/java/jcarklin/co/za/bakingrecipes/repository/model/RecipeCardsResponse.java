package jcarklin.co.za.bakingrecipes.repository.model;

import java.util.List;

public class RecipeCardsResponse {

    final private List<RecipeComplete> list;

    public RecipeCardsResponse(List<RecipeComplete> list) {
        this.list = list;
    }

    public List<RecipeComplete> getList() {
        return list;
    }
}

