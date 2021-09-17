package com.github.andreunifi;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Compito {
    public static void main(String[] args){
        int N=9;
        int M=14;                         //Assegnazione delle variabili richieste nel testo
        int K=15;
        int nA=8;
        int nB=5;
        int T1=1; //1*1000 millsecondi
        int T2=2; //2*1000 millisecondi
        RequestQueue queue= new RequestQueue(K);
        Client[] clients= new Client[N];
        Worker[] workers= new Worker[M]; //Worker riorganizzati in un array

        for (int i=0;i<N;i++){
            clients[i]= new Client(queue);
            clients[i].setName(String.valueOf(i));
            clients[i].start();
        }
        ResourceManager manager= new ResourceManager(nA,nB);
        GestoreClient gestore= new GestoreClient(clients);
        for(int i=0;i<M;i++){
            workers[i]= new Worker(queue,gestore,T1,T2,manager);
            workers[i].start();
        }


        try {                                 //aggiunto un blocco try/catch per lo sleep di 10 secondi
            Thread.sleep(10000);
            for(int i=0;i<N;i++){
                clients[i].interrupt();

            }

            for(int i=0;i<N;i++)
                clients[i].join();

             //Mi ero dimenticato di interrompere correttamente i thread Worker

            for(int i=0;i<M;i++){
                workers[i].interrupt();

            }

            for(int i=0;i<M;i++)
                workers[i].join();


            gestore.stampaThread();
            System.out.println("Risorse a:"+ manager.nA + " Risorse b:" + manager.nB);

        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

    }


}
class Request {
    public int thread_id;
    public float value;

    public Request(int thread_id, float value) {
        this.thread_id = thread_id;
        this.value = value;
    }
}

class RequestQueue {
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
//DEBUG
    public void debugPrintInfo() throws InterruptedException { //funzione di debug per la stampa delle richieste in coda
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
//FINE DEBUG
class ResourceManager {
    public int nA;
    public int nB;
    private Semaphore mutex= new Semaphore(1);
    private Semaphore risorseA;
    private Semaphore risorseB;

    public ResourceManager(int nA, int nB) {
        this.nA = nA;
        this.nB = nB;
        risorseA= new Semaphore(nA);
        risorseB= new Semaphore(nB);
    }
    public void getResourceA()throws InterruptedException{
        risorseA.acquire();
        mutex.acquire();
        nA--;
        mutex.release();
    }

    public void releaseResourceA() throws InterruptedException{
        mutex.acquire();
        nA++;
        mutex.release();
        risorseA.release();
    }

    public void getResourceB()throws InterruptedException{
        risorseB.acquire();
        mutex.acquire();
        nB--;
        mutex.release();
    }

    public void releaseResourceB() throws InterruptedException{
        mutex.acquire();
        nB++;
        mutex.release();
        risorseB.release();
    }
}

class Client extends Thread{
    public Semaphore isDone= new Semaphore(0);
    public long tempoinviato;
    public long temporitorno=0; //Rimozione di volatile
    public long max=0;
    public long min=System.currentTimeMillis();
    public int numerorichieste=0;
    public float media=0;
    public long tempototale=0;
    public RequestQueue queue;
    public float valorericevuto; //Rimozione di volatile
    public float valoreinviato=0;

    public Client(RequestQueue q) {
        this.queue=q;
    }

    @Override
    public void run() {
        try { //Ho spostato l'aggiunta di una nuova richiesta dentro il while
            while (!isInterrupted()){
                float value= (float) (Math.random()*100); //avevo considerato il 100 incluso, il bound è [0-100) invece
                Request req= new Request(Integer.parseInt(this.getName()),value);
                queue.put(req);
                valoreinviato=value; //non avevo assegnato, per fretta, il valore value all'attributo "valoreinviato"
                tempoinviato=System.currentTimeMillis();



                isDone.acquire();

                //DEBUG

                System.out.println("La richiesta del thread " + this.getName() +
                        " e' stata completata in: " + (this.temporitorno - this.tempoinviato) + " millisecondi"
                        + ", con valore inviato: " + this.valoreinviato + " e con valore ricevuto: " + this.valorericevuto);
                //FiNE DEBUG

                numerorichieste++;
                long delay=this.temporitorno-tempoinviato;
                if(delay<min)
                    min=delay;
                if(delay>max)
                    max=delay;

                tempototale+=delay;


            }
        }catch (InterruptedException ie){
            //Avevo dimenticato di calcolare la media dopo l'interruzione del thread
            if(numerorichieste>0){
                this.media=tempototale/numerorichieste;      //codice per evitare una Arithemtic Exception se il client non riesce
            }else {                                          //a ricevere la richiesta perchè interrotto
                this.media=0;
            }

        }

    }
}


class GestoreClient {
    Client[] array;

    public GestoreClient(Client[] array) {
        this.array = array;
    }

    public Client getClient(int client_id){
        return array[client_id];

    }
    public void stampaThread(){
        String k="";
        for(Client c: array){
            k+="\n[" + c.getName() + "]" + " ha minimo: " + c.min + ", ha massimo: "
                    + c.max + ", e ha media: " + c.media + ", con " + c.numerorichieste + " richieste";
        }
        System.out.println(k);
    }
}

class Worker extends Thread {
    private int T1;
    private int T2;
    private ResourceManager rm;
    private RequestQueue q;
    private GestoreClient gc;

    public Worker(RequestQueue q, GestoreClient gc, int T1, int T2, ResourceManager manager) {
        this.q = q;
        this.gc = gc;
        this.T1 = T1;
        this.T2 = T2;
        this.rm = manager;
    }

    @Override
    public void run() {
        try {
            while (true) { //Avevo dimenticato di aggiungere il ciclo while al metodo run
                Request r = q.get();
                rm.getResourceA();
                try {                   //Utilizzo sbagliato dei try/catch/finally: avevo utilizzato il blocco catch per rilasciare le
                    sleep(T1*1000); //risorse al posto del finally e nella mia implementazione del compito l'interruzione del Worker
                    rm.getResourceB();   //non garantiva il corretto rilascio delle risorse.
                    try {
                        sleep(T2 * 1000);
                    }finally {
                        rm.releaseResourceB();
                    }
                } finally {
                    rm.releaseResourceA();
                }

                Client client = gc.getClient(r.thread_id);
                client.temporitorno = System.currentTimeMillis();
                client.valorericevuto = (r.value * 2);
                client.isDone.release();



            }

        } catch (InterruptedException ie) {}
    }
}





