package com.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String argv[]) {

        UndirGraph graph = null;
        try {
            File input = new File("test1.txt");
            Scanner scanner = new Scanner(input);

            graph = new UndirGraph(scanner);
        } catch (FileNotFoundException ignored) { }

        System.out.println("TEST 1:");
        System.out.println(graph.searchPath(0, 3));
        System.out.println(graph.searchPath(0, 5));
        System.out.println(graph.searchPath(0, 6));
        System.out.println(graph.searchPath(4, 7));
        System.out.println(graph.searchPath(7, 4));

        graph = null;
        try {
            File input = new File("test2.txt");
            Scanner scanner = new Scanner(input);

            graph = new UndirGraph(scanner);
        } catch (FileNotFoundException ignored) { }

        System.out.println("\nTEST 2:");
        System.out.println(graph.searchPath(0, 2));
        System.out.println(graph.searchPath(0, 3));
        System.out.println(graph.searchPath(0, 6));
        System.out.println(graph.searchPath(0, 7));
        System.out.println(graph.searchPath(0, 8));
        System.out.println(graph.searchPath(0, 11));
        System.out.println(graph.searchPath(0, 12));


        graph = null;
        try {
            File input = new File("test3.txt");
            Scanner scanner = new Scanner(input);

            graph = new UndirGraph(scanner);
        } catch (FileNotFoundException ignored) { }

        System.out.println("\nTEST 3:");
        System.out.println(graph.searchPath(0, 2));
        System.out.println(graph.searchPath(0, 3));
        System.out.println(graph.searchPath(0, 4));
        System.out.println(graph.searchPath(0, 5));

        graph = null;
        try {
            File input = new File("test4.txt");
            Scanner scanner = new Scanner(input);

            graph = new UndirGraph(scanner);
        } catch (FileNotFoundException ignored) { }

        System.out.println("\nTEST 4:");
        System.out.println(graph.searchPath(0, 5));
        System.out.println(graph.searchPath(0, 6));
        System.out.println(graph.searchPath(0, 8));
        System.out.println(graph.searchPath(0, 9));

    }



}
