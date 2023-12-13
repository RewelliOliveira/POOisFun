package MoodleCalculadora;

class Calculator {
    int battery;
    int batteryMax;
    float display;

    public Calculator(int batteryMax) {
        this.batteryMax = batteryMax;
        this.battery = 0;
        this.display = 0.0f;
    }

    public void charge(int value) {
        if (value < 0) {
            return;
        }
        this.battery += value;
        if (this.battery >= this.batteryMax) {
            this.battery = batteryMax;
        }
    }

    public void sum(int x, int y) {
        if (useBattery()) this.display = x + y;
    }

    public void division(float num, float den) {
        if (!useBattery()) return;
        if (den == 0) System.out.println("fail: divisao por zero");
        else this.display = num / den;
    }

    public boolean useBattery() {
        if (this.battery <= 0) {
            System.out.println("fail: bateria insuficiente");
            return false;
        } else {
            this.battery -= 1;
            return true;
        }
    }

    public String toString() {
        return String.format("display = %.2f, battery = %d", this.display, this.battery);
    }
}
