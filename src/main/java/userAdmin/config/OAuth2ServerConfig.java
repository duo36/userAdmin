//package userAdmin.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import userAdmin.jwt.MyAuthenticationManager;
//import userAdmin.jwt.MyTokenEnhancer;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
//
//
//    private static final String RESOURCE_ID = "server1";
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//
//        //配置兩個用戶端,一個用於password認證一個用於client認證
//        clients.inMemory().
//                withClient("client_1")
//                .resourceIds(RESOURCE_ID)
//                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("select")
//                .authorities("client")
//                .secret("123456")
//                .and().
//                withClient("client_2")
//                .resourceIds(RESOURCE_ID)
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("select")
//                .authorities("client")
//                .secret("123456")
//                .and()
//                .withClient("web")
//                .redirectUris("http://www.google.com.tw")
//                .secret("123456")
//                .authorizedGrantTypes("implicit")
//                .scopes("account", "account.readonly", "role", "role.readonly")
//                .resourceIds("friend", "common", "user")
//                .accessTokenValiditySeconds(3600);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),accessTokenConverter()));
//
//                endpoints.tokenStore(jwtTokenStore())
//                        .tokenEnhancer(tokenEnhancerChain)
//                        .authenticationManager(authenticationManager())
//                        .userDetailsService(userDetailsService)
//                        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer.passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//
//    }
//
//    @Bean
//    public TokenEnhancer tokenEnhancer() {
//        return new MyTokenEnhancer();
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("123");
//        return converter;
//    }
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(jwtTokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        defaultTokenServices.setAccessTokenValiditySeconds(300);
//        defaultTokenServices.setRefreshTokenValiditySeconds(3000);
//
//        return defaultTokenServices;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//       return new MyAuthenticationManager();
//    }
//}
