package com.springboot.springboot.draf;

import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * @author heartccace
 * @create 2020-03-26 15:52
 * @Description TODO
 * @Version 1.0
 */
public class Test {
    public void daomain() {
        ProtectionDomain protectionDomain = this.getClass().getProtectionDomain();
        System.out.println(protectionDomain);

    }
    public static void main(String[] args) {
        new Test().daomain();
        String property = System.getProperty("java.class.path");
        String[] split = property.split(";");


        System.out.println(Arrays.toString(split) );
    }
}
