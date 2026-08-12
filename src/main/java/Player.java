import java.util.Objects;

public final class Player {
    private final long id;
    private final String name;
    private final int strength;

    public Player(long id, String name, int strength) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        if (strength < 0) {
            throw new IllegalArgumentException("strength must be non-negative");
        }
        this.id = id;
        this.name = name;
        this.strength = strength;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player{id=" + id + ", name='" + name + "', strength=" + strength + '}';
    }
}
