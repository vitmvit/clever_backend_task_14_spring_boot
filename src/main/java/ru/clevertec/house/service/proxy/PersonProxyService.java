package ru.clevertec.house.service.proxy;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.house.cache.Cache;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.parent.UuidDto;

import java.util.UUID;

@Aspect
@AllArgsConstructor
public class PersonProxyService {
    private final Cache<UUID, UuidDto> cache;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Pointcut("execution(* ru.clevertec.house.service.impl.PersonServiceImpl.getByUuid(..)))")
    public void getMethod() {

    }

    @Pointcut("execution(* ru.clevertec.house.service.impl.PersonServiceImpl.create(..)))")
    public void createMethod() {

    }

    @Pointcut("execution(* ru.clevertec.house.service.impl.PersonServiceImpl.update(..)))")
    public void updateMethod() {

    }

    @Pointcut("execution(* ru.clevertec.house.service.impl.PersonServiceImpl.delete(..)))")
    public void deleteMethod() {

    }

    /**
     * Возвращает объект PersonDto по указанному идентификатору.
     * Если объект есть в кэше, метод возвращает его из кэша.
     * В противном случае, вызывает метод из оригинального сервиса и кэширует результат.
     *
     * @param pjp прокси-объект для вызова оригинального метода
     * @return объект PersonDto
     * @throws Throwable при возникновении исключения при выполнении оригинального метода
     */
    @Around("getMethod()")
    public Object doGet(ProceedingJoinPoint pjp) throws Throwable {
        UUID uuid = (UUID) pjp.getArgs()[0];
        if (cache.get(uuid) == null) {
            PersonDto personDto = (PersonDto) pjp.proceed();
            cache.put(uuid, personDto);
            return personDto;
        } else {
            return cache.get(uuid);
        }
    }

    /**
     * Выполняет создание объекта с кэшированием результата.
     * После выполнения метода, результат добавляется в кэш.
     *
     * @param pjp прокси-объект для вызова оригинального метода
     * @return объект, созданный методом, с кэшированным результатом
     * @throws Throwable если возникла ошибка при выполнении оригинального метода
     */
    @Around("createMethod()")
    public Object doCreate(ProceedingJoinPoint pjp) throws Throwable {
        PersonDto personDto = (PersonDto) pjp.proceed();
        cache.put(personDto.getUuid(), personDto);
        return personDto;
    }

    /**
     * Выполняет обновление объекта с кэшированием результата.
     * После выполнения метода, результат обновления добавляется в кэш.
     *
     * @param pjp прокси-объект для вызова оригинального метода
     * @return объект, обновленный методом, с кэшированным результатом
     * @throws Throwable если возникла ошибка при выполнении оригинального метода
     */
    @Around("updateMethod()")
    public Object doUpdate(ProceedingJoinPoint pjp) throws Throwable {
        PersonDto personDto = (PersonDto) pjp.proceed();
        cache.put(personDto.getUuid(), personDto);
        return personDto;
    }

    /**
     * Выполняет удаление объекта с кэшированием результата.
     * После выполнения метода, соответствующий объект удаляется из кэша.
     *
     * @param pjp прокси-объект для вызова оригинального метода
     * @return идентификатор удаленного объекта
     * @throws Throwable если возникла ошибка при выполнении оригинального метода
     */
    @Around("deleteMethod()")
    public Object doDelete(ProceedingJoinPoint pjp) throws Throwable {
        UUID uuid = (UUID) pjp.getArgs()[0];
        pjp.proceed();
        cache.remove(uuid);
        return uuid;
    }
}
