package ua.com.alevel.levelone;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class AreaOfTriangle {

    public static void run(BufferedReader reader) throws IOException {
        System.out.print("Lvl-1.Task-3 Enter coordinates of A(a1,a2), B(b1,b2), C(c1,c2)\na1 = ");
        try {
            double a1 = Double.parseDouble(reader.readLine());
            System.out.print("a2 = ");
            double a2 = Double.parseDouble(reader.readLine());
            System.out.print("b1 = ");
            double b1 = Double.parseDouble(reader.readLine());
            System.out.print("b2 = ");
            double b2 = Double.parseDouble(reader.readLine());
            System.out.print("c1 = ");
            double c1 = Double.parseDouble(reader.readLine());
            System.out.print("c2 = ");
            double c2 = Double.parseDouble(reader.readLine());
            DecimalFormat dF = new DecimalFormat("#.###############");
            double area = Math.abs(((c1 - a1) * (b2 - a2) - (b1 - a1) * (c2 - a2)) / 2);
            if (area == 0) System.out.println("Degenerate triangle");
            else System.out.println(dF.format(area));
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
