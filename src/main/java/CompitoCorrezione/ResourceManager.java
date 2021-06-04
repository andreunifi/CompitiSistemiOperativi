package CompitoCorrezione;

import java.util.concurrent.Semaphore;

public class ResourceManager {
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
