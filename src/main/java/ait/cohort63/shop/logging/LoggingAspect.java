package ait.cohort63.shop.logging;

import ait.cohort63.shop.model.dto.ProductDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("execution(* ait.cohort63.shop.service.ProductServiceImpl.saveProduct(..))")
    public void saveProduct() {
        //metod bez tela ispolizuetsa dle ustanovki tocki sreza
    }
    //@Before("saveProduct()")
    public void beforeSavingProduct(){
        logger.info("Method saveProduct in class ProductServiceImpl was called");
    }

    @Before("saveProduct()")
    public void beforeSavingProductWithArgs(JoinPoint joinPoint){
        //izvlekaiem parametri metoda - Object[] - massiv parametrov vizova perehvatchenogo metoda
        Object[] params = joinPoint.getArgs();
        //Logiruiem informatsiiu o vizove metoda i ego parametrov
        logger.info("Method saveProductWithArgs in class ProductServiceImpl was called with param {}", params[0]);
    }
    @After("saveProduct()")
    public void afterSavingProduct(){
        logger.info("Method afterSavingProduct in class ProductServiceImpl finished his work");
    }

    // Advice After Returning srabativaiet posle zavershenija raboti
    // Advice After Throwing srabativajet v sluchaie vibrosa iskliucheniia
    @AfterReturning(
            pointcut = "execution(* ait.cohort63.shop.service.ProductServiceImpl.getProductById(..))",
            returning = "result")
    public void afterAfterReturningProductGetByID(Object result) {
        logger.info("Method GetProduct ByID return successfully: {}", result);
    }
    @AfterThrowing(
            pointcut = "execution(* ait.cohort63.shop.service.ProductServiceImpl.getProductById(..))",
            throwing = "ex")
    public void afterThrowingExceptionFromGetProductById(JoinPoint joinPoint, Exception ex){
    Object[] params = joinPoint.getArgs();
    logger.error("Method GetProduct by ID with param {} return exception: {} ", params[0], ex.getMessage());
    }

    // Advice Around samii moshnii i gibkii tip v AOP
    @Pointcut("execution(* ait.cohort63.shop.service.ProductServiceImpl.getAllActiveProducts(..))")
    public void getAllProducts(){

    }
    @Around("getAllProducts()")
    public Object aroundGetAllActiveProducts(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try{
        logger.info("Method getAllProducts in class ProductServiceImpl called ");
            result = joinPoint.proceed();
            logger.info("Method getAllProducts in class ProductServiceImpl successfully returned:{}", result);
            //Otbrosim vse produkti deshevle 3.00 €
           result = ((List<ProductDTO>) result).stream()
                    .filter(product->product.getPrice().doubleValue()>0.56)
                    .toList();

        }catch(Throwable ex){
            logger.error("Method getAll threw an exception: {}", ex.getMessage());


        }
        return result == null ? new ArrayList<>() : result;

    }

    //======================================================================================================

    @Pointcut("execution(*  *..*Service*.find*(..))")
    void anyFindOnServiceLayer(){

    }
    @Pointcut("execution(java.util.List *(..))")
    void returnList(){}

    @Pointcut("execution(ait.cohort63.shop.model.dto.ProductDTO ait.cohort63.shop.service..*(..))")
    void returnsProductDTO(){}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    void anyGetMapping(){}

}
