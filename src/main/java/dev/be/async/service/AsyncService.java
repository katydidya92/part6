package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncService {

    private final EmailService emailService;

    public void asyncCall_1() {
        System.out.println("[asyncCall_1] ::" + Thread.currentThread().getName());
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
        /*
         * 비동기로 동작할 수 있게 Sub Thread에게 위임
         * 즉, Bean을 사용해야지만 가능하다는 의미
         */
    }

    public void asyncCall_2() {
        System.out.println("[asyncCall_2] ::" + Thread.currentThread().getName());
        EmailService emailService = new EmailService();
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
        /*
         - 1번과 다르게 2번은 직접적인 Instance를 생성하는 것이기 때문에
         - 스프링의 도움을 받을 수 없어 Sub Thread 동작이 불가능하다.
         - 즉 동기적으로 동작한다는 뜻이다.
        */
    }

    public void asyncCall_3() {
        System.out.println("[asyncCall_3] ::" + Thread.currentThread().getName());
        sendMail();
        /*
         * 이미 빈을 가져온 상태이기 때문에 내부에서 작성해도 사용할 수 없다.
         * 즉 sendMail은 @Async가 없는 것과 동일하다.
         */
    }

    @Async
    public void sendMail() {
        System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    }
}
