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
import io.biza.deepthought.admin.api.delegate.BranchAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankBranch;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
import io.biza.deepthought.data.repository.BrandRepository;
import io.biza.deepthought.data.repository.BankBranchRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BranchAdminApiDelegateImpl implements BranchAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  private BankBranchRepository branchRepository;

  @Autowired
  private BrandRepository brandRepository;
  
  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<List<DioBankBranch>> listBranches(UUID brandId) {
    List<BankBranchData> branchData = branchRepository.findAllByBrandId(brandId);
    LOG.debug("Listing all branchs for brand id of {} and received {}", brandId, branchData);
    return ResponseEntity.ok(mapper.mapAsList(branchData, DioBankBranch.class));
  }

  @Override
  public ResponseEntity<DioBankBranch> getBranch(UUID brandId, UUID branchId) {
    Optional<BankBranchData> data = branchRepository.findByIdAndBrandId(branchId, brandId);

    if (data.isPresent()) {
      LOG.info("Retrieving a single branch with brand of {} and id of {} and content of {}",
          brandId, branchId, data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioBankBranch.class));
    } else {
      LOG.warn(
          "Attempted to retrieve a single branch but could not find with brand of {} and id of {}",
          brandId, branchId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioBankBranch> createBranch(UUID brandId, DioBankBranch createData)
      throws ValidationListException {
    
    DeepThoughtValidator.validate(validator, createData);

    Optional<BrandData> brand = brandRepository.findById(brandId);

    if (!brand.isPresent()) {
      LOG.warn("Attempted to create a branch with non existent brand of {}", brandId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND).explanation(Labels.ERROR_INVALID_BRAND).build();
    }
        
    BankBranchData data = mapper.map(createData, BankBranchData.class);
    data.brand(brand.get());
    LOG.debug("Created a new branch for brand {} with content of {}", brandId, data);
    return getBranch(brandId, branchRepository.save(data).id());
  }

  @Override
  public ResponseEntity<Void> deleteBranch(UUID brandId, UUID branchId) {
    Optional<BankBranchData> optionalData = branchRepository.findByIdAndBrandId(branchId, brandId);

    if (optionalData.isPresent()) {
      LOG.debug("Deleting branch with brand of {} and branch id of {}", brandId, branchId);
      branchRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      LOG.warn(
          "Attempted to delete a branch but it couldn't be found with brand {} and branch {}",
          brandId, branchId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioBankBranch> updateBranch(UUID brandId, UUID branchId,
      DioBankBranch updateData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, updateData);
    
    Optional<BankBranchData> optionalData = branchRepository.findByIdAndBrandId(branchId, brandId);

    if (optionalData.isPresent()) {
      BankBranchData data = optionalData.get();
      mapper.map(updateData, data);
      branchRepository.save(data);
      LOG.debug("Updated branch with brand {} and branch {} containing content of {}", brandId,
          branchId, data);
      return getBranch(brandId, data.id());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
