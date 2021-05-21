package compito11_6_19;

import java.util.ArrayList;

public class Assigner extends Thread{
    private Limited_Shared_Queue queue; //coda delle richieste
    private WorkerManager manager; //manager per i worker

    public Assigner(Limited_Shared_Queue queue, WorkerManager manager) {
        this.queue = queue;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            while (true){
                Worker notbusy= manager.getFirstNotBusyWorker(); //il metodo ritorno il primo worker non impegnato in una
                //operazione
                Request request= queue.removeRequest(); //viene tolta una request dalla code per eesere
                //elaborata
                notbusy.setReceived(request); //il worker riceve la request
                System.out.println("Assigner " + this.getName() + " has assigned a request from Requester " +request.getRequesterthreadreference().getName() + "  to worker " + notbusy.getName());
            }
        }catch (InterruptedException exception){

        }
    }
}
