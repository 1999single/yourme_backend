package com.single.yourme.utils;

import org.junit.Test;

import static org.junit.Assert.*;


public class JwtUtilTest {

    @Test
    public void createToken() {

        JwtUtil.CustomClaim claim = new JwtUtil.CustomClaim("1999single", "xjx");
        String src = JwtUtil.createToken(claim);
        System.out.println(src);
        System.out.println();
        System.out.println(JwtUtil.parseToken(src).getAudience());
    }
}