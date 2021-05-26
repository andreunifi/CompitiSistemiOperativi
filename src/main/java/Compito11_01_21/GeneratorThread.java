package Compito11_01_21;

import java.util.Random;

public class GeneratorThread extends Thread{
    private MessageQueue coda; //coda dei messaggi
    private int Bound; //intervallo di generazione dei messaggi

    public GeneratorThread(MessageQueue coda, int bound) {
        this.coda = coda;
        Bound = bound;
    }

    @Override
    public void run() {
        try {
            while (true){
                Messaggio m= new Messaggio(new Random().nextInt(Bound));
                coda.addMessage(m); //generazione e aggiunta di un nuovo messaggio
                //System.out.println(getName() + " ha generato un nuovo messaggio con id : " + m.id) ;
            }
        }catch (Exception e){

        }
    }
}
