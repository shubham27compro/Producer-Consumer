package TaskQueueHandler;

class TaskPCSync {
    String action;
    int[] dataset;
    int value, delay;

    TaskPCSync(String action, int[] dataset, int value , int delay) {
        this.action = action;
        this.dataset = dataset;
        this.value = value;
        this.delay = delay;
    }

}
