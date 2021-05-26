package Compito11_01_21;

import javax.annotation.Resource;
import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<Risorsa> risorseA; //lista risorse tipo a
    private ArrayList<Risorsa> risorseB; //lista risorse tipo b

    public ResourceManager(int nA,int nB) {
        risorseA= new ArrayList<>(nA); //inizializzo le liste
        risorseB= new ArrayList<>(nB);
        for(int i=0;i<nA;i++)
            risorseA.add(new Risorsa(0));
        for(int i=0;i<nB;i++)
            risorseB.add(new Risorsa(1));
    }

    public synchronized Risorsa getresource() throws InterruptedException {
        Risorsa returned;
        if(!risorseA.isEmpty()){  //verifico che lista a non sia vuota -> se non lo è bene, la risorsa ottenuta è di tipo a
            returned= risorseA.remove(0);
        } else { //altrimenti voglio ottenere una risorsa di tipo b, ma non mi è garantito che ce ne siano
            while (risorseB.isEmpty())
                wait();//rimango in wait fino a che non ho una risorsa disponibile su b
            returned=risorseB.remove(0);

        }
        notifyAll(); //notifico la rimozione di una risorsa
        return returned;
    }


    public synchronized void releaseResource(Risorsa res){
        if(res.type==0){
            risorseA.add(res); //aggiungo la risorsa alla lista richiesta
        }else {
            risorseB.add(res);
        }
        notifyAll(); //notifico l'aggiunta della risorsa
    }

    public int returnSizeA(){
        return risorseA.size();
    }
    public int returnSizeB(){
        return risorseA.size();
    }


}
