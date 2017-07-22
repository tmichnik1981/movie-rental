package com.epam.katowice;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class MovieRentalApplication {

	public static final String LANGUAGES_CACHE = "languages";
	public static final String CATEGORIES_CACHE = "categories";
	public static final String ACTORS_CACHE = "actors";

	public static void main(String[] args) {
		SpringApplication.run(MovieRentalApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		GuavaCache languagesCache = new GuavaCache(LANGUAGES_CACHE, CacheBuilder.newBuilder().build());
		GuavaCache categoriesCache = new GuavaCache(CATEGORIES_CACHE, CacheBuilder.newBuilder().build());
		GuavaCache actorsCache = new GuavaCache(ACTORS_CACHE, CacheBuilder.newBuilder().build());
		simpleCacheManager.setCaches(Arrays.asList(languagesCache, categoriesCache, actorsCache));
		return simpleCacheManager;
	}
}