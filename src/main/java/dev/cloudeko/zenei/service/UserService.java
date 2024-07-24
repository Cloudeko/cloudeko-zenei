package dev.cloudeko.zenei.service;

import dev.cloudeko.zenei.dto.User;
import dev.cloudeko.zenei.mapping.UserMapper;
import dev.cloudeko.zenei.models.UserEntity;
import dev.cloudeko.zenei.models.UserPasswordEntity;
import dev.cloudeko.zenei.repository.UserPasswordRepository;
import dev.cloudeko.zenei.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    @Inject
    UserRepository userRepository;

    @Inject
    UserPasswordRepository userPasswordRepository;

    public Uni<User> createUser(String name, String email, String password, String image) {
        return createUser(name, email, password, image, false);
    }

    @WithTransaction
    public Uni<User> createUser(String name, String email, String password, String image, boolean admin) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setImage(image);
        user.setAdmin(admin);

        UserPasswordEntity userPassword = new UserPasswordEntity();
        userPassword.setPasswordHash(BcryptUtil.bcryptHash(password));

        return userRepository.persist(user)
                .onItem().ifNotNull().invoke(userPassword::setUser)
                .onItem().ifNotNull().transformToUni(u -> userPasswordRepository.persist(userPassword))
                .onItem().ifNotNull().transform(passwordEntity -> userMapper.toDomain(passwordEntity.getUser()));
    }

    @WithSession
    public Uni<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email).onItem().ifNotNull().transform(userMapper::toDomain);
    }

    @WithSession
    public Uni<User> getUserByName(String name) {
        return userRepository.findByName(name).onItem().ifNotNull().transform(userMapper::toDomain);
    }
}
