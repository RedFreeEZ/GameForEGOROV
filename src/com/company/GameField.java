package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    //��������� ����
    private final int Size = 320;
    private final int DotSize = 16; //������ ������ ��
    private final int All_Dots = 400; //������� ������� ������ ����� ����������� �� ����
    private Image dot;
    private Image Point;
    //�������� ������
    private int pointX;
    private int pointY;
    //���� ������ ������
    private int[] x = new int[All_Dots];
    private int[] y = new int[All_Dots];
    private int dots;//������
    private Timer timer;
    //������� ����������� ������
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    //�������� ���� �� �����
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);// ����� ������� ���� � ����� � ����
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DotSize;
            y[i] = 48;
        }                   //250ms
        timer = new Timer(250, this);
        timer.start();
        createPoint();
    }

    public void createPoint() {//20 ����� ����� ����������� �� ���� 320/16
        pointX = new Random().nextInt(20) * DotSize;
        pointY = new Random().nextInt(20) * DotSize;
    }

    public void loadImages() {
        ImageIcon s = new ImageIcon("GC.png");
        dot = s.getImage();
        ImageIcon p = new ImageIcon("ram.png");
        Point = p.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//����������� ����� ������������ ����������(����������� �����������)
        if (inGame) {
            g.drawImage(Point, pointX, pointY, this);
            //�������������� ������
            for (int i = 0; i < dots; i++) {
                //������ �����
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.WHITE);
            g.drawString(str, 125, Size / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];//�������� � ����� 5-4,4-3 �� �
            y[i] = y[i - 1];//�������� � ����� 5-4,4-3 �� �
        }
        if (left) {
            x[0] -= DotSize;
        }
        if (right) {
            x[0] += DotSize;
        }
        if (up) {
            y[0] -= DotSize;
        }
        if (down) {
            y[0] += DotSize;
        }

    }

    //�������� �� ���������� �� �� ������
    public void checkPoint() {
        if (x[0] == pointX && y[0] == pointY) {
            dots++;//����������� :)
            createPoint();
        }
    }

    //������ �������� �� ����� UwU
    public void checkCollisions() {
        //�������� �� ����
        for (int i = dots; i > 0; i--) {
            //���� � ���� ���� ������ 4 ������ ��� �� ����� ���� �������
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false; //GAME OVER
            }
        }
        //�������� �� ����� �� ������� ������
        if (x[0] > Size) {
            inGame = false; //GAME OVER
        }
        if (x[0] < 0) {
            inGame = false; //GAME OVER
        }
        if (y[0] > Size) {
            inGame = false; //GAME OVER
        }
        if (y[0] < 0) {
            inGame = false; //GAME OVER
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //����� ���������� ������ ���(����� 250��)
        if (inGame) {
            checkPoint();
            checkCollisions();
            move();
        }
        repaint();//�������� paintComponent, paintComponent - ����������� ��������� ��� ��������� ���� �������� � swing
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            //���� � �������� ������, � �� ���� ���� ����� �����(������ ����� ��� ����). ���� �� ����� ������ ����� ���� ����
            //�������� �����
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            //�������� ������
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {

                left = false;
                up = true;
                right = false;

            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;

            }
        }
    }
}
