package ru.bardinpetr.itmo.lab2.models;

import lombok.Setter;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;

import java.util.Comparator;
import java.util.List;

public class ResultsTableModel {
    @Setter
    private PointResultDatabase dao;

    public List<PointResult> getList() {
        return dao
                .getAll()
                .stream()
                .sorted(
                        Comparator
                                .comparing(PointResult::getPrimaryKey)
                                .reversed()
                )
                .toList();
    }
}

