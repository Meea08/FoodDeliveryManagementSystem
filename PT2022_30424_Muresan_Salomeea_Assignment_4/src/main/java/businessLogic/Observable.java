package businessLogic;

import GUI.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers(String message);
}
