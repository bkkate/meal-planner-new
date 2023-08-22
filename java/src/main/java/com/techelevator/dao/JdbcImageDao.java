package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcImageDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void uploadImageToRecipe(byte[] imageBytes, String imageType, int recipeId) {
        // use String JDBC to insert the image into the database;
        String sql= "UPDATE recipes SET image=?, image_type=? WHERE recipe_id=?;";

        jdbcTemplate.update(sql, imageBytes, imageType, recipeId);
    }


}
