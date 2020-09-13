package com.denissinkov.simpleposter.repos;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.User;
import com.denissinkov.simpleposter.domain.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface PostRepo extends CrudRepository<Post, Long> {
    @Query("select new com.denissinkov.simpleposter.domain.dto.PostDto(" +
            "p, " +
            "count(pl), " +
            "sum(case when  pl = :user then 1 else 0 end) > 0 " +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.status not in ('DELETED') " +
            "group by  p ")
    Page<PostDto> findAll(Pageable pageable, @Param("user") User user);

    @Query("select new com.denissinkov.simpleposter.domain.dto.PostDto(" +
            "p, " +
            "count(pl), " +
            "sum(case when  pl = :user then 1 else 0 end) > 0 " +
            ") " +
            "from Post p left join p.likes pl " +
            "where p.status not in ('DELETED') " +
            "and author = :author " +
            "group by  p ")
    Page<PostDto> findByAuthor(Pageable pageable, @Param("author") User author, @Param("user") User user);
}