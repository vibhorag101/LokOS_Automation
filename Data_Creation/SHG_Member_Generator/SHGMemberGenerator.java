package Data_Creation.SHG_Member_Generator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class SHGMemberGenerator{

    static long mobileNumber = 9999900000L;
    static String address = "address";
    static long accountNumber = 10000000000L;
    static int voterIDSuffix = 1000000; 


    public static void main(String[] args){
        String csvFilePath = "SHG_MemberData.csv";
        
        /* "num_rows" - Determines No. Of. members in the SHG 
         * or, No.Of. rows in the CSV File.
        */
        int num_rows = 25;
        Random random = new Random();

        try{
            FileWriter writer = new FileWriter(csvFilePath);

            /* Column Headers */
            writer.append("name,");
            writer.append("mobileNumber,");
            writer.append("address,");
            writer.append("accountNumber,");
            writer.append("voterID\n");

            for(int i = 1; i <= num_rows; i++){
                /* To generate Random Name */
                String name = generateRandomName(random, 5);

                writer.append(name + ",");
                writer.append(mobileNumber + i + ",");
                writer.append(address + i + ",");
                writer.append(accountNumber + i + ",");
                writer.append("WHY" + (voterIDSuffix + i) + "\n");
            }

            writer.flush();
            writer.close();

            System.out.print("CSV File Created Successfully!");
        }
        catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public static String generateRandomName(Random random, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString().toLowerCase();
    }
}