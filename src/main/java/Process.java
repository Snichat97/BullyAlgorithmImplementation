import java.util.ArrayList;
import java.util.List;


public class Process {
    int processNumber;
    List <Process> replicaServers = new ArrayList ();
    LeaderAnnouncementListener lal;
    LeaderElection le;
    HealthCheckMonitor timer;
    boolean termLeaderElected = false;
    ProcessCluster processCluster;
    Process leader = null;


    public Process(int partitonNumber, ProcessCluster processCluster) {
        this.processNumber=partitonNumber;
        this.lal= new LeaderAnnouncementListener(this);
        this.le= new LeaderElection(processNumber,this);
        this.processCluster = processCluster;
        this.timer= new HealthCheckMonitor(this);
    }

    public void setReplicaServers(ArrayList replicaServers) throws InterruptedException{
        this.replicaServers=replicaServers;
        this.le.askVotes();
    }


}
