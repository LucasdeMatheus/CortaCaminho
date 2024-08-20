package com.myproject.CortaCaminho.domain.Profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository <Profile, Long>{

//    SELECT u FROM Url u
//    WHERE LOWER(u.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    @Query("""
            SELECT p FROM Profile p WHERE LOWER(p.user.login) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Profile> findAllByLogin(@Param("keyword") String keyword, Pageable pageable);
}
