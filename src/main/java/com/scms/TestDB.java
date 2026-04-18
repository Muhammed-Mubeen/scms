package com.scms;

import com.scms.util.DBUtil;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection con = DBUtil.getDataSource().getConnection()) {
            System.out.println("✅ Connected to DB successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}