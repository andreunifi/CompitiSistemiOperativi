package com.github.andreunifi;

import Compito08_02_21.Consumer;
import Compito08_02_21.Device;
import Compito08_02_21.DevicesStatus;
import Compito08_02_21.Reader;
import Compito11_01_21.*;
import Compito25_11_20.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {


    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        int N=5;
        int K=3;
        MessageQueue queue= new MessageQueue();
        ResourceManager manager= new ResourceManager(2,23);
        ConsumerThread[] threads= new ConsumerThread[K];
        GeneratorThread[] generatorThreads= new GeneratorThread[N];
        for(int i=0;i<N;i++){
            generatorThreads[i]= new GeneratorThread(queue,3);
            generatorThreads[i].setName(String.valueOf(i));
            generatorThreads[i].start();
        }
        for(int i=0;i<K;i++){
            threads[i]= new ConsumerThread(queue,manager,i);
            threads[i].setName(String.valueOf(i));
            threads[i].start();
        }
        int counter=0;
        while (counter<=20){
            System.out.println("Numero messaggi in coda: " +queue.numMex);
            String numMex="";
            for(int i=0;i<K;i++)
                numMex+="\nConsumer" + threads[i].getName() + " ha consumato " + threads[i].numMexProcessed;
            System.out.println(numMex);
            System.out.println("Le risorse a sono: " + manager.returnSizeA() + " e le risorse b sono: " + manager.returnSizeB());
            counter++;
            Thread.sleep(1000);
        }

        for(int i=0;i<N;i++){
            generatorThreads[i].interrupt();
            generatorThreads[i].join();
        }

        for(int i=0;i<K;i++){
            threads[i].interrupt();

        }
        for(int i=0;i<K;i++){
            threads[i].join();
            System.out.println(threads[i].getName() + " ha elaborato: " + threads[i].numMexProcessed);
        }
        System.out.println("Le risorse a sono: " + manager.returnSizeA() + " e le risorse b sono: " + manager.returnSizeB());















    }


}