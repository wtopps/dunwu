package io.github.dunwu.quickstart.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 退出成功处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Component
public class DunwuLogoutSuccessHandler implements LogoutSuccessHandler {

	private final ObjectMapper objectMapper;

	public DunwuLogoutSuccessHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		BaseResult baseResult = ResultUtil.successBaseResult();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.getWriter().write(objectMapper.writeValueAsString(baseResult));
	}

}