package ru.bardinpetr.itmo.lab3.data.dao.impl;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.beans.EntityManagerProvider;
import ru.bardinpetr.itmo.lab3.data.dao.DAO;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@Named("userDAO")
@ApplicationScoped
public class UserDAO extends DAO<Long, User> implements Serializable {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public UserDAO() {
        super(User.class);
    }

    @PostConstruct
    public void init() {
        setManager(entityManagerProvider.getEntityManager());
    }

    public Optional<User> findByLogin(String login) {
        var res = findMatching("login", login);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    public List<PointResult> getPointResults(Long id) {
        var user = fetch(id, List.of("pointResults"));
        if (user.isEmpty()) return List.of();
        return user.get().getPointResults();
    }
}
