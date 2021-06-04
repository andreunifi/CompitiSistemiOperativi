package CompitoCorrezione;

public class Worker extends Thread {
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
            while (true) {
                Request r = q.get();
                rm.getResourceA();
                try {
                    sleep(T1*1000);
                    rm.getResourceB();
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

/*
while (true){
                Request r= q.get();
                rm.getResourceA();
                try{
                    sleep(T1*1000);
                    rm.getResourceB();
                    try{
                        sleep(T2*1000);
                        Client client=gc.getClient(r.thread_id);
                        client.temporitorno=System.currentTimeMillis();
                        client.valorericevuto= (r.value*2);
                        client.isDone.release();
                        rm.releaseResourceA();
                        rm.releaseResourceB();

                    }catch (InterruptedException ie){}
                    finally {
                        rm.releaseResourceA();
                        rm.releaseResourceB();
                    };
                }catch (InterruptedException ie){}
                finally {
                    rm.releaseResourceA();

                }


            }
 */
