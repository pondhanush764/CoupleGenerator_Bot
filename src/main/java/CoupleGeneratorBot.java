import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Pattern;

public class CoupleGeneratorBot extends TelegramLongPollingBot {

    static boolean reply_to_New = false;
    GeneratorClass genClass = new GeneratorClass();

    @Override
    public String getBotUsername() {
        return "sono io";
    }

    @Override
    public String getBotToken() {
        return "1752119241:AAEz0W5ZKaOs2oAMg4-lRY_9Yvtthh886o0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String response;
        int totNomi=0;


        if (update.hasMessage() && update.getMessage().hasText()) {
            if(update.getMessage().isCommand()){
                switch(update.getMessage().getText()){
                    case "/newprocess":
                        reply_to_New=false;
                        InviaMessaggio("Inserisci i nomi separati da una virgola oppure un nome alla volta.\nPuoi abortire l'inserimento oppure concluderlo con gli appositi comandi.",update);
                        reply_to_New=true;
                        break;
                    case "/abort":
                        if(!genClass.getElencoNomi().isEmpty()){
                            genClass.destroyElencoNomi();
                            InviaMessaggio("Processo abortito!",update);
                        }
                        else{
                            InviaMessaggio("Nessun processo da abortire o non sono state inserite parole prima di inviare /abort!\nIniziane uno nuovo con /newprocess !",update);
                        }
                        reply_to_New=false;
                        break;
                    case "/done":
                        if(genClass.getElencoNomi().isEmpty()){
                            InviaMessaggio("Inserisci dei nomi prima di inviare /done...\nRiavvia con /newprocess !",update);
                        }
                        else{
                            response=genClass.GeneraCoppie();
                            genClass.destroyElencoNomi();
                            InviaMessaggio(response,update);
                        }
                        reply_to_New=false;
                        break;
                    default:
                        if(update.getMessage().getText().equals("/start")){
                            InviaMessaggio("Genera delle coppie di nomi usando questo bot!\n" +
                                    "Puoi elencare i nomi volta per volta oppure in più messaggi tramite il comando /newprocess." +
                                    "Qualora avessi finito di caricare i nomi fai /done;" +
                                    " se invece pensi di aver sbagliato dei nomi invia /abort in modo tale da bloccare tutta l'operazione.",update);
                        }else {
                            InviaMessaggio("", update);
                        }
                        break;
                }
            }
            else{
                if(reply_to_New){
                    String[] nomi = InputChecker(update.getMessage().getText());
                    for(String nome:nomi){
                        totNomi+=1;
                    }
                    if (totNomi <= 0) {
                        InviaMessaggio("Inserisci dei nomi... Riavvia con /newprocess !", update);
                        reply_to_New=false;
                    } else {
                        genClass.setElencoNomi(nomi);
                        InviaMessaggio("Puoi inserire altri nomi oppure abortire o concludere l'inserimento con gli appositi comandi", update);
                    }
                }
                else{
                    InviaMessaggio("",update);
                }
            }
        }
    }



    private void InviaMessaggio(String response,Update update) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(update.getMessage().getChatId().toString());
        if (response.isEmpty()) {
            message.setText("Inserire un comando valido");
        } else {
            message.setText(response);
        }
        try{
            execute(message);
        }catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private String[] InputChecker(String text) {
        Pattern pattern = Pattern.compile("\\w+(,\\w+)*");//pattern per le parole multiple
        Pattern pattern2 = Pattern.compile("\\w");//pattern per le parole singole

        if(pattern.matcher(text).find()){  //serve a capire se la stringa in input è valida con il pattern assegnato: se è valida find() -> vero  |  altrimenti find() -> falso
            String[] nomi = text.split(",");//separa la stringa inserita in update
            return nomi;
        }
        else if(pattern2.matcher(text).find()){
            String[] nome = new String[1];
            nome[0]= text;
            return nome;
        }
        else{
            return null;
        }
    }

}