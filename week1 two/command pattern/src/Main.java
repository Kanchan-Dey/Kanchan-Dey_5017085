// Step 2: Define Command Interface
interface Command {
    void execute();
}

// Step 3: Implement Concrete Commands

// Concrete Command to turn on the light
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
}

// Concrete Command to turn off the light
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
}

// Step 5: Implement Receiver Class
class Light {
    public void turnOn() {
        System.out.println("The light is on.");
    }

    public void turnOff() {
        System.out.println("The light is off.");
    }
}

// Step 4: Implement Invoker Class
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}

// Step 6: Test the Command Implementation
 class CommandPatternExample {
    public static void main(String[] args) {
        // Create a Light object
        Light light = new Light();

        // Create command objects
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        // Create remote control object
        RemoteControl remote = new RemoteControl();

        // Test turning the light on
        remote.setCommand(lightOn);
        System.out.println("Testing Light On Command:");
        remote.pressButton();

        System.out.println();

        // Test turning the light off
        remote.setCommand(lightOff);
        System.out.println("Testing Light Off Command:");
        remote.pressButton();
    }
}
