package com.esprit.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Excursion;
import com.esprit.myapp.utile.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceExcursion {
    public ArrayList<Excursion> excursions;
    public static ServiceExcursion instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceExcursion() {
        req = new ConnectionRequest();
    }

    public static ServiceExcursion getInstance() {
        if (instance == null)
            instance = new ServiceExcursion();
        return instance;
    }

    public boolean addExcursion(Excursion e) {
        String url = Statics.BASE_URL + "new/" + e.getLibelle() + "/" + e.getDescription() + "/" + e.getDuration() + "/" + e.getProgramme() + "/" + e.getPrix() + "/" + e.getVille();
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

    public ArrayList<Excursion> parseExcursions(String jsonText) {
        try {
            excursions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> excursionListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) excursionListJson.get("root");
            for (Map<String, Object> obj : list) {
                Excursion t = new Excursion();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                if (obj.get("libelle") == null)
                    t.setLibelle("null");
                else
                    t.setLibelle(obj.get("libelle").toString());
                if (obj.get("duration") == null)
                    t.setDuration("null");
                else
                    t.setDuration(obj.get("duration").toString());
                if (obj.get("description") == null)
                    t.setDescription("null");
                else
                    t.setDescription(obj.get("description").toString());
                if (obj.get("programme") == null)
                    t.setProgramme("null");
                else
                    t.setProgramme(obj.get("programme").toString());
                if (obj.get("ville") == null)
                    t.setVille("null");
                else
                    t.setVille(obj.get("prix").toString());
                if (obj.get("prix") == null)
                    t.setPrix(null);
                else
                    t.setPrix(((float) Float.parseFloat(obj.get("prix").toString())));
                if (obj.get("excursioncategorie") == null)
                    t.setExcursioncategorie(null);
                else
                    t.setExcursioncategorie(obj.get("excursioncategorie").toString());
                excursions.add(t);
            }
        } catch (IOException ex) {

        }
        return excursions;
    }

    public ArrayList<Excursion> getAllExcursion() {
        String url = Statics.BASE_URL ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                excursions = parseExcursions(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return excursions;
    }
}
