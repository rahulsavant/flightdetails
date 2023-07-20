package com.flightdetails.Repository;

import com.flightdetails.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post,Long> {

}
