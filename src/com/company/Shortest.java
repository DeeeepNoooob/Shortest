package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Shortest {
    int m,n;
    Point[][] p;
    ArrayList<Point> result = new ArrayList<>();

    public void map(int m, int n) {
        this.p = new Point[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                Point ptemp = new Point();
                Random in = new Random();
                ptemp.r = in.nextInt(9) + 1;
                ptemp.u = in.nextInt(9) + 1;
                ptemp.x = i;
                ptemp.y = j;
                this.p[i][j] = ptemp;
            }
        p[0][0].s = 0;
    }

    public void sh(int x, int y, Point[][] p) {
        for (int i = 1; i <= x; i++) {
            p[i][0].s = p[i - 1][0].s + p[i - 1][0].r;
            p[i][0].from = p[i - 1][0];
        }
        for (int i = 1; i <= y; i++) {
            p[0][i].s = p[0][i - 1].s + p[0][i - 1].u;
            p[0][i].from = p[0][i - 1];
        }
        for (int i = 1; i <= x; i++)
            for (int j = 1; j <= y; j++) {
                p[i][j].s = Math.min(p[i][j - 1].s + p[i][j - 1].u, p[i - 1][j].s + p[i - 1][j].r);
                if (p[i][j - 1].s + p[i][j - 1].u <= p[i - 1][j].s + p[i - 1][j].r)
                    p[i][j].from = p[i][j - 1];
                else
                    p[i][j].from = p[i - 1][j];
            }
    }

    public void find(int x, int y, Point[][] p) {
        result.add(p[x][y]);
        if (p[x][y].from != p[0][0]) {
            int a, b;
            a = p[x][y].from.x;
            b = p[x][y].from.y;
            find(a, b, p);
        }

    }
    public void route(int x,int y,Point[][] p){
        sh(x,y,p);
        find(x,y,p);
        System.out.print("(0,0)");
        for (int i = result.size()-1; i >= 0; i--)
            System.out.print("——("+result.get(i).x + "," + result.get(i).y + ")");
        System.out.println();
        System.out.println(result.get(0).s);
    }
    Shortest(int m, int n) {
        this.m=m;
        this.n=n;
        map(m, n);
    }

    public static void main(String[] args) {
        Shortest st = new Shortest(10, 10);
//        st.find(9,9,st.p);
//        for (int j = st.n-1; j >= 0; j--) {
//            for (int i = 0; i < st.m; i++) {
//                System.out.print("(" + st.p[i][j].r + "," + st.p[i][j].u + ")");
//            }
//            System.out.println();
//        }
        new Diagram(st,9,9);
    }
}
