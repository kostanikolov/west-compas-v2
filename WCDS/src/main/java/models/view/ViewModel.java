package models.view;

public class ViewModel<T> {

    private T object;

    public ViewModel(T object) {
        this.object = object;
    }
}
