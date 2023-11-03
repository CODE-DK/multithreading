В данном пакете находятся примеры кода для темы [Потоки в Java]
Примеры кода показывают как работать с потоками в Java, как создавать потоки, как запускать потоки, как останавливать потоки,
как синхронизировать потоки, как работать с многопоточностью в Java.

## Содержание

- [Содержание](#содержание)
- [Потоки в Java](#потоки-в-java)
- [Создание потоков](#создание-потоков)
- [Запуск потоков](#запуск-потоков)
- [Пакет java.util.concurrent](#пакет-javautilconcurrent)
  - [Состав пакета java.util.concurrent](#состав-пакета-javautilconcurrent)
- [Что такое Future](#что-такое-future)
- [Что такое CompletableFuture](#что-такое-completablefuture)
- [Планировщики потоков](#планировщики-потоков)
  - [FixedThreadPool](#fixedthreadpool)
  - [CachedThreadPool](#cachedthreadpool)
  - [SingleThreadExecutor](#singlethreadexecutor)
  - [SingleThreadScheduledExecutor](#singlethreadscheduledexecutor)
  - [ScheduledThreadPool](#scheduledthreadpool)
  - [WorkStealingPool](#workstealingpool)
- [ForkJoinPool vs ExecutorService](#forkjoinpool-vs-executorservice)
- [Locks](#locks)
- [Locks vs Synchronized](#locks-vs-synchronized)
  - [В чем отличие Locks от Syncronized?](#в-чем-отличие-locks-от-syncronized)
  - [Когда что использовать?](#когда-что-использовать)
  - [Примеры](#примеры)
    - [Пример использования Syncronized:](#пример-использования-syncronized)
    - [Пример 2 использования Syncronized:](#пример-2-использования-syncronized)
    - [Пример 3 использования Syncronized:](#пример-3-использования-syncronized)
  - [Так что же такое монитор?](#так-что-же-такое-монитор)
  - [Что такое мьютекс?](#что-такое-мьютекс)
  - [Как происходит блокировка?](#как-происходит-блокировка)
  - [Что такое получить монитор?](#что-такое-получить-монитор)
- [Concurrent collections](#concurrent-collections)
  - [ConcurrentHashMap](#concurrenthashmap)

## Потоки в Java

Потоки в Java - это легковесные процессы, которые могут выполняться параллельно друг другу.
Потоки в Java позволяют выполнять несколько задач одновременно, что увеличивает производительность программы.

Потоки в Java могут быть созданы 3мя способами:

1. Расширение класса Thread
2. Реализация интерфейса Runnable
3. Реализация интерфейса Callable

В чем отличие между интерфейсами Runnable и Callable?

1. Интерфейс Runnable не возвращает результат выполнения потока, а интерфейс Callable возвращает результат выполнения потока.
2. Интерфейс Runnable не может выбрасывать исключения, а интерфейс Callable может выбрасывать исключения.
3. Интерфейс Runnable не может быть использован в пуле потоков, а интерфейс Callable может быть использован в пуле потоков.

Когда что использовать?

1. Интерфейс Runnable лучше использовать для выполнения задач, которые не возвращают результат выполнения потока.
2. Интерфейс Callable лучше использовать для выполнения задач, которые возвращают результат выполнения потока.
3. Интерфейс Callable лучше использовать для выполнения задач, которые могут выбрасывать исключения.

Важно помнить что потоки в Java - это не процессы, а легковесные процессы.
Потоки в Java не могут быть созданы без процесса.
Процессом в Java является JVM.

## Создание потоков

Пример создания потока расширением класса Thread:

```java
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Этот код выполняется в отдельном потоке");
        System.out.println("Очень похоже на метод main()");
        System.out.println("Отличие в том что этот код выполняется в отдельном потоке");
        System.out.println("После создания объекта MyThread нужно вызвать метод start()");
    }
}
```

Пример создания потока реализацией интерфейса Runnable:

```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Похоже на Thread но это не Thread");
        System.out.println("Этот код также выполняется в отдельном потоке");
        System.out.println("Но для запуска этого потока нужно создать объект Thread и передать в него объект Runnable");
    }
}
```

Пример создания потока реализацией интерфейса Callable:

```java

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("Похоже на Runnable но данный интерфейс возвращает результат выполнения потока");
        System.out.println("Этот код также выполняется в отдельном потоке");
        System.out.println("Но чтобы запустить такой поток нужно воспользоваться классом FutureTask или ExecutorService");
        return "Hello from MyCallable!";
    }
}
```

## Запуск потоков

Пример запуска потока расширением класса Thread:

```java

public class Main {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
```

Пример запуска потока реализацией интерфейса Runnable:

```java

public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }
}
```

Пример запуска потока реализацией интерфейса Callable:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        String result = futureTask.get();
        System.out.println(result);
    }
}
```

## Пакет java.util.concurrent

Пакет java.util.concurrent содержит классы для работы с потоками в Java.
В данном пакете находятся классы для работы с потоками, синхронизации потоков, планирования потоков,
примитивы синхронизации, блокировки, семафоры, очереди, пулы потоков, планировщики потоков и другие классы.

Это более продвинутые классы для работы с потоками, чем классы Thread и Runnable.

### Состав пакета java.util.concurrent

Пакет java.util.concurrent содержит следующие классы:
- Atomics
- Concurrent collections
- [Executors (thread pools)](#forkjoinpool-vs-executorservice)
- Locks

Пример запуска потока с помощью класса ExecutorService:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

Пример запуска потока с помощью класса ExecutorService и интерфейса Callable:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new MyCallable());
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

Пример запуска потока с помощью класса ExecutorService и интерфейса Runnable:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new MyRunnable(), "Hello from ExecutorService!");
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

## Что такое Future

Future - это интерфейс, который представляет собой результат выполнения потока.
Future позволяет получить результат выполнения потока, проверить завершился ли поток, отменить поток.
Простыми словами Future - это результат выполнения потока.
Важно помнить что Future - это не поток, а результат выполнения потока.

Пример использования Future:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

[Потоки в Java]: https://metanit.com/java/tutorial/8.1.php

## Что такое CompletableFuture

CompletableFuture - это класс, который представляет собой результат выполнения потока.
CompletableFuture позволяет получить результат выполнения потока, проверить завершился ли поток, отменить поток.
В отличие от Future, CompletableFuture позволяет выполнять асинхронные задачи, которые могут зависеть друг от друга.

Пример использования CompletableFuture:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello from CompletableFuture!");
            return "Hello from CompletableFuture!";
        });
        String result = completableFuture.get();
        System.out.println(result);
    }
}
```

Пример использования CompletableFuture с зависимостью:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello from CompletableFuture!");
            return "Hello from CompletableFuture!";
        });
        CompletableFuture<String> completableFuture2 = completableFuture.thenApplyAsync((s) -> {
            System.out.println("Hello from CompletableFuture2!");
            return s + " Hello from CompletableFuture2!";
        });
        String result = completableFuture2.get();
        System.out.println(result);
    }
}
```

Пример использования CompletableFuture с зависимостью и обработкой исключения:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello from CompletableFuture!");
            return "Hello from CompletableFuture!";
        });
        CompletableFuture<String> completableFuture2 = completableFuture.thenApplyAsync((s) -> {
            System.out.println("Hello from CompletableFuture2!");
            return s + " Hello from CompletableFuture2!";
        });
        CompletableFuture<String> completableFuture3 = completableFuture2.exceptionally((e) -> {
            System.out.println("Hello from CompletableFuture3!");
            return "Hello from CompletableFuture3!";
        });
        String result = completableFuture3.get();
        System.out.println(result);
    }
}
```

## Планировщики потоков

Планировщики потоков - это классы, которые позволяют планировать потоки.
Планировщики потоков позволяют планировать потоки с помощью пула потоков, который можно настроить.

При создании планировщика потоков можно настроить:

1. Количество потоков в пуле потоков
2. Приоритет потоков в пуле потоков
3. Время жизни потоков в пуле потоков
4. Какие потоки в пуле потоков можно переиспользовать

Пример использования планировщика потоков:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(() -> {
            System.out.println("Hello from ScheduledExecutorService!");
            return "Hello from ScheduledExecutorService!";
        }, 1, TimeUnit.SECONDS);
        String result = scheduledFuture.get();
        System.out.println(result);
        scheduledExecutorService.shutdown();
    }
}
```

Пример использования планировщика потоков с переиспользованием потоков:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(() -> {
            System.out.println("Hello from ScheduledExecutorService!");
            return "Hello from ScheduledExecutorService!";
        }, 1, TimeUnit.SECONDS);
        String result = scheduledFuture.get();
        System.out.println(result);
        scheduledExecutorService.shutdown();
    }
}
```

### FixedThreadPool

Пример fixedThreadPool:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

### CachedThreadPool

Пример cachedThreadPool:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

### SingleThreadExecutor

Пример singleThreadExecutor:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

### SingleThreadScheduledExecutor

Пример singleThreadScheduledExecutor:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(() -> {
            System.out.println("Hello from ScheduledExecutorService!");
            return "Hello from ScheduledExecutorService!";
        }, 1, TimeUnit.SECONDS);
        String result = scheduledFuture.get();
        System.out.println(result);
        scheduledExecutorService.shutdown();
    }
}
```

### ScheduledThreadPool

Пример scheduledThreadPool:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(() -> {
            System.out.println("Hello from ScheduledExecutorService!");
            return "Hello from ScheduledExecutorService!";
        }, 1, TimeUnit.SECONDS);
        String result = scheduledFuture.get();
        System.out.println(result);
        scheduledExecutorService.shutdown();
    }
}
```

### WorkStealingPool

Пример workStealingPool:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```


## ForkJoinPool vs ExecutorService

ForkJoinPool - это класс, который представляет собой пул потоков.
ForkJoinPool позволяет выполнять асинхронные задачи, которые могут зависеть друг от друга.
ForkJoinPool позволяет выполнять задачи с помощью ForkJoinTask.

ExecutorService - это класс, который представляет собой пул потоков.
ExecutorService позволяет выполнять асинхронные задачи, которые могут зависеть друг от друга.
ExecutorService позволяет выполнять задачи с помощью Future.

Пример использования ForkJoinPool:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> forkJoinTask = forkJoinPool.submit(() -> {
            System.out.println("Hello from ForkJoinPool!");
            return "Hello from ForkJoinPool!";
        });
        String result = forkJoinTask.get();
        System.out.println(result);
        forkJoinPool.shutdown();
    }
}
```

Пример использования ExecutorService:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            System.out.println("Hello from ExecutorService!");
            return "Hello from ExecutorService!";
        });
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
```

В чем отличие ForkJoinPool от ExecutorService?

1. ForkJoinPool позволяет выполнять задачи с помощью ForkJoinTask, а ExecutorService позволяет выполнять задачи с помощью Future.
2. ForkJoinPool позволяет выполнять задачи, которые могут зависеть друг от друга, а ExecutorService позволяет выполнять задачи, которые не зависят друг от друга.

Когда что использовать?

1. ForkJoinPool лучше использовать для выполнения задач, которые могут зависеть друг от друга.
2. ExecutorService лучше использовать для выполнения задач, которые не зависят друг от друга.

## Locks

Locks - это классы, которые позволяют синхронизировать потоки.
Locks позволяют синхронизировать потоки с помощью блокировок.

Пример использования Locks:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("Hello from Locks!");
        } finally {
            lock.unlock();
        }
    }
}
```

## Locks vs Synchronized

Syncronized - это ключевое слово, которое позволяет синхронизировать потоки.
Syncronized позволяет синхронизировать потоки с помощью монитора.

Locks - это классы, которые позволяют синхронизировать потоки.
Locks позволяют синхронизировать потоки с помощью блокировок.

### В чем отличие Locks от Syncronized?

1. Locks позволяет синхронизировать потоки с помощью блокировок, а Syncronized позволяет синхронизировать потоки с помощью монитора.
2. Locks позволяет синхронизировать потоки, которые могут зависеть друг от друга, а Syncronized позволяет синхронизировать потоки, которые не зависят друг от друга.
3. Locks позволяет синхронизировать потоки, которые могут выбрасывать исключения, а Syncronized позволяет синхронизировать потоки, которые не могут выбрасывать исключения.

### Когда что использовать?

1. Locks лучше использовать для синхронизации потоков, которые могут зависеть друг от друга.
2. Syncronized лучше использовать для синхронизации потоков, которые не зависят друг от друга.
3. Locks лучше использовать для синхронизации потоков, которые могут выбрасывать исключения.

При синхронизации потоков с помощью Locks нужно всегда использовать блок try-finally.

### Примеры

#### Пример использования Syncronized:

```java

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        synchronized (Main.class) {
            System.out.println("В данном случае монитором является класс Main");
            System.out.println("Не самый лучший вариант синхронизации потоков");
            System.out.println("Потому что в данном случае синхронизируются все потоки (класс то всего 1)");
        }
    }
}
```

#### Пример 2 использования Syncronized:

```java

public class Main {
    private static final Object monitor = new Object();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        synchronized (monitor) {
            System.out.println("В данном случае монитором является объект Object");
            System.out.println("Пример не чильно лучше чем первый пример");
            System.out.println("Потому что в данном случае синхронизируются также все потоки (объект то в static создан)");
        }
    }
}
```

#### Пример 3 использования Syncronized:

```java

public class Main {
    private final Object monitor = new Object();

    public void doSomething() throws ExecutionException, InterruptedException {
        synchronized (monitor) {
            System.out.println("В данном случае монитором является объект Object");
            System.out.println("Пример лучше чем первый и второй пример");
            System.out.println("Потому что в данном случае синхронизируются только потоки, которые работают с данным объектом");
        }
    }
}   
```

### Так что же такое монитор?

Монитор - это объект, который используется для синхронизации и блокировки потоков.
Это просто любая ссылка как на класс так и на объект. Не важно new Object() или new String() или что то еще. 
Главное чтобы он был.

### Что такое мьютекс?

Мьютекс - это объект, который используется для синхронизации и блокировки потоков.
Мьютекс - это просто другое название монитора.

### Как происходит блокировка?

При блокировке потоки пытаются получить монитор.
Если монитор свободен, то потоки получают монитор и выполняют свою работу.
Если монитор занят, то потоки ждут пока монитор освободится.

### Что такое получить монитор?

Получить монитор - это значит получить ссылку на объект, который используется для синхронизации и блокировки потоков.

## Concurrent collections

Concurrent collections - это классы, которые позволяют работать с коллекциями в многопоточной среде.
В основе работы concurrent collections лежит блокировка.

Наиболее используемые коллекции

- ConcurrentHashMap
- ConcurrentLinkedQueue
- ConcurrentSkipListMap
- ConcurrentSkipListSet
- CopyOnWriteArrayList
- CopyOnWriteArraySet

### ConcurrentHashMap

ConcurrentHashMap - это класс, который представляет собой коллекцию.
Это привычная нам коллекция Map, которая позволяет хранить данные в виде ключ-значение.
Но работать она может в многопоточной среде. Почему? Потому что в основе работы ConcurrentHashMap лежит блокировка.

Блокировка работает следующим образом:

1. При добавлении элемента в ConcurrentHashMap блокируется только та часть коллекции, в которую добавляется элемент.
2. При удалении элемента из ConcurrentHashMap блокируется только та часть коллекции, из которой удаляется элемент.
3. При получении элемента из ConcurrentHashMap блокируется только та часть коллекции, из которой получается элемент.
4. При обновлении элемента в ConcurrentHashMap блокируется только та часть коллекции, в которую обновляется элемент.

и т.д.

Пример использования ConcurrentHashMap:

// TODO: Еще не доделал. Допишу....