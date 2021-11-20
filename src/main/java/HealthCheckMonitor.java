public class HealthCheckMonitor {
    Process process;
    int rand;
    public HealthCheckMonitor(Process process){
        this.process=process;
    }
    public void induceSleepTime() throws InterruptedException
    {
        rand = (int) (Math.random() * 10000) + 1000;
        System.out.println(rand);
        Thread.sleep(rand);
        this.process.le.askVotes();
    }
}
