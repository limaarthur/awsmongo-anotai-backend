package com.ignitec.anota_ai.entities;

import com.ignitec.anota_ai.dtos.CategoryDto;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String title;

    private String description;

    private String ownerId;

    public Category() {
    }

    public Category(CategoryDto categoryDto) {
        this.title = categoryDto.title();
        this.description = categoryDto.description();
        this.ownerId = categoryDto.ownerId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerId", this.ownerId);
        json.put("id", this.id);
        json.put("type", "category");

        return json.toString();
    }
}
