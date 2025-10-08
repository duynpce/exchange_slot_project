//package  Main.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Random;
//
//@Component
//public class UtilityForTesting {
//    private final String [] studentCode ={"ce", "si", "ai"};
//    private final String [] classCode ={"se", "ia", "is"};
//    private final String [] slot ={"1,2", "3,4"};
//    private final String [] subjectCode = {"MAE101","PRF192","CSI106","CEA101"};
//
//    private static final Random random = new Random();
//
//    int rand(int limit){/// return random number from 0 to limit - 1
//        if(limit <= 0 ) {
//            System.out.println("limit must be > 0 ");
//            return -1;
//        }
//        return random.nextInt(limit);
//    }
//
//    @Autowired
//    RunTestcase runTestcase;
//
//    public void generateClassRequest(int numberOfRequest) {/// generate fake request and write it into testClassRequest
//        Random rand = new Random();
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(runTestcase.getClassRequestFile()))){
//            for(int i = 0 ; i < numberOfRequest; i++) {
//                int randStudent = rand.nextInt(3);
//                int randClass = rand.nextInt(3);
//                int randSlot = rand.nextInt(2);
//                writer.write(
//                            studentCode[rand(3)] + String.valueOf(1000 + rand(9000)) + '/' +
//                                slot[rand(2)] + '/' +
//                                classCode[rand(3)] + String.valueOf(1000 + rand(9000))
//                );
//                writer.newLine();
//            }
//        }catch (IOException e){
//            System.out.println("write failed : " + e.getMessage() );
//        }
//    }
//
//    public void generateSlotRequest(int numberOfRequest){
//
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(runTestcase.getSlotRequestFile()))){
//            for(int i = 0 ; i < numberOfRequest; i++) {
//                writer.write(
//                        studentCode[rand(3)] + String.valueOf(1000 + rand(9000)) + '/' +
//                            slot[rand(2)] + '/' +
//                            classCode[rand(3)] + String.valueOf(1000 + rand(9000)) + '/' +
//                            subjectCode[rand(4)]
//                );
//                writer.newLine();
//            }
//        }catch (IOException e){
//            System.out.println("write failed : " + e.getMessage());
//        }
//    }
//
//    public void generateAccount(int numberOfAccount) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(runTestcase.getAccountFile()))) {
//            for (int i = 0; i < numberOfAccount; i++) {
//                String userName = studentCode[rand(studentCode.length)] + (1000 + rand(9000));
//                String password = "pw" + (1000 + rand(9000));
//                String phoneNumber = "09" + (10000000 + rand(90000000));
//                String accountName = classCode[rand(classCode.length)] + "_" + subjectCode[rand(subjectCode.length)];
//                String studentID = studentCode[rand(studentCode.length)] + (1000 + rand(9000));
//
//                writer.write(userName + "/" + password + "/" + phoneNumber + "/" + accountName + "/" + studentID);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println("write account failed: " + e.getMessage());
//        }
//    }
//
//
//}