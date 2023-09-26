package ru.bardinpetr.itmo.lab2.models;

import lombok.Setter;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultsTableModel implements Serializable {
    @Setter
    private PointResultDatabase dao;
    @Setter
    private String ownerUsername;

    public List<PointResult> getList() {
        if (ownerUsername == null || dao == null)
            return List.of();
        var src = dao
                .getAll()
                .stream()
                .filter(i -> i.owner().equals(ownerUsername))
                .toList();
        return IntStream
                .range(0, src.size())
                .mapToObj(i -> src.get(i).withId(i + 1))
                .sorted(Comparator
                        .comparing(PointResult::getPrimaryKey)
                        .reversed())
                .collect(Collectors.toList());
    }
}

