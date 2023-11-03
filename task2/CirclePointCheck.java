import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CirclePointCheck {
    public static void main(String[] args) {

        String ctrName = args[0];
        String pntName = args[1];
        try {
            BufferedReader centerFile = new BufferedReader(new FileReader(ctrName));
            double centerX, centerY;
            double rad;

            String centerLine = centerFile.readLine();
            String[] centerParts = centerLine.split(" ");
            centerX = Double.parseDouble(centerParts[0]);
            centerY = Double.parseDouble(centerParts[1]);
            rad = Double.parseDouble(centerFile.readLine());

            centerFile.close();

            BufferedReader pointFile = new BufferedReader(new FileReader(pntName));
            StringBuilder result = new StringBuilder();

            String line;
            while ((line = pointFile.readLine()) != null) {
                String[] pointParts = line.split(" ");
                double pointX = Double.parseDouble(pointParts[0]);
                double pointY = Double.parseDouble(pointParts[1]);

                double dist = Math.sqrt(Math.pow(pointX - centerX, 2) + Math.pow(pointY - centerY, 2));

                if (dist < rad) {
                    result.append("1\n");
                } else if (dist == rad) {
                    result.append("0\n");
                } else {
                    result.append("2\n");
                }
            }

            pointFile.close();
            System.out.println(result.toString());
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}