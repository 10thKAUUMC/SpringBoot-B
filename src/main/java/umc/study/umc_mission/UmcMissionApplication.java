package umc.study.umc_mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * UMC Mission 애플리케이션의 시작점(Entry Point).
 *
 * <p>Spring Boot 애플리케이션은 반드시 하나의 메인 클래스가 필요하며,
 * 이 클래스의 {@link #main(String[])} 메서드가 프로그램의 시작점이 된다.
 * Java 프로그램은 항상 {@code public static void main} 메서드에서 실행이 시작된다.</p>
 *
 * <p>{@code @SpringBootApplication} 어노테이션 하나로 아래 3가지가 동시에 적용된다:</p>
 * <ul>
 *   <li>{@code @SpringBootConfiguration} — 이 클래스가 Spring Boot 설정 클래스임을 선언</li>
 *   <li>{@code @EnableAutoConfiguration} — 클래스패스(라이브러리)를 분석하여
 *       필요한 설정을 자동으로 적용 (예: DB 라이브러리가 있으면 DataSource를 자동 설정)</li>
 *   <li>{@code @ComponentScan} — 이 클래스가 속한 패키지({@code umc.study.umc_mission})와
 *       그 하위 패키지를 스캔하여 {@code @Component}, {@code @Service}, {@code @Repository},
 *       {@code @Controller} 등이 붙은 클래스를 자동으로 Spring 빈(Bean)으로 등록</li>
 * </ul>
 */
// @SpringBootApplication — 위에서 설명한 3가지 어노테이션을 하나로 합친 편의 어노테이션.
// Spring Boot 프로젝트의 메인 클래스에 반드시 붙여야 한다.
@SpringBootApplication
public class UmcMissionApplication {

    /**
     * 애플리케이션의 시작점.
     * JVM(자바 가상 머신)이 이 메서드를 가장 먼저 호출한다.
     *
     * <p>{@code SpringApplication.run()}이 호출되면:</p>
     * <ol>
     *   <li>Spring 컨테이너(ApplicationContext)가 생성된다.</li>
     *   <li>컴포넌트 스캔을 통해 모든 빈(Bean)이 등록된다.</li>
     *   <li>내장 톰캣(Tomcat) 서버가 시작되어 HTTP 요청을 받을 준비를 한다.</li>
     * </ol>
     *
     * @param args 커맨드라인 인자 (예: {@code --server.port=9090}으로 포트를 변경할 수 있다)
     */
    public static void main(String[] args) {
        // SpringApplication.run() — Spring Boot 애플리케이션을 실행하는 핵심 메서드.
        // 첫 번째 인자: 메인 설정 클래스 (이 클래스 자체)
        // 두 번째 인자: 커맨드라인에서 전달받은 인자들
        SpringApplication.run(UmcMissionApplication.class, args);
    }
}
