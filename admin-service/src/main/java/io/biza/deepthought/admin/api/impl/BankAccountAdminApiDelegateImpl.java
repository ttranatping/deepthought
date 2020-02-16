package io.biza.deepthought.admin.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.BankAccountAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccount;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.BankBranchRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankAccountAdminApiDelegateImpl implements BankAccountAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  private BankAccountRepository bankAccountRepository;

  @Autowired
  private BankBranchRepository branchRepository;
  
  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<List<DioBankAccount>> listBankAccounts(UUID brandId, UUID branchId) {
    List<BankAccountData> bankAccountData = bankAccountRepository.findAllByBranchIdAndBranchBrandId(branchId, branchId);
    LOG.debug("Listing all bankAccounts for branch id of {} and received {}", branchId, bankAccountData);
    return ResponseEntity.ok(mapper.mapAsList(bankAccountData, DioBankAccount.class));
  }

  @Override
  public ResponseEntity<DioBankAccount> getBankAccount(UUID brandId, UUID branchId, UUID bankAccountId) {
    Optional<BankAccountData> data = bankAccountRepository.findByIdAndBranchIdAndBranchBrandId(bankAccountId, branchId, branchId);

    if (data.isPresent()) {
      LOG.info("Retrieving a single bankAccount with branch of {} and id of {} and content of {}",
          branchId, bankAccountId, data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioBankAccount.class));
    } else {
      LOG.warn(
          "Attempted to retrieve a single bankAccount but could not find with branch of {} and id of {}",
          branchId, bankAccountId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioBankAccount> createBankAccount(UUID brandId, UUID branchId, DioBankAccount createData)
      throws ValidationListException {
    
    DeepThoughtValidator.validate(validator, createData);

    Optional<BankBranchData> branch = branchRepository.findByIdAndBrandId(branchId, brandId);

    if (!branch.isPresent()) {
      LOG.warn("Attempted to create a bank account with non existent brand of {} branch of {}", brandId, branchId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND).explanation(Labels.ERROR_INVALID_BRAND).build();
    }
        
    BankAccountData data = mapper.map(createData, BankAccountData.class);
    data.branch(branch.get());
    LOG.debug("Created a new bankAccount for branch {} branch {} with content of {}", branchId, branchId, data);
    return getBankAccount(branchId, branchId, bankAccountRepository.save(data).id());
  }

  @Override
  public ResponseEntity<Void> deleteBankAccount(UUID brandId, UUID branchId, UUID bankAccountId) {
    Optional<BankAccountData> optionalData = bankAccountRepository.findByIdAndBranchIdAndBranchBrandId(bankAccountId, branchId, brandId);

    if (optionalData.isPresent()) {
      LOG.warn("Attempted to delete a bank account with non existent brand of {} branch of {}", brandId, branchId);
      bankAccountRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      LOG.warn(
          "Attempted to delete a bankAccount but it couldn't be found with branch {} branch {} and bankAccount {}",
          branchId, branchId, bankAccountId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioBankAccount> updateBankAccount(UUID brandId, UUID branchId, UUID bankAccountId,
      DioBankAccount updateData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, updateData);
    
    Optional<BankAccountData> optionalData = bankAccountRepository.findByIdAndBranchIdAndBranchBrandId(bankAccountId, branchId, branchId);

    if (optionalData.isPresent()) {
      BankAccountData data = optionalData.get();
      mapper.map(updateData, data);
      bankAccountRepository.save(data);
      LOG.debug("Updated bankAccount with branch {} branch {} and bankAccount {} containing content of {}", branchId, branchId,
          bankAccountId, data);
      return getBankAccount(branchId, branchId, data.id());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
