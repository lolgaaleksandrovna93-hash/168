import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void register_addsUniquePlayer() {
        Game game = new Game();
        Player p = new Player(1, "Alice", 10);
        game.register(p);

        // ИСПРАВЛЕНИЕ: Используем getAllPlayers() и проверяем размер
        Collection<Player> players = game.getAllPlayers();
        assertEquals(1, players.size());

        // Проверяем наличие игрока через поиск по имени (так как это Map)
        assertTrue(players.stream().anyMatch(pl -> pl.getName().equals("Alice")));
    }

    @Test
    void register_rejectsDuplicateByName() {
        Game game = new Game();
        Player p1 = new Player(1, "Alice", 10);
        Player p2 = new Player(2, "Alice", 20); // То же имя, другой ID

        game.register(p1);
        assertEquals(1, game.getAllPlayers().size());

        game.register(p2);

        // ИСПРАВЛЕНИЕ: Теперь дубликат определяется по имени.
        // Размер должен остаться 1.
        assertEquals(1, game.getAllPlayers().size(), "Duplicate by name should not be added");

        // Убеждаемся, что остался именно первый игрок (логика нашего register - не перезаписывать)
        Player stored = game.getPlayerByName("Alice");
        assertEquals(1, stored.getId());
        assertEquals(10, stored.getStrength());
    }

    @Test
    void register_rejectsNullPlayer() {
        Game game = new Game();
        assertThrows(IllegalArgumentException.class, () -> game.register(null));
    }

    @Test
    void round_bothRegistered_firstWins() {
        Game game = new Game();
        game.register(new Player(1, "Alice", 20));
        game.register(new Player(2, "Bob", 10));
        assertEquals(1, game.round("Alice", "Bob"));
    }

    @Test
    void round_bothRegistered_secondWins() {
        Game game = new Game();
        game.register(new Player(1, "Alice", 10));
        game.register(new Player(2, "Bob", 20));
        assertEquals(2, game.round("Alice", "Bob"));
    }

    @Test
    void round_bothRegistered_draw() {
        Game game = new Game();
        game.register(new Player(1, "Alice", 15));
        game.register(new Player(2, "Bob", 15));
        assertEquals(0, game.round("Alice", "Bob"));
    }

    @Test
    void round_firstNotRegistered_throwsException() {
        Game game = new Game();
        game.register(new Player(2, "Bob", 20));
        NotRegisteredException thrown = assertThrows(
                NotRegisteredException.class,
                () -> game.round("Alice", "Bob")
        );
        assertTrue(thrown.getMessage().contains("not registered"));
    }

    @Test
    void round_secondNotRegistered_throwsException() {
        Game game = new Game();
        game.register(new Player(1, "Alice", 20));
        NotRegisteredException thrown = assertThrows(
                NotRegisteredException.class,
                () -> game.round("Alice", "Bob")
        );
        assertTrue(thrown.getMessage().contains("not registered"));
    }

    @Test
    void round_bothNotRegistered_throwsException() {
        Game game = new Game();
        NotRegisteredException thrown = assertThrows(
                NotRegisteredException.class,
                () -> game.round("Alice", "Bob")
        );
        assertTrue(thrown.getMessage().contains("not registered"));
    }

    @Test
    void round_nullNames_throwsIllegalArgument() {
        Game game = new Game();
        game.register(new Player(1, "Alice", 10));
        assertThrows(IllegalArgumentException.class, () -> game.round(null, "Alice"));
        assertThrows(IllegalArgumentException.class, () -> game.round("Alice", null));
    }

    @Test
    void player_invalidStrength_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Player(1, "A", -1));
    }

    @Test
    void player_invalidName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Player(1, "", 10));
        assertThrows(IllegalArgumentException.class, () -> new Player(1, null, 10));
    }
}