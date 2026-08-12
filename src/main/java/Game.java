import java.util.*;

public class Game {
    // Ключ — имя игрока (для быстрого поиска в round)
    private final Map<String, Player> registeredPlayers = new HashMap<>();

    public void register(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player must not be null");
        }

        // Ветка 1: имя уже есть -> игнорируем (дубликат по имени)
        // Ветка 2: имени нет -> добавляем
        if (registeredPlayers.containsKey(player.getName())) {
            return;
        }

        registeredPlayers.put(player.getName(), player);
    }

    public int round(String playerName1, String playerName2) {
        if (playerName1 == null || playerName2 == null) {
            throw new IllegalArgumentException("player names must not be null");
        }

        // Быстрый поиск O(1)
        Player p1 = registeredPlayers.get(playerName1);
        Player p2 = registeredPlayers.get(playerName2);

        // Ветка 1: хотя бы один null -> исключение
        // Ветка 2: оба найдены -> сравнение
        if (p1 == null || p2 == null) {
            throw new NotRegisteredException("One or both players are not registered");
        }

        int s1 = p1.getStrength();
        int s2 = p2.getStrength();

        // Три ветки сравнения силы
        if (s1 > s2) {
            return 1;
        } else if (s2 > s1) {
            return 2;
        } else {
            return 0;
        }
    }

    // Метод для тестов: возвращает всех игроков (порядок не гарантирован, но размер и состав проверить можно)
    public Collection<Player> getAllPlayers() {
        return Collections.unmodifiableCollection(registeredPlayers.values());
    }

    // Опционально: если нужно строго по имени для тестов
    public Player getPlayerByName(String name) {
        return registeredPlayers.get(name);
    }
}