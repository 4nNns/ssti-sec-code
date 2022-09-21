package com.ha0l.sstiseccode.controller;

import com.ha0l.sstiseccode.model.PageTemplate;
import com.ha0l.sstiseccode.model.PageTemplateFixture;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ha0l
 * @date 2022-09-08
 */

@Controller("/vel")
public class VelocityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityController.class);

    /**
     * SSTI of Java velocity. The latest Velocity version still has this problem.
     * Fix method: Avoid to use Velocity.evaluate method.
     * http://localhost:8790/vel/velocityVuln?payload=%23set($e=%22e%22);$e.getClass().forName(%22java.lang.Runtime%22).getMethod(%22getRuntime%22,null).invoke(null,null).exec(%22open%20-a%20Calculator%22)
     * @param payload
     */
    @GetMapping("velocityVuln")
    public void velocityVuln(String payload) {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put("author", "ha0l");
        context.put("address", "Beijing");
        context.put("tel", "13000000000");
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "vuln", payload);
    }

}
