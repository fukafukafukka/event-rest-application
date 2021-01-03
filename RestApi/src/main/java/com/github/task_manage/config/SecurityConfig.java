package com.github.task_manage.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.github.task_manage.security.SimpleAccessDeniedHandler;
import com.github.task_manage.security.SimpleAuthenticationEntryPoint;
import com.github.task_manage.security.SimpleAuthenticationFailureHandler;
import com.github.task_manage.security.SimpleAuthenticationSuccessHandler;

/**
 * セキュリティ設定クラス.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//パスワードエンコーダーのBean定義
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);        // CORSリクエストでcookie情報の取得を許可するか
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));   // CORSリクエストを許可するドメイン
        configuration.setAllowedHeaders(Arrays.asList(  // CORSリクエストで受信を許可するヘッダー情報(以下は例です)
                "Access-Control-Allow-Headers",//左記の意味は、クライアント側からヘッダの送信を許可するまでで、次の意味は持たない。→response.addHeader("Access-Control-Allow-Headers", "*");これをセットさせるには、コントローラー側でセットする必要がある。
                "Access-Control-Allow-Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Cache-Control",
                "Content-Type",
                "Accept-Language",
                "x-xsrf-token"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  // CORSリクエストを許可するHTTPメソッド

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // CORSリクエストを許可するURLの形式(特に決まりがなければ「/**」でもOK)

        return source;
    }

    // データソース
    @Autowired
    private DataSource dataSource;

    // ユーザーIDとパスワードを取得するSQL文。これもtrueの値がなんなのか質問すること。
    private static final String USER_SQL = "SELECT"
            + "    mail_address,"
            + "    password,"
            + "    'true'"
            + " FROM"
            + "    user"
            + " WHERE"
            + "    mail_address = ?";

    // ユーザーのロールを取得するSQL文。user_id取る必要があるのはなぜなのか質問すること。
    private static final String ROLE_SQL = "SELECT"
            + "    user_id,"
            + "    user_role"
            + " FROM"
            + "    user"
            + " WHERE"
            + "    mail_address = ?";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	// 直リンクの禁止＆ログイン不要ページの設定
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") //「/admin」配下のパス全て、ROLE_ADMIN権限がないとアクセスできない。
                .antMatchers("/user/delete/**").hasRole("ADMIN") //「/delete/」配下のパス全て、ROLE_ADMIN権限がないとアクセスできない。先にアクセス拒否したい方を書く。直感と逆。
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER") //上記の「delete」以外のパス全て、ROLE_ADMINかROLE_USER権限を持つユーザーであればアクセスできる。
                .antMatchers("/task/**").hasAnyRole("ADMIN","USER") //「/task/」配下のパス全て、ROLE_ADMINかROLE_USER権限を持つユーザーであればアクセスできる。
                .antMatchers("/prelogin").permitAll() //「/prelogin」は直リンクOK
                .anyRequest().authenticated(); //それ以外は直リンク禁止

        // 認証・認可失敗の処理
        http
        	.exceptionHandling()
        		.authenticationEntryPoint(authenticationEntryPoint()) // 未認証ユーザーのAPIアクセス対処。
        		.accessDeniedHandler(accessDeniedHandler()); // 未認可ユーザーのAPIアクセス対処。

        //ログイン処理の実装
        http
            .formLogin()
                .loginProcessingUrl("/login").permitAll() //ログイン処理のパス
                //.loginPage("/login") //ログインページの指定。今回はRESTなAPIなので不使用。
                //.failureUrl("/login?error") //ログイン失敗時の遷移先。今回はRESTなAPIなので不使用。
                	.usernameParameter("mailAddress") //ログインページのメールアドレス
                	.passwordParameter("password") //ログインページのパスワード
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());
                //.defaultSuccessUrl("/home", true); //ログイン成功後の遷移先。今回はRESTなAPIなので不使用。

        // ログアウト処理
        http
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler());
                //.logoutSuccessUrl("/login"); //今回はRESTなAPI作成なので不要。

        // cross-origin-resource-sharingの処理
        http
        	.cors()
        		.configurationSource(this.corsFilter());

        http
        	.csrf()
//        		.disable();
        		.csrfTokenRepository(new CookieCsrfTokenRepository());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // ログイン処理時のユーザー情報を、DBから取得する
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(USER_SQL) //ユーザーの取得
            .passwordEncoder(passwordEncoder()) //パスワードの復号
            .authoritiesByUsernameQuery(ROLE_SQL); //ロールの取得

    }

    AuthenticationEntryPoint authenticationEntryPoint() {
        return new SimpleAuthenticationEntryPoint();
    }

    AccessDeniedHandler accessDeniedHandler() {
        return new SimpleAccessDeniedHandler();
    }

    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleAuthenticationSuccessHandler();
    }

    AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleAuthenticationFailureHandler();
    }

    LogoutSuccessHandler logoutSuccessHandler() {
    	return new HttpStatusReturningLogoutSuccessHandler();
    }

}
