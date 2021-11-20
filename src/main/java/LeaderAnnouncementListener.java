public class LeaderAnnouncementListener {
    Process process;

    public LeaderAnnouncementListener(Process process){
        this.process = process;
    }

    public synchronized boolean processAlive(){
        return true;
    }

    public synchronized boolean listenToHearbeat (Process leader) throws InterruptedException {
        if (this.process.leader.processNumber == process.processNumber){
            return true;
        }
        return false;
    }

    public synchronized void informProcess(Process process) {
        if(this.process.leader==null || this.process.leader.processNumber<process.processNumber){
            if(this.process.leader!=null){
                System.out.println(this.process.processNumber+"whose leader was "+this.process.leader.processNumber+" has accepted "+process.processNumber+" as the leader.");
            }
            else{
                System.out.println(this.process.processNumber+" has accepted "+process.processNumber+" as the leader.");
            }
            this.process.leader=process;
        }
    }
}
