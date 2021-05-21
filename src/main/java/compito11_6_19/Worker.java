package compito11_6_19;

import java.util.Random;

public class Worker extends Thread{
    private Request received; //richiesta ricevuta dall'assigner
    public boolean isBlocked=false; //booleano che mi indica lo stallo
    public boolean isWorking=false; //booleamo che mi indica lo stato del worker
    public int timesused=0; //contatore per le richieste arrivate
    private WorkerManager manager; //manager che gestisce i worker

    Worker(WorkerManager manager){
        this.manager=manager;
    }


    public Request getReceived() {
        return received;
    }

    public void setReceived(Request received) {
        this.received = received;
        this.received.setReceived_Date(System.currentTimeMillis());

    }

    @Override
    public void run() {
        Random rand= new Random(); //genero un numero casuale
        try{
            while (true){

                if(rand.nextInt(101)<=10){ // codice per determinare se il thread è bloccato:
                    //ogni volta che eseguo il while, ricalcolo se il thread entrerà in stallo
                    //(bound: 0-101) <10 ----> 10% di probabilità che si blocchi
                    sleep(1000);
                    isBlocked=true; //segnalo il blocco del thread
                    //il thread per questa esecuzione del ciclo while non fa nulla eccetto aspettare

                }else {
                    //
                    isBlocked=false;
                    if(!isWorking){
                        //worker isn't busy with anything
                    }else{
                        //worker is busy with a request
                        timesused++; //incrementa contatore delle richieste
                        received.setFinalized_value(received.getSentvalue()*2); //la richiesta ricevuta viene
                        // modificata
                        received.getRequesterthreadreference().setFinalizedrequest(received);
                        //attraverso la reference al thread Requester, passo la nuova richiesta(con valore modificato)
                        //al thread Requester originale che l'ha inviata
                        received.getRequesterthreadreference().iswaiting=false; //Segnalo al thread Requester che ha ricevuto
                        //la richiesta
                        manager.releaseWorker(this); //rilascio questo worker che non è più occupato
                    }

                }
            }
        }catch (InterruptedException excpe){

        }

    }

    @Override
    public void interrupt() {
        if(isBlocked){
            System.out.println("Worker " + this.getName() + "is blocked! Cannot interrupt.");
        }else {
            super.interrupt();
        }
        //faccio l'override del metodo interrupt----> posso usarlo solo se il thread non è in stallo

    }



}
