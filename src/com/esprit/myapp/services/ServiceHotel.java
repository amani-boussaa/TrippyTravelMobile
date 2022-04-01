package com.esprit.myapp.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Excursion;
import com.esprit.myapp.entities.Hotel;
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


public class ServiceHotel {
    public ArrayList<Hotel> Hotel;
    public static ServiceHotel instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public static boolean resultOk =true;

    private ServiceHotel() {
        req = new ConnectionRequest();
    }

    public static ServiceHotel getInstance() {
        if (instance == null)
            instance = new ServiceHotel();
        return instance;
    }

    public boolean addHotel(Hotel h)
    {
        String url = Statics.BASE_URL + "addHotel?libelle="+h.getLibelle()+"&localisation="+h.getLocalisation()+"&nbetoile="+h.getNbetoile()+"&nbchdispo="+h.getNbchdispo()+"&description_hotel="+h.getDescription_hotel();
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
    public ArrayList<Hotel> parseHotel(String jsonText) {
        try {
            Hotel = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> HotelListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) HotelListJson.get("root");
            for (Map<String, Object> obj : list) {
                Hotel t = new Hotel();
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
//                if (obj.get("nbetoile") == null)
//                    t.setNbetoile(0);
//                else
//                    t.setNbetoile(Integer.parseInt(obj.get("nbetoile").toString()));
//                if (obj.get("nbchdispo") == null)
//                    t.setNbchdispo(0);
//                else
//                    t.setNbchdispo(Integer.parseInt(obj.get("nbchdispo").toString()));
                if (obj.get("descriptionHotel") == null)
                    t.setDescription_hotel("null");
                else
                    t.setDescription_hotel(obj.get("descriptionHotel").toString());

                Hotel.add(t);
            }
        } catch (IOException ex) {

        }
        return Hotel;
    }
    public ArrayList<Hotel> getAllHotel() {
        String url = Statics.BASE_URL+"displayHotel" ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Hotel = parseHotel(new String(req.getResponseData()));
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
        return Hotel;
    }
    /***amani***/
//    public ArrayList<Hotel> parseHotel()
//    {
//        ArrayList<Hotel> result = new ArrayList<>();
//        String url = Statics.BASE_URL+"/displayHotel";
//        req.setUrl (url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                JSONParser jsonp;
//                jsonp = new JSONParser();
//                try {
//                    Map<String,Object>mapHotel = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//
//                    List<Map<String,Object>> listOfMaps = (List<Map<String, Object>>) mapHotel.get("root");
//
//                    for (Map<String,Object> obj : listOfMaps)
//                    {
//                        Hotel a = new Hotel();
//                        float id = Float.parseFloat(obj.get("id").toString());
//                        String libelle = obj.get("libelle").toString();
//                        String localisation = obj.get("localisation").toString();
//                        float nbetoile = Float.parseFloat(obj.get("nbetoile").toString());
//                        float nbchdispo = Float.parseFloat(obj.get("nbchdispo").toString());
//                        String description_hotel = obj.get("description_hotel").toString();
//
//
//                        a.setId((int)id);
//                        a.setLibelle(libelle);
//                        a.setLocalisation(localisation);
//                        a.setId((int)nbetoile);
//                        a.setId((int)nbchdispo);
//                        a.setDescription_hotel(description_hotel);
//
//                        result.add(a);
//                    }
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return result;
//    }

    public Hotel DetailHotel(int id,Hotel hotel)
    {
        String url = Statics.BASE_URL+"/detailHotel"+id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) ->

        {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                hotel.setLibelle(obj.get("obj").toString());
                hotel.setLocalisation(obj.get("obj").toString());
                hotel.setNbetoile(Integer.parseInt(obj.get("obj").toString()));
                hotel.setNbchdispo(Integer.parseInt(obj.get("obj").toString()));
                hotel.setDescription_hotel(obj.get("obj").toString());


            }catch (IOException ex){
                System.out.println("erreur retaled to sql:("+ex.getMessage());
            }
            System.out.println("data ==="+str);
        }

                ));
        NetworkManager.getInstance().addToQueueAndWait(req);
        return hotel;
    }







    ///////////////////////////////////////////////////////////////////////////:

    public boolean deleteHotel(int id) {
        String url=Statics.BASE_URL+"deleteHotel?id="+id;
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

    public boolean modifierHotel(Hotel Hotel){

        String url=Statics.BASE_URL+"updateHotel?id="+Hotel.getId()+"&libelle="+Hotel.getLibelle()+"&localisation="+Hotel.getLocalisation()+"&nbetoile="+Hotel.getNbetoile()+"&nbchdispo="+Hotel.getNbchdispo()+"&description_hotel="+Hotel.getDescription_hotel();
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

