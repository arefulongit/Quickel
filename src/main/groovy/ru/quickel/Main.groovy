package ru.quickel


import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Main {

    static void main(String... args) {
        new Quickel(args)
                .loadAll()
    }

}
