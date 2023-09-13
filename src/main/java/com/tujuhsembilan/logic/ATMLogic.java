package com.tujuhsembilan.logic;

import data.model.Bank;

import data.model.Customer;

import data.repository.BankRepo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;


@NoArgsConstructor(access = AccessLevel.NONE)
public class ATMLogic {

  public static void login() {

  }

  public static void accountBalanceInformation(String bankName) {
    Optional<Bank> bankOptional = BankRepo.findBankByName(bankName);

    if (bankOptional.isPresent()) {
      Bank bank = bankOptional.get();
      Optional<Customer> customerOptional = bank.getCustomers().stream()
              .findFirst();

      if (customerOptional.isPresent()) {
        Customer customer = customerOptional.get();
        System.out.println("Informasi Saldo Nasabah " + customer.getFullName());
        System.out.println("Saldo: " + customer.getBalance());
      } else {
        System.out.println("Nomor rekening  tidak ditemukan dalam bank " + bankName + ".");
      }
    } else {
      System.out.println("Bank dengan nama " + bankName + " tidak ditemukan.");
    }
  }

  public static void withdrawMoney() {

  }

  public static void phoneCreditsTopUp() {
  }

  public static void electricityBillsToken() {
  }

  public static void accountMutation() {
  }

  public static void moneyDeposit() {
  }

}
