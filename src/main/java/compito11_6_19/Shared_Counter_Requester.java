package compito11_6_19;

import java.util.concurrent.Semaphore;

public class Shared_Counter_Requester {
    private int value=0; //contatore shared tra i thread Requester, valore iniziale =0
    private Semaphore mutex= new Semaphore(1); //mutex per la sincronizzazione

    public Shared_Counter_Requester() {
    }

    public int getValue() throws InterruptedException{
        mutex.acquire(); //sezione critica!
        int returnedvalue=this.value;
        this.value++;
        mutex.release();
        return returnedvalue; //ritorno il valore
    }
}
