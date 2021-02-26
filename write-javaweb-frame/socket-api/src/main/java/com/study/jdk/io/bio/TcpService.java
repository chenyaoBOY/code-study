package com.study.jdk.io.bio;


import com.study.jdk.io.bio.Person;

public interface TcpService {

    Person getPerson(String name, Integer age);
}
