package compito11_6_19;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class WorkerManager {
    private ArrayList<Worker> workerlist; //arraylist dei worker
    private Semaphore notbusyworkers; //semaforo per la gestione dei worker
    private Semaphore mutex= new Semaphore(1); //mutex per la sezioni critiche

    public WorkerManager(int size) {
        workerlist= new ArrayList<>(size);
        for(int i=0;i<size;i++){
            Worker work= new Worker(this);
            work.setName(String.valueOf(i));
            workerlist.add(work);
            work.start();
        }
        notbusyworkers= new Semaphore(size);
        //inizializzazione dei worker del semaforo.
    }


    public Worker getFirstNotBusyWorker() throws InterruptedException{ //il metodo ritorna il primo worker disponibile
        //a eseguire una richiesta
        notbusyworkers.acquire(); //rimango in wait fino a che non ho un worker disponibile
        mutex.acquire(); //sezione critica!
        Worker worker=null;
        for(Worker notbusy: workerlist){
            if(!notbusy.isWorking && !notbusy.isBlocked){
                notbusy.isWorking=true;
                worker=notbusy;
                break;
            }
            //ritorno il primo worker non impegnato con una richiesta o in stallo


        }
        mutex.release(); //fine della sezione critica
        return worker;
    }

    public void releaseWorker(Worker we)throws InterruptedException{ //rendo di nuovo disponibile il worker
        mutex.acquire(); //sezione critica!
        we.isWorking=false;
        mutex.release();
        notbusyworkers.release(); //segnalo la liberazione di un worker
    }
//DEBUG
    public void PrintWorkerStatus(){
        String status="";
        for(Worker work: workerlist)
            status+= "\nWorker " + work.getName() + " Status: " + String.valueOf(work.isWorking) + " Isblocked=" + String.valueOf(work.isBlocked) + " TimesUsed:" + String.valueOf(work.timesused);
        System.out.println(status);
    }

}
