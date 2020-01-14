package app;

public class Log {

    String additionString;

    Log(String additionalMessage) {
        this.additionString = additionalMessage;
    }

    public void log(String logData) {
        System.out.println(logData + " in " + additionString);
    }

    public void log(String logData, int lineNumber) {
        System.out.println(logData + "\n\n\n in " + additionString + " on line " + lineNumber);
    }
}