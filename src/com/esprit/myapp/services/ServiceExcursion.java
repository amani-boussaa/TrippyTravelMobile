package com.esprit.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Excursion;
import com.esprit.myapp.entities.ExcursionComments;
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
    public static boolean resultOk = true;

    public void ajoutExcursion(Excursion excursion) {
        String url = Statics.BASE_URL + "addExcursionapi?libelle=" + excursion.getLibelle() + "&description=" + excursion.getDescription()+"&programme="+excursion.getProgramme() + "&ville=" + excursion.getVille() + "&prix=" + excursion.getPrix() + "&duration=" + excursion.getDuration() + "&excursioncategorie_id=" + excursion.getExcursioncategorie_id() ;
        req.setUrl(url);
        req.addResponseListener((e)->{
            String str = new String(req.getResponseData());
            System.out.println("data="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
//    public boolean addExcursion(Excursion e) {
//        String url = Statics.BASE_URL + "new/" + e.getLibelle() + "/" + e.getDescription() + "/" + e.getDuration() + "/" + e.getProgramme() + "/" + e.getPrix() + "/" + e.getVille();
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200;
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }

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
                    t.setPrix(obj.get("prix").toString());
                if (obj.get("excursioncategorie_id") == null)
                    t.setExcursioncategorie_id(null);
                else
                    t.setExcursioncategorie_id(obj.get("image").toString());
                if (obj.get("image") == null)
                    t.setImage(null);
                else
                    t.setImage(obj.get("image").toString());

                excursions.add(t);
            }
        } catch (IOException ex) {

        }
        return excursions;
    }

    public ArrayList<Excursion> getAllExcursion() {
        String url = Statics.BASE_URL+"allexcursionapi" ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                excursions = parseExcursions(new String(req.getResponseData()));
                req.removeResponseListener(this);
                /**new ads**/
                JSONParser jsonp = new JSONParser();
                String str = new String(req.getResponseData());
                try {
                    Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));


                    System.out.println(str);
                }catch (IOException ex){
                    System.out.println("error related to sql"+ex.getMessage());

                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return excursions;
    }

    // détail excursion
    public Excursion DetailExcursion(int id, Excursion excursion){
        String url = Statics.BASE_URL ;
        String str = new String(req.getResponseData());
        req.setUrl(url);
        req.addResponseListener(evt -> {
            JSONParser jsonp = new JSONParser();
            try{
                Map<String,Object>obj=jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                excursion.setLibelle(obj.get("libelle").toString());
                excursion.setProgramme(obj.get("programme").toString());
                excursion.setDescription(obj.get("description").toString());
                excursion.setId(Integer.parseInt(obj.get("id").toString()));
                excursion.setPrix(obj.get("prix").toString());
                excursion.setComments((ExcursionComments[]) obj.get("comments"));
            }catch (IOException ex){
                System.out.println("error related to sql"+ex.getMessage());
            }

            System.out.println("data=="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return excursion;
    }

    /*****************************************************Delete**************************************************************/
    public boolean deleteExcursion(int id) {
        String url = Statics.BASE_URL + "deleteExcursionapi?id=" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    /*****************************************************Update**************************************************************/
    public boolean updateExcursion(Excursion excursion) {
        String url = Statics.BASE_URL + "updateExcursionapi?id=" + excursion.getId() + "&libelle=" + excursion.getLibelle() + "&description="+excursion.getDescription();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk=req.getResponseCode() == 200; //code response http 200 OK
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }


}
