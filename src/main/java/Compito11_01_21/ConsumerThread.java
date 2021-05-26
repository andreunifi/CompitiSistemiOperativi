package Compito11_01_21;

public class ConsumerThread extends Thread{
    private Risorsa resource;
    private MessageQueue queue;
    private ResourceManager manager;
    private int type;
    public int numMexProcessed=0;

    public ConsumerThread(MessageQueue queue,ResourceManager manager,int type) {
        this.queue = queue;
        this.manager=manager;
        this.type=type;
    }

    @Override
    public void run() {
        try {
            while (true){
                resource=manager.getresource();
                //System.out.println(getName() + " ha acquisito una risorsa di tipo " + resource.getResourceType());
                Messaggio m= queue.getMessaggio(type);
                //System.out.println(getName() + " ha elaborato il messaggio " + m.id);
                numMexProcessed++;
                manager.releaseResource(resource);
                sleep(10);
            }
        }catch (InterruptedException ie){ }
        finally {
            manager.releaseResource(resource);
        }
    }

}
