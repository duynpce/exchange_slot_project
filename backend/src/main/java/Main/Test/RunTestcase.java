//package Main.Test;
//
//import Main.Model.Enity.Account;
//import Main.Model.Enity.ExchangeClassRequest;
//import Main.Model.Enity.ExchangeSlotRequest;
//import Main.Service.AccountService;
//import Main.Service.ExchangeClassRequestService;
//import Main.Service.ExchangeSlotRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;
//
//
//@Component
//public class RunTestcase {
//
//    @Autowired
//    ExchangeClassRequestService exchangeClassRequestService;
//
//    @Autowired
//    ExchangeSlotRequestService exchangeSlotRequestService;
//
//    @Autowired
//    AccountService accountService;
//
//    private final String accountFile = "D:\\GithubRepository\\exchange_slot_project\\backend\\src\\test\\java\\TestAccount.txt";
//    private final String classRequestFile = "D:\\GithubRepository\\exchange_slot_project\\backend\\src\\test\\java\\TestClassRequest.txt";
//    private final String slotRequestFile = "D:\\GithubRepository\\exchange_slot_project\\backend\\src\\test\\java\\TestSlotRequest.txt";
//
//    public String getAccountFile() {
//        return accountFile;
//    }
//
//    public String getClassRequestFile() {
//        return classRequestFile;
//    }
//
//    public String getSlotRequestFile() {
//        return slotRequestFile;
//    }
//
//    public  void testAddAccount(){
//
//        File file = new File(accountFile);
//        try(Scanner scanner = new Scanner(file)){
//            while(scanner.hasNextLine()){
//                String line = scanner.nextLine();
//                String [] index = line.split("/");
//                String message = accountService.register(new Account(index[0],index[1],index[2],index[3],index[4],index[5])) ? "success" : "failed";
//                System.out.println(message);
//            }
//        } catch (IOException e) {
//            System.out.println("error : " + e.getMessage());
//        }catch (DataIntegrityViolationException e){
//            System.out.println("error : existed account" );
//        }
//
//    }
//
//    public  void testAddClassRequest(){
//        File file = new File(classRequestFile);
//        try(Scanner scanner = new Scanner(file)) {
//            while(scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String [] index = line.split("/");
//                ExchangeClassRequest request = new ExchangeClassRequest(index[0], index[1], index[2]);
//                boolean addSuccess = exchangeClassRequestService.add(request);
//                if (addSuccess) {
//                    System.out.println("add success");
//                } else {
//                    System.out.println("add failed");
//                }
//            }
//        }catch(IOException e){
//            System.out.println("error : " + e.getMessage());
//        }catch (DataIntegrityViolationException e){
//            System.out.println("error : existed class request with student code");
//        }
//    }
//
//    public  void testAddSlotRequest(){
//        File file = new File(slotRequestFile);
//        try(Scanner scanner = new Scanner(file)) {
//            while(scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String [] index = line.split("/");
//                ExchangeSlotRequest request = new ExchangeSlotRequest(index[0],index[1],index[2],index[3]);
//                boolean addSuccess = exchangeSlotRequestService.add(request);
//                if (addSuccess) {
//                    System.out.println("add success");
//                } else {
//                    System.out.println("add failed");
//                }
//            }
//        }catch(IOException e){
//            System.out.println("error : " + e.getMessage());
//        }catch (DataIntegrityViolationException e ){
//            System.out.println("error : existed request with the same student code and subject code");
//        }
//    }
//
//}
