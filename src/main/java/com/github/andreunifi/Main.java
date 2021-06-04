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
        int N=5;
        int M=4;
        int K=10;
        int nA=8;
        int nB=5;
        int T1=1; //1*1000 millsecondi
        int T2=2; //2*1000 millisecondi
        RequestQueue queue= new RequestQueue(K);
        Client[] clients= new Client[N];
        Worker[] workers= new Worker[M];

        for (int i=0;i<N;i++){
            clients[i]= new Client(queue);
            clients[i].setName(String.valueOf(i));
            clients[i].start();
        }
        ResourceManager manager= new ResourceManager(nA,nB);
        GestoreClient gestore= new GestoreClient(clients);
        for(int i=0;i<M;i++){
            workers[i]= new Worker(queue,gestore,T1,T2,manager);
            workers[i].start();
        }


        try {
            Thread.sleep(10000);
            for(int i=0;i<N;i++){
                clients[i].interrupt();

            }
            for(int i=0;i<N;i++)
                clients[i].join();



            for(int i=0;i<M;i++){
                workers[i].interrupt();

            }

            for(int i=0;i<M;i++)
                workers[i].join();


            gestore.stampaThread();
            System.out.println("Risorse a:"+ manager.nA + " Risorse b:" + manager.nB);

        }catch (InterruptedException ie){
            ie.printStackTrace();
        }











    }
}