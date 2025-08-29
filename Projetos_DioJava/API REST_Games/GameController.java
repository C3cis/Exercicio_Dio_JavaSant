package com.example.games.controller;

import com.example.games.model.Game;
import com.example.games.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin(origins = "*") // libera CORS para testes
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Game one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Game create(@RequestBody Game game) {
        return service.save(game);
    }

    @PutMapping("/{id}")
    public Game update(@PathVariable Long id, @RequestBody Game game) {
        game.setId(id);
        return service.save(game);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
