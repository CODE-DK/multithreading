package com.example.threads;

public class SimpleRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Этот метод очень похож на main() но выполняется в отдельном потоке");
        System.out.println("В отличии от Thread, Runnable не имеет метода start()");
        System.out.println("Поэтому для запуска потока необходимо создать объект Thread и передать в него Runnable");
        System.out.println("После чего вызвать метод start() у объекта Thread");
    }

}
