package com.esprit.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Hotel;
import com.esprit.myapp.services.ServiceHotel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Form;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;


public class AddHotelForm extends BaseForm
{
    Form current;
    public AddHotelForm(Resources res)
    {
        super ("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Hotel");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> { });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();
        Label s3 = new Label();
        Label s4 = new Label();
        Label s5 = new Label();

        addTab(swipe,s1,res.getImage("hotel.jpg"),"","",res);

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
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("All", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Ajouter", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Popular", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("My Favorites", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        //list menu
        all.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            ListHotelForm a = new ListHotelForm(res);
            a.show();
            refreshTheme();
        });
        //ajouter menu
        featured.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();
            iDialog.dispose(); //na7iw loader baad mamalna ajout
            new AddHotelForm(res).show();
            refreshTheme(); //actualisation
        });


        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        TextField libelle = new TextField("","entrer libelle");
        libelle.setUIID("TextFieldBlocked");
        addStringValue("libelle",libelle);

        TextField localisation = new TextField("","entrer localisation");
        localisation.setUIID("TextFieldBlocked");
        addStringValue("localisation",localisation);

        TextField nbetoile = new TextField("","entrer nombre etoile");
        nbetoile.setUIID("TextFieldBlocked");
        addStringValue("nombre etoile",nbetoile);

        TextField nbchdispo = new TextField("","entrer nombre chdispo");
        nbchdispo.setUIID("TextFieldBlocked");
        addStringValue("nombre chdispo",nbchdispo);

        TextField description_hotel = new TextField("","entrer description hotel");
        description_hotel.setUIID("TextFieldBlocked");
        addStringValue("description hotel",description_hotel);

        Button btnAjouter = new Button("Ajouter");
        addStringValue("",btnAjouter);

        btnAjouter.addActionListener((e)-> {
            try {
                if(libelle.getText() ==""|| localisation.getText() ==""|| nbetoile.getText() =="" || nbchdispo.getText() =="" || description_hotel.getText() =="")
                {
                    Dialog.show("Veuillez verifier les données","","anuler","ok");
            }
                else
                {
                    InfiniteProgress ip = new InfiniteProgress();;
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Hotel h = new Hotel(String.valueOf(libelle.getText()).toString(),String.valueOf(localisation.getText()).toString(),Integer.valueOf(nbetoile.getText()),Integer.valueOf(nbchdispo.getText()),String.valueOf(description_hotel.getText()).toString());

                    ServiceHotel.getInstance().addHotel(h);
                    iDialog.dispose();

                   new ListHotelForm(res).show();

                    refreshTheme();

                }

    }catch (Exception ex)
    {
        ex.printStackTrace();
    }

    }
        );
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text , Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if(image.getHeight() < size){
            image = image.scaledHeight(size);
        }

        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2){
            image = image.scaledHeight(Display.getInstance().getDisplayHeight()/2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overlay = new Label("","Imageoverlay");


        Container pagel =
                LayeredLayout.encloseIn(
                        imageScale,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text ,"LargeWhiteText"),
                                        spacer
                                )
                        )
                );
        swipe.addTab("",res.getImage("back.png"), pagel);

    }

    public void binButtonSelection (Button btn,Label l)
    {
        btn.addActionListener(e -> {
                if(btn.isSelected()) {
                  updateArrowPosition(btn,l);
                   }
             });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, getX() + btn.getWidth() /2 - l.getWidth() /2);
        l.getParent().repaint();
    }

    private void addStringValue(String b, Component v)
    {
        add(BorderLayout.west(new Label(b,"PaddedLabel")).add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

}