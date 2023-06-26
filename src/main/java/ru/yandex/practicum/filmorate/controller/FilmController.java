package ru.yandex.practicum.filmorate.controller;


import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int generatedId = 1;

    @PostMapping
    public Film createFilm(@RequestBody Film film){
        isValid(film);
        film.setId(generatedId++);
        films.put(film.getId(),film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms(){
        return new ArrayList<>(films.values());
    }
    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        isValid(film);
        if (film.getId() == null){
            film.setId(generatedId++);
        }
        films.put(film.getId(),film);
        return film;
    }

    private void isValid(Film film){
        if (film.getName() == null || film.getName().isBlank()){
            throw new ValidationException("Наименование не должно быть пустым");
        } else if (film.getDescription().length() > 200){
            throw new ValidationException("Описание не должно превышать 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("Дата создания фильма не может быть ранее 28.12.1895");
        } else if (film.getDuration().toMinutes() < 90){
            throw new ValidationException("Фильм должен быть не менее 90 минут");
        }
    }
}

