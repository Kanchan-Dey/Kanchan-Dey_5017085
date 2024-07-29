class Logger {
    private static Logger instance;

    private Logger() {

    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println(message);
    }
}
class SingletonTest {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        if (logger1 == logger2) {
            System.out.println("Singleton pattern working correctly.");
        } else {
            System.out.println("Singleton pattern failed.");
        }

        logger1.log("This is a log message.");
    }
}
