package ro.iss2024;

public interface Observable {
    void addObserver(Observer o);
    void removeObsrver(Observer o);
    void notifyObservers();

}
