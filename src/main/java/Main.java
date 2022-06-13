public class Main {

    static volatile boolean toggle;
    static int iterationsNumber = 5;
    static int count;

    public static void main(String[] args) throws InterruptedException {

        int waitTime = 300;

        Runnable taskOn = () -> turnOnToggle(true);
        Runnable taskOff = () -> turnOnToggle(false);
        Thread user = new Thread(null, taskOn, "Пользователь");
        Thread game = new Thread(null, taskOff, "Игрушка");

        user.start();
        game.start();

        user.join();
        Thread.sleep(waitTime);
        game.interrupt();
    }

    public static void turnOnToggle(boolean value) {

        while (count < iterationsNumber) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            if (toggle != value) {
                toggle = value;
                System.out.printf("%s %s тумблер\n", Thread.currentThread().getName(), value ? "включил" : "выключил");
                count++;
                int waitTime = 500;
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
