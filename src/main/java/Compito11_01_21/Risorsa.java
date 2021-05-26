package Compito11_01_21;

public class Risorsa {
    public int type;

    public Risorsa(int type) {
        this.type = type;
    }
    public String getResourceType(){
        String result="";
        switch (type){
            case 1:
                result="B";
                break;
            case 0:
                result="A";
                break;
        }
        return  result;
    }
}
