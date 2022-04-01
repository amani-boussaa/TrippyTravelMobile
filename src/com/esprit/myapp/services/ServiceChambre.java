package com.esprit.myapp.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Excursion;
import com.esprit.myapp.entities.Chambre;
import com.esprit.myapp.utile.Statics;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;


public class ServiceChambre {
    public ArrayList<Chambre> Chambre;
    public static ServiceChambre instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public static boolean resultOk =true;

    private ServiceChambre() {
        req = new ConnectionRequest();
    }

    public static ServiceChambre getInstance() {
        if (instance == null)
            instance = new ServiceChambre();
        return instance;
    }

    public boolean addChambre(Chambre h)
    {
        String url = Statics.BASE_URL + "addChambre?typechambre="+h.gettypechambre()+"&prix="+h.getprix()+"&description_chambre="+h.getdescription_chambre();
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
    /**amani**/
    public ArrayList<Chambre> parseChambre(String jsonText) {
        try {
            Chambre = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ChambreListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) ChambreListJson.get("root");
            for (Map<String, Object> obj : list) {
                Chambre t = new Chambre();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                if (obj.get("typechambre") == null)
                    t.settypechambre("null");
                else
                    t.settypechambre(obj.get("typechambre").toString());

                if (obj.get("description_chambre") == null)
                    t.setdescription_chambre("null");
                else
                    t.setdescription_chambre(obj.get("description_chambre").toString());

                Chambre.add(t);
            }
        } catch (IOException ex) {

        }
        return Chambre;
    }
    public ArrayList<Chambre> getAllChambre() {
        String url = Statics.BASE_URL+"displayChambre" ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Chambre = parseChambre(new String(req.getResponseData()));
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
        return Chambre;
    }
    /***amani***/


    public Chambre DetailChambre(int id,Chambre Chambre)
    {
        String url = Statics.BASE_URL+"/detailChambre"+id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) ->

        {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                Chambre.settypechambre(obj.get("obj").toString());
                Chambre.setprix(Integer.parseInt(obj.get("obj").toString()));
                Chambre.setdescription_chambre(obj.get("obj").toString());



            }catch (IOException ex){
                System.out.println("erreur retaled to sql:("+ex.getMessage());
            }
            System.out.println("data ==="+str);
        }

        ));
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Chambre;
    }







    ///////////////////////////////////////////////////////////////////////////:

    public boolean deleteChambre(int id) {
        String url=Statics.BASE_URL+"deleteChambre?id="+id;
        req.setUrl(url);


        req.addResponseListener(new ActionListener<NetworkEvent>(){

            @Override
            public void actionPerformed(NetworkEvent evt ){

                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;

    }


    /////////////////////////////////////////////////////////
    //update

    public boolean modifierChambre(Chambre Chambre){

        String url=Statics.BASE_URL+"updateChambre?id="+Chambre.getId()+"&typechambre="+Chambre.gettypechambre()+"&prix="+Chambre.getprix()+"&description_chambre="+Chambre.getdescription_chambre();
        req.setUrl(url);


        req.addResponseListener(new ActionListener<NetworkEvent>(){

            @Override
            public void actionPerformed(NetworkEvent evt ){

                resultOk = req.getResponseCode()== 200; // response http
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req); // execution request
        return resultOk;

    }
}

