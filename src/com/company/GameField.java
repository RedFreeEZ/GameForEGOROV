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
    //Параметры игры
    private final int Size = 320;
    private final int DotSize = 16; //размер игрока по
    private final int All_Dots = 400; //сколько игровых единиц может поместиться на поле
    private Image dot;
    private Image Point;
    //Значения поинта
    private int pointX;
    private int pointY;
    //Макс размер змейки
    private int[] x = new int[All_Dots];
    private int[] y = new int[All_Dots];
    private int dots;//Длинна
    private Timer timer;
    //Текущее направление змейки
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    //Проверка умер ли игрок
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);// Чтобы читался ввод с клавы в игре
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

    public void createPoint() {//20 ячеек может поместиться на поле 320/16
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
        super.paintComponent(g);//Перерисовка всего стандартного компонента(техническая перерисовка)
        if (inGame) {
            g.drawImage(Point, pointX, pointY, this);
            //Перирисовываем змейку
            for (int i = 0; i < dots; i++) {
                //Рисуем точку
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
            x[i] = x[i - 1];//Движение в цикле 5-4,4-3 по х
            y[i] = y[i - 1];//Движение в цикле 5-4,4-3 по у
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

    //Проверка не наткнулись ли на память
    public void checkPoint() {
        if (x[0] == pointX && y[0] == pointY) {
            dots++;//Удлинняемся :)
            createPoint();
        }
    }

    //Нельзя выходить за рамки UwU
    public void checkCollisions() {
        //Проверка на укус
        for (int i = dots; i > 0; i--) {
            //Если у змеи тело меньше 4 клеток она не может себя укусить
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false; //GAME OVER
            }
        }
        //Проверка на выход за границы экрана
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
        //Метод вызываемый каждый тик(через 250мс)
        if (inGame) {
            checkPoint();
            checkCollisions();
            move();
        }
        repaint();//Вызывает paintComponent, paintComponent - стандартный компанент для отрисовке всех объектов к swing
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            //Если я двигаюсь вправо, я не могу идти резко влево(только вверх или вниз). Змея не может ходить через саму себя
            //Движение влево
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            //Движение вправо
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
