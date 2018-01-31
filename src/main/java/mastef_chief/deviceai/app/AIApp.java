package mastef_chief.deviceai.app;

import ai.api.AIConfiguration;
import ai.api.AIDataService;

import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

import com.mrcrayfish.device.api.app.*;
import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.component.*;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.TextArea;
import com.mrcrayfish.device.api.app.component.TextField;
import com.mrcrayfish.device.api.utils.BankUtil;
import com.mrcrayfish.device.api.utils.RenderUtil;
import com.mrcrayfish.device.programs.system.layout.StandardLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Random;

public class AIApp extends Application {


    //ai.api Configuration
    AIConfiguration configuration = new AIConfiguration("85596f0543854d7fa2c97004fa7ce633");

    AIDataService dataService = new AIDataService(configuration);

    Random rand = new Random();

    //Layout Variables
    private StandardLayout layoutAIMenu;
    private StandardLayout layoutAI;
    private StandardLayout layoutAIInfo;
    private StandardLayout layoutAIFAQ;
    private StandardLayout layoutAIQuestionsLayout;
    private Layout layoutAIDisplay;

    //Text Field Variable;
    private TextField aiInputTextField;

    //Text Area Variables
    private TextArea faqTextArea;
    private TextArea questionsTextArea;

    //Label Variables
    private Label poweredByLabel;
    private Label authorLabel;

    //Button Variables
    private Button sendButton;
    private Button startAIConversationButton;
    private Button aiInfoButton;
    private Button aiConversationBackButton;
    private Button aiInfoBackButton;
    private Button aiFAQBackButton;
    private Button aiQuestionsBackButton;
    private Button onlineStatusButton;
    private Button offlineStatusButton;
    private Button faqButton;
    private Button questionsButton;
    private Button expandAILayoutButton;
    private Button discordButton;

    //Image Variables
    private Image dialogFlowLogoImage;

    //Text Variables
    private Text aiDescriptionText;
    private Text aiOutputTextArea;

    //Models
    private ModelPlayer menuPlayerModel;
    private ModelBiped infoPlayerModel;
    private ModelBiped aiPlayerModel;
    private ModelBiped dicePlayerModel;



    //Assets
    private static final ResourceLocation speechbubble = new ResourceLocation("deviceai:textures/app/gui/speechbubble.png");
    private static final ResourceLocation playerTexture = new ResourceLocation("deviceai:textures/app/models/mastefchief.png");
    private static final ResourceLocation aiTexture = new ResourceLocation("deviceai:textures/app/models/aitexture.png");
    private static final ResourceLocation diceTexture = new ResourceLocation("deviceai:textures/app/models/dice.png");



    @Override
    public void init() {

        //---AI Menu Layout---
        layoutAIMenu = new StandardLayout("Menu", 200, 65, this, (Layout)null);

        menuPlayerModel = new ModelPlayer(0.0F, true);

        layoutAIMenu.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            GlStateManager.pushMatrix();
            {
                GlStateManager.enableDepth();
                GlStateManager.translate(x + 25, y + 10, 15);
                GlStateManager.scale((float) -2.5, (float) -2.5, (float) -2.5);
                GlStateManager.rotate(-10F, 1, 0, 0);
                GlStateManager.rotate(180F, 0, 0, 1);
                GlStateManager.rotate(-20F, 0, 1, 0);
                float scaleX = (mouseX - x - 25) / (float) width;
                float scaleY = (mouseY - y - 20) / (float) height;
                mc.getTextureManager().bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
                menuPlayerModel.render(Minecraft.getMinecraft().player , 0F, 0F, 0F, -70F * scaleX + 20F, 30F * scaleY, 1.0F);
                GlStateManager.disableDepth();
            }
            GlStateManager.popMatrix();
        });
        layoutAIMenu.setIcon(Icons.HOME);
        this.setCurrentLayout(layoutAIMenu);

        //Create AI Menu Components

        startAIConversationButton = new Button(65, 25, 125, 16,"Start Conversation", Icons.CHAT);
        startAIConversationButton.setToolTip("Start Conversation", "Begin chatting with Aura AI");
        startAIConversationButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                this.setCurrentLayout(layoutAI);
                getDialogFlowStatus();
                aiInputTextField.setFocused(true);
            }
        });

        aiInfoButton = new Button(65, 45, 125, 16, "Aura AI Information", Icons.INFO);
        aiInfoButton.setToolTip("Info", "Information about Aura AI");
        aiInfoButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                this.setCurrentLayout(layoutAIInfo);
            }
        });

        //Add AI Menu Components

        layoutAIMenu.addComponent(aiInfoButton);
        layoutAIMenu.addComponent(startAIConversationButton);

        //---End AI Menu Layout---

        //---AI Conversation Layout---
        layoutAI = new StandardLayout("Conversation", 250, 150, this, (Layout)null);

        aiPlayerModel = new ModelBiped(0.0F);
        dicePlayerModel = new ModelBiped(0.0F);

        layoutAI.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {

            GlStateManager.pushMatrix();
            {
                GlStateManager.enableDepth();
                GlStateManager.translate(x + 25, y + 0, 15);
                GlStateManager.scale((float) -2.5, (float) -2.5, (float) -2.5);
                GlStateManager.rotate(-10F, 1, 0, 0);
                GlStateManager.rotate(180F, 0, 0, 1);
                GlStateManager.rotate(-20F, 0, 1, 0);
                float scaleX = (mouseX - x - 25) / (float) width;
                float scaleY = (mouseY - y - 20) / (float) height;
                mc.getTextureManager().bindTexture(aiTexture);
                aiPlayerModel.render(Minecraft.getMinecraft().player , 0F, 0F, 0F, -70F * scaleX + 20F, 30F * scaleY, 2.0F);
                GlStateManager.translate(x + 25, y + 50, 15);
                mc.getTextureManager().bindTexture(diceTexture);
                dicePlayerModel.render(Minecraft.getMinecraft().player , 0F, 0F, 0F, -70F * scaleX + 20F, 30F * scaleY, 5.0F);
                GlStateManager.disableDepth();
            }
            GlStateManager.popMatrix();

            mc.getTextureManager().bindTexture(speechbubble);
            RenderUtil.drawRectWithTexture(x + 46, y + 35, 0, 0, 200, 90, 146, 52);
        });

        layoutAI.setIcon(Icons.CHAT);

        //Create AI Conversation Components
        layoutAIDisplay = new Layout(0, 0, 250, 150);

        layoutAIDisplay.setVisible(false);


        aiInputTextField = new TextField(5,130, 220);
        aiInputTextField.setPlaceholder("Type here");
        aiInputTextField.setFocused(true);

        aiOutputTextArea = new Text("Welcome " + Minecraft.getMinecraft().player.getName() + "! How may I help you today?" , 70,42, 170);
        aiOutputTextArea.setTextColor(Color.BLACK);
        aiOutputTextArea.setShadow(false);

        onlineStatusButton = new Button(230,2, Icons.ONLINE);
        onlineStatusButton.setToolTip("AI Network Status: " + TextFormatting.GREEN + "Online", "You have access to all AI responses");
        onlineStatusButton.setVisible(false);
        offlineStatusButton = new Button(230, 2, Icons.OFFLINE);
        offlineStatusButton.setToolTip("AI Network Status: " + TextFormatting.RED + "Offline","The AI responses are limited");
        offlineStatusButton.setVisible(false);
        faqButton = new Button(211, 2, Alphabet.QUESTION_MARK);
        faqButton.setToolTip("FAQ", "Frequently Asked Questions");
        faqButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                this.setCurrentLayout(layoutAIFAQ);
            }
        });
        questionsButton = new Button(192, 2, Icons.BOOK_OPEN);
        questionsButton.setToolTip("Questions", "Questions you can ask Aura");
        questionsButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                this.setCurrentLayout(layoutAIQuestionsLayout);
            }
        });

        /*expandAILayoutButton = new Button(20, 100, Icons.EXPAND);
        expandAILayoutButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                //Todo Expand AI Layout
            }
        });*/

        aiConversationBackButton = new Button(100,2, Icons.ARROW_LEFT);
        aiConversationBackButton.setToolTip("Back", "Takes you back to AI menu");
        aiConversationBackButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                aiInputTextField.clear();
                this.setCurrentLayout(layoutAIMenu);
            }
        });
        sendButton = new Button(230, 130, Icons.SEND);
        sendButton.setToolTip("Send", "Send message to Aura");
        sendButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                getDialogFlowStatus();
                layoutAIDisplay.setVisible(false);
                aiRequestAndResponse();
                aiInputTextField.setFocused(true);
            }
        });

        //Add AI Conversation Components
        layoutAI.addComponent(layoutAIDisplay);

        layoutAI.addComponent(aiInputTextField);

        layoutAI.addComponent(aiOutputTextArea);

        layoutAI.addComponent(onlineStatusButton);
        layoutAI.addComponent(offlineStatusButton);
        layoutAI.addComponent(faqButton);
        layoutAI.addComponent(questionsButton);
        layoutAI.addComponent(expandAILayoutButton);
        layoutAI.addComponent(aiConversationBackButton);
        layoutAI.addComponent(sendButton);


        //---End AI Conversation Layout---

        //---AI Info Layout---
        layoutAIInfo = new StandardLayout("Info", 250, 135, this, (Layout)null);

        infoPlayerModel = new ModelBiped(0.0F);

        layoutAIInfo.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {

            GlStateManager.pushMatrix();
            {
                GlStateManager.enableDepth();
                GlStateManager.translate(x + 25, y + 33, 15);
                GlStateManager.scale((float) -2.5, (float) -2.5, (float) -2.5);
                GlStateManager.rotate(-10F, 1, 0, 0);
                GlStateManager.rotate(180F, 0, 0, 1);
                GlStateManager.rotate(-20F, 0, 1, 0);
                float scaleX = (mouseX - x - 25) / (float) width;
                float scaleY = (mouseY - y - 20) / (float) height;
                mc.getTextureManager().bindTexture(playerTexture);
                infoPlayerModel.render(Minecraft.getMinecraft().player , 0F, 0F, 0F, -70F * scaleX + 20F, 30F * scaleY, 1F);
                GlStateManager.disableDepth();
            }
            GlStateManager.popMatrix();

            mc.getTextureManager().bindTexture(speechbubble);
            RenderUtil.drawRectWithTexture(x + 46, y + 45, 0, 0, 200, 50, 146, 52);
        });

        layoutAIInfo.setIcon(Icons.INFO);

        //Create AI Info Components
        authorLabel = new Label("Author: Mastef_Chief",50, 28);
        authorLabel.setTextColor(Color.BLACK);
        authorLabel.setShadow(false);
        authorLabel.setScale(1.5);
        discordButton = new Button(175, 2,  "Discord Info");
        discordButton.setClickListener((mouseX, mouseY, mouseButton) -> {

            if(mouseButton == 0){

                Dialog.Message dialog = new Dialog.Message("Discord: Mastef_Chief#9057");
                dialog.setTitle("Discord Info");
                this.openDialog(dialog);

            }

        });
        aiInfoBackButton = new Button(50,2, Icons.ARROW_LEFT);
        aiInfoBackButton.setToolTip("Back", "Takes you back to AI menu");
        aiInfoBackButton.setClickListener((mouseX, mouseY, mouseButton) -> {

            if(mouseButton == 0){

                aiInputTextField.clear();
                this.setCurrentLayout(layoutAIMenu);

            }

        });

        aiDescriptionText = new Text("Aura AI is an artificial interface for MrCrayfish's Device Mod. It can do a variety of thing.", 73, 54, 175);
        aiDescriptionText.setTextColor(Color.BLACK);
        aiDescriptionText.setShadow(false);

        poweredByLabel = new Label("Powered By",90, 105);
        poweredByLabel.setTextColor(Color.BLACK);
        poweredByLabel.setShadow(false);
        dialogFlowLogoImage = new Image(150, 105,86, 23, "http://mastefchief.com/mods/deviceai/textures/dialogflow.png");

        //Add AI Info Components
        layoutAIInfo.addComponent(aiInfoBackButton);

        layoutAIInfo.addComponent(poweredByLabel);
        layoutAIInfo.addComponent(authorLabel);

        layoutAIInfo.addComponent(discordButton);

        layoutAIInfo.addComponent(aiDescriptionText);

        layoutAIInfo.addComponent(dialogFlowLogoImage);

        //---End AI Info Layout---


        //---AI FAQ Layout---
        layoutAIFAQ = new StandardLayout("Frequently Asked Question", 225, 165, this, (Layout)null);
        layoutAIFAQ.setIcon(Icons.BOOK_CLOSED);

        //Create AI FAQ Components
        faqTextArea = new TextArea(0,21, 225,143);
        faqTextArea.setEditable(false);
        faqTextArea.setWrapText(true);
        faqTextArea.writeText("Coming Soon");

        aiFAQBackButton = new Button(160, 2, Icons.ARROW_LEFT);
        aiFAQBackButton.setToolTip("Back", "Takes you back to AI Conversation");
        aiFAQBackButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                this.setCurrentLayout(layoutAI);
            }
        });

        //Create AI FAQ Components
        layoutAIFAQ.addComponent(faqTextArea);

        layoutAIFAQ.addComponent(aiFAQBackButton);

        //---End AI FAQ Layout---


        //---AI Questions Layout---
        layoutAIQuestionsLayout = new StandardLayout("Questions", 225, 165, this, (Layout)null);
        layoutAIQuestionsLayout.setIcon(Icons.BOOK_OPEN);

        //Create AI Questions Components
        questionsTextArea = new TextArea(0,21, 225,143);
        questionsTextArea.setEditable(false);
        questionsTextArea.setWrapText(true);
        questionsTextArea.writeText("Coming Soon");

        aiQuestionsBackButton = new Button(75, 2, Icons.ARROW_LEFT);
        aiQuestionsBackButton.setToolTip("Back", "Takes you back to AI Conversation");
        aiQuestionsBackButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                this.setCurrentLayout(layoutAI);
            }
        });

        //Create AI Questions Components
        layoutAIQuestionsLayout.addComponent(questionsTextArea);

        layoutAIQuestionsLayout.addComponent(aiQuestionsBackButton);

        //---End AI Questions Layout---

    }

    @Override
    public void handleKeyTyped(char character, int code) {
        super.handleKeyTyped(character, code);

        if(code == Keyboard.KEY_RETURN){

            getDialogFlowStatus();
            layoutAIDisplay.setVisible(false);
            aiRequestAndResponse();
            aiInputTextField.setFocused(true);

        }

    }

    private void getDialogFlowStatus(){

        try {
            AIRequest request = new AIRequest("test");

            AIResponse response = dataService.request(request);


            if (response.getStatus().getCode() == 200) {
                offlineStatusButton.setVisible(false);
                onlineStatusButton.setVisible(true);
            } else {
                onlineStatusButton.setVisible(false);
                offlineStatusButton.setVisible(true);
                aiOutputTextArea.setText(response.getStatus().getErrorDetails());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            onlineStatusButton.setVisible(false);
            offlineStatusButton.setVisible(true);
        }

    }

    private void aiRequestAndResponse(){

        if(aiInputTextField.getText().isEmpty()){
            aiOutputTextArea.setText("");
            aiOutputTextArea.setText("I can't answer what you haven't asked.");
        }else {

            if (aiInputTextField.getText().equalsIgnoreCase("Whats my balance?") || aiInputTextField.getText().equalsIgnoreCase("Whats my balance") || aiInputTextField.getText().equalsIgnoreCase("What's my balance?") || aiInputTextField.getText().equalsIgnoreCase("What's my balance")) {

                aiOutputTextArea.setText("");

                BankUtil.getBalance((nbt, success) -> {
                    if (success) {
                        int balance = nbt.getInteger("balance");

                        aiOutputTextArea.setText("Your balance is currently $" + balance);
                    }
                });



            }else if(aiInputTextField.getText().equalsIgnoreCase("Flip a coin")){

                int num = rand.nextInt(2);

                aiOutputTextArea.setText("");

                if(num == 0){
                    aiOutputTextArea.setText("I flipped the coin and it landed on HEADS.");
                }else if (num == 1){
                    aiOutputTextArea.setText("I flipped the coin and it landed on TAILS.");
                }

            } else if(aiInputTextField.getText().equalsIgnoreCase("Roll a dice")) {

                layoutAIDisplay.setVisible(true);

                int num = rand.nextInt(6);

                aiOutputTextArea.setText("");

                if(num == 5){

                    aiOutputTextArea.setText("I rolled the dice and the number is 6.");
                    diceRoll(0, 270);

                } else if(num == 4){

                    aiOutputTextArea.setText("I rolled the dice and the number is 5.");
                    diceRoll(0, 0);

                } else if(num == 3){

                    aiOutputTextArea.setText("I rolled the dice and the number is 4.");
                    diceRoll(280, 0);

                } else if(num == 2){

                    aiOutputTextArea.setText("I rolled the dice and the number is 3.");
                    diceRoll(100, 0);

                } else if(num == 1){

                    aiOutputTextArea.setText("I rolled the dice and the number is 2.");
                    diceRoll(0, 90);

                } else if(num == 0){

                    aiOutputTextArea.setText("I rolled the dice and the number is 1.");
                    diceRoll(210, 0);

                }

            } else if (aiInputTextField.getText().equalsIgnoreCase("Whats the weather?") || aiInputTextField.getText().equalsIgnoreCase("Whats the weather") || aiInputTextField.getText().equalsIgnoreCase("What's the weather?") || aiInputTextField.getText().equalsIgnoreCase("What's the weather")){


                aiOutputTextArea.setText("");

                if(Minecraft.getMinecraft().player.getEntityWorld().getWorldInfo().isRaining()){

                    aiOutputTextArea.setText("It is currently raining.");

                } else {

                    aiOutputTextArea.setText("It is currently sunny.");
                }
                } else {
                aiOutputTextArea.setText("");
                try {
                    AIRequest request = new AIRequest(aiInputTextField.getText());

                    AIResponse response = dataService.request(request);


                    if (response.getStatus().getCode() == 200) {
                        aiOutputTextArea.setText(response.getResult().getFulfillment().getSpeech());
                    } else {
                        aiOutputTextArea.setText(response.getStatus().getErrorDetails());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    aiOutputTextArea.setText("Sorry, but I am unable to connect to the AI Network. My responses are limited.");
                }
            }

        }
        aiInputTextField.clear();

    }

    public void diceRoll(float yaw, float pitch){



        layoutAIDisplay.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {

            GlStateManager.pushMatrix();
            {
                GlStateManager.enableDepth();
                GlStateManager.translate(x + 25, y + 75, 15);
                GlStateManager.scale((float) -2.5, (float) -2.5, (float) -2.5);
                GlStateManager.rotate(-10F, 1, 0, 0);
                GlStateManager.rotate(180F, 0, 0, 1);
                GlStateManager.rotate(-20F, 0, 1, 0);
                float scaleX = (mouseX - x - 25) / (float) width;
                float scaleY = (mouseY - y - 20) / (float) height;
                mc.getTextureManager().bindTexture(diceTexture);
                infoPlayerModel.render(Minecraft.getMinecraft().player , 0F, 0F, 0F, yaw, pitch, 1F);
                GlStateManager.disableDepth();
            }
            GlStateManager.popMatrix();


        });

    }




    @Override
    public void load(NBTTagCompound nbtTagCompound) {



    }

    @Override
    public void save(NBTTagCompound nbtTagCompound) {

    }
}
