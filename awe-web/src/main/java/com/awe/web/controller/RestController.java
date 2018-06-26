package com.awe.web.controller;

import com.awe.core.inspection.MethodDescriptor;
import com.awe.core.throwable.GeneralException;
import com.awe.web.core.RestResponse;
import com.awe.web.properties.WebProperties;
import com.awe.web.service.FileService;
import com.awe.web.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;


/**
 * description 请求分发
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@Controller
public class RestController {

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);
    // requestBody
    private static final String REQUEST_BODY_NAME = "request_body_param";

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private GenericConversionService conversionService;

    @Autowired
    private WebProperties webProperties;

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RestResponse upload(MultipartFile file, HttpServletRequest request) {
        String md5 = request.getParameter("md5");
        int chunk = Integer.parseInt(Objects.isNull(request.getParameter("chunk"))? "0" : request.getParameter("chunk"));
        int chunks = Integer.parseInt(Objects.isNull(request.getParameter("chunks"))? "0" : request.getParameter("chunks"));;
        // 文件大小
        Long size = Long.valueOf(request.getParameter("size"));

        fileService.upload(file, md5, chunks, chunk, size);
        return RestResponse.ok("上传成功");
    }

    /**
     * POST 请求处理
     *
     * @param request     HttpServletRequest
     * @param requestBody requestBody
     * @return restResponse
     */
    @ResponseBody
    @RequestMapping(value = "${awt.web.restfulPrefix}/**", method = RequestMethod.POST)
    public RestResponse exec(HttpServletRequest request, @RequestBody(required = false) String requestBody) {
        String uri = request.getRequestURI();
        uri = uri.substring(contextPath.length() + webProperties.getRestfulPrefix().length());
        int endIndex = uri.lastIndexOf('.') > -1 ? uri.lastIndexOf('.') : uri.length();
        String[] params = uri.substring(uri.startsWith("/") ? 1 : 0, endIndex).split("/");
        // service method 匹配请求路径 默认两次路径
        if (params.length != 2) {
            return RestResponse.error(new GeneralException("请求的路径不正确"));
        }

        String beanName = params[0];
        String service = params[1];

        if (!StringUtils.isEmpty(requestBody)) {
            request.setAttribute(REQUEST_BODY_NAME, requestBody);
        }
        return execute(beanName, service, request);
    }

    /**
     * 通用页面调整请求
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/view/**", method = RequestMethod.GET)
    public ModelAndView exec(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int lastIndex = uri.lastIndexOf('.');
        String view;
        if (lastIndex > 0)
            view = uri.substring(contextPath.length() + 6, lastIndex);
        else
            view = uri.substring(contextPath.length() + 6);
        return new ModelAndView(view);
    }

    private RestResponse execute(String beanName, String service, HttpServletRequest request) {
        if (StringUtils.isEmpty(beanName) || StringUtils.isEmpty(service)) {
            return RestResponse.error(new GeneralException("未完全指定请求的资源"));
        }

        logger.info("Invoke ModuleName: {}, ServiceName: {}", beanName, service);
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException ignore) {
            logger.error("Do not support UTF-8 encoding");
        }


        Object bean = SpringUtils.getBean(beanName);

        MethodDescriptor method = selectMethod(beanName, service, request);

        if (Objects.isNull(method)) {
            logger.error("{}.{}方法不存在", beanName, service);
            return RestResponse.error(new GeneralException("匹配的方法不存在"));
        }
        Object result = ReflectionUtils.invokeMethod(method.getMethod(), bean, method.getArgs());

        return RestResponse.ok(result);
    }

    /**
     * 选举方法
     *
     * @param beanName
     * @param service
     * @param request
     * @return
     */
    private MethodDescriptor selectMethod(String beanName, String service, HttpServletRequest request) {
        ConcurrentHashMap<String, List<MethodDescriptor>> services = MethodDescriptor.SERVICES_DESCRIPTOR.get(beanName);
        if (Objects.isNull(services)) {
            logger.info("请求的实例{}不存在", beanName);
            throw new GeneralException("请求资源不存在");
        }
        List<MethodDescriptor> methodDescriptors = services.get(service);
        if (Objects.isNull(methodDescriptors)) {
            logger.info("请求的方法{}不存在", service);
            throw new GeneralException("请求资源不存在");
        }

        // 匹配参数
        methodDescriptors.forEach(methodDescriptor -> methodDescriptor.setArgs(resolveArgs(methodDescriptor.getMethod(), request)));

        BinaryOperator<MethodDescriptor> matchMethod = (x, y) -> {
            Set xs = new HashSet(Arrays.asList(Objects.nonNull(x.getArgs()) ? x.getArgs() : new Object[]{}));
            Set ys = new HashSet(Arrays.asList(Objects.nonNull(y.getArgs()) ? y.getArgs() : new Object[]{}));
            MethodDescriptor methodDescriptor = x;
            if (xs.size() < ys.size()) {
                methodDescriptor = y;
            }
            return methodDescriptor;
        };

        // 选举方法
        return methodDescriptors.stream()
                .filter(method -> {
                    Object[] args = method.getArgs();
                    if (Objects.nonNull(args)) {
                        return !Arrays.asList(args).contains(null);
                    } else {
                        return true;
                    }
                }).reduce(matchMethod)
                .orElse(methodDescriptors.stream().reduce(matchMethod).orElse(null));
    }

    /**
     * 方法参数
     *
     * @param method  方法名称
     * @param request request
     * @return 参数值
     */
    private Object[] resolveArgs(Method method, HttpServletRequest request) {

        // 获得参数名称
        Class<?>[] parameterTypes = method.getParameterTypes();

        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] argsNames = parameterNameDiscoverer.getParameterNames(method);

        // 方法参数
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            try {
                if (parameterTypes[i].isPrimitive()) {
                    Assert.notNull(request.getParameter(argsNames[i]), "参数不存在");
                }
            } catch (Exception ignore) {
                continue;
            }

            args[i] = resolveArg(parameterTypes[i], argsNames[i], request);
        }
        return args;
    }

    private Object resolveArg(Class<?> parameter, String parameterName, HttpServletRequest request) {
        // 方法参数接收单个文件
        if (MultipartFile.class.isAssignableFrom(parameter)) {
            if (!(request instanceof MultipartRequest)) {
                return null;
            }
            return ((MultipartRequest) request).getFile(parameterName);
        }
        // 基本类型参数
        return extractArg(parameter, parameterName, request);
    }

    /**
     * 基本类型参数提取
     *
     * @param parameterType 参数类型
     * @param parameterName 参数名称
     * @param request       请求
     * @return 匹配参数
     */
    private Object extractArg(Class<?> parameterType, String parameterName, HttpServletRequest request) {
        Object value = request.getParameter(parameterName);
        // 基本类型
        if (parameterType.isPrimitive() && Objects.isNull(value)) {
            throw new IllegalArgumentException("A null value cannot be assigned to a primitive type");
        }
        value = convert(value, parameterType);
        return value;
    }


    /**
     * 参数类型转换
     *
     * @param source     源数据
     * @param targetType 目标类型
     * @return 目标类型数据
     */
    private <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }
}
