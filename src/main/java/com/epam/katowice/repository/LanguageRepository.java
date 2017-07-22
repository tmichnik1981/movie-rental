package com.epam.katowice.repository;


import com.epam.katowice.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
