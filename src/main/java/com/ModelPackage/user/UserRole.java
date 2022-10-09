package com.ModelPackage.user;

import org.json.JSONException;

import java.io.IOException;

public interface UserRole {
    public void roleAction(String name, String id) throws JSONException, IOException, InterruptedException;
}

