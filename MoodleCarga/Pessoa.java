package MoodleCarga;

import java.util.Objects;

class Pessoa implements Pass {
    private String id;

    public Pessoa(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) object;
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId();
    }
}
