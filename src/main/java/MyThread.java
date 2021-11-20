import java.util.ArrayList;
import java.util.List;

class MyThread implements Runnable{
    int id;
    Process process;
    ArrayList replicas;
    public MyThread(int i, Process process, List<Process> replicaServers) throws InterruptedException {
        this.id = i;
        this.process=process;
        this.replicas=(ArrayList) replicaServers;
    }
    public void run(){
        try{
            System.out.println("Runnable started id:"+id+" "+ this.process.processNumber);
            this.process.setReplicaServers((ArrayList) this.replicas);
            System.out.println("Run: "+ Thread.currentThread().getName());
            System.out.println("Runnable ended id:"+id+" "+ this.process.processNumber);
        }catch(Exception err){
            err.printStackTrace();
        }
    }
}