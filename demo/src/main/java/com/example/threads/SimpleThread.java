package com.example.threads;

public class SimpleThread extends Thread {

    @Override
    public void run() {
        System.out.println("Этот метод очень похож на main() но выполняется в отдельном потоке");
        System.out.println("В отличии от Runnable, Thread имеет метод start()");
        System.out.println("Поэтому для запуска потока достаточно вызвать метод start() у объекта Thread");
    }
}
