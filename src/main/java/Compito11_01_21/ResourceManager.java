package Compito11_01_21;

import javax.annotation.Resource;
import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<Risorsa> risorseA;
    private ArrayList<Risorsa> risorseB;

    public ResourceManager(int nA,int nB) {
        risorseA= new ArrayList<>(nA);
        risorseB= new ArrayList<>(nB);
        for(int i=0;i<nA;i++)
            risorseA.add(new Risorsa(0));
        for(int i=0;i<nB;i++)
            risorseB.add(new Risorsa(1));
    }

    public synchronized Risorsa getresource() throws InterruptedException {
        Risorsa returned;
        if(!risorseA.isEmpty()){
            returned= risorseA.remove(0);
        } else {
            while (risorseB.isEmpty())
                wait();
            returned=risorseB.remove(0);

        }
        notifyAll();
        return returned;
    }


    public synchronized void releaseResource(Risorsa res){
        if(res.type==0){
            risorseA.add(res);
        }else {
            risorseB.add(res);
        }
        notifyAll();
    }

    public int returnSizeA(){
        return risorseA.size();
    }
    public int returnSizeB(){
        return risorseA.size();
    }


}
