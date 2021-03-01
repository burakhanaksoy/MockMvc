package io.thebman.restapitesting.repository;

import io.thebman.restapitesting.view.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserById(Integer id);
}
