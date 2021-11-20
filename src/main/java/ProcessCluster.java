import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessCluster {
    List<Process> replicaServers = new ArrayList();
    List<Process> downServers = new ArrayList();
    Process leader;

    public ProcessCluster() {
        System.out.println("========================================Bully Algorithm Implementation========================================");
        for (int i = 0; i <= 5; i += 1) {
            Process process = new Process(i, this);
            this.replicaServers.add(process);
        }

        System.out.println(replicaServers);
    }

    public void setLeader(Process leader) throws InterruptedException {
        this.leader = leader;
        leader.le.flag=true;
        this.leader.le.triggerHealthChecks();
    }

    public void killLeader() {
        System.out.println("=============================================Leader has gone down=============================================");
        downServers.add(leader);
        leader.le.flag=false;
        leader = null;
        triggerElection();
        for (int i = 0; i <= 5; i++) {
            this.replicaServers.get(i).termLeaderElected = false;
        }
    }

    void triggerElection() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            int numThreads = this.replicaServers.size() - this.downServers.size()-1;
            for (int i = 0; i <= numThreads; i++) {
                Process process = this.replicaServers.get(i);
                if (!this.downServers.contains(process))
                    executor.execute(new MyThread(i, process, this.replicaServers));
            }
            executor.shutdown(); // once you are done with ExecutorService
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("=============================Leader has been elected=============================");
    }
}