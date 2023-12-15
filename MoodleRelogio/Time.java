package MoodleRelogio;

class Time {
    private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second) {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        setHour(hour);
        setMinute(minute);
        setSecond(second);
    }

    public String toString() {
        return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        if (hour < 24) this.hour = hour;
        else System.out.println("fail: hora invalida");
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        if (minute < 60) this.minute = minute;
        else System.out.println("fail: minuto invalido");

    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        if (second < 60) this.second = second;
        else System.out.println("fail: segundo invalido");
    }

    public void nextSecond() {
        this.second += 1;
        if (getSecond() + 1 >= 60) {
            setSecond(0);
            if (getMinute() + 1 == 60) {
                setMinute(0);
                if (getHour() + 1 == 24) {
                    setHour(0);
                    return;
                }
                setHour(getHour() + 1);
                return;
            }
            setMinute(getMinute() + 1);
        }
    }
}
