package com.example.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // create executor service example
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // submit task for execution
        executorService.submit(() -> {
            System.out.println("Этот метод очень похож на main() но выполняется в отдельном потоке");
            System.out.println("В отличии от Runnable, Thread имеет метод start()");
            System.out.println("Поэтому для запуска потока достаточно вызвать метод start() у объекта Thread");
        });

        // shutdown executor service
        executorService.shutdown();

        // для чего нужен метод shutdown()?
        // чтобы не допустить утечки ресурсов
        // если не вызвать shutdown(), то программа не завершится
    }
}
