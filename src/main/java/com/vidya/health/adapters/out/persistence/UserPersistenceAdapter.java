package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.UserJpaEntity;
import com.vidya.health.application.ports.UserPort;
import com.vidya.health.application.usecases.dto.UserView;
import com.vidya.health.domain.user.Role;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository repo;

    public UserPersistenceAdapter(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<UserView> findByEmail(String email) {
        return repo.findByEmail(email.toLowerCase()).map(Mapper::toView);
    }

    public Optional<UserJpaEntity> findEntityByEmail(String email) {
        return repo.findByEmail(email.toLowerCase());
    }

    @Override
    public Optional<UserView> findById(UUID id) {
        return repo.findById(id).map(Mapper::toView);
    }

    @Override
    public UserView createUser(String email, String passwordHash, Role role) {
        UserJpaEntity e = new UserJpaEntity(UUID.randomUUID(), email.toLowerCase(), passwordHash, role);
        return Mapper.toView(repo.save(e));
    }

    @Override
    public void setPassword(UUID userId, String passwordHash) {
        UserJpaEntity e = repo.findById(userId).orElseThrow();
        e.setPasswordHash(passwordHash);
        repo.save(e);
    }
}
