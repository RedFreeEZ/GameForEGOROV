package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javax.swing.text.StyleConstants.setBackground;

public class Main extends JFrame {

    public Main(){
        setTitle("бродилка");
        setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
        setSize(340,360);//320, 345
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main m = new Main();


        }
    }


