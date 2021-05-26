package Compito11_01_21;

import java.util.ArrayList;

public class MessageQueue {
    public ArrayList<Messaggio> unlimitedqueue; //coda illimitata di messaggi
    public int numMex=0; //contatore per il numero di messaggi

    public MessageQueue() {
        unlimitedqueue= new ArrayList<>();
    }

    public synchronized void addMessage(Messaggio m){
        //i thread generator possono aggiungere il messaggio appena lo hanno creato, non devono aspettare nulla in quanto la coda è illimitata
        numMex++; //incremento il contatore
        unlimitedqueue.add(m); // aggiungo il messaggio alla coda
        notifyAll(); //notifico eventuali thread Consumer che la coda non è più vuota

    }

    public synchronized  Messaggio getMessaggio(int type)throws InterruptedException{
        while (unlimitedqueue.isEmpty())
            wait(); //rimango in wait, impossibile consumare messaggi in una coda vuota
        Messaggio m=null;
        for(int i=0;i<unlimitedqueue.size();i++){
            if(unlimitedqueue.get(i).id==type){
                m=unlimitedqueue.remove(i); // se trovo messaggi che hanno come tipo lo stesso richiesto dal thread consumer, lo rimuovo dalla lista e lo faccio ritornare dalla funzione
                break;
            }

        }
        if(m==null){
            System.out.println("Impossibile trovare un messaggio del tipo specificato nella lista"); //nessun messaggio del tipo richiesto
        }else {
            numMex--;
            notifyAll(); //segnalo la rimozione di un messaggio
        }
        return m; //ritorno il messaggio
    }



}
