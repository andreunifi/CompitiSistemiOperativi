package compito11_6_19;

import java.util.concurrent.Semaphore;

public class Requester extends Thread{
    private Limited_Shared_Queue queue; //coda delle richiesete
    private Shared_Counter_Requester counter; //counter condiviso con i vari thread
    private Request sentrequest; //richiesta inviata
    private Request finalizedrequest; //richiesta ricevuta
    public boolean iswaiting=true; //booleano che indica se il thread sta aspettando una risposta dal worker
    //è uguale a true perchè all'inizio invio subito una richiesta. Verrò modificato in seguito dall'apposito
    //worker che tramite una reference allo specifico Thread Requester modificherà il booleano in false.

    public Requester(Limited_Shared_Queue queue,Shared_Counter_Requester counter) {
        this.queue = queue;
        this.counter=counter;
    }

    @Override
    public void run() {
        try {
            Request request= new Request(counter.getValue(),this);
            //il compito chiedeva che si inviasse una richiesta subito all'inizio
            this.queue.addRequest(request);
            this.sentrequest=request;
            while (true){
                if(iswaiting){
                    //il thread sta ancora aspettando una risposta dal worker


                }else{
                    //il thread ha ricevuto una risposta
                    System.out.println("Requester " + this.getName() + " has received back the finalized request."
                                    + "\nInitial value is: " + finalizedrequest.getSentvalue()+" , finalized value is " + finalizedrequest.
                                    getFinalized_value() + " and time passed was " + String.valueOf(finalizedrequest.getReceived_Date() - finalizedrequest.getSent_Date()));
                    //una nuova richiesta viene inviata al worker
                    Request req= new Request(counter.getValue(),this);
                    queue.addRequest(req);
                    this.sentrequest=req;
                }

            }

        }catch (InterruptedException excep){


        }
    }

    public Request getFinalizedrequest() {
        return finalizedrequest;
    }

    public void setFinalizedrequest(Request finalizedrequest) {
        this.finalizedrequest = finalizedrequest;
    }

    public Request getSentrequest() {
        return sentrequest;
    }

    public void setSentrequest(Request sentrequest) {
        this.sentrequest = sentrequest;
    }


    /*

    Request req= new Request(counter.getValue(),this);
            iswaiting=true;
            queue.addRequest(req);
            this.sentrequest=req;
            while (true){
                if(iswaiting){
                    //Requester is still waiting for a response from a worker
                    //System.out.println("Requester " + this.getName() + " is waiting for a response..");
                }else{
                    //The worker gave a response.
                    System.out.println("Requester " + this.getName() + " has received back the finalized request."
                    + "\nInitial value is: " + finalizedrequest.getSentvalue()+" , finalized value is " + finalizedrequest.
getFinalized_value() + " and time passed was " + String.valueOf(finalizedrequest.getReceived_Date() - finalizedrequest.getSent_Date())
                    );
                    //a new request is added to the queue
                    this.finalizedrequest=null;
                    queue.addRequest(new Request(counter.getValue(),this));
                }

            }

        }catch (InterruptedException excep){

        }
     */
}
