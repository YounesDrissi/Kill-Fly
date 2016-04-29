package com.abdellah.pcsalon.myapplication;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdellah.pcsalon.myapplication.bluetooth.BluetoothConnection;
import com.abdellah.pcsalon.myapplication.dynamicGraph.DynamicGraphActivity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by PC SALON on 28/04/2016.
 */
public class UpdateAffichage extends Thread  {

    Fragments fragments;

    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;

    private int i = 0;
    DataInputStream dataInputStream;
    OutputStream outputStream;
    private TextView directionView;
    ImageView v;
    TextView densiteView;
    private boolean premierAppel=true;
    private DynamicGraphActivity graphe;
    private BluetoothSocket socket;
    private Handler handler;

    public UpdateAffichage(Fragments fragments,Handler handler) {
        this.fragments = fragments;
        this.handler=handler;
        this.socket=this.fragments.getSocket();
        try {
            this.dataInputStream= new DataInputStream(this.socket.getInputStream());
            this.outputStream=this.socket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.v=Fragments.v;
        this.directionView=this.fragments.getDirectionView();
        this.densiteView=this.fragments.getDensiteView();
        this.graphe=this.fragments.getGraphe();

    }


    @Override
    public void run() {


        Bundle data = null;
        while (true) {
            try {
                data=envoieCommande();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ///////////////////////////////
            i++;
            float distance = 0;
            try {
                //update(int direction, float distance, int intensiten,Double tr,Double ws)
                if(i%108 <12){
                    distance=0;
                }else if (i%108 <12){
                    distance=TwoFragment.getUn();
                }else if (i%108 <24){
                    distance=TwoFragment.getDeux();
                }else if (i%108 <36){
                    distance=TwoFragment.getTrois();
                }else if (i%108 <48){
                    distance=TwoFragment.getQuatre();
                }else if (i%108 <60){
                    distance=TwoFragment.getCinq();
                }else if (i%108 <72){
                    distance=TwoFragment.getSix();
                }else if (i%108 <84){
                    distance=TwoFragment.getSept();
                }else if (i%108 <96){
                    distance=TwoFragment.getHuite();
                }else if (i%108 <108){
                    distance=TwoFragment.getOther();
                }

                //update(int direction, float distance,Double tr,Double ws)
                update((i%12), distance,(Double)data.get("tr"),(Double)data.get("ws"));
            }catch (Exception e){
                System.out.println("kestrel deconnecté");
            }

           /* try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }
    public Bundle envoieCommande() throws IOException {
        Bundle data;
        try {
            outputStream.write("S\r".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        while (true) {
        try {
            String[] results = new String[BluetoothConnection.STATE_CONNECTED];
            if(premierAppel) {
                System.out.println("ligne 1 =" + this.dataInputStream.readLine());
                premierAppel=false;
            }
            this.dataInputStream.readLine();
            results[BluetoothConnection.STATE_NONE] = this.dataInputStream.readLine();
            results[BluetoothConnection.STATE_LISTEN] = this.dataInputStream.readLine();
            results[BluetoothConnection.STATE_CONNECTING] = this.dataInputStream.readLine();
            data = this.parseData(results);

            //System.out.println(data.get("tr"));
            //System.out.println(data.get("ws"));

            //System.out.println(data);
        } catch (IOException e) {
            //Log.e(BluetoothConnection.TAG, "disconnected", e);
            System.out.println("disconnected");
            this.socket.close();
            return null;
        }
        return data;
        //  }
    }
    public void update(int direction, float distance,Double tr,Double ws) {
      //  System.out.println("update");
        float x = 0,y = 0;
        //hight,y=946;with,x=1248k

        try {

           // Chronometre.getCercle().repaint(Color.BLACK);
            switch (direction) {
                case 0:
                    x=(TwoFragment.getxZero());
                    y=(TwoFragment.getyZero() - distance);
                    break;
                case 1:
                    x=((float) (TwoFragment.getxZero() + ((distance / 2.15))));
                    y=((float) (TwoFragment.getyZero() - ((distance / 1.11))));
                    break;
                case 2:
                    x=((float) (TwoFragment.getxZero() + ((distance / 1.11))));
                    y=((float) (TwoFragment.getyZero() - ((distance / 2.15))));
                    break;
                case 3:
                    x=(TwoFragment.getxZero() + distance);
                    y=(TwoFragment.getyZero());
                    break;
                case 4:
                    x=((float) (TwoFragment.getxZero() + ((distance / 1.11))));
                    y=((float) (TwoFragment.getyZero() + ((distance / 2.15))));
                    break;
                case 5:
                    x=((float) (TwoFragment.getxZero() + ((distance / 2.15))));
                    y=((float) (TwoFragment.getyZero() + ((distance / 1.11))));
                    break;
                case 6:
                    x=(TwoFragment.getxZero());
                    y=(TwoFragment.getyZero() + distance);
                    break;
                case 7:
                    x=((float) (TwoFragment.getxZero() - ((distance / 2.15))));
                    y=((float) (TwoFragment.getyZero() + ((distance / 1.11))));
                    break;
                case 8:
                    x=((float) (TwoFragment.getxZero() - ((distance / 1.11))));
                    y=((float) (TwoFragment.getyZero() + ((distance / 2.15))));
                    break;
                case 9:
                    x=(TwoFragment.getxZero() - distance);
                    y=(TwoFragment.getyZero());
                    break;
                case 10:
                    x=((float) (TwoFragment.getxZero() - ((distance / 1.11))));
                    y=((float) (TwoFragment.getyZero() - ((distance / 2.15))));
                    break;
                case 11:
                    x=((float) (TwoFragment.getxZero() - ((distance / 2.15))));
                    y=((float) (TwoFragment.getyZero() - ((distance / 1.11))));
                    break;


            }

            Bundle messageBundle=new Bundle();
            /**
             * Le message échangé entre la Thread et le Handler
             */


            Message myMessage;
            myMessage=this.handler.obtainMessage();
            //Ajouter des données à transmettre au Handler via le Bundle
            //transmettre la couleur
            if(distance<TwoFragment.getDeux()){
                messageBundle.putInt("couleur", Color.GREEN);
            }else if (distance<TwoFragment.getCinq()) {
                messageBundle.putInt("couleur",Color.rgb(237 , 127 , 16));
            }else {
                messageBundle.putInt("couleur", Color.RED);
            }

            //pour ws et tr dans la view de la cible
            messageBundle.putDouble("ws", ws);
            messageBundle.putDouble("tr", tr);
            //pour la cible

            messageBundle.putFloat("x", x);
            messageBundle.putFloat("y", y);
            //le temps pour le graphe
            messageBundle.putInt("temp",i);
            //Ajouter le Bundle au message
            myMessage.setData(messageBundle);
            //Envoyer le message
            this.handler.sendMessage(myMessage);




        } catch (Exception e) {
            //  Log.d(TAG, "update no...");
            //update();
        }
    }

    /**
     * recuperer les donnees
     * @param dataStrings
     * @return
     */
    private Bundle parseData(String[] dataStrings) {
        Bundle data = new Bundle();
        dataStrings[STATE_NONE] = dataStrings[STATE_NONE].replace("> ", "");
        String[] splitHeaders = dataStrings[STATE_NONE].split(",");
        String[] splitUnits = dataStrings[STATE_LISTEN].split(",");
        String[] splitData = dataStrings[STATE_CONNECTING].split(",");

        for (int i = STATE_NONE; i < splitHeaders.length; i += STATE_LISTEN) {
            String h = splitHeaders[i];
            if (h.equals("WS") || h.equals("AV")) {
                //  data.putString("windunit", splitUnits[i]);
                data.putDouble("ws", Double.parseDouble(splitData[i]));
            }
            if (h.equals("TR")) {
                // data.putString("directionWindUnit", splitUnits[i]);
                data.putDouble("tr", Double.parseDouble(splitData[i]));
            }
        }
        return data;
    }


}
