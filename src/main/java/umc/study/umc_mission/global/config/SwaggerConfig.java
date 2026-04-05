package umc.study.umc_mission.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger(OpenAPI) 문서 자동 생성을 위한 설정 클래스.
 *
 * <p>Swagger란? — REST API의 명세(어떤 URL로 어떤 파라미터를 보내면 어떤 응답이 오는지)를
 * 자동으로 문서화해 주는 도구이다. 서버를 실행한 뒤 브라우저에서
 * {@code http://localhost:8080/swagger-ui/index.html}에 접속하면
 * API 목록을 시각적으로 확인하고, 직접 테스트해 볼 수 있다.</p>
 *
 * <p>이 클래스에서는 API 문서의 제목, 설명, 버전 정보와 함께
 * JWT 인증 방식을 Swagger UI에서 사용할 수 있도록 설정한다.</p>
 *
 * @see <a href="https://springdoc.org/">SpringDoc OpenAPI 공식 문서</a>
 */
// @Configuration — 이 클래스가 Spring의 설정(Configuration) 클래스임을 선언한다.
// Spring이 이 클래스 안의 @Bean 메서드들을 읽어서 빈(Bean) 객체를 생성한다.
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 객체를 생성하여 Spring 컨테이너에 빈(Bean)으로 등록한다.
     * 이 빈이 등록되면 Swagger UI가 이 설정을 읽어 API 문서를 자동으로 만들어 준다.
     *
     * @return 설정이 완료된 OpenAPI 객체
     */
    // @Bean — 이 메서드가 반환하는 객체를 Spring이 관리하는 빈(Bean)으로 등록한다.
    // 다른 곳에서 OpenAPI 타입을 주입(Inject)받으면 이 메서드가 만든 객체가 전달된다.
    @Bean
    public OpenAPI swagger() {
        // API 문서의 기본 정보(제목, 설명, 버전)를 설정한다.
        Info info = new Info()
                .title("UMC Mission")              // API 문서 상단에 표시될 제목
                .description("UMC 10기 미션 API")   // API에 대한 간략한 설명
                .version("0.0.1");                  // API 버전 정보

        // JWT 인증 스키마 이름을 정의한다.
        // Swagger UI에서 "Authorize" 버튼을 클릭했을 때 이 이름이 표시된다.
        String securityScheme = "JWT TOKEN";

        // SecurityRequirement — API 호출 시 위에서 정의한 인증 방식이 필요하다는 것을 명시한다.
        // 이 설정이 있으면 Swagger UI의 각 API 옆에 자물쇠 아이콘이 표시된다.
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityScheme);

        // Components — 인증 스키마의 구체적인 설정을 정의한다.
        // HTTP 방식의 Bearer 토큰(JWT)을 사용하겠다는 의미이다.
        Components components = new Components()
                .addSecuritySchemes(securityScheme, new SecurityScheme()
                        .name(securityScheme)
                        .type(SecurityScheme.Type.HTTP)  // HTTP 인증 방식 사용
                        .scheme("Bearer")                // "Bearer" 스키마 (Authorization: Bearer <토큰>)
                        .bearerFormat("JWT"));           // 토큰 형식이 JWT임을 명시

        // 위에서 만든 설정들을 모두 합쳐서 OpenAPI 객체를 완성하여 반환한다.
        return new OpenAPI()
                .info(info)                                       // API 기본 정보 설정
                .addServersItem(new Server().url("/"))             // 서버 기본 URL을 "/" (현재 호스트)로 설정
                .addSecurityItem(securityRequirement)              // 전역 인증 요구사항 추가
                .components(components);                          // 인증 스키마 컴포넌트 추가
    }
}
