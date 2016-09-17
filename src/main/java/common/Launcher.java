package common;

public class Launcher {
    private static Executor executor;

    public static void main(String args[]){
        executor.exec();
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
}
