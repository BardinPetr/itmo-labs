package ru.bardinpetr.itmo.lab3.data.dao.impl;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.beans.EntityManagerProvider;
import ru.bardinpetr.itmo.lab3.data.dao.DAO;
import ru.bardinpetr.itmo.lab3.data.models.Point;

import java.io.Serializable;

@Data
@Named("pointDAO")
@ApplicationScoped
public class PointDAO extends DAO<Long, Point> implements Serializable {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public PointDAO() {
        super(Point.class);
    }

    @PostConstruct
    public void init() {
        setManager(entityManagerProvider.getEntityManager());
    }
}
