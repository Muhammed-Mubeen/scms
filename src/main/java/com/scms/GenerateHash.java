package com.scms;

import org.mindrot.jbcrypt.BCrypt;

public class GenerateHash {
    public static void main(String[] args) {
        String password = "faculty123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println("Hash: " + hash);
    }
}