package org.study.controller.request;

import org.study.controller.anno.FieldExist;
import org.study.controller.anno.Password;

/**
 * @author dizhang
 * @date 2021-02-09
 */

@FieldExist(number = 1, fields = {"email", "tel"})
public class User {
    private String name;

    @Password
    private String password;

    private String email;

    private String tel;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public User(String name, String password, String email, String tel) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
