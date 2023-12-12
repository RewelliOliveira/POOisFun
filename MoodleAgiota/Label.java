package MoodleAgiota;

enum Label{
    GIVE,TAKE,PLUS;

    public String toString() {
        return this.name().toLowerCase();
    }
}
