package Compito11_01_21;

import java.util.Random;

public class GeneratorThread extends Thread{
    private MessageQueue coda;
    private int Bound;

    public GeneratorThread(MessageQueue coda, int bound) {
        this.coda = coda;
        Bound = bound;
    }

    @Override
    public void run() {
        try {
            while (true){
                Messaggio m= new Messaggio(new Random().nextInt(Bound));
                coda.addMessage(m);
                //System.out.println(getName() + " ha generato un nuovo messaggio con id : " + m.id) ;
            }
        }catch (Exception e){

        }
    }
}
