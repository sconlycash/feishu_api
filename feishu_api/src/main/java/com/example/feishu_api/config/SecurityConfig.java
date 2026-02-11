package com.example.feishu_api.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 解决跨域（如果前端调用，必须加）
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // 测试阶段允许所有源，生产按需配置
        configuration.addAllowedMethod("*"); // 允许所有HTTP方法（GET/POST等）
        configuration.addAllowedHeader("*"); // 允许所有请求头
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 1. 彻底关闭CSRF（POST请求必须关）
                .csrf().disable()
                // 2. 开启跨域（解决跨域导致的403）
                .cors().configurationSource(corsConfigurationSource()).and()
                // 3. 禁用session（无状态接口推荐）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 4. 关闭默认的HTTP Basic认证
                .httpBasic().disable()
                // 5. 关闭默认的表单登录
                .formLogin().disable()
                // 6. 关闭注销功能
                .logout().disable()
                // 7. 配置权限规则
                .authorizeRequests()
                // 精准放行你的3个接口（区分请求方法，更严谨）
                .antMatchers(HttpMethod.POST, "/user/login", "/user/register").permitAll()
                .antMatchers(HttpMethod.GET, "/user/info").permitAll()
                // 其他接口如果不需要认证，也可以改成permitAll()
                .anyRequest().permitAll(); // 测试阶段临时放行所有，排查问题

        // 关闭缓存控制头（避免浏览器缓存导致的问题）
        http.headers().cacheControl().disable();
    }
}