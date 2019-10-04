package TaskQueueHandler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class Producer implements Runnable {
    static SyncQueue taskQueueHandlerObj;
    static TaskPCSync[] tasks;
    Producer(SyncQueue taskQueueHandlerObj, TaskPCSync[] tasks) {
        this.taskQueueHandlerObj = taskQueueHandlerObj;
        this.tasks = tasks;
    }
    static int waitTime, randomTasksNumber;
    static int queueLengthLimit = 100;
    static Timer producerSchedulerTimer = new Timer();
    static Random randomNumberGeneratorObj = new Random();

    //Function for producer to add random number of tasks (0-5) in task queue after random interval of time(0-5000ms).
    static void randomWaitInput() {
        //producer waiting for 0 to 5 seconds to add more tasks. hence, random wait time with bound 5000ms.
        waitTime = randomNumberGeneratorObj.nextInt(4000 ) * 2;
        System.out.println("Producer waiting for : " + waitTime);

        producerSchedulerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //producer adding random number of tasks in a single go (between 0 to 5)
                randomTasksNumber = randomNumberGeneratorObj.nextInt(5 );
                System.out.println("Adding no. of tasks : " + randomTasksNumber);
                if(taskQueueHandlerObj.taskQueue.size() <= (queueLengthLimit - randomTasksNumber)) {
                    for(int i = 0; i < randomTasksNumber; i++) {
                        taskQueueHandlerObj.SAdd(tasks[i]);
                    }
                } else {
                    System.out.println("Task taskQueueHandlerObjueue limit exceeded, cannot add more tasks");
                }
                randomWaitInput();
            }
        }, waitTime );

    }

    public void run() {
        randomWaitInput();
    }

}

