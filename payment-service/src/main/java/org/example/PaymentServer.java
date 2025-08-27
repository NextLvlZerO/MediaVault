package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentServer {

    private static final Logger log = LoggerFactory.getLogger(PaymentServer.class);

    public static void main(String[] args) throws Exception {

        int port = 9090;
        log.info("Payment Server started on port: {}", port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();

            }
        }



    }


    private static void handleClient(Socket clientSocket) {

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {

            String request = in.readLine();
            log.info("Client received: {}", request);

            if (request != null && request.startsWith("PAYMENT_REQUEST:")) {
                String payload = request.split(":")[1].trim();
                String[] parts = payload.split(";");
                String paymentType = parts[0];
                String paymentId = parts[1];
                double amount = Double.parseDouble(parts[2]);

                String status;
                if ("SUBSCRIPTION".equals(paymentType)) {
                    status = "SUCCESS";
                }
                else if ("MEDIUM".equals(paymentType)) {
                    int percentage = new Random().nextInt(100);
                    if (percentage < 101) {
                        status = "SUCCESS";
                    } else {
                        status = "FAILED";
                    }
                } else {
                    status = "FAILED";
                }


                String response = status + ": (PaymentId=" + paymentId + ", Type=" + paymentType + ", Amount=" + amount + ", Time=" + LocalDateTime.now() + ")";
                out.println(response);

                log.info("Responded with: {}", response);
            } else {
                log.error("ERROR: Invalid request {}", request);
                out.println("ERROR: Invalid request");
            }


        } catch (IOException e) {
            log.error("ERROR: I/O exception {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }


}