package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        SELECT u.id, first_name, last_name, middle_name, email, deleted
        FROM users u 
        LEFT JOIN sessions s ON u.id = s.user_id
        WHERE device_type = :#{#deviceType.code}
        GROUP BY u.id, first_name, last_name, middle_name, email, deleted
        ORDER BY MAX(s.started_at_utc) DESC 
        """, nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession(DeviceType deviceType);

    @Query(value = """
        SELECT u.id, u.first_name, u.last_name, u.middle_name, u.email, u.deleted, count(s.id) as sessions_count 
        FROM users u 
        LEFT JOIN sessions s ON u.id = s.user_id 
        GROUP BY u.id 
        ORDER BY sessions_count desc 
        LIMIT 1
        """, nativeQuery = true)
    User getUserWithMostSessions();
}
