package com.example.executors;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        // create fork join pool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // submit task for execution
        forkJoinPool.submit(() -> {
            System.out.println("Этот метод очень похож на main() но выполняется в отдельном потоке");
            System.out.println("В отличии от Runnable, Thread имеет метод start()");
            System.out.println("Поэтому для запуска потока достаточно вызвать метод start() у объекта Thread");
        });

        // shutdown fork join pool
        forkJoinPool.shutdown();

        // для чего нужен метод shutdown()?
        // чтобы не допустить утечки ресурсов
        // если не вызвать shutdown(), то программа не завершится

        // отличие от ExecutorService в том, что ForkJoinPool использует
        // несколько потоков для выполнения задачи
        // ExecutorService использует один поток
        // ForkJoinPool используется для выполнения задач, которые можно разбить на подзадачи
        // и выполнить эти подзадачи параллельно
        // например, сортировка слиянием

        // Когда что использовать?
        // ExecutorService - для выполнения задач, которые нельзя разбить на подзадачи
        // ForkJoinPool - для выполнения задач, которые можно разбить на подзадачи
    }
}
