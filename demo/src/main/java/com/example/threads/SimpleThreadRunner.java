package com.example.threads;

public class SimpleThreadRunner {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("Создаем объект Runnable");
        Runnable runnable = new SimpleRunnable();
        System.out.println("Создаем объект Thread");
        Thread thread = new Thread(runnable);
        System.out.println("Запускаем поток");
        thread.start();
        System.out.println("Создаем объект Thread");
        Thread thread2 = new SimpleThread();
        System.out.println("Запускаем поток");
        thread2.start();
    }
}
