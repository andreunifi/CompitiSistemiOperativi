package Compito11_01_21;

public class ConsumerThread extends Thread{
    private Risorsa resource; //risorsa utilizzata
    private MessageQueue queue; //coda dei messaggi
    private ResourceManager manager; //manager delle risorse
    private int type; //tipo di messaggio da consumare
    public int numMexProcessed=0; //numero messaggi consumati

    public ConsumerThread(MessageQueue queue,ResourceManager manager,int type) {
        this.queue = queue;
        this.manager=manager;
        this.type=type;
    }

    @Override
    public void interrupt() {
        manager.releaseResource(resource); //voglio che si rilasci la risorsa in caso di interrupt
        super.interrupt();
    }

    @Override
    public void run() {
        try {
            while (true){
                resource=manager.getresource();
                try{
                    Messaggio m= queue.getMessaggio(type);
                    //System.out.println(getName() + " ha elaborato il messaggio " + m.id);
                    numMexProcessed++;
                    //ho finito di processare il messaggio, rilascio la risorsa richiesta
                }catch (InterruptedException interruptedException){}finally {
                    manager.releaseResource(resource);
                    // cerco di ottenere una risorsa a, altrimenti una b
                }
                //System.out.println(getName() + " ha acquisito una risorsa di tipo " + resource.getResourceType());

                sleep(10);
                manager.releaseResource(resource);
            }
        }catch (InterruptedException ie){}


    }

}
