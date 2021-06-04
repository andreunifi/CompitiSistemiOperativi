package CompitoCorrezione;

import java.util.concurrent.Semaphore;

public class Client extends Thread{
    public Semaphore isDone= new Semaphore(0);
    public long tempoinviato;
    public volatile  long temporitorno=0;
    public long max=0;
    public long min=System.currentTimeMillis();
    public int numerorichieste=0;
    public float media=0;
    public long tempototale=0;
    public RequestQueue queue;
    public volatile float valorericevuto;
    public float valoreinviato=0;

    public Client(RequestQueue q) {
        this.queue=q;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()){
                float value= (float) (Math.random()*101);
                Request req= new Request(Integer.parseInt(this.getName()),value);
                queue.put(req);
                valoreinviato=value;
                tempoinviato=System.currentTimeMillis();
                numerorichieste++;


                isDone.acquire();
                System.out.println("La richiesta del thread " + this.getName() +
                        " è stata completata in: " + (this.temporitorno - this.tempoinviato) + " millisecondi"
                + ", con valore inviato: " + this.valoreinviato + " e con valore ricevuto: " + this.valorericevuto);
                long delay=this.temporitorno-tempoinviato;
                if(delay<min)
                    min=delay;
                if(delay>max)
                    max=delay;

                tempototale+=delay;
                this.media=tempototale/numerorichieste;
                //DEBUG

            }
        }catch (InterruptedException ie){


        }

    }
}
