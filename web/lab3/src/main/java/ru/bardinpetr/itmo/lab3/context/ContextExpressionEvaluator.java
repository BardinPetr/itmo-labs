// Generated by delombok at Thu May 09 13:42:06 MSK 2024
package ru.bardinpetr.itmo.lab3.context;

import jakarta.el.ELException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.Optional;

@ApplicationScoped
public class ContextExpressionEvaluator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContextExpressionEvaluator.class);

    public <T> Optional<T> evaluateExpression(Class<T> targetClass, String expression) {
        var context = FacesContext.getCurrentInstance();
        try {
            var res = context.getApplication().evaluateExpressionGet(context, "#{" + expression + "}", targetClass);
            return Optional.ofNullable(res);
        } catch (ELException ex) {
            log.error("Bean search by expression \'{}\' failed: {}", expression, ex.toString());
        }
        return Optional.empty();
    }
}
