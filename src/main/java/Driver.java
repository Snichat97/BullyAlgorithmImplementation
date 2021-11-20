import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        ProcessCluster pc =new ProcessCluster();
        pc.triggerElection();
        Thread.sleep(3000);
    }

}
