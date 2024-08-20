package com.myproject.CortaCaminho.domain.Url;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Url u WHERE u.user.id = :userId AND u.url = :url")
    boolean existsByUserIdAndUrl(@Param("userId") Long userId, @Param("url") String url);


    boolean existsByShortenedUrl(String shortenedUrl);

    @Modifying
    @Transactional
    @Query("UPDATE Url u SET u.status = :status WHERE u.id = :id")
    void desativarById(@Param("id") Long id, @Param("status") Status status);

    @Query("SELECT u FROM Url u WHERE u.statusTime < :currentDate AND u.scheduledStatus = true")
    List<Url> findExpiredUrls(@Param("currentDate") LocalDateTime currentDate);

    Optional<Url> findByIdAndUserId(Long id, Long userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Url u WHERE u.id = :urlId AND u.user.id = :userId")
    int deleteByUrlIdAndUserId(@Param("urlId") Long urlId, @Param("userId") Long userId);

    

    boolean existsByUrl(String url);

    @Query("""
    SELECT u FROM Url u 
    WHERE LOWER(u.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<Url> findAllByTitle(@Param("keyword") String keyword, Pageable pageable);


    @Query("""
    SELECT u FROM Url u 
    WHERE LOWER(u.keywords) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<Url> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
