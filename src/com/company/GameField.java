package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    //????????? ????
    private String xls;
    private int pX;
    private int pY;
    private int nexX;
    private int nexY;
    private String xl;
    private int count;
    private final int Size = 320;
    private final int DotSize = 32; //?????? ?????? ?? 32 ???? ???????
    private final int All_Dots = 400; //??????? ??????? ?????? ????? ??????????? ?? ????
    private Image dot;
    private int[] x = new int[All_Dots];
    private int[] y = new int[All_Dots];
    private int dots;//??????
    private Timer timer;
    //??????? ??????????? ??????
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    //???????? ???? ?? ?????
    private boolean inGame = true;
    private boolean IsStone = false;
    String[][] objects = new String[10][10];

    public void vvv() {
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects.length; j++) {
                int rnd = ((int) (Math.random() * 6));
                if (rnd < 4) {
                    objects[i][j] = "G";
                } else if (rnd == 4) {
                    objects[i][j] = "W";
                } else {
                    objects[i][j] = "B";
                    if (objects[0][1] == objects[i][j]) {
                        objects[0][1] = "G";
                    }
                }
                objects[0][1] = "G";
            }
        }
    }

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        vvv();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);// ????? ??????? ???? ? ????? ? ????
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
        super.paintComponent(g);//??????????? ????? ???????????? ??????????(??????????? ???????????)
        if (inGame) {

            for (int y1 = 0; y1 < 10; y1++) {
                for (int x1 = 0; x1 < 10; x1++) {
                    objects[1][0] = "G";
                    objects[2][0] = "G";
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

            //?????????????? ??????
            for (int i = 0; i < dots; i++) {
                //?????? ?????
                g.drawImage(dot, x[i], y[i], this);
                String counter = Integer.toString(Integer.valueOf(count));
                g.setColor(Color.black);
                g.drawString(counter, 290, 30);

            }
            String str = "??????? ????? ??????";
            g.setColor(Color.black);
            g.drawString(str, 0, 10);

        }
        if (count == 10) {
            inGame = false;
            String str = "You Win, Son";
            g.setColor(Color.WHITE);
            g.drawString(str, 125, Size / 2);
        }
      /*  if (inGame == false) {
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
            if (IsStone != true) {
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
            if (nexX < 9) {
                nexX = ((x[0]) / DotSize + 1);
                nexY = ((y[0]) / DotSize);
                xl = objects[nexY][nexX];
                if (xl.equals("B")) {
                    IsStone = true;
                }
            } else {
                IsStone = true;
            }

        } else if (left == true && right == false) {
            if (nexX > 0) {
                nexX = ((x[0]) / DotSize - 1);
                nexY = ((y[0]) / DotSize);
                xl = objects[nexY][nexX];
                if (xl.equals("B")) {
                    IsStone = true;
                }
            } else {
                IsStone = true;
            }
        } else if (up == true && down == false) {
            if (nexY > 0) {
                nexX = ((x[0]) / DotSize);
                nexY = ((y[0]) / DotSize - 1);
                xl = objects[nexY][nexX];
                if (xl.equals("B")) {
                    IsStone = true;
                }
            } else {
                IsStone = true;
            }


        } else if (down == true && up == false) {
            if (nexY < 9) {
                nexX = ((x[0]) / DotSize);
                nexY = ((y[0]) / DotSize + 1);
                xl = objects[nexY][nexX];
                if (xl.equals("B")) {
                    IsStone = true;
                }
            } else {
                IsStone = true;
            }
        }
        // System.out.print(xl);
        return xl;
    }


    //???????? ?? ?????????? ?? ?? ??????
    public void checkPoint() {
        //  for (int i = 0; i < dots; i++) {

        pX = ((x[0]) / DotSize);
        pY = ((y[0]) / DotSize);
        xls = objects[pY][pX];
        // }


        if (xls.equals("W")) {
            count++;
            for (int y1 = pY; y1 < pY + 1; y1++) {
                for (int x1 = pX; x1 < pX + 1; x1++) {
                    objects[y1][x1] = "G";
                }
            }
        }

    }

    //?????? ???????? ?? ????? UwU
    public void checkCollisions() {
        //???????? ?? ????? ?? ??????? ??????
        if(x[0] > Size) {
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
        //????? ?????????? ?????? ???(????? 250??)
        if (inGame) {
            checkStone();
            checkPoint();
            checkCollisions();
            move();
        }
        repaint();//???????? paintComponent, paintComponent - ??????????? ????????? ??? ????????? ???? ???????? ? swing
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            //???? ? ???????? ??????, ? ?? ???? ???? ????? ?????(?????? ????? ??? ????). ???? ?? ????? ?????? ????? ???? ????
            //???????? ?????
            if (key == KeyEvent.VK_LEFT) {
                left = true;
                up = false;
                down = false;
                right = false;
            }
            //???????? ??????
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
