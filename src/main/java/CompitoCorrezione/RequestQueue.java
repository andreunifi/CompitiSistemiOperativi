package CompitoCorrezione;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class RequestQueue {
    private ArrayList<Request> requests;
    private Semaphore mutex= new Semaphore(1);
    private Semaphore piene= new Semaphore(0);
    private Semaphore vuote;

    public RequestQueue(int k){
        requests= new ArrayList<>(k);
        vuote= new Semaphore(k);
    }

    public Request get() throws InterruptedException{
        piene.acquire();
        mutex.acquire();
        Request r= requests.remove(0);
        mutex.release();
        vuote.release();
        return r;
    }

    public void put(Request rq) throws InterruptedException {
        vuote.acquire();
        mutex.acquire();
        requests.add(rq);
        mutex.release();
        piene.release();
    }

    public void debugPrintInfo() throws InterruptedException {
        mutex.acquire();
        String n="";
        int i=0;
        for(Request r: requests){
            n+="\n" +"[" + i +"] "  + "Richiesta dal thread_id: " + r.thread_id + " con value " + r.value;
            i++;
        }
        mutex.release();
        System.out.println(n);
    }
}
