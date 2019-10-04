package TaskQueueHandler;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

class SyncQueue{
    LinkedList taskQueue;
    private Timer acknowledgementTimer = new Timer();
    private static SyncQueue single_instance = null;
    Consumer availableConsumer;
    private SyncQueue() { taskQueue = new LinkedList<TaskPCSync>(); }

    // static method to create instance of Singleton class
    public static SyncQueue getInstance()
    {
        if (single_instance == null)
            single_instance = new SyncQueue();

        return single_instance;
    }

    //Function used by the consumers to tell queue that i am available to handle tasks
    void imAvailable(Consumer availableConsumer){
        this.availableConsumer = availableConsumer;
    }

    /**
     *     synchronized remove function to make removal of tasks
     *     thread safe and make tasks removal from task queue synchronous
     */
    synchronized TaskPCSync SRemove() {
        TaskPCSync removedTask = (TaskPCSync) taskQueue.remove();
        System.out.println("Got: " + removedTask.action);
        return removedTask;
    }

    /**
     *     synchronized add function to make addition of tasks
     *     thread safe and make tasks addition in task queue synchronous
     */
    synchronized void SAdd(TaskPCSync task) {
        taskQueue.add(task);
        System.out.println("Producer adding task : " + task.action);
        if (availableConsumer.bconsumerAvailable == true){
            synchronized (availableConsumer) {
                try {
                    availableConsumer.bconsumerAvailable = false;
                    availableConsumer.notify();
                    System.out.println("Consumer notified - 1");

                } catch (Exception e) {
                    System.out.println("Error here");
                }
            }
        }
    }

    void ack(Consumer consumerAckObj){
        System.out.println("Acknowledgement received");
        consumerAckObj.bconsumerAvailable = true;
        acknowledgementTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!taskQueue.isEmpty()) {
                    System.out.println("Consumer notified - 2");
                    synchronized (consumerAckObj) {
                        try {
                            consumerAckObj.notify();
                        } catch (Exception e) {
                            System.out.println("Error in ack response");
                        }
                    }
                }
            }
        }, 3000 );
    }
}

