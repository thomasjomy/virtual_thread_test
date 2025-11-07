package my.training;

public class VirtualThread1 {

    public static void main(String[] args) throws InterruptedException {

        Runnable task = () -> {
            System.out.println("I am running in firstVirtualThread - " + Thread.currentThread().getName() + ", isDaemon : " + Thread.currentThread().isDaemon());
        };

        Thread platformThread = Thread.ofPlatform()
                .name("Platform firstVirtualThread 1")
                .unstarted(task);
        platformThread.start();
        platformThread.join();

        Thread firstVirtualThread = Thread.ofVirtual()
                .name("First virtual firstVirtualThread")
                .unstarted(task);
        firstVirtualThread.start();
        firstVirtualThread.join();

        Thread secondVT = Thread.startVirtualThread(task);
        secondVT.join();
    }

}
