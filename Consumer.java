package TaskQueueHandler;

class Consumer implements Runnable {
    SyncQueue taskQueueHandlerObj;
    TaskPCSync tasktoconsume;
    boolean bconsumerAvailable ;
    Consumer(SyncQueue taskQueueHandlerObj) {
        this.taskQueueHandlerObj = taskQueueHandlerObj;
        bconsumerAvailable = false;
    }

    public void run() {
        taskQueueHandlerObj.imAvailable(this);
        bconsumerAvailable = true;
        System.out.println("Consumer started and waiting");
        synchronized (this) {
            try {
                this.wait();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Error in consumer - 1");
            }
        }
        System.out.println("Consumer exiting from wait state");
        while (true) {
            System.out.println("Consumer while loop");
            if (!taskQueueHandlerObj.taskQueue.isEmpty()) {
                bconsumerAvailable = false;
                System.out.println("Consumer removing task  ");
                tasktoconsume = taskQueueHandlerObj.SRemove();
                System.out.println("Consumer removed task : " + tasktoconsume.action );
                int res = search(tasktoconsume.dataset, tasktoconsume.value);
                if (res == -1)
                    System.out.println("Value not found");
                else
                    System.out.println("Value found at index : " + res);
                try {
                    System.out.println("Consumer waiting for task delay : " + tasktoconsume.delay + "ms");
                    Thread.sleep(tasktoconsume.delay);
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
                taskQueueHandlerObj.ack(this);
                synchronized (this) {
                    try {
                        this.wait();
                        System.out.println("Notification received by consumer - 1");

                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("Error in consumer - 2");
                    }
                }
            }
            else {
                synchronized (this) {
                    try {
                        this.wait();
                        System.out.println("Notification received by consumer - 2");
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("Error in consumer - 3");
                    }
                }
            }
        }
    }

    //search function to find the value in the given dataset specified by the task.
    public static int search(int arr[], int x)
    {
        int n = arr.length;
        for(int i = 0; i < n; i++)
        {
            if(arr[i] == x)
                return i;
        }
        return -1;
    }
}
