package com.example.detect_ai.Model.Memento;

import java.util.ArrayList;

public class Caretaker {
    private final ArrayList<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento memento){
        mementos.add(memento);
    }

    public Memento getMemento(int index){
        return mementos.get(index-1);
    }

    public int getLength(){
        return mementos.size();
    }

    public boolean remove(){
        int index = mementos.size()-1;
          if(mementos.isEmpty()){
              return false;
          }else {
              mementos.remove(index);
              return true;
          }
    }
}