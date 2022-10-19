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
@SessionAttributes("pageTemplates")
@Controller
public class FreemarkerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerController.class);

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public ModelAndView welcomePage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;

    }

    @RequestMapping("/redirect_any")
    public ModelAndView redirect_any(@RequestParam("view") String redirView) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("secret","test1234");
        modelAndView.setViewName(redirView);
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage(@ModelAttribute("pageTemplates") Map<Integer, PageTemplate> pageTemplates) {

        ModelAndView model = new ModelAndView();

        model.addObject("pageTemplates",pageTemplates);
        model.setViewName("admin");

        return model;
    }

    @RequestMapping(value = "/admin/edit_page/{idTemplate}", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView adminEditPageTemplate(
            @PathVariable int idTemplate,
            @ModelAttribute PageTemplate newTemplate,
            @ModelAttribute("pageTemplates") Map<Integer, PageTemplate> pageTemplates,
            HttpServletRequest request,
            @RequestParam(required=false , value = "action") String action) {

        ModelAndView model = new ModelAndView();

        if("GET".equals(request.getMethod())) {

            PageTemplate pageTemplate = pageTemplates.get(idTemplate);
            model.addObject("templateForm",pageTemplate);

        }
        else if("POST".equals(request.getMethod()) && action.equals("test")) {

            model.addObject("preview", previewPageTemplate(newTemplate.getTemplateCode(),request));
            model.addObject("templateForm", newTemplate);
        }
        else if("POST".equals(request.getMethod()) && action.equals("save")) {
            LOGGER.info("Saving " + newTemplate.getTitle() + "...");
            pageTemplates.put(idTemplate, newTemplate);

            return new ModelAndView("redirect:" + "/admin");
        }

        model.setViewName("edit_page");
        return model;
    }


    @ModelAttribute("pageTemplates")
    public Object getTemplates() {
        return PageTemplateFixture.loadTemplates();
    }


    private String previewPageTemplate(String templateContent,HttpServletRequest request) {
        Configuration cfg = new Configuration();
        //cfg.setLogTemplateExceptions(false);

        //Unsafe
        //cfg.setNewBuiltinClassResolver(new SecureTemplateClassResolver(cfg));
        cfg.setTemplateExceptionHandler(new TemplateExceptionHandler() {
            @Override
            public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
                System.out.println("ERROR:"+te.getMessage());
                throw te;
            }
        });

        //Template template = cfg.getTemplate(templatePath);
        //Template template = cfg.getTemplate("template1.ftl");

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringLoader);

        stringLoader.putTemplate("tpl", templateContent);

        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", "guest");
        data.put("slogan", "Welcome to the World Wild Web!");
        data.put("message", "Hello World!");
        data.put("req", request);

        boolean testBlocked = false;
        boolean displayOutputTpl = true;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Template template = cfg.getTemplate("tpl");

            Writer writer = new OutputStreamWriter(output);

            template.process(data, writer);
            writer.flush();
        }
        catch (TemplateException e) {
            testBlocked = true;
            return "Template blocked: "+e.getMessage();
        }
        catch (Exception e) {
            return "Error: "+e.getMessage();
        }

        //System.out.println("Template filter: "+(testBlocked?"BLOCKED":"EXECUTE"));

        return new String(output.toByteArray());
    }

}

