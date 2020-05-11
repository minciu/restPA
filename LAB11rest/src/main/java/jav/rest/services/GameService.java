package jav.rest.services;

import jav.rest.trans.GameTrans;
import jav.rest.baza.Game;
import jav.rest.exceptions.DuplicateExceptions;
import jav.rest.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class GameService {

	@Autowired
    private GameRepository gameRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<GameTrans> getAllGames() {
        return ((List<Game>) gameRepository.findAll())
                .stream()
                .map(game -> modelMapper.map(game, GameTrans.class))
                .collect(Collectors.toList());
    }

    private boolean checkIfGameExists(Long id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.isPresent();
    }

    public void addGame(GameTrans gameTrans) {
        if (gameTrans.getId() != null && checkIfGameExists(gameTrans.getId())) {
            throw new DuplicateExceptions("Game with id " + gameTrans.getId() + " already exists");
        }
        gameRepository.save(modelMapper.map(gameTrans, Game.class));
    }
}
