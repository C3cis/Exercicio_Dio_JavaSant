package com.example.games.service;

import com.example.games.model.Game;
import com.example.games.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository repo;

    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    public List<Game> findAll() { return repo.findAll(); }
    public Game findById(Long id) { return repo.findById(id).orElse(null); }
    public Game save(Game g) { return repo.save(g); }
    public void delete(Long id) { repo.deleteById(id); }
}
