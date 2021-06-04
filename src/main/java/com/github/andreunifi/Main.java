package com.github.andreunifi;

import Compito10_6_20.PriorityResourceManaer;
import Compito10_6_20.RequesterThread;
import Compito11_01_21.*;
import Compito16_09_20.*;
import Compito23_06_20.Message;
import Compito23_06_20.MessageQueue;
import Compito23_06_20.OutputQueue;
import Compito23_06_20.ProcessorThread;
import CompitoCorrezione.*;
import CompitoCorrezione.ResourceManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {


    public static void main(String[] args){
        RequestQueue queue= new RequestQueue(10);
        Client[] clients= new Client[5];
        Worker[] workers= new Worker[4];

        for (int i=0;i<5;i++){
            clients[i]= new Client(queue);
            clients[i].setName(String.valueOf(i));
            clients[i].start();
        }
        ResourceManager manager= new ResourceManager(10,3);
        GestoreClient gestore= new GestoreClient(clients);
        for(int i=0;i<4;i++){
            workers[i]= new Worker(queue,gestore,1,2,manager);
            workers[i].start();
        }


        try {
            Thread.sleep(10000);
            for(int i=0;i<5;i++){
                clients[i].interrupt();

            }
            for(int i=0;i<5;i++)
                clients[i].join();

            gestore.stampaThread();

            for(int i=0;i<4;i++){
                workers[i].interrupt();
                workers[i].join();
            }
            System.out.println("Risorse a:"+ manager.nA + " Risorse b:" + manager.nB);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }











    }
}