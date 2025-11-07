package my.training;

import java.util.concurrent.StructuredTaskScope;

public class ScopeValues {

    public static void main(String[] args) throws InterruptedException {

        ScopedValue<String> scopedValue = ScopedValue.newInstance();

        Runnable task = () -> {
          if(scopedValue.isBound()){
              System.out.println("Scoped value bound to " +  scopedValue.get());
          } else {
              System.out.println("Scoped value no");
          }
        };

        System.out.println("Running task...");
        Thread thread =  Thread.ofPlatform().unstarted(task);

        ScopedValue
                .where(scopedValue, "KEY")
                .run(thread::start);


        /*ScopedValue.where(scopedValue, "KEY")
                        .run(() -> {
                            try(var scope = new StructuredTaskScope<String>()){
                                scope.fork(task);
                                scope.join();
                            } catch (InterruptedException e){
                                throw new RuntimeException(e);
                            }
                        });*/
        thread.join();
        task.run();
    }
}
