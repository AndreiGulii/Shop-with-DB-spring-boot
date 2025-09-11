package ait.cohort63.shop.controller;

import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Role;
import ait.cohort63.shop.model.entity.User;
import ait.cohort63.shop.repository.ProductRepository;
import ait.cohort63.shop.repository.RoleRepository;
import ait.cohort63.shop.repository.UserRepository;
import ait.cohort63.shop.security.dto.LoginRequestDTO;
import ait.cohort63.shop.security.dto.TokenResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    // To upper case Strg+shift+u
    private static final String TEST_PRODUCT_TITLE = "Test Product ";
    private static final int TEST_PRODUCT_PRICE = 777;
    private static final String TEST_ADMIN_NAME = "Test Admin";
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_PASSWORD = "Test Password";

    private static final String ROLE_ADMIN_TITLE = "ROLE_ADMIN";
    private static final String ROLE_USER_TITLE = "ROLE_USER";

    private final String URL_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/api/auth";
    private final String PRODUCT_RESOURCE_NAME = "/api/products";
    private final String LOGIN_ENDPOINT = "/login";
    private final String BEARER_PREFIX = "Bearer ";
    private final String AUTH_HEADER_NAME = "Authorization";


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort // podstavleaiet nomer porta v peremennuiu port
    private int port;

    // umeet otpravleati i poluchati zaprosi
    private TestRestTemplate template;

    // vibirajem Springovii org.springframework.http.HttpHeaders;
    private HttpHeaders headers;//hranit zagolovki

    private ProductDTO testProduct;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private String adminAccessToken;
    private String userAccessToken;

    private static Long testProductId;

    @BeforeEach
    public void setUp() {

        // Initsialoziruiem TestRestTemplate
        template = new TestRestTemplate();
        // Initsializiruiem Zagolovki
        headers = new HttpHeaders();

        //Sozdaiom testovii produkt
        testProduct = new ProductDTO();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(new BigDecimal(TEST_PRODUCT_PRICE));

        //peremennaia sohraniajet roli iz bazi dannih
        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            roleAdmin = roleRepository.findByTitle(ROLE_ADMIN_TITLE)
                    .orElseThrow(()-> new RuntimeException("Role Admin Not Found in DB"));
            roleUser = roleRepository.findByTitle(ROLE_USER_TITLE)
                    .orElseThrow(()-> new RuntimeException("Role User Not Found in DB"));
            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userRepository.save(admin);
        }
        if (user== null) {
            roleUser = (roleUser == null)? roleRepository.findByTitle(ROLE_USER_TITLE)
                    .orElseThrow(()-> new RuntimeException("Role User Not Found in DB")) : roleUser;

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            userRepository.save(user);
        }

        // POST http://localhost:port/api/auth/login+LoginRequestDTO

        LoginRequestDTO loginAdminDTO = new  LoginRequestDTO(TEST_ADMIN_NAME, TEST_PASSWORD);
        LoginRequestDTO loginUserDTO = new LoginRequestDTO(TEST_USER_NAME, TEST_PASSWORD);

        String authUrl = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginAdminDTO);

       ResponseEntity<TokenResponseDTO> response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDTO.class
        );
       assertTrue(response.hasBody(), "Authorisation response body is empty");

       TokenResponseDTO tokenResponse = response.getBody();
       adminAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();

       request = new HttpEntity<>(loginUserDTO);

        response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDTO.class
        );
        assertTrue(response.hasBody(), "Authorisation (user) response body is empty");

        tokenResponse = response.getBody();
        userAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();


    }

    // Testi budut zdesi
    // Void eto klass pustishka esli metod ne ojidaiet nichego na vhod to mi doljni ego ispolizovati
    @Test
    public void positiveGettingAllProductsWithoutAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<ProductDTO>> response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ProductDTO>>() {}

        );

        // Proveriaiem status otveta
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected Status code");

        // Proverka nalichia tela zaprosa
        assertTrue(response.hasBody(), "Response doesn't have body");
    }
    @Test
    public void negativeSavingProductWithoutAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;

        HttpEntity<ProductDTO> request = new HttpEntity<>(testProduct);

        ResponseEntity<ProductDTO> response = template.exchange(
                url,
                HttpMethod.POST,
                request,
                ProductDTO.class
        );

        //Proveriaiem Status on doljen biti 403
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected Status code");

        // Proverka otsutstviia tela
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    void negativeSavingProductWithUserTokenAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;

        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<ProductDTO> request = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDTO> response = template.exchange(
                url,
                HttpMethod.POST,
                request,
                ProductDTO.class
                );
        // Todo Homework sostaviti zapros, peredati tuda testovii produkt i zagolovki s avtorisatsiei, otpraviti zapros,
        //  poluchiti otvet, proveriti status otveta i tela
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected Status code");
        assertTrue(response.hasBody(), "Response has unexpected no body");



    }

    @Order(10)
    @Test
    void positiveSavingProductWithAdminTokenAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
        HttpEntity<ProductDTO> request = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDTO>response = template.exchange(
                url,
                HttpMethod.POST,
                request,
                ProductDTO.class
        );
    //Povtornoie sohranenije produkta s takim je imenem ne vozmojno
    // po etomu v poslednem testovom metode etot produkt nujno udaliti iz bazi
    // nam nujno zafiksirovati id etogo produkta

    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected Status code");
    assertTrue(response.hasBody(), "Response body is empty");
    ProductDTO savedProduct = response.getBody();
    assertNotNull(savedProduct, "Response body is null");
    assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");
    //Sohraniaiem id producta dlea dalineishego ispolizovanija
    testProductId = savedProduct.getId();
    }
    @Test
    @Order(20)
    void negativeGettingProductByIdWithoutAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME+"/"+testProductId;
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ProductDTO>response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                ProductDTO.class
        );
        // ToDo HW сформировать запросы без добавления авторизации,
        //  отправить запрос, получить ответ, проверить статус и отсутствие тела
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected Status code");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    @Order(30)
    void negativeGettingProductByIdWithBasicAuthorisation(){
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME+"/"+testProductId;
        HttpEntity<Void>request = new HttpEntity<>(headers);
        ResponseEntity<ProductDTO> response = template.withBasicAuth(TEST_ADMIN_NAME,TEST_PASSWORD)
                .exchange(
                url,
                HttpMethod.GET,
                request,
                ProductDTO.class
        );
        // ToDo HW proveriti kakoi status i kakoie telo poluchaiem
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected Status code");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    @Order(40)
    void negativeGettingProductWithIncorrectTokenAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME+"/"+testProductId;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken+"a"));
        //ToDo HW zapros otvet proveriti kakoi status i kakoie telo poluchaiem
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ProductDTO>response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                ProductDTO.class
        );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected Status code");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    @Order(50)
    void positiveGettingProductByIdWithUserTokenAuthorisation() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME+"/"+testProductId;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        //ToDo HW zapros otvet proveriti kakoi status i kakoie telo poluchaiem (nalichije v tele objekta (ne null)
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ProductDTO>response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                ProductDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected Status code");
        assertTrue(response.hasBody(), "Response body is empty");
        ProductDTO savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body is null");
        assertEquals(testProductId, savedProduct.getId(), "Saved product has unexpected id");
        //V poslednem teste tsepochiki udaliaiem iz BD sohranionnii product
        productRepository.deleteById(testProductId);
    }
}