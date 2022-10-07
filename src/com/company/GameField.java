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
    int nexX;
    int nexY;
    String  xl;
    private int stoneY;
    private int stoneX;
    private int count;
    private final int Size = 320;
    private final int DotSize = 32; //размер игрока по 32 надо сдлеать
    private final int All_Dots = 400; //сколько игровых единиц может поместиться на поле
    private Image dot;
    private Image Point;
    private Image Stone;
    //Значения поинта
    private int pointX;
    private int pointY;
    //Макс размер змейки
    int x1 ;
    int y1 ;
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
    private boolean IsStone = false;
    private boolean Movment = true;

    String[][] objects = {

            {"B", "B", "B", "G", "G", "W", "G", "W", "B", "B"},
            {"G", "G", "G", "G", "B", "G", "G", "G", "B", "B"},//B G B B B G G B B B
            {"B", "B", "B", "G", "G", "G", "G", "G", "G", "B"},
            {"B", "B", "B", "G", "G", "G", "G", "B", "B", "B"},
            {"B", "B", "B", "B", "G", "G", "G", "B", "W", "B"},
            {"G", "G", "G", "G", "G", "G", "G", "G", "B", "B"},
            {"B", "G", "G", "G", "G", "W", "G", "W", "W", "B"},
            {"G", "B", "B", "G", "G", "W", "G", "W", "W", "B"},
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
            x[i] = 0 - i * DotSize;
            y[i] = 32;

        //   int count =
        }                   //250ms
        timer = new Timer(1000, this);
        timer.start();
        createPoint();
        createStones();

    }

    public void createPoint() {//10 ячеек может поместиться на поле 320/32

            pointX = new Random().nextInt(10) * DotSize;
            pointY = new Random().nextInt(10) * DotSize;


    }
    public void createStones() {//20 ячеек может поместиться на поле 320/16
        stoneX = new Random().nextInt(10) * DotSize;
        stoneY = new Random().nextInt(10) * DotSize;
    }

    public void loadImages() {
        ImageIcon s = new ImageIcon("GC.png");
        dot = s.getImage();
        ImageIcon p = new ImageIcon("ram.png");
        Point = p.getImage();
        ImageIcon st = new ImageIcon("Stone.png");
        Stone = st.getImage();

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
                g.drawImage(Point, pointX, pointY, this);
                g.drawImage(Stone, stoneX, stoneY, this);
            }
            //Перирисовываем змейку
            for (int i = 0; i < dots; i++) {
                //Рисуем точку
               g.drawImage(dot, x[i], y[i], this);
                String counter = Integer.toString(Integer.valueOf(count));
                g.setColor(Color.WHITE);
                g.drawString(counter, 290, 30);

        }} else {
            String str = "Game Over";
            g.setColor(Color.WHITE);
            g.drawString(str, 125, Size / 2);
        }
    }

    public void move() {
        if (left) {if(IsStone!=true){
            x[0] -= DotSize;}else IsStone=false;
        }
        if (right) {if(IsStone!=true){
            x[0] += DotSize;}else IsStone=false;
        }
        if (up) {if(IsStone!=true){
            y[0] -= DotSize;}else IsStone=false;
        }
        if (down) {if(IsStone!=true){
            y[0] += DotSize;}else IsStone=false;
        }



    }


    public String checkStone() {
      //   if(checkStone().equals("B")==xl)

    if (right==true && left==false) {
        for (int i = 0; i < dots; i++) {
        nexX = ((x[i])/DotSize+1);
        //   System.out.println("x: "+ nexX +" ||||| ");
        nexY = ((y[i])/DotSize);
        //   System.out.println("y: "+ nexY+" ||||| ");
        xl = objects[nexY][nexX];
        if(xl == "B"){IsStone = true;}
        //    System.out.println("XL: "+xl);
    }}else if(left == true && right==false){
            for (int i = 0; i < dots; i++) {
                nexX = ((x[i])/DotSize-1);
                //   System.out.println("x: "+ nexX +" ||||| ");
                nexY = ((y[i])/DotSize);
                //   System.out.println("y: "+ nexY+" ||||| ");
                xl = objects[nexY][nexX];
                if(xl == "B"){IsStone = true;}
            }}else if(up == true&& down==false){
            for (int i = 0; i < dots; i++) {
                nexX = ((x[i])/DotSize);
                //   System.out.println("x: "+ nexX +" ||||| ");
                nexY = ((y[i])/DotSize-1);
                //   System.out.println("y: "+ nexY+" ||||| ");
                xl = objects[nexY][nexX];
                if(xl == "B"){IsStone = true;}
            }}else if(down == true&& up==false){
        for (int i = 0; i < dots; i++) {
            nexX = ((x[i])/DotSize);
            //   System.out.println("x: "+ nexX +" ||||| ");
            nexY = ((y[i])/DotSize+1);
            //   System.out.println("y: "+ nexY+" ||||| ");
            xl = objects[nexY][nexX];
            if(xl == "B"){IsStone = true;}
        }


}
        System.out.print(xl);
        return xl;}


    //Проверка не наткнулись ли на память
    public void checkPoint() {
        if (x[0] == pointX && y[0] == pointY) {
        //    dots++;//Удлинняемся :)
             count++;

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
            }
            //Движение вправо
            if (key == KeyEvent.VK_RIGHT) {
                right = true;
                up = false;
                down = false;
                left = false;

            }
            if (key == KeyEvent.VK_UP ) {

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
