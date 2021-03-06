package com.card.alumni.security.filter;

import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;
import com.card.alumni.security.entity.JwtUser;
import com.card.alumni.security.entity.OnlineUser;
import com.card.alumni.security.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@SuppressWarnings({"unchecked", "all"})
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {


    @Value("${jwt.online}")
    private String onlineKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;

    public JwtAuthorizationTokenFilter(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = jwtTokenUtil.getToken(request);
        OnlineUser onlineUser = null;
        try {
            onlineUser = (OnlineUser) redisTemplate.opsForValue().get(onlineKey + authToken);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        }
        if (onlineUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            JwtUser userDetails = (JwtUser) this.userDetailsService.loadUserByUsername(onlineUser.getUserName());
            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserContext.setCurrentUser(convert(userDetails));
            }
        }

        if (onlineUser != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            // 重新设置token超时时间
            redisTemplate.expire(onlineKey + authToken, expiration, TimeUnit.MILLISECONDS);
        }
        chain.doFilter(request, response);
    }

    private UserContext convert(JwtUser userDetails) {
        User user = new User();
        user.setId(userDetails.getId());
        user.setPhone(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setPhotoImg(userDetails.getAvatar());
        user.setCreateTime(userDetails.getCreateTime());
        user.setPwdLastResetTime(userDetails.getLastPasswordResetDate());

        UserContext userContext = new UserContext();
        userContext.setUser(user);
        return userContext;
    }
}
