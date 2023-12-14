package MoodleCarga;

enum Direcao {
    IN,
    OUT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
