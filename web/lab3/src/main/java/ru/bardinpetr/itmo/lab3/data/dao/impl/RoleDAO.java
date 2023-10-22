package ru.bardinpetr.itmo.lab3.data.dao.impl;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab3.data.beans.EntityManagerProvider;
import ru.bardinpetr.itmo.lab3.data.dao.DAO;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.Role;
import ru.bardinpetr.itmo.lab3.data.models.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Named("roleDAO")
@ApplicationScoped
@Slf4j
public class RoleDAO extends DAO<String, Role> implements Serializable {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public RoleDAO() {
        super(Role.class);
    }

    @PostConstruct
    public void init() {
        setManager(entityManagerProvider.getEntityManager());
    }
}
