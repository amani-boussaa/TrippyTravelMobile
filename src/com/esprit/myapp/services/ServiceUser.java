package com.esprit.myapp.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.gui.BlogForm;
import com.esprit.myapp.gui.SessionManager;
import com.esprit.myapp.utile.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.ui.Dialog;
import java.util.Map;

import java.awt.*;

public class ServiceUser {
    public static ServiceUser instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private Resources res;
    public static ServiceUser getInstance() {
        if (instance == null)
            instance = new ServiceUser();
        return instance;
    }

    public ServiceUser () {
        req = new ConnectionRequest();
    }

    public void signup(com.codename1.ui.TextField firstname, com.codename1.ui.TextField lastname, com.codename1.ui.TextField email, com.codename1.ui.TextField password, TextField confirmPassword, Resources res) {
        String url = Statics.BASE_URL+"user/signup?firstname="+firstname.getText().toString()+"&lastname="+lastname.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString();

        req.setUrl(url);

        if(firstname.getText().equals(" ") && lastname.getText().equals(" ") &&
                email.getText().equals(" ") && password.getText().equals(" ")) {
            com.codename1.ui.Dialog.show("erreur", "remplir les champs","OK",null);
        }

        req.addResponseListener((e)->{
            byte[]data = (byte[]) e.getMetaData();
            String responseData = new String(data);
            System.out.println("data ====>"+responseData);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void signin(TextField email,TextField password, Resources rs ) {


        String url = Statics.BASE_URL+"user/signin?email="+email.getText().toString()+"&password="+password.getText().toString();
        req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener((e) ->{

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";


            try {

                if(json.equals("password incorrect") || json.equals("user not found")) {
                    Dialog.show("Echec d'authentification","email ou mot de passe éronné","OK",null);
                }
                else {
                    System.out.println("data =="+json);

                    Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));



                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int)id);

                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    Dialog.show("Success", "auth succeed", "OK", null);

                    //photo

                    //if(user.get("photo") != null)
                        //SessionManager.setPhoto(user.get("photo").toString());


                    //if(user.size() >0 )
                        // new ListReclamationForm(rs).show();
                        //new AjoutReclamationForm(rs).show();

                }

            }catch(Exception ex) {
                ex.printStackTrace();
            }



        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

}
