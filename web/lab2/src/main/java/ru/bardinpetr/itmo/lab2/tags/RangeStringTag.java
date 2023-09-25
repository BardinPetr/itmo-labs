package ru.bardinpetr.itmo.lab2.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import lombok.Setter;

import java.io.IOException;


public class RangeStringTag extends SimpleTagSupport {
    @Setter
    private Double[] range;
    @Setter
    private Boolean[] inclusive;

    @Override
    public void doTag() throws JspException, IOException {
        if (range == null || inclusive == null)
            return;
        var writer = getJspContext().getOut();
        writer.print(inclusive[0] ? "[" : "(");
        writer.print(range[0]);
        writer.print(", ");
        writer.print(range[1]);
        writer.print(inclusive[1] ? "]" : ")");
    }
}
