package com.github.andreunifi;

import compito11_6_19.*;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Shared_Counter_Requester counter = new Shared_Counter_Requester();
        Limited_Shared_Queue queue = new Limited_Shared_Queue(15);

        WorkerManager manager = new WorkerManager(20);

        for (int i = 0; i < 10; i++) {
            Requester requester = new Requester(queue, counter);
            requester.setName(String.valueOf(i));
            requester.start();
        }
        for (int i = 0; i < 4; i++) {
            Assigner assigner = new Assigner(queue, manager);
            assigner.setName(String.valueOf(i));
            assigner.start();
        }


    }


}