package compito11_6_19;

public class Request {
    private int sentvalue; //valore inviato dal requester
    private int finalized_value; //valore dopo essere stato eleaborato
    private final Requester requesterthreadreference; //reference al requester per segnalare la fine dell'operazione
    private long Sent_Date; //data di invio dal requester
    private long Received_Date; //data di ricezione dal worker

    public Request(int value, Requester requesterthreadreference) {
        this.sentvalue = value;
        this.requesterthreadreference = requesterthreadreference;
        this.setSent_Date(System.currentTimeMillis());
    }


    //getter e setter per i vari attributi

    public int getSentvalue() {
        return sentvalue;
    }

    public void setSentvalue(int sentvalue) {
        this.sentvalue = sentvalue;
    }

    public long getSent_Date() {
        return Sent_Date;
    }

    public long getReceived_Date() {
        return Received_Date;
    }

    public void setReceived_Date(long received_Date) {
        Received_Date = received_Date;
    }

    public void setSent_Date(long sent_Date) {
        Sent_Date = sent_Date;
    }

    public int getFinalized_value() {
        return finalized_value;
    }

    public void setFinalized_value(int finalized_value) {
        this.finalized_value = finalized_value;
    }

    public Requester getRequesterthreadreference() {
        return requesterthreadreference;
    }
}
