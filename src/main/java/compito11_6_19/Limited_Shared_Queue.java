package compito11_6_19;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Limited_Shared_Queue {
    private ArrayList<Request> listrequest;
    private Semaphore mutex= new Semaphore(1);
    private Semaphore piene= new Semaphore(0);
    private Semaphore vuote;

    public Limited_Shared_Queue(int size){
        listrequest= new ArrayList<>(size);
        vuote= new Semaphore(size);
    }

    public void addRequest(Request req)throws InterruptedException{
        vuote.acquire(); //rimango in attesa finchè non ho un posto libero
        mutex.acquire(); //sezione critica!
        listrequest.add(req);
        mutex.release();
        piene.release(); //segnalo l'aggiunta di un nuovo elemento
    }

    public Request removeRequest()throws InterruptedException{
        Request returnedrequest;
        piene.acquire(); //rimango in attesa funchè non è vuoto
        mutex.acquire(); //sezione critica
        returnedrequest= listrequest.remove(listrequest.size()-1);
        mutex.release();
        vuote.release(); //segnalo la rimozione di un elemento
        return returnedrequest;
    }


}
