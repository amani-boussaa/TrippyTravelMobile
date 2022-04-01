package com.esprit.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Attraction;
import com.esprit.myapp.utile.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceAttraction {
    public ArrayList<Attraction> attractions;
    public static ServiceAttraction instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceAttraction() {
        req = new ConnectionRequest();
    }

    public static ServiceAttraction getInstance() {
        if (instance == null)
            instance = new ServiceAttraction();
        return instance;
    }
    public void ajoutAttraction(Attraction attraction) {
        String url = Statics.BASE_URL + "addAttraction?libelle=" + attraction.getLibelle() + "&localisation=" + attraction.getLocalisation() + "&horraire=" + attraction.getHorraire()  + "&prix=" + attraction.getPrix()  + "&image=" + attraction.getImage();
        req.setUrl(url);
        req.addResponseListener((e)->{
            String str = new String(req.getResponseData());
            System.out.println("data="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }


    public ArrayList<Attraction> parseAttraction(String jsonText) {
        try {
            attractions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> attractionListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) attractionListJson.get("root");
            for (Map<String, Object> obj : list) {
                Attraction t = new Attraction();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                if (obj.get("libelle") == null)
                    t.setLibelle("null");
                else
                    t.setLibelle(obj.get("libelle").toString());
                
                if (obj.get("localisation") == null)
                    t.setLocalisation("null");
                else
                    t.setLocalisation(obj.get("localisation").toString());
                
                if (obj.get("horraire") == null)
                    t.setHorraire("null");
                else
                    t.setHorraire(obj.get("horraire").toString());                
                                
                if (obj.get("image") == null)
                    t.setImage("null");
                else
                    t.setImage(obj.get("image").toString());

                attractions.add(t);
            }
        } catch (IOException ex) {

        }
        return attractions;
    }

    public ArrayList<Attraction> getAllAttraction() {
        String url = Statics.BASE_URL+"displayAttraction" ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                attractions = parseAttraction(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return attractions;
    }
    // détail article
    public Attraction DetailAttraction(int id, Attraction attraction){
        String url = Statics.BASE_URL+"" ;
        String str = new String(req.getResponseData());
        req.setUrl(url);
        req.addResponseListener((evt -> {
            JSONParser jsonp = new JSONParser();
            try{
                Map<String,Object>obj=jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                attraction.setLibelle(obj.get("libelle").toString());
                attraction.setLocalisation(obj.get("localisation").toString());
                attraction.setHorraire(obj.get("horraire").toString());
                attraction.setPrix(Integer.parseInt(obj.get("prix").toString()));
                attraction.setImage(obj.get("image").toString());

            }catch (IOException ex){
                System.out.println("error related to sql"+ex.getMessage());
            }

            System.out.println("data=="+str);
        }));
        NetworkManager.getInstance().addToQueueAndWait(req);
        return attraction;
    }

    /*****************************************************Delete**************************************************************/
    public boolean deleteAttraction(int id) {
        String url = Statics.BASE_URL + "deleteAttraction?id=" + id;
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
    public boolean updateAttraction(Attraction attraction) {
        String url = Statics.BASE_URL + "updateAttraction?id=" + attraction.getId() + "&libelle=" + attraction.getLibelle() + "&localisation=" + attraction.getLocalisation() + "&horraire=" + attraction.getHorraire()  + "&prix=" + attraction.getPrix()  + "&image=" + attraction.getImage();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //code response http 200 OK
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }


}
