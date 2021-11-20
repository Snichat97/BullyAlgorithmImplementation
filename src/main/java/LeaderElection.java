public class LeaderElection {
    int votes;
    Process process;
    boolean flag;

    public LeaderElection(int processNumber, Process process) {
        this.process = process;
        System.out.println("Print replica servers " + this.process.replicaServers);
    }

    public void askVotes() throws InterruptedException {
        final boolean[] largerAlive = {false};
        process.replicaServers.stream().filter(process -> process.processNumber > this.process.processNumber).forEach(process -> {
            System.out.println("--------------- "+this.process.processNumber+" is sending to "+process.processNumber);
            boolean b = process.lal.processAlive();
            if(largerAlive[0] && this.process.leader==null && b){
                largerAlive[0] =true;
            }
        });
        if(largerAlive[0]==false ){
            declareVictoryMessage();
            this.process.leader=this.process;
        }
    }

    public void declareVictoryMessage() throws InterruptedException {
        process.replicaServers.stream().forEach(process -> {
            process.lal.informProcess(this.process);
        });
        this.process.processCluster.setLeader(this.process);
        triggerHealthChecks();
    }

    public void triggerHealthChecks() throws InterruptedException {
        while (this.flag) {
            Thread.sleep(100);
            process.replicaServers.forEach(process -> {
                if (process.processNumber != this.process.processNumber) {
                    try {
                        process.lal.listenToHearbeat(this.process);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
