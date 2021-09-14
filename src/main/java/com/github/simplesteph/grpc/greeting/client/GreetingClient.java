package com.github.simplesteph.grpc.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",50051)
                .usePlaintext()
                .build();
        System.out.println("Create stub");
        //old and dummy
        //DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);
        //DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newFutureStub(channel);

        //created a greet service client(synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
        //GreetServiceGrpc.GreetServiceFutureStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
        // created a greet buffer greeting message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Sumeth")
                .setLastName("Chantanyakan")
                .build();
        Greeting greeting1 = Greeting.newBuilder()
                .setFirstName("Mana")
                .setLastName("Chantanyakan")
                .build();
//        Greeting greeting2 = Greeting.newBuilder()
//                .setFirstName("Jan")
//                .setLastName("worawon")
//                .build();
        //call the RPC and get a GreetRequest
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                //.setGreeting(greeting2)
                .build();
        GreetRequest greetRequest1 = GreetRequest.newBuilder()
                .setGreeting(greeting1)
                .build();
        //call the RPC and get back a GreetResponce(protocol buffer)
        GreetResponse greetResponse = greetClient.greet(greetRequest);
        GreetResponse greetResponse1 = greetClient.greet(greetRequest1);
        System.out.println(greetResponse.getResult());
        System.out.println(greetResponse1.getResult());
        System.out.println("Shutting down chanel");
        channel.shutdown();
    }
}
