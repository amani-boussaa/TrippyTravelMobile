package com.esprit.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.entities.Article;
import com.esprit.myapp.utile.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceArticle {
    public ArrayList<Article> articles;
    public static ServiceArticle instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceArticle() {
        req = new ConnectionRequest();
    }

    public static ServiceArticle getInstance() {
        if (instance == null)
            instance = new ServiceArticle();
        return instance;
    }
    public void ajoutArticle(Article article) {
        String url = Statics.BASE_URL + "addArticleapi?title=" + article.getTitle() + "&content=" + article.getContent() + "&id_category=" + article.getCategory() ;
        req.setUrl(url);
        req.addResponseListener((e)->{
            String str = new String(req.getResponseData());
            System.out.println("data="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }


    public ArrayList<Article> parseArticles(String jsonText) {
        try {
            articles = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> articleListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) articleListJson.get("root");
            for (Map<String, Object> obj : list) {
                Article t = new Article();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                if (obj.get("title") == null)
                    t.setTitle("null");
                else
                    t.setTitle(obj.get("title").toString());
                if (obj.get("content") == null)
                    t.setContent("null");
                else
                    t.setContent(obj.get("content").toString());
                if (obj.get("image") == null)
                    t.setImage(null);
                else
                    t.setImage(obj.get("image").toString());

                articles.add(t);
            }
        } catch (IOException ex) {

        }
        return articles;
    }

    public ArrayList<Article> getAllArticle() {
        String url = Statics.BASE_URL+"api/article/" ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                articles = parseArticles(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return articles;
    }
    // détail article
    public Article DetailArticle(int id, Article article){
        String url = Statics.BASE_URL ;
        String str = new String(req.getResponseData());
        req.setUrl(url);
        req.addResponseListener((evt -> {
            JSONParser jsonp = new JSONParser();
            try{
                Map<String,Object>obj=jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                article.setTitle(obj.get("title").toString());
                article.setContent(obj.get("content").toString());
                article.setId(Integer.parseInt(obj.get("id").toString()));
            }catch (IOException ex){
                System.out.println("error related to sql"+ex.getMessage());
            }

            System.out.println("data=="+str);
        }));
        NetworkManager.getInstance().addToQueueAndWait(req);
        return article;
    }

    /*****************************************************Delete**************************************************************/
    public boolean deleteArticle(int id) {
        String url = Statics.BASE_URL + "deleteArticleapi?id=" + id;
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
    public boolean updateArticle(Article article) {
        String url = Statics.BASE_URL + "updateArticleapi?id=" + article.getId() + "&title=" + article.getTitle() + "&content=" + article.getContent();
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
