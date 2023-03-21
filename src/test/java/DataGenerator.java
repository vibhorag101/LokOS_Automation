import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
public class DataGenerator {
    /* SHG Data Block */
    static long shg_number;
    static long shg_accountNumber;
    static long shg_address;

    /* SHG Member's Data Block */
    static long mobileNumber;
    static long address;
    static long accountNumber;
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

        /* Initializing Data Counter */
        initializeCounter();

        /* Generating SHG Data */
        generateSHGData(num_shg, num_member);
    }

    public static void initializeCounter(){
        File file = new File("DataCounter.csv");
        try {
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);

            if (scanner.hasNextLine())scanner.nextLine();
            String line = scanner.nextLine();
            String[] fields = line.split(",");

//            System.out.println(fields[0]);
//            System.out.println(fields[1]);
//            System.out.println(fields[2]);
//            System.out.println(fields[3]);
//            System.out.println(fields[4]);
//            System.out.println(fields[5]);
            shg_number = Long.parseLong(fields[0]);
            shg_address = Long.parseLong(fields[1]);
            shg_accountNumber = Long.parseLong(fields[2]);
            mobileNumber = Long.parseLong(fields[3]);
            address = Long.parseLong(fields[4]);
            accountNumber = Long.parseLong(fields[5]);

//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] fields = line.split(",");
////                createSHG(fields[0], fields[1], fields[2]);
//            }
            scanner.close();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in reading the CSV File!");
        }
    }

    public static int randomNumber(int lowerBound, int upperBound) {
        return (lowerBound + (int) (Math.random() * ((upperBound - lowerBound) + 1)));
    }

    public static String AadhaarGenerator() {
        String digit1 = "2";
        String digit2_6 = String.valueOf(randomNumber(10000, 99999));
        String digit7_8 = "23";
        String digit9_11 = String.valueOf(randomNumber(100, 999));
        String num = digit1 + digit2_6 + digit7_8 + digit9_11;
        String aadhaarNumber = num + Aadhaar.generateVerhoeff(num);
        if(aadhaarSet.contains(aadhaarNumber)) return AadhaarGenerator();
        return aadhaarNumber;
    }

    private static void generateSHGData(int num_shg, int num_member){
        String csvFilePath = "SHG_Data.csv";

//        shg_accountNumber = ;
//        shg_address = ;

        try{
            FileWriter writer = new FileWriter(csvFilePath);
            /* Column Headers */
            writer.append("name,");
            writer.append("address,");
            writer.append("accountNumber\n");

            for(int i = 1; i <= num_shg; i++){
                writer.append("SHG " + (shg_number+i) + ",");
                writer.append("shg_address" + (shg_address+i) + ",");
                writer.append(shg_accountNumber + i + "\n");
                /* Generating Member Data */
                generateMemberData((shg_number+i), num_member);
            }

            writer.flush();
            writer.close();

            System.out.println("CSV File Created Successfully for SHGs");
        }
        catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    private static void generateMemberData(long shg_number, int num_member){
        String csvFilePath = "SHG_MemberData-" + shg_number + ".csv";
        Random random = new Random();

        try{
            FileWriter writer = new FileWriter(csvFilePath);

            /* Column Headers */
            writer.append("name,");
            writer.append("mobileNumber,");
            writer.append("address,");
            writer.append("accountNumber,");
//            writer.append("voterID\n");
            writer.append("aadhaarNumber\n");

            for(int i = 1; i <= num_member; i++){
                /* To generate Random Name */
                String name = generateRandomName(random, 5);
//                voterIDSuffix++;
                accountNumber++;
                mobileNumber++;
                writer.append(name + ",");
                writer.append(mobileNumber + ",");
                writer.append("address" + (address+ i) + ",");
                writer.append(accountNumber + ",");
//                writer.append("WHY" + (voterIDSuffix) + "\n");
                writer.append(AadhaarGenerator() + "\n");
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
