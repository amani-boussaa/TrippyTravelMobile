package com.esprit.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Maisonshotes;
import com.esprit.myapp.utile.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicesMaisonshotes {
    public ArrayList<Maisonshotes> Maisonshotes;
    public static ServicesMaisonshotes instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServicesMaisonshotes() {
        req = new ConnectionRequest();
    }

    public static ServicesMaisonshotes getInstance() {
        if (instance == null)
            instance = new ServicesMaisonshotes();
        return instance;
    }

    public boolean addMaisonshotes(Maisonshotes m) {
         String url = Statics.BASE_URL + "api/Maisonshotes/add?libelle="+m.getLibelle()+"&capacite="+m.getCapacite()+"&localisation="+m.getLocalisation()+"&proprietaire="+m.getProprietaire()+"&prix="+m.getPrix()+"&nbrChambres="+m.getNbrChambres()+"&type_maison="+m.getTypeMaison();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Maisonshotes> parseMaisonshotes(String jsonText) {
        try {
            Maisonshotes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> MaisonshotesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) MaisonshotesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Maisonshotes M = new Maisonshotes();
                float id = Float.parseFloat(obj.get("id").toString());
                M.setId((int) id);
                if (obj.get("libelle") == null)
                    M.setLibelle("null");
                else
                    M.setLibelle(obj.get("libelle").toString());
               
                Maisonshotes.add(M);
            }
        } catch (IOException ex) {

        }
        return Maisonshotes;
    }


    public ArrayList<Maisonshotes> getAllMaisonshotes() {
        String url = Statics.BASE_URL+"api/Maisonshotes" ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Maisonshotes = parseMaisonshotes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Maisonshotes;
    }
     // détail maison
    public Maisonshotes DetailMaison(int id, Maisonshotes Maisonshotes){
        String url = Statics.BASE_URL+"detailMaisonapi" ;
        String str = new String(req.getResponseData());
        req.setUrl(url);
        req.addResponseListener((evt -> {
            JSONParser jsonp = new JSONParser();
            try{
                Map<String,Object>obj=jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                Maisonshotes.setLibelle(obj.get("libelle").toString());
                Maisonshotes.setLocalisation(obj.get("localisation").toString());
                Maisonshotes.setProprietaire(obj.get("proprietaire").toString());
                Maisonshotes.setId(Integer.parseInt(obj.get("id").toString()));
              
            }catch (IOException ex){
                System.out.println("error related to sql"+ex.getMessage());
            }

            System.out.println("data=="+str);
        }));
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Maisonshotes;
    }
    
    /*****************************************************Delete**************************************************************/
    public boolean deleteMaisonshotes(int id) {
        String url = Statics.BASE_URL + "deleteMaisonshotesapi?id=" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    /*****************************************************Update**************************************************************/
    public boolean ModifierMaisonshotes(Maisonshotes Maisonshotes) {
        String url = Statics.BASE_URL + "updateMaisonapi?id=" + Maisonshotes.getId() + "&libelle=" + Maisonshotes.getLibelle() + "&capacite="+Maisonshotes.getCapacite()+ "&localisation="+Maisonshotes.getLocalisation()+ "&proprietaire="+Maisonshotes.getProprietaire()+ "&prix="+Maisonshotes.getPrix()+ "&nbrChambres="+Maisonshotes.getNbrChambres()+ "&type_maison="+Maisonshotes.getTypeMaison();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK=req.getResponseCode() == 200; //code response http 200 OK
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
