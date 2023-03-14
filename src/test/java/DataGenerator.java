import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
public class DataGenerator {
    /* SHG Data Block */
    static long shg_accountNumber = 20000000000L;
    static String shg_address = "shg_address";

    /* SHG Member's Data Block */
    static long mobileNumber = 9999900000L;
    static String address = "address";
    static long accountNumber = 10000000000L;
//    static int voterIDSuffix = 1000000;
    static HashSet<String> aadhaarSet = new HashSet<String>();

    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        /* "num_shg" - Determines No. Of. SHGs
         * or, No.Of. rows in the CSV File.
         */
        System.out.print("Enter No. Of. SHG: ");
        int num_shg = scan.nextInt();

        /* "num_member" - Determines No. Of. members in the SHG
         * or, No.Of. rows in the CSV File.
         */
        System.out.print("\nNo. Of. Members in Each SHG: ");
        int num_member = scan.nextInt();

//        while(true){
//            for(int i = 0; i < 625; i++){
//                String number = AadhaarGenerator();
//                aadhaarSet.add(number);
//            }
//            System.out.println(aadhaarSet.size());
//            if(aadhaarSet.size() != 625) break;
//            aadhaarSet.clear();
//        }
//        System.out.println("Duplication Detected");

        generateSHGData(num_shg, num_member);
    }

    public static int randomNumber(int lowerBound, int upperBound){
        return(lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1)));
    }

    public static String AadhaarGenerator(){
        String digit1 = "2";
        String digit2_6 = String.valueOf(randomNumber(10000,99999));
        String digit7_8 = "23";
        String digit9_11 = String.valueOf(randomNumber(100,999));
        String num = digit1 + digit2_6 + digit7_8 + digit9_11;
        String aadhaarNumber = num + Aadhaar.generateVerhoeff(num);
        if(aadhaarSet.contains(aadhaarNumber)) return AadhaarGenerator();
        return aadhaarNumber;
    }

    private static void generateSHGData(int num_shg, int num_member){
        String csvFilePath = "SHG_Data.csv";

        try{
            FileWriter writer = new FileWriter(csvFilePath);
            /* Column Headers */
            writer.append("name,");
            writer.append("address,");
            writer.append("accountNumber\n");

            for(int i = 1; i <= num_shg; i++){
                writer.append("SHG " + i + ",");
                writer.append(shg_address + i + ",");
                writer.append(shg_accountNumber + i + "\n");
                generateMemberData(i, num_member);
            }

            writer.flush();
            writer.close();

            System.out.println("CSV File Created Successfully for SHGs");
        }
        catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    private static void generateMemberData(int shg_number, int num_member){
        String csvFilePath = "SHG_MemberData-" + shg_number + ".csv";
        Random random = new Random();

        try{
            FileWriter writer = new FileWriter(csvFilePath);

            /* Column Headers */
            writer.append("name,");
            writer.append("mobileNumber,");
            writer.append("address,");
            writer.append("accountNumber,");
            writer.append("aadhaarNumber\n");

            for(int i = 1; i <= num_member; i++){
                /* To generate Random Name */
                String name = generateRandomName(random, 5);
//                voterIDSuffix++;
                accountNumber++;
                mobileNumber++;
                writer.append(name + ",");
                writer.append(mobileNumber + ",");
                writer.append(address + i + ",");
                writer.append(accountNumber + ",");
//                writer.append("WHY" + (voterIDSuffix) + "\n");
            }

            writer.flush();
            writer.close();

            System.out.println("CSV File Created Successfully for SHG: " + shg_number);
        }
        catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static String generateRandomName(Random random, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString().toLowerCase();
    }
}
