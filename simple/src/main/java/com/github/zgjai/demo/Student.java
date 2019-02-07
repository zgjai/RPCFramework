package com.github.zgjai.demo;

import java.io.Serializable;

/**
 * @author zhangguijiang
 * @date 2018/10/9 下午5:17
 * @description
 */
public class Student implements Serializable {

    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
