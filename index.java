package TaskQueueHandler;

public class index {

    public static void main(String args[]) {

        //Setting up task queue.
        SyncQueue taskQueueHandlerObj = SyncQueue.getInstance();

        //Tasks to process.
        TaskPCSync tasks[] = new TaskPCSync[5];

        //Setting up Producer.
        Producer producerThreadObj = new Producer(taskQueueHandlerObj,tasks);
        Thread producerThread = new Thread(producerThreadObj, "producer" );

        //Setting up Consumer.
        Consumer consumerTheadObj = new Consumer(taskQueueHandlerObj);
        Thread consumerThread = new Thread(consumerTheadObj, "Consumer" );

        /**
         * For creating a task we create tasks from TaskPCSync class
         * Where the tasks take action as first parameter.
         * Data set to find from as second parameter.
         * The value to search for in data dataset as second parameter.
         * Delay for the consumer as forth parameter.
         */
        tasks[0] = new TaskPCSync("search task 1", new int[]{27, 34, 14, 78, 63}, 34, 2400);
        tasks[1] = new TaskPCSync("search task 2", new int[]{2, 5, 1, 7, 6}, 5, 3300);
        tasks[2] = new TaskPCSync("search task 3", new int[]{25, 57, 13, 73, 65, 47, 23}, 47, 1600);
        tasks[3] = new TaskPCSync("search task 4", new int[]{34, 12, 67, 34, 78, 54, 56}, 78, 2500);
        tasks[4] = new TaskPCSync("search task 5", new int[]{14, 2, 45, 34, 78, 67, 56}, 45, 2700);

        //Starting threads
        producerThread.start();
        consumerThread.start();

        System.out.println("Press Control-C to stop.");
    }
}
