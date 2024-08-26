package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

        Boolean existsByUsername(String username);

        Boolean existsByEmail(String email);

        Boolean existsByPhone(String phone);

        User findByUuid(String uuid);

        @Query(nativeQuery = true, value = "SELECT * FROM Users u join authorities a on u.[user_id] = a.[user_id] join [role] r on r.id = a.role_id WHERE u.is_active = 1 AND ((:keyword IS NULL OR u.username LIKE %:keyword% ) OR (:keyword IS NULL OR u.fullname LIKE %:keyword% ) OR (:keyword IS NULL OR u.email LIKE %:keyword%)) "
                        +
                        "AND (:role IS NULL OR r.[role_desc] like %:role% ) " +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'username-desc' THEN u.username END DESC, " +
                        "CASE WHEN :sort = 'username-asc' THEN u.username END ASC, " +
                        "CASE WHEN :sort = 'fullname-desc' THEN u.fullname END DESC, " +
                        "CASE WHEN :sort = 'fullname-asc' THEN u.fullname END ASC, " +
                        "CASE WHEN :sort = 'create-desc' THEN u.create_at END DESC, " +
                        "CASE WHEN :sort = 'create-asc' THEN u.create_at END ASC, " +
                        "CASE WHEN :sort = 'birthday-desc' THEN u.birthday END DESC, " +
                        "CASE WHEN :sort = 'birthday-asc' THEN u.birthday END ASC")
        public List<User> findAll(@Param("keyword") String keyword,
                        @Param("role") String role,
                        @Param("sort") String sort);

        @Query("select u from Users u where u.email = :email")
        public Optional<User> findByEmail(@Param("email") String email);

        @Query("select u from Users u where u.username = :username")
        public Optional<User> findByUsername(@Param("username") String username);

        @Query(nativeQuery = true, value = "select count(*) from users where is_active = 'true'")
        public Integer getTotalUsers();

        @Query(nativeQuery = true, value = "select * from users where token = :token")
        public User findByToken(@Param("token") String token);

}
