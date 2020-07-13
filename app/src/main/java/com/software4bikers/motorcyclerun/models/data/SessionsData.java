package com.software4bikers.motorcyclerun.models.data;

public class SessionsData {
    public String id;
    public String userId;
    public String createdAt;
    public String updatedAt;
    public SessionsData(String id, String userId, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return  this.createdAt;
    }

    public String getId() {
        return this.id;
    }
}
