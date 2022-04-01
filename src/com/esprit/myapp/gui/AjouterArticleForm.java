package com.esprit.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Article;
import com.esprit.myapp.services.ServiceArticle;

public class AjouterArticleForm extends BaseForm{
    Form current;
    public AjouterArticleForm(Resources res){
        super("Newsfeed", BoxLayout.y());//heritage de newsfeed et formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Article");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e->{

        });
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe,spacer1,res.getImage("article.jpg"),"","",res);
//
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Liste des articles", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();
            iDialog.dispose(); //na7iw loader baad mamalna ajout
            new ListArticlesForm(res).show();
            refreshTheme(); //actualisation
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });


        //

        TextField title = new TextField("","Entrer un titre");
        title.setUIID("TextFieldBlack");
        addStrngValue("Titre",title);

        TextField content = new TextField("","Entrer un contenu");
        content.setUIID("TextFieldBlack");
        addStrngValue("Contenu",content);

        TextField id_category = new TextField("","entrer l'id de category");
        id_category.setUIID("TextFieldBlack");
        addStrngValue("id_category",id_category);

        Button btnAjouter = new Button("Ajouter");
        addStrngValue("",btnAjouter);

        //onclick button event
        btnAjouter.addActionListener((e)->{
            try {
                if (title.getText()=="" || content.getText()=="" || id_category.getText()==""){
                    Dialog.show("tous les champs sont obligatoires !","","Annuler","OK");
                }else {
                    InfiniteProgress ip = new InfiniteProgress(); //loading after insert data
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Article article = new Article(
                            String.valueOf(title.getText()).toString(),
                            String.valueOf(content.getText()).toString(),
                            String.valueOf(id_category.getText()).toString()
                    );
                    ServiceArticle.getInstance().ajoutArticle(article);

                    iDialog.dispose(); //na7iw loader baad mamalna ajout
                    new ListArticlesForm(res).show();
                    refreshTheme(); //actualisation
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

    private void addStrngValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
                .add(BorderLayout.CENTER,v));
        add(createLineSeparator());
    }
    private void addTab(Tabs swipe, Label spacer,Image img, String string, String text ,Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight());
        if (img.getHeight()>size){
            img = img.scaledHeight(size);
        }

        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1 =
                LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();


    }
}
