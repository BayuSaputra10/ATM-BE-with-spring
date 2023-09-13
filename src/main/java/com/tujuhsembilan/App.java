package com.tujuhsembilan;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.tujuhsembilan.logic.ATMLogic;

import data.constant.BankCompany;
import data.model.ATM;
import data.model.Bank;
import data.model.Customer;
import data.repository.ATMRepo;
import data.repository.BankRepo;

import static com.tujuhsembilan.logic.ConsoleUtil.*;

public class App {

    public static void main(String[] args) {
        boolean loop = true;
        while (loop) {
            printClear();
            printDivider();
            int num = 1;
            for (String menu : Arrays.asList(BankCompany.values()).stream()
                    .map(item -> "ATM " + item.getName())
                    .collect(Collectors.toList())) {
                System.out.println(" " + num + ". " + menu);
                num++;
            }
            printDivider("-");
            System.out.println(" 0. EXIT");
            printDivider();

            System.out.print(" > ");
            int selection = in.nextInt() - 1;
            if (selection >= 0 && selection < BankCompany.values().length) {
                new App(BankCompany.getByOrder(selection).getName()).start();
            } else if (selection == -1) {
                loop = false;
            } else {
                System.out.println("Invalid input");
                delay();
            }
        }
    }

    /// --- --- --- --- ---

    final Bank bank;
    final ATM atm;

    public App(String bankName) {
        Bank lBank = null;
        ATM lAtm = null;

        Optional<Bank> qBank = BankRepo.findBankByName(bankName);
        if (qBank.isPresent()) {
            Optional<ATM> qAtm = ATMRepo.findATMByBank(qBank.get());
            if (qAtm.isPresent()) {
                lBank = qBank.get();
                lAtm = qAtm.get();
            }
        }

        this.bank = lBank;
        this.atm = lAtm;
    }

    public void start() {
        if (bank != null && atm != null) {
            ATMLogic.login();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan nomor rekening: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Masukkan PIN: ");
            String pin = scanner.nextLine();

            boolean isAuthorized = checkCredentials(accountNumber, pin);

            if (isAuthorized) {
                displayATMMenu();
            } else {
                System.out.println("Nomor rekening atau PIN salah.");
                delay();
            }
        } else {
            System.out.println("Cannot find Bank or ATM");
            delay();
        }
    }

    private boolean checkCredentials(String accountNumber, String pin) {
        Optional<Customer> customer = bank.getCustomers().stream()
                .filter(cust -> accountNumber.equals(cust.getAccount()) && pin.equals(cust.getPin()))
                .findAny();

        return customer.isPresent();
    }


    private void displayATMMenu() {
        while (true) {
            System.out.println("1. Cek Saldo");
            System.out.println("2. Tarik Uang");
            System.out.println("3. Isi Ulang Pulsa Telepon");
            System.out.println("4. Token Tagihan Listrik");
            System.out.println("5. Mutasi Rekening");
            System.out.println("6. Deposit Uang");
            System.out.println("7. Keluar");
            System.out.print("Pilih opsi: ");
            int option = in.nextInt();

            switch (option) {
                case 1:
                ATMLogic.accountBalanceInformation(bank.getName());
                    break;
                case 2:
                ATMLogic.withdrawMoney();
                    break;
                case 3:
                ATMLogic.phoneCreditsTopUp();
                    break;
                case 4:
                ATMLogic.electricityBillsToken();
                    break;
                case 5:
                ATMLogic.accountMutation();
                    break;
                case 6:
                    ATMLogic.moneyDeposit();
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan ATM.");
                    delay();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }
}