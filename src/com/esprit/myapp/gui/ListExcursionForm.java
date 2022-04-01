package com.esprit.myapp.gui;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.animations.ComponentAnimation;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Excursion;
import com.esprit.myapp.services.ServiceExcursion;

import java.util.ArrayList;

public class ListExcursionForm extends BaseForm {
    Form current;
    public ListExcursionForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste excursions");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});

        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, spacer1, res.getImage("bg_3.jpg"), "", "", res);

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
        RadioButton mesListes = RadioButton.createToggle("Excursions", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Statistique", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter excursion", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        //list menu
        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
              ListExcursionForm a = new ListExcursionForm(res);
              a.show();
            refreshTheme();
        });
        //ajouter menu
        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();
            iDialog.dispose(); //na7iw loader baad mamalna ajout
            new AjouterExcursionForm(res).show();
            refreshTheme(); //actualisation
        });

        //statitistique menu
        liste.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();
            iDialog.dispose(); //na7iw loader baad mamalna ajout
            new StatistiquePieExcursionForm(res).show();
            refreshTheme(); //actualisation
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        //

        /**list excursion**/
        ArrayList<Excursion>list = ServiceExcursion.getInstance().getAllExcursion();
        for (Excursion excursion : list){

            /**image**/
            Form hi = new Form("Toolbar", new BoxLayout(BoxLayout.Y_AXIS));
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(hi.getWidth(), hi.getWidth() / 5, 0xffff0000), true);
            URLImage background = URLImage.createToStorage(placeholder, "400px-AGameOfThrones.jpg",
                    excursion.getImage());
            background.fetch();
            Image i = URLImage.createToStorage(placeholder, excursion.getImage(), excursion.getImage(), URLImage.RESIZE_SCALE);
            addButton(i, excursion,res);
//            addButton(res.getImage("news-item-1.jpg"), excursion,res);
        }
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

    private void addButton(Image img,Excursion excursion, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        Label libelletxt = new Label("Libéllé: "+excursion.getLibelle(),"NewsTopLine2");
        Label descriptiontxt = new Label("Description: "+excursion.getDescription(),"NewsTopLine2");

        createLineSeparator();

        // supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprimerStyle = new Style(lSupprimer.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE,supprimerStyle);
        lSupprimer.setIcon(supprimerImage);
        lSupprimer.setTextPosition(RIGHT);

        //click delete button
        lSupprimer.addPointerPressedListener(l->{
            Dialog dig = new Dialog("Suppression");
            if (dig.show("Suppression","Vous voulez supprimer cette excursion?","Anuuler","Ok")){
                dig.dispose();
            }else{
                dig.dispose();
                //n3aytou l supprimerExcursion
                if (ServiceExcursion.getInstance().deleteExcursion(excursion.getId())){
                    new ListExcursionForm(res).show();
                }
            }
        });

        // update button
        Label lUpdate = new Label(" ");
        lUpdate.setUIID("NewsTopLine");
        Style updateStyle = new Style(lUpdate.getUnselectedStyle());
        updateStyle.setFgColor(0xf7ad02);
        FontImage updateImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT,updateStyle);
        lUpdate.setIcon(updateImage);
        lUpdate.setTextPosition(LEFT);

        //click update button
        lUpdate.addPointerPressedListener(l->{
            new ModifierExcursionForm(res,excursion).show();
        });

        //detail button
        Label lDetail = new Label(" ");
        lDetail.setUIID("NewsTopLine");
        Style detailStyle = new Style(lDetail.getUnselectedStyle());
        updateStyle.setFgColor(0xf7ad02);
        FontImage detailImage = FontImage.createMaterial(FontImage.MATERIAL_INFO,updateStyle);
        lDetail.setIcon(detailImage);
        lDetail.setTextPosition(LEFT);

        //click detail button
        lDetail.addPointerPressedListener(l->{
            new ExcursionDetailForm(res,excursion).show();
        });

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        BoxLayout.encloseX(libelletxt),
                        BoxLayout.encloseX(descriptiontxt,lSupprimer,lUpdate,lDetail)
                ));

        add(cnt);
    }
}
