package com.jasdjf.testobserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    public List<MyObserver> observers = new ArrayList<>();

    public void registerObserver(MyObserver observer){
        if(observer == null){
            throw new NullPointerException();
        }
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public void unregisterObserver(MyObserver observer){
        if(observer==null){
            throw new NullPointerException();
        }
        observers.remove(observer);
    }

    public void allNotify(){
        for(MyObserver observer : observers){
            observer.update();
        }
    }
}
