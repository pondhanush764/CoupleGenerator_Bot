import java.util.ArrayList;

public class GeneratorClass {

    private ArrayList<String> elencoNomi = new ArrayList<String>();



    public void setElencoNomi (String[] nomi){
        for(String nome:nomi){
            elencoNomi.add(nome);
        }
    }

    public void destroyElencoNomi(){
        this.elencoNomi.clear();
    }

    public ArrayList<String> getElencoNomi(){
        return this.elencoNomi;
    }

    public String GeneraCoppie() {

        String message=""; //messaggio da inviare all'utente
        String nome1,nome2;
        int indexNome1, indexNome2;
        int originalSize = this.elencoNomi.size();


        while(this.elencoNomi.size()>1){//ripeto il ciclo finchè non ottengo un numero di coppie diverse sufficiente

            do{
                indexNome1=(int)(Math.random()*this.elencoNomi.size());
                indexNome2=(int)(Math.random()*this.elencoNomi.size());
            }while(indexNome1 == indexNome2);

            nome1=this.elencoNomi.get(indexNome1);//assegno al nome1 la stringa contenuta nella posizione "aleatoria" indexNome1
            nome2=this.elencoNomi.get(indexNome2);//assegno al nome2 la stringa contenuta nella posizione "aleatoria"
            if(indexNome1>indexNome2) {
                this.elencoNomi.remove(indexNome1);//rimuovo nell'elenco dei nomi, il nome salvato in nome1
                this.elencoNomi.remove(indexNome2);//rimuovo nell'elenco dei nomi, il nome salvato in nome2
            }
            else{
                this.elencoNomi.remove(indexNome2);//rimuovo nell'elenco dei nomi, il nome salvato in nome1
                this.elencoNomi.remove(indexNome1);//rimuovo nell'elenco dei nomi, il nome salvato in nome2
            }
            message+="*" + nome1 + "*" + " - " + "*" + nome2 + "*" + "\n";
        }

        if(originalSize==1){
            message+="*" + elencoNomi.get(0) + "* è l'unico nome inserito...";
        }else if(this.elencoNomi.size()==1) {
            message+="*" + elencoNomi.get(0) + "* è stato escluso dalle combinazioni.";
        }

        return message;
    }
}
