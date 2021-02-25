package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Test Filter.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {
    /**
     * Test req.getRequestURI() contains /signin.
     * @throws IOException exception
     * @throws ServletException exception
     */
    @Test
    public void trydoFilter1() throws IOException, ServletException {
        AuthFilter filter = new AuthFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(req.getRequestURI()).thenReturn("/signin");
        filter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    /**
     * Test req.getRequestURI() not contains /signin.
     * @throws IOException exception
     * @throws ServletException exception
     */
    @Test
    public void trydoFilter2() throws IOException, ServletException {
        AuthFilter filter = new AuthFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getRequestURI()).thenReturn("");

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn(null);

        filter.doFilter(req, resp, filterChain);
        verify(req, times(1)).getSession();
    }
    /**
     * Test session login not null.
     * @throws IOException exception
     * @throws ServletException exception
     */
    @Test
    public void trydoFilter3() throws IOException, ServletException {
        AuthFilter filter = new AuthFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);
        FilterConfig filterConfig = mock(FilterConfig.class);

        when(req.getRequestURI()).thenReturn("");

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn(1);

        filter.doFilter(req, resp, filterChain);
        filter.init(filterConfig);
        filter.destroy();
        verify(filterChain, times(1)).doFilter(req, resp);
    }
}