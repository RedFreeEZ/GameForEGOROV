package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    //Параметры игры
    private String xls;
    private int pX;
    private int pY;
    private int nexX;
    private int nexY;
    private String  xl;
    private int count;
    private final int Size = 320;
    private final int DotSize = 32; //размер игрока по 32 надо сдлеать
    private final int All_Dots = 400; //сколько игровых единиц может поместиться на поле
    private Image dot;
    private int[] x = new int[All_Dots];
    private int[] y = new int[All_Dots];
    private int dots;//Длинна
    private Timer timer;
    //Текущее направление змейки
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    //Проверка умер ли игрок
    private boolean inGame = true;
    private boolean IsStone = false;
    private boolean Movment = true;

    String[][] objects = {
            {"B", "B", "B", "G", "G", "W", "G", "W", "B", "B"},
            {"G", "G", "G", "G", "B", "G", "G", "G", "B", "B"},//4 1
            {"B", "B", "B", "G", "G", "G", "G", "G", "G", "B"},
            {"B", "B", "B", "G", "G", "G", "G", "B", "B", "B"},
            {"B", "B", "B", "B", "G", "G", "G", "B", "B", "B"},
            {"G", "G", "G", "G", "G", "G", "G", "G", "B", "B"},
            {"B", "G", "G", "G", "G", "W", "G", "W", "W", "B"},
            {"B", "B", "B", "G", "G", "W", "G", "W", "W", "B"},
            {"B", "B", "B", "G", "G", "W", "G", "W", "B", "B"},
            {"B", "B", "B", "G", "G", "G", "G", "B", "W", "B"},
    };


    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);// Чтобы читался ввод с клавы в игре
    }

    public void initGame() {
        dots = 1;
        for (int i = 0; i < dots; i++) {
            x[i] = 0;
            y[i] = 32;
        }                   //250ms
        timer = new Timer(250, this);
        timer.start();
    }


    public void loadImages() {
        ImageIcon s = new ImageIcon("GC.png");
        dot = s.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//Перерисовка всего стандартного компонента(техническая перерисовка)
        if (inGame) {

            for (int y1 = 0; y1 < 10; y1++) {
                for (int x1 = 0; x1 < 10; x1++) {
                    switch (objects[y1][x1]) {
                        case "B":
                            g.setColor(new Color(246, 20, 79));
                            break;
                        case "W":
                            g.setColor(new Color(21, 172, 246));
                            break;
                        case "G":
                            g.setColor(new Color(244, 233, 246));
                            break;
                    }


                    g.fillRect(x1 * DotSize, y1 * DotSize, DotSize, DotSize);
                }
            }

            //Перирисовываем змейку
            for (int i = 0; i < dots; i++) {
                //Рисуем точку
                g.drawImage(dot, x[i], y[i], this);
                String counter = Integer.toString(Integer.valueOf(count));
                g.setColor(Color.WHITE);
                g.drawString(counter, 290, 30);

            }  String str = "Собирай синие поинты";
            g.setColor(Color.black);
            g.drawString(str, 0, 10);

        }if (count == 10) {
            inGame = false;
            String str = "You Win, Son";
            g.setColor(Color.WHITE);
            g.drawString(str, 125, Size / 2);
        }
        /*else {
            String str = "Game Over";
            g.setColor(Color.WHITE);
            g.drawString(str, 125, Size / 2);
        }*/

    }

    public void move() {

        if (left) {
            if (IsStone != true) {
                x[0] -= DotSize;
                left = false;
            } else {
                IsStone = false;
            }
        }

        if (right) {
            if (IsStone == false) {
                x[0] = x[0] + DotSize;
                right = false;
            } else {
                IsStone = false;
            }
        }


        if (up) {
            if (IsStone != true) {
                y[0] -= DotSize;
                up = false;
            } else {
                IsStone = false;
            }
        }

        if (down) {
            if (IsStone != true) {
                y[0] += DotSize;
                down = false;
            } else {
                IsStone = false;
            }
        }


    }


    public String checkStone() {
        if (right == true && left == false) {
            for (int i = 0; i < dots; i++) {
                nexX = ((x[i]) / DotSize + 1);
                nexY = ((y[i]) / DotSize);
                xl = objects[nexY][nexX];
                if (xl == "B") {
                    IsStone = true;
                }
            }
        } else if (left == true && right == false) {
            for (int i = 0; i < dots; i++) {
                nexX = ((x[i]) / DotSize - 1);
                nexY = ((y[i]) / DotSize);
                xl = objects[nexY][nexX];
                if (xl == "B") {
                    IsStone = true;
                }
            }
        } else if (up == true && down == false) {
            for (int i = 0; i < dots; i++) {
                nexX = ((x[i]) / DotSize);
                nexY = ((y[i]) / DotSize - 1);
                xl = objects[nexY][nexX];
                if (xl == "B") {
                    IsStone = true;
                }
            }
        } else if (down == true && up == false) {
            for (int i = 0; i < dots; i++) {
                nexX = ((x[i]) / DotSize);
                nexY = ((y[i]) / DotSize + 1);
                xl = objects[nexY][nexX];
                if (xl == "B") {
                    IsStone = true;
                }
            }


        }
        // System.out.print(xl);
        return xl;
    }


    //Проверка не наткнулись ли на память
    public void checkPoint() {
        for (int i = 0; i < dots; i++) {
            pX = ((x[i]) / DotSize);
            pY = ((y[i]) / DotSize);
            xls = objects[pY][pX];
        }
        if (xls == "W") {
            count++;
            for (int y1 = pY; y1 < pY + 1; y1++) {
                for (int x1 = pX; x1 < pX + 1; x1++) {
                    objects[y1][x1] = "G";
                }
            }
        }
    }

    //Нельзя выходить за рамки UwU
    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
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
            checkStone();
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
            if (key == KeyEvent.VK_LEFT) {
                left = true;
                up = false;
                down = false;
                right = false;
            }
            //Движение вправо
            if (key == KeyEvent.VK_RIGHT) {
                right = true;
                up = false;
                down = false;
                left = false;
            }
            if (key == KeyEvent.VK_UP) {
                left = false;
                up = true;
                right = false;
                down = false;
            }
            if (key == KeyEvent.VK_DOWN) {
                down = true;
                right = false;
                left = false;
                up = false;
            }
        }
    }
}
