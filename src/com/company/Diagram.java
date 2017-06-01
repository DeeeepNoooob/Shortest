package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.acl.Owner;
import java.util.ArrayList;
import javax.swing.*;
/*
   version v1.0
   saokai
 */

/**
 * Created by Yk on 2017/5/18.
 */
public class Diagram extends JFrame implements Runnable {
    private static final int w = 50;
    private int m;
    private int n;
    private int x;
    private int y;
    private Graphics g;
    private Shortest st;

    private Color rectColor = new Color(0xf5f5f5);

    public void paint(Graphics g) {
        g.setColor(Color.black);
        int a = m * w;
        int b = n * w;
        for (int i = 0; i < m; i++)

            g.drawLine(50 + (i * w), 50, 50 + (i * w), b);

        for (int i = 0; i < n; i++)
            g.drawLine(50, 50 + (i * w), a, 50 + (i * w));
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                Graphics2D g2 = (Graphics2D) g;
                ((Graphics2D) g).setStroke(new BasicStroke(5.0F));
                g.drawLine(50 + (i * w), 50 + (j * w), 50 + (i * w), 50 + (j * w));
                g.setFont(new Font("Arial", 0, 10));//
                g.drawString("(" + i + "," + (9 - j) + ")", 25 + (i * w), 60 + (j * w));
            }
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3.0f));
        g2.setColor(Color.red);
        for (int i = st.result.size() - 1; i >= 0; i--) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int x1 = st.result.get(i).x;
            int y1 = st.result.get(i).y;
            int x2 = st.result.get(i).from.x;
            int y2 = st.result.get(i).from.y;
            g2.drawLine(50 + x1 * w, (n - y1) * w, 50 + x2 * w, (n - y2) * w);
        }
    }

    public void update(Graphics g) {
        super.paint(getGraphics());
    }

    Diagram(Shortest st, int x, int y) {
        this.st = st;
        this.x = x;
        this.y = y;
        st.route(x, y, st.p);

        setLocationRelativeTo(getOwner());
        setTitle("Shortest");
        this.m = st.m;
        this.n = st.n;
//        Container p = getContentPane();
        setSize(m * w + 50, n * w + 50);
        setVisible(true);
//        p.setBackground(rectColor);
        setLayout(null);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Thread(this).start();


    }

    @Override
    public void run() {
        paint(getGraphics());
        this.addMouseListener(new MouseAdapter() {
                                  @Override
                                  public void mouseClicked(MouseEvent e) {
                                      st.result.clear();
                                      int a = x;
                                      int b = y;
                                      int x, y;
                                      x = e.getX();
                                      y = e.getY();
                                      Point p1, p2;
                                      for (int i = 0; i < st.m; i++) {
                                          if (x >= 40 + i * 50 && x < 60 + i * 50) {
                                              for (int j = 0; j < st.n - 1; j++)
                                                  if (y >= 60 + 50 * j && y < 90 + 50 * j) {
                                                      int x1 = (int) (x - 40) / 50;
                                                      int y1 = (int) st.n - 2 - (y - 60) / 50;
                                                      p1 = st.p[x1][y1];
                                                      p2 = st.p[x1][y1 + 1];
                                                      String str = JOptionPane.showInputDialog("更改(" + p1.x + "," + p1.y + ")到(" + p2.x + "," + p2.y + ")的路径长度");
                                                      if (str==null)
                                                          return;
                                                      if (str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("18") || str.equals("9")) {

                                                          p1.u = Integer.parseInt(str);
                                                          st.route(a, b, st.p);
                                                          update(getGraphics());
                                                          repaint();
                                                      } else {
                                                          System.out.print("路径长度不合法");
                                                      }

                                                  }

                                          }
                                      }
                                      for (int i = 0; i < st.m - 1; i++)
                                          if (x >= 60 + i * 50 && x < 90 + i * 50) {
                                              for (int j = 0; j < st.n; j++)
                                                  if (y >= 40 + 50 * j && y < 60 + 50 * j) {
                                                      int x1 = (int) (x - 60) / 50;
                                                      int y1 = (int) st.n - 1 - (y - 40) / 50;
                                                      p1 = st.p[x1][y1];
                                                      p2 = st.p[x1 + 1][y1];
                                                      String str = JOptionPane.showInputDialog("更改(" + p1.x + "," + p1.y + ")到(" + p2.x + "," + p2.y + ")的路径长度");
                                                      if (str==null)
                                                          return;
                                                      if (str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9")) {
                                                          p1.r = Integer.parseInt(str);
                                                          st.route(a, b, st.p);
                                                          update(getGraphics());
                                                          repaint();
                                                      } else {
                                                          System.out.print("路径长度不合法");
                                                      }

                                                  }


                                          }
                                  }
                              }


        );
    }
}
