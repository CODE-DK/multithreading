package com.example.atomics;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicsCuncurrencyExample {
    public static void main(String[] args) {
        // exampleы for all atomics from java.util.concurrent.atomic package

        // AtomicInteger
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("atomicInteger.get() = " + atomicInteger.get());
        System.out.println("atomicInteger.incrementAndGet() = " + atomicInteger.incrementAndGet());
        
        // AtomicLong
        AtomicLong atomicLong = new AtomicLong(0);
        System.out.println("atomicLong.get() = " + atomicLong.get());
        System.out.println("atomicLong.incrementAndGet() = " + atomicLong.incrementAndGet());

        // AtomicBoolean
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        System.out.println("atomicBoolean.get() = " + atomicBoolean.get());
        System.out.println("atomicBoolean.compareAndSet(false, true) = " + atomicBoolean.compareAndSet(false, true));

        // AtomicReference
        AtomicReference<String> atomicReference = new AtomicReference<>();
        System.out.println("atomicReference.get() = " + atomicReference.get());
        System.out.println("atomicReference.compareAndSet(null, \"Hello world!\") = " + atomicReference.compareAndSet(null, "Hello world!"));
        System.out.println("atomicReference.get() = " + atomicReference.get());

        // e.t.c

        // для чего нужны атомики?
        // чтобы не допустить состояния гонки
        // например, если два потока одновременно пытаются увеличить одну и ту же переменную на единицу
        // то результат может быть неожиданным
        // например, если переменная равна 0, то после двух инкрементов она должна стать равной 2
        // но если оба потока одновременно инкрементируют переменную, то результат может быть равен 1

        // как работают атомики?
        // атомики используют CAS (compare and swap) операцию
        // CAS операция - это атомарная операция, которая сравнивает значение переменной с ожидаемым значением
        // если значения совпадают, то переменной присваивается новое значение
        // если значения не совпадают, то переменная не изменяется
        // CAS операция выполняется за одну инструкцию процессора
        // поэтому CAS операция является атомарной
        // атомарная операция - это операция, которая выполняется за одну инструкцию процессора
    }
}
