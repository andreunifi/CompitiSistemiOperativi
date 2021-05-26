package Compito11_01_21;

import java.util.ArrayList;

public class MessageQueue {
    public ArrayList<Messaggio> unlimitedqueue;
    public int numMex=0;

    public MessageQueue() {
        unlimitedqueue= new ArrayList<>();
    }

    public synchronized void addMessage(Messaggio m){
        numMex++;
        unlimitedqueue.add(m);
        notifyAll();

    }

    public synchronized  Messaggio getMessaggio(int type)throws InterruptedException{
        while (unlimitedqueue.isEmpty())
            wait();
        Messaggio m=null;
        for(int i=0;i<unlimitedqueue.size();i++){
            if(unlimitedqueue.get(i).id==type){
                m=unlimitedqueue.remove(i);
                break;
            }

        }
        if(m==null){
            System.out.println("Impossibile trovare un messaggio del tipo specificato nella lista");
        }else {
            numMex--;
        }
        return m;
    }



}
