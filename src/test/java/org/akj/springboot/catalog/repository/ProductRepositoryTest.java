package org.akj.springboot.catalog.repository;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.akj.springboot.catalog.entity.Amount;
import org.akj.springboot.catalog.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	void setUp() throws Exception {
		System.out.println(UUID.randomUUID().toString());
	}

	@Test
	public void testBeanInitiationStatus() {
		Assertions.assertNotNull(dataSource);
		Assertions.assertNotNull(entityManager);
		Assertions.assertNotNull(jdbcTemplate);
		Assertions.assertNotNull(repository);
	}

	@Test
	final void testFindByCode() {
		Optional<Product> product = repository.findByCode("P001");

		Assertions.assertNotNull(product.get());
	}

	@Test
	final void testFindAll() {
		List<Product> products = repository.findAll();
		
		Assertions.assertNotNull(products);
	}

	@Test
	@Transactional
	final void testSave() {
		Product product = new Product();
		product.setCode("P010");
		product.setName("Test");

		Amount amt = new Amount();
		amt.setAmount(new BigDecimal(13500));
		amt.setCurrency("CNY");
		product.setPrice(amt);

		Product rsp = repository.save(product);
		Assertions.assertNotNull(rsp.getId());
	}

}
