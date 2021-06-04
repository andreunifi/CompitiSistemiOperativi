package CompitoCorrezione;

public class GestoreClient {
    Client[] array;

    public GestoreClient(Client[] array) {
        this.array = array;
    }

    public Client getClient(int client_id){
        return array[client_id];

    }
    public void stampaThread(){
        String k="";
        for(Client c: array){
            k+="\n[" + c.getName() + "]" + " ha minimo: " + c.min + ", ha massimo: "
                    + c.max + ", e ha media: " + c.media + ", con " + c.numerorichieste + " richieste";
        }
        System.out.println(k);
    }
}
