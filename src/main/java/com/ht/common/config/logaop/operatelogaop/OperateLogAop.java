package com.ht.common.config.logaop.operatelogaop;


import com.ht.common.service.ISystemOperateLogService;
import com.ht.common.shiro.util.UserInfoUtil;
import com.ht.common.entity.SystemOperateLog;
import com.ht.common.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @ClassName LogCollectAop
 * @Author yakun.shi
 * @Date 2019/6/26 8:55
 * @Version 1.0
 **/
@Aspect
@Component
public class OperateLogAop {

    private static Logger logger = LoggerFactory.getLogger(OperateLogAop.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISystemOperateLogService operateLogService;

    /**
     * 定义有一个切入点，范围为web包下的类
     */
    @Pointcut("@annotation(com.ht.common.config.logaop.operatelogaop.OperateLogger)")
    public void logCollect() {
    }

    /**
     * 检查参数是否为空
     */
    @AfterReturning("logCollect()")
    public void doAfterReturning(JoinPoint pjp) throws Throwable {
        logger.info("开始日志");
        MethodSignature signature = ((MethodSignature) pjp.getSignature());
        //得到拦截的方法
        Method method = signature.getMethod();
        OperateLogger annotation = method.getAnnotation(OperateLogger.class);
        StringBuilder value = new StringBuilder(annotation.value());
        OperateType operateType = annotation.type();
        SystemOperateLog operateLog = new SystemOperateLog();
        operateLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
        operateLog.setIpAddress(IPUtils.getIp(request));
        operateLog.setLoginName(UserInfoUtil.getLoginUser());
        int type = 0;
        if (operateType.equals(OperateType.add)) {
            type = 1;
        } else if (operateType.equals(OperateType.delete)) {
            type = 2;
            Object[] args = pjp.getArgs();
            for (int k = 0; k < args.length; k++) {
                Object arg = args[k];
                // 获取对象类型
                String typeName = arg.getClass().getTypeName();
                //1 判断是否是基础类型
                if ("java.lang.String".equals(typeName)) {
                   value.append("，删除id为:").append(arg).append("的数据");
                }
            }
        } else if(operateType.equals(OperateType.update)){
            Object[] args = pjp.getArgs();
            for (int k = 0; k < args.length; k++) {
                Class<?> arg = args[k].getClass();
                // 获取对象类型
                Field idField = arg.getDeclaredField("id");
                //1 判断是否是基础类型
                if (idField!=null) {
                    idField.setAccessible(true);
                    String id = (String) idField.get(args[k]);
                    value.append("，修改id为: ").append(id).append("的数据");
                }
            }
            type = 3;
        }
        else {
            throw new Exception("日志注解出现错误,注解type值不再规定范围内");
        }
        operateLog.setValue(value.toString());
        operateLog.setOperateType(type);
        logger.info("拦截系统日志内容:", operateLog);
        //处理操作日志，增删改查
        operateLogService.save(operateLog);

    }


}
